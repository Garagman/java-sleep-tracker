package ru.yandex.practicum.sleeptracker;

import java.util.Comparator;
import java.util.List;

public class MinDurationAnalyzer implements SleepAnalyzer {
    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        long minDuration = sessions.stream()
                .mapToLong(SleepingSession::getDurationMinutes)
                .min()
                .orElse(0);

        return new SleepAnalysisResult("Минимальая продолжительность сна (мин)", minDuration);
    }
}
