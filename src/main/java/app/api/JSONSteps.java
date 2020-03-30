package app.api;

import app.common.Context;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;

public class JSONSteps {

    private Context context;

    public JSONSteps() {
        if(context == null) {
            context = Context.getInstance();
        }
    }

    public static Object getJsonType(String sValue, String sType) {
        try {
            switch (sType.toLowerCase()) {
                case "INT":
                case "INTEGER":
                    return Integer.parseInt(sValue);
                case "LONG":
                    return Long.parseLong(sValue);
                case "FLOAT":
                    return Float.parseFloat(sValue);
                case "DOUBLE":
                    return Double.parseDouble(sValue);
                case "BOOLEAN":
                    return Boolean.parseBoolean(sValue);
                case "NULL":
                    return null;
                default:
                    return sValue;
            }
        } catch (Exception e) {
            Context.getInstance().getReports().stepFail(e.getMessage());
            return null;
        }
    }

    //public static <T> T getJsonNodeType(String sValue) {
    public static JsonNodeType getJsonNodeType(String sValue) {
        if(sValue == null || sValue.equalsIgnoreCase("NULL")) {
            return  JsonNodeType.NULL;
        } else if((sValue.startsWith("\"") && sValue.endsWith("\"")) || (sValue.startsWith("\'") && sValue.endsWith("\'"))) {
            return  JsonNodeType.STRING;
        } else if ((BooleanUtils.toBooleanObject(sValue) == Boolean.TRUE) || (BooleanUtils.toBooleanObject(sValue) == Boolean.FALSE)) {
            return  JsonNodeType.BOOLEAN;
        } else if (StringUtils.isNumeric(sValue)) {
            return  JsonNodeType.NUMBER;
        } else {
            return  JsonNodeType.STRING;
        }
    }
}
