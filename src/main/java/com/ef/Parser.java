package com.ef;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

public class Parser {

    public static void main(String[] args) {
        Args arguments = new Args(args);
        Duration duration = arguments.duration();
        int threshold = arguments.threshold();
        LocalDateTime startDate = arguments.startDate();
        LocalDateTime endDate = arguments.startDate().plus(1, duration.units());
        try (LogReader reader = new LogReader(arguments.file())) {
            try (LogSaver saver = new LogSaver()) {
                reader.logStream()
                        .peek(saver::save)
                        .filter(log -> log.getLogDate().isAfter(startDate))
                        .filter(log -> log.getLogDate().isBefore(endDate))
                        .collect(Collectors.groupingBy(Log::getIp))
                        .entrySet().stream()
                        .filter(entry -> entry.getValue().size() > threshold)
                        .map(Map.Entry::getKey)
                        .forEach(System.out::println);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("Unexpected IO error occurred.", e);
        }
    }
}