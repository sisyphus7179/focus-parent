package sisyphus.focus.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberUtils {

    public static double round(double d) {
        return round(d, 2, RoundingMode.HALF_UP);
    }

    public static double round(double d, int newScale, RoundingMode roundingMode) {
        BigDecimal bigDecimal = BigDecimal.valueOf(d);
        return bigDecimal.setScale(newScale, roundingMode).doubleValue();
    }

}
