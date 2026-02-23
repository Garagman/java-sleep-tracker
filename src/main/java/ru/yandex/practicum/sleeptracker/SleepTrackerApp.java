package ru.yandex.practicum.sleeptracker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class SleepTrackerApp {

    private static final String FILE_NAME = "sleep_log.txt";

    private static final String DATE_TIME_PATTERN = "dd.MM.yy HH:mm";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    private static final List<SleepAnalyzer> ANALYZERS = List.of(
            new TotalSessionsAnalyzer(),
            new MinDurationAnalyzer(),
            new MaxDurationAnalyzer(),
            new AvgDurationAnalyzer(),
            new BadQualityCountAnalyzer(),
            new InsomniaNightsAnalyzer(),
            new ChronotypeAnalyzer()
    );

    public static void main(String[] args) {

        try {
            List<SleepingSession> sessions = readSessionsFromResources(FILE_NAME);

            ANALYZERS.stream()
                    .map(analyzer -> analyzer.apply(sessions))
                    .forEach(SleepTrackerApp::printResult);

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private static List<SleepingSession> readSessionsFromResources(String fileName) throws IOException {

        InputStream inputStream = SleepTrackerApp.class
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IOException(String.format("Файл не найден: %s", fileName));
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        return reader.lines()
                .filter(line -> !line.isBlank())
                .map(SleepTrackerApp::parseLine)
                .collect(Collectors.toList());
    }

    private static SleepingSession parseLine(String line) {

        String[] parts = line.split(";");

        LocalDateTime start = LocalDateTime.parse(parts[0], FORMATTER);
        LocalDateTime end = LocalDateTime.parse(parts[1], FORMATTER);
        SleepQuality quality = SleepQuality.valueOf(parts[2]);

        return new SleepingSession(start, end, quality);
    }

    private static void printResult(SleepAnalysisResult result) {
        System.out.println(String.format("%s: %s",result.getDescription(), result.getValue()));
    }
}