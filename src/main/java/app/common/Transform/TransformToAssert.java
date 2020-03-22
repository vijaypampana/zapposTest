package app.common.Transform;

import cucumber.api.Transformer;

public class TransformToAssert extends Transformer<Boolean> {
    @Override
    public Boolean transform(String value) {
        return value.equalsIgnoreCase("Assert");
    }
}
