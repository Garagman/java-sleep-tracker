package ru.yandex.practicum.sleeptracker;

import java.util.List;

public class TotalSessionsAnalyzer implements SleepAnalyzer {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {
        return new SleepAnalysisResult("Общее количество сессий сна", sessions.size());
    }
}
