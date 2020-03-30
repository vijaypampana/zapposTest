package app.api;

import app.common.Context;

public class HeadersAndParams {

    private Context context;

    public HeadersAndParams() {
        if (context == null) {
            context = Context.getInstance();

        }
    }
}
