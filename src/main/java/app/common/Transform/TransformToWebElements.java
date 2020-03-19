package app.common.Transform;

import app.common.Context;
import cucumber.api.Transformer;
import org.openqa.selenium.WebElement;

import java.util.List;

public class TransformToWebElements extends Transformer<List<WebElement>> {
    @Override
    public List<WebElement> transform(String value) {
        return Context.getInstance().findElements(value);
    }
}
