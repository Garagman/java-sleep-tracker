package ru.yandex.practicum.sleeptracker;

import ru.yandex.practicum.sleeptracker.SleepingSession;

import java.util.List;
import java.util.function.Function;

public interface SleepAnalyzer extends Function<List<SleepingSession>, SleepAnalysisResult>{
}
