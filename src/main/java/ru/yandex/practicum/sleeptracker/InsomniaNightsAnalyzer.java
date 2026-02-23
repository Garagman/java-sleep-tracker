package ru.yandex.practicum.sleeptracker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class InsomniaNightsAnalyzer implements SleepAnalyzer {

    private static final LocalTime NIGHT_START = LocalTime.of(0, 0);
    private static final LocalTime NIGHT_END = LocalTime.of(6, 0);

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        if (sessions.isEmpty()) {
            return new SleepAnalysisResult("Количество бессонных ночей", 0L);
        }

        // Границы логирования
        LocalDate startDate = sessions.stream()
                .map(session -> session.getStart().toLocalDate())
                .min(LocalDate::compareTo)
                .orElseThrow();

        LocalDate endDate = sessions.stream()
                .map(session -> session.getEnd().toLocalDate())
                .max(LocalDate::compareTo)
                .orElseThrow();

        long insomniaCount = startDate.plusDays(1)
                .datesUntil(endDate.plusDays(1))
                .filter(date -> isInsomniaNight(date, sessions))
                .count();

        return new SleepAnalysisResult("Количество бессонных ночей", insomniaCount);
    }

    private boolean isInsomniaNight(LocalDate date, List<SleepingSession> sessions) {

        LocalDateTime nightStart = date.atTime(NIGHT_START);
        LocalDateTime nightEnd = date.atTime(NIGHT_END);

        boolean hasSleep = sessions.stream()
                .anyMatch(session ->
                        session.getStart().isBefore(nightEnd) &&
                                session.getEnd().isAfter(nightStart)
                );

        return !hasSleep;
    }
}