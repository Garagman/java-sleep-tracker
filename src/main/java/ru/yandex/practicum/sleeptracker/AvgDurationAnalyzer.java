package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class AvgDurationAnalyzer implements SleepAnalyzer {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        double avgDuration = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .average()
                .orElse(0);

        return new SleepAnalysisResult("Средняя продолжительность сна (мин)", Math.round(avgDuration));
    }
}
