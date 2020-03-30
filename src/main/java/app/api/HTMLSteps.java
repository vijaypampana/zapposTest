package app.api;

import app.common.Context;

public class HTMLSteps {

    private Context context;

    public HTMLSteps() {
        if(context == null) {
            context = Context.getInstance();
        }
    }
}
