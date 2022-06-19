package sisyphus.focus.core.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.time.LocalDateTime.ofInstant;
import static java.time.format.DateTimeFormatter.ofPattern;

public class DateUtils {

    private static final Map<String, DateTimeFormatter> DATE_TIME_FORMATTERS = new HashMap<>();
    public static final String YYYY_MM_DD_NO_SEP = "yyMMdd";
    public static final String YYYY_MM_DD_SEP_DASH = "yyyy-MM-dd";
    public static final String HH_MM_SS_NO_SEP = "hhmmss";
    public static final String HH_MM_SS_SEP_COLON = "hh:mm:ss";

    static {
        DATE_TIME_FORMATTERS.put(YYYY_MM_DD_NO_SEP, ofPattern(YYYY_MM_DD_NO_SEP));
        DATE_TIME_FORMATTERS.put(YYYY_MM_DD_SEP_DASH, ofPattern(YYYY_MM_DD_SEP_DASH));
        DATE_TIME_FORMATTERS.put(HH_MM_SS_NO_SEP, ofPattern(HH_MM_SS_NO_SEP));
        DATE_TIME_FORMATTERS.put(HH_MM_SS_SEP_COLON, ofPattern(HH_MM_SS_SEP_COLON));
    }


    private DateUtils() {
    }

    public static String format(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DATE_TIME_FORMATTERS.get(pattern);
        if (Objects.isNull(dateTimeFormatter)) {
            dateTimeFormatter = ofPattern(pattern);
            DATE_TIME_FORMATTERS.put(pattern, dateTimeFormatter);
        }
        return dateTimeFormatter.format(localDateTime);
    }

    public static String format(Date date, String pattern) {
        return format(ofInstant(date.toInstant(), ZoneId.systemDefault()), pattern);
    }

}
