package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DurationAnalyzersTest {

    @Test
    void shouldCalculateMinDuration() {
        List<SleepingSession> sessions = List.of(
                session(1, 22, 6),
                session(2, 23, 7),
                session(3, 23, 5)
        );

        MinDurationAnalyzer analyzer = new MinDurationAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals(360L, result.getValue());
    }

    @Test
    void shouldCalculateMaxDuration() {
        List<SleepingSession> sessions = List.of(
                session(1, 22, 6),
                session(2, 23, 8)
        );

        MaxDurationAnalyzer analyzer = new MaxDurationAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals(540L, result.getValue());
    }

    @Test
    void shouldCalculateAverageDuration() {
        List<SleepingSession> sessions = List.of(
                session(1, 22, 6),
                session(2, 22, 4)
        );

        AvgDurationAnalyzer analyzer = new AvgDurationAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals(420L, result.getValue());
    }

    private SleepingSession session(int day, int startHour, int endHour) {
        LocalDateTime start = LocalDateTime.of(2025, 10, day, startHour, 0);
        LocalDateTime end = start.plusDays(startHour > endHour ? 1 : 0).withHour(endHour);
        return new SleepingSession(start, end, SleepQuality.GOOD);
    }
}