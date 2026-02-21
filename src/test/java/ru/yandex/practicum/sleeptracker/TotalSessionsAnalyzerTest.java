package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TotalSessionsAnalyzerTest {

    @Test
    void shouldCountAllSessions() {
        List<SleepingSession> sessions = List.of(
                session(1, 22, 6, SleepQuality.GOOD),
                session(2, 23, 7, SleepQuality.GOOD)
        );

        TotalSessionsAnalyzer analyzer = new TotalSessionsAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals(2, result.getValue());
    }

    @Test
    void shouldReturnZeroForEmptyList() {
        TotalSessionsAnalyzer analyzer = new TotalSessionsAnalyzer();
        SleepAnalysisResult result = analyzer.apply(List.of());

        assertEquals(0, result.getValue());
    }

    private SleepingSession session(
            int day,
            int startHour,
            int endHour,
            SleepQuality quality
    ) {
        LocalDateTime start = LocalDateTime.of(2025, 10, day, startHour, 0);
        LocalDateTime end = startHour > endHour
                ? start.plusDays(1).withHour(endHour)
                : start.withHour(endHour);

        return new SleepingSession(start, end, quality);
    }
}
