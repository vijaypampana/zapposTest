package app.common.Transform;

import app.common.Context;
import cucumber.api.Transformer;

public class TransformTextUsingYaml extends Transformer<String> {
    @Override
    public String transform(String value) {
        return Context.getInstance().getData(value);
    }
}
