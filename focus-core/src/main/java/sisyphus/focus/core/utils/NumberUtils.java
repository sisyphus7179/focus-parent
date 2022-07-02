package sisyphus.focus.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {

    public static String roundToStr(double d) {
        return String.valueOf(round(d));
    }

    public static String roundToStr(double d, int newScale, RoundingMode roundingMode) {
        return String.valueOf(round(d, newScale, roundingMode));
    }

    public static String roundToPer(double d) {
        return String.join("", String.valueOf(round(d) * 100).split("\\.")[0], "%");
    }

    public static String roundToPer(double d, int newScale, RoundingMode roundingMode) {
        return String.join("", String.valueOf(round(d, newScale, roundingMode) * 100), "%");
    }

    public static double round(double d) {
        return round(d, 2, RoundingMode.HALF_UP);
    }

    public static double round(double d, int newScale, RoundingMode roundingMode) {
        BigDecimal bigDecimal = BigDecimal.valueOf(d);
        return bigDecimal.setScale(newScale, roundingMode).doubleValue();
    }

}
