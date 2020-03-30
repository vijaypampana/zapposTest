package app.api;

import app.common.Context;
import app.common.Transform.TransformTextUsingYaml;
import app.common.Utils.DataUtils;
import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import groovy.json.JsonBuilder;
import groovy.json.JsonSlurper;
import groovy.util.Eval;
import groovy.xml.XmlSlurper;
import groovy.xml.slurpersupport.GPathResult;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CommonSteps {

    private Context context;

    public CommonSteps() {
        if(context == null) {
            context = Context.getInstance();
        }
    }

    @Given("^I set API Server as \"(.*)\"$")
    public void setHostUrl(String sHost) {
        context.setsHost(sHost);
    }

    @Given("^I set API Base Path as \"(.*)\"$")
    public void setBasePath(String sBasePath) {
        context.setsBasePath(sBasePath);
    }

    @Given("^I set API Basic Authentication using \"(.*)\" and \"(.*)\"$")
    public void setBasicAuth(String sUserName, String sPassword) {
        context.setBasicAuthScheme(sUserName, sPassword);
    }

    @Given("^I log API response Time$")
    public void getAPIResponseTime() {
        context.getReports().stepInfo("Response Time : " + context.getResponse().getTime());
    }

    @Given("^I verify API response status code is (\\d+)$")
    public void verifyAPIResponseCode(int i) {
        Assert.assertEquals(context.getResponse().getStatusCode(), i);
    }

    @Given("^I store API response as \"(.*)\"$")
    public void storeAPIResponse(String storeKey) {
        DataUtils.store(storeKey, context.getValidatableResponse());
    }

    @Given("^I make API GET call using URL \"(.*)\"$")
    public void getCall(@Transform(TransformTextUsingYaml.class) String sUrl) {
        Response resp = RestAssured.given().relaxedHTTPSValidation().spec(context.getRequestSpecification()).when().get(sUrl);
        context.getReports().stepInfo(sUrl);
        context.getReports().stepCode(resp.asString());
        context.setResponse(resp);
        resetSpec();
    }

    @Given("^I make API POST call using URL \"(.*)\"$")
    public void postCall(@Transform(TransformTextUsingYaml.class) String sUrl) {
        Response resp = RestAssured.given().relaxedHTTPSValidation().spec(context.getRequestSpecification()).when().post(sUrl);
        context.getReports().stepInfo(sUrl);
        context.getReports().stepCode(resp.asString());
        context.setResponse(resp);
        resetSpec();
    }

    @Given("^I make API PUT call using URL \"(.*)\"$")
    public void putCall(@Transform(TransformTextUsingYaml.class) String sUrl) {
        Response resp = RestAssured.given().relaxedHTTPSValidation().spec(context.getRequestSpecification()).when().put(sUrl);
        context.getReports().stepInfo(sUrl);
        context.getReports().stepCode(resp.asString());
        context.setResponse(resp);
        resetSpec();
    }

    @Given("^I make API DELETE call using URL \"(.*)\"$")
    public void deleteCall(@Transform(TransformTextUsingYaml.class) String sUrl) {
        Response resp = RestAssured.given().relaxedHTTPSValidation().spec(context.getRequestSpecification()).when().delete(sUrl);
        context.getReports().stepInfo(sUrl);
        context.getReports().stepCode(resp.asString());
        context.setResponse(resp);
        resetSpec();
    }

    @Given("^I make API POST call using \"(.*)\" and below table$")
    public void postCall(@Transform(TransformTextUsingYaml.class) String sUrl, DataTable table) {
        buildRequest(table);
        postCall(sUrl);
    }

    public void buildRequest(DataTable table) {
        String sValAt00 = table.topCells().get(0);
        String sValAt01 = table.topCells().get(1);
        String sContentType = "";
        Object oBody = null;

        if(sValAt00.equalsIgnoreCase("_REFFILE")) {
            File file = new File(getClass().getClassLoader().getResource("api/" + sValAt01).getFile());
            String resource = getResourceAsString(file);

            if(sValAt01.endsWith(".json")) {
                sContentType = "application/json";
                oBody = getModifiedJson(resource, table);
            } else if(sValAt01.endsWith(".xml")) {
                sContentType = "text/xml;charset=UTF-8";
                oBody = getModifiedXML(resource, table);
            } else {
                context.getReports().stepFail("_REFFILE is neither a json or XML");
            }
        } else {
            Map<String, Object> mapReturn = new HashMap<>();

            table.raw().forEach( row -> {
                String sKey = row.get(0);
                String sValue = row.get(1);

                if(row.size() == 3) {
                    mapReturn.put(sKey, JSONSteps.getJsonType(sValue, row.get(2)));
                } else {
                    switch (JSONSteps.getJsonNodeType(sValue)) {
                        case NULL:
                            mapReturn.put(sKey, null);
                            break;
                        case BOOLEAN:
                            mapReturn.put(sKey, Boolean.valueOf(sValue));
                            break;
                        case NUMBER:
                            try {
                                mapReturn.put(sKey, Long.valueOf(sValue));
                            } catch (NumberFormatException e) {
                                mapReturn.put(sKey, Double.valueOf(sValue));
                            }
                            break;
                        case STRING:
                            mapReturn.put(sKey, String.valueOf(sValue));
                            break;
                        case ARRAY:
                        case OBJECT:
                            //TO DO
                            break;
                    }
                }
            });
            sContentType = "application/json";
            oBody = mapReturn;
        }
        context.setsContentType(sContentType);
        context.setBody(oBody);
    }


    public String getResourceAsString(File file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file.getPath())));
        } catch (Exception e) {
            return null;
        }
    }

    private String getModifiedJson(String resource, DataTable table) {
        //Only _REFFILE file is provided, no modification details are provided
        if(table.raw().size() == 1) {
            return resource;
        } else {
            Boolean skip = true;
            JsonBuilder builder = new JsonBuilder(new JsonSlurper().parseText(resource));

            for (List<String> row : table.raw()) {
                if(skip) {
                    skip = false;
                } else {
                    String query = row.get(0);
                    String value = context.getData(row.get(1));

                    if(row.size() == 3) {
                        Eval.xy(builder, JSONSteps.getJsonType(value, row.get(2)), "x.content." + query + " = y");
                    } else {
                        Eval.xy(builder, value, "x.content." + query + " = y");
                    }
                }
            }
            context.getReports().stepCode(builder.toString());
            return builder.toString();
        }
    }

    private String getModifiedXML(String resource, DataTable table) {
        if(table.raw().size() == 1) {
            return resource;
        } else {
            Boolean skip = true;
            GPathResult builder = null;
            try {
                builder = new XmlSlurper().parseText(resource);
                for (List<String> row : table.raw()) {
                    String query = context.getData(row.get(0));
                    String value = context.getData(row.get(1));
                    if (skip) {
                        skip = false;
                    } else {
                        Eval.x(builder, "x" + query.substring(query.indexOf(".")) + " = '" + value + "'");
                    }
                }
            } catch (Exception e) {
                context.getReports().stepFail(e.getMessage());
            }
        }
        return null;
    }

    @Given("^I validate response matches \"(.*)\" schema$")
    public void validateJsonResponseSchema(String fileName) {
        try {
            context.getValidatableResponse().assertThat().body(matchesJsonSchemaInClasspath("api/" + fileName));
        } catch (Exception e) {
            context.getReports().stepFail(e.getMessage());
        }
    }

    //This method will reset the API values
    public void resetSpec() {
        context.clearHeaders();
        context.clearFormParams();
        context.clearQueryParams();
        context.clearBody();
        context.clearContentType();
        context.setBasicAuthScheme(null, null);
    }

}
