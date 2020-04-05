package app.common.web;

import java.util.LinkedHashMap;

public enum DeepLink {

    REGISTRATION("/web/home/register", "Registration.Login"),
    RESULT_CART("/web/home/cart", "Result.Cart"),
    CHANGE_PASSWORD("https://www.zappos.com/passwordchange", "Registration.ChangePSW"),
    HOME("https://www.zappos.com", "Home");

    private String url;
    private String landingPage;

    DeepLink(String url, String landingPage) {
        this.url = url;
        this.landingPage = landingPage;
    }

    public String getUrl() {
        return url;
    }

    public String getLandingPage() {
        return landingPage;
    }

    public static String getEnum(String actualEnum) {
        actualEnum = actualEnum.toUpperCase();
        actualEnum = actualEnum.replace(" ", "_");      //replace "/" to "_" and "&" to "AND"
        return actualEnum;
    }

    //This step will add any other parameters other than userid and password
    public String getUrl(LinkedHashMap<String, String> parameters) {
        StringBuilder param = new StringBuilder();

        parameters.keySet().forEach( p1 -> {
            if(!(p1.equalsIgnoreCase("username") || p1.equalsIgnoreCase("password"))) {
                param.append(p1).append("=").append(parameters.get(p1)).append("&");
            }
        });

        //if url ends with "?" or "&" append the param string and remove the last & sign else add "?" and append param
        if(url.endsWith("?") || url.endsWith("&")) {
            return url + (param.toString().equals("") ? "" : param.substring(0, param.length() - 1));
        } else {
            return url + (param.toString().equals("") ? "" : "?" + param.substring(0, param.length() - 1));
        }
    }

}
