package app.api;

import app.common.Context;
import app.common.Transform.TransformTextUsingYaml;
import app.common.Utils.DataUtils;
import cucumber.api.DataTable;
import cucumber.api.Transform;
import cucumber.api.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

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

        }
    }


    public String getResourceAsString(File file) {
        try {
            return new String(Files.readAllBytes(Paths.get(file.getPath())));
        } catch (Exception e) {
            return null;
        }
    }

    private String getModifiedJson(String resource, DataTable table) {
        return null;
    }

    private String getModifiedXML(String resource, DataTable table) {
        return null;
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
