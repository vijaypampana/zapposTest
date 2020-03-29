package app.common;

import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class APIContext {

    private String sHost, sBasePath, sContentType, body;
    private Map<String, String> headers = new HashMap<>();
    private Map<String, String> params = new HashMap<>();
    private Map<String, String> formParams = new HashMap<>();
    private BasicAuthScheme basicAuthScheme = null;

    private RequestSpecBuilder requestSpecBuilder = null;
    private RequestSpecification requestSpecification = null;           //requestSpecBuilder.build() = requestSpecification()

    private Response response = null;
    private ValidatableResponse validatableResponse = null;

    public String getsHost() {
        return sHost;
    }

    public void setsHost(String sHost) {
        this.sHost = sHost;
    }

    public String getsBasePath() {
        return sBasePath;
    }

    public void setsBasePath(String sBasePath) {
        this.sBasePath = sBasePath;
    }

    public String getsContentType() {
        return sContentType;
    }

    public void setsContentType(String sContentType) {
        this.sContentType = sContentType;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void clearHeaders() {
        this.headers.clear();
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public void clearQueryParams() {
        this.params.clear();
    }

    public Map<String, String> getFormParams() {
        return formParams;
    }

    public void setFormParams(Map<String, String> formParams) {
        this.formParams = formParams;
    }

    public void clearFormParams() {
        this.formParams.clear();
    }

    public BasicAuthScheme getBasicAuthScheme() {
        return basicAuthScheme;
    }

    public void setBasicAuthScheme(BasicAuthScheme basicAuthScheme) {
        if(basicAuthScheme == null) {
            basicAuthScheme = new BasicAuthScheme();
        }
        this.basicAuthScheme = basicAuthScheme;
    }

    //Override Method for setBasicAuthScheme
    public void setBasicAuthScheme(String username, String password) {
        if(basicAuthScheme == null) {
            basicAuthScheme = new BasicAuthScheme();
        }
        this.basicAuthScheme.setUserName(username);
        this.basicAuthScheme.setPassword(password);
    }

    public RequestSpecBuilder getRequestSpecBuilder() {
        return requestSpecBuilder;
    }

    public void setRequestSpecBuilder(RequestSpecBuilder requestSpecBuilder) {
        this.requestSpecBuilder = requestSpecBuilder;
    }

    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }

    public void setRequestSpecification(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public ValidatableResponse getValidatableResponse() {
        return validatableResponse;
    }

    public void setValidatableResponse(ValidatableResponse validatableResponse) {
        this.validatableResponse = validatableResponse;
    }

    public void clearBody() {
        this.body = "";
    }

    public void clearContentType() {
        this.setsContentType(null);
    }
}
