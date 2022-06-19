package sisyphus.focus.core.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DateUtils {

    private static final Map<String, DateTimeFormatter> DATE_TIME_FORMATTERS = new HashMap<>();

    private DateUtils() {
    }

    public static String format(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter dateTimeFormatter = DATE_TIME_FORMATTERS.get(pattern);
        if (Objects.isNull(dateTimeFormatter)) {
            dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
            DATE_TIME_FORMATTERS.put(pattern, dateTimeFormatter);
        }
        return dateTimeFormatter.format(localDateTime);
    }

    public static String format(Date date, String pattern) {
        return format(LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()), pattern);
    }

    public static void main(String[] args) {
        System.out.println(format(LocalDateTime.now(), "yyyyMMdd"));
        System.out.println(format(new Date(), "yyyyMMdd"));
    }

}
