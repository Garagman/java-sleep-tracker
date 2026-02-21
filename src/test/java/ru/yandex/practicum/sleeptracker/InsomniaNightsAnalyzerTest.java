package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InsomniaNightsAnalyzerTest {

    @Test
    void noInsomniaIfSleepTouchesNight() {
        List<SleepingSession> sessions = List.of(
                session(1, 23, 7)
        );

        InsomniaNightsAnalyzer analyzer = new InsomniaNightsAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals(0L, result.getValue());
    }

    @Test
    void insomniaIfDaySleep() {
        List<SleepingSession> sessions = List.of(
                sessionDay(1),
                session(2, 23, 7)
        );

        InsomniaNightsAnalyzer analyzer = new InsomniaNightsAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals(1L, result.getValue());
    }

    @Test
    void shouldCountMultipleInsomniaNights() {
        List<SleepingSession> sessions = List.of(
                session(1, 23, 7),
                session(3, 23, 7)
        );

        InsomniaNightsAnalyzer analyzer = new InsomniaNightsAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals(1L, result.getValue());
    }

    @Test
    void worksAcrossMonthBoundary() {
        List<SleepingSession> sessions = List.of(
                session(30, 23, 7)
        );

        InsomniaNightsAnalyzer analyzer = new InsomniaNightsAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals(0L, result.getValue());
    }

    private SleepingSession session(int day, int startHour, int endHour) {
        LocalDateTime start = LocalDateTime.of(2025, 10, day, startHour, 0);
        LocalDateTime end = start.plusDays(startHour > endHour ? 1 : 0).withHour(endHour);
        return new SleepingSession(start, end, SleepQuality.GOOD);
    }

    private SleepingSession sessionDay(int day) {
        LocalDateTime start = LocalDateTime.of(2025, 10, day, 10, 0);
        LocalDateTime end = start.withHour(12);
        return new SleepingSession(start, end, SleepQuality.GOOD);
    }
}