package com.ef;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class Args {
    private Map<String, String> values = new HashMap<>();

    public Args(String... args) {
        for (String arg : args) {
            parse(arg, this.values::put);
        }
    }

    private static void parse(String arg, BiConsumer<String, String> consumer) {
        if (arg.startsWith("--") && arg.contains("=")) {
            int eqIndex = arg.indexOf('=');
            consumer.accept(arg.substring(2, eqIndex), arg.substring(eqIndex + 1));
        } else {
            throw new IllegalArgumentException("Illegal argument format. Expected: --arg_name=arg_value");
        }
    }

    private static DateTimeFormatter argDateTimeFormatter() {
        return new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_DATE)
                .appendLiteral('.')
                .append(ISO_LOCAL_TIME)
                .toFormatter();
    }

    public LocalDateTime startDate() {
        return value("startDate")
                .map(date -> LocalDateTime.parse(date, argDateTimeFormatter()))
                .orElseThrow(() -> new IllegalArgumentException("Unable to find argument startDate."));
    }

    public Duration duration() {
        return value("duration")
                .map(String::toUpperCase)
                .map(Duration::valueOf)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find argument duration."));
    }

    public int threshold() {
        return value("threshold")
                .map(Integer::parseInt)
                .orElseThrow(() -> new IllegalArgumentException("Unable to find argument threshold."));
    }

    public File file() {
        return value("file")
                .map(File::new)
                .orElse(new File("access.log"));
    }

    private Optional<String> value(String key) {
        return Optional.of(key).map(this.values::get);
    }
}
