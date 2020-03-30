package app.api;

import app.common.Context;

public class XMLSteps {

    private Context context;

    public XMLSteps() {
        if(context == null) {
            context = Context.getInstance();
        }
    }
}
