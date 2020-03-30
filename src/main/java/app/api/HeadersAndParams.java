package app.api;

import app.common.Context;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import org.apache.http.HttpHeaders;

public class HeadersAndParams {

    private Context context;

    public HeadersAndParams() {
        if (context == null) {
            context = Context.getInstance();
        }
    }

    //Header Steps
    @Given("^I set API Authorization header as \"(.*)\"$")
    public void setAuthorizationHeader(String sValue) {
        addAuthorizationHeader(sValue);
    }

    @Given("^I (?:add|update) API Authorization header as \"(.*)\"$")
    public void addAuthorizationHeader(String sValue) {
        addHeader(HttpHeaders.AUTHORIZATION, context.getData(sValue));
    }

    @Given("^I set API \"(.*)\" header as \"(.*)\"$")
    public void setHeader(String sHeader, String sValue) {
        addHeader(sHeader, sValue);
    }

    @Given("^I set API headers using below table$")
    public void setHeaders(DataTable table) {
        table.raw().forEach( row -> {
            addHeader(row.get(0), row.get(1));
        });
    }

    @Given("^I (?:add|update) API header \"(.*)\" as \"(.*)\"$")
    public void addHeader(String sHeader, String sValue) {
        context.getHeaders().put(sHeader, context.getData(sValue));
    }

    @Given("^I clear API Headers$")
    public void clearHeaders() {
        context.clearHeaders();
    }

    //Form Parameters
    @Given("^I (?:add|update) API form parameters \"(.*)\" as \"(.*)\"$")
    public void addFormParams(String sQuery, String sValue) {
        context.getFormParams().put(sQuery, context.getData(sValue));
    }

    @Given("^I (?:add/update) API form params using below table$")
    public void addFormParams(DataTable table) {
        table.raw().forEach(row -> {
            addFormParams(row.get(0), row.get(1));
        });
    }

    @Given("^I clear API form parameters$")
    public void clearFormParameters() {
        context.clearFormParams();
    }

    //Query Parameters
    @Given("^I (?:add|update) API query parameters \"(.*)\" as \"(.*)\"$")
    public void addQueryParams(String sQuery, String sValue) {
        context.getParams().put(sQuery, context.getData(sValue));
    }

    @Given("^I (?:add/update) API query params using below table$")
    public void addQueryParams(DataTable table) {
        table.raw().forEach(row -> {
            addQueryParams(row.get(0), row.get(1));
        });
    }

    @Given("^I clear API query parameters$")
    public void clearQueryParameters() {
        context.clearQueryParams();
    }




}
