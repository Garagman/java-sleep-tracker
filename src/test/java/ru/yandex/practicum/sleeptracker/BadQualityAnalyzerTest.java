package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BadQualityAnalyzerTest {

    @Test
    void shouldCountBadSessions() {
        List<SleepingSession> sessions = List.of(
                session(1, SleepQuality.BAD),
                session(2, SleepQuality.GOOD),
                session(3, SleepQuality.BAD)
        );

        BadQualityCountAnalyzer analyzer = new BadQualityCountAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals(2L, result.getValue());
    }

    @Test
    void shouldReturnZeroIfNoBad() {
        List<SleepingSession> sessions = List.of(
                session(1, SleepQuality.GOOD)
        );

        BadQualityCountAnalyzer analyzer = new BadQualityCountAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals(0L, result.getValue());
    }

    private SleepingSession session(int day, SleepQuality quality) {
        LocalDateTime start = LocalDateTime.of(2025, 10, day, 22, 0);
        LocalDateTime end = start.plusDays(1).withHour(6);
        return new SleepingSession(start, end, quality);
    }
}
