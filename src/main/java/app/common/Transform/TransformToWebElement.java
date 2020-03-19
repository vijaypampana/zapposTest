package app.common.Transform;

import app.common.Context;
import cucumber.api.Transformer;
import org.openqa.selenium.WebElement;

public class TransformToWebElement extends Transformer<WebElement> {
    @Override
    public WebElement transform(String value) {
        return Context.getInstance().findElement(value);
    }
}
