package ru.yandex.practicum.sleeptracker;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChronotypeAnalyzerTest {

    @Test
    void shouldDetectOwl() {
        List<SleepingSession> sessions = List.of(
                sessionCrossMidnight(1, 23, 30, 10)
        );

        ChronotypeAnalyzer analyzer = new ChronotypeAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals(Chronotype.OWL, result.getValue());
    }

    @Test
    void shouldReturnPigeonIfTie() {
        List<SleepingSession> sessions = List.of(
                // Жаворонок: до 22 и до 7
                sessionCrossMidnight(1, 21, 0, 6),

                // Сова: после 23 и после 9
                sessionCrossMidnight(2, 23, 30, 10)
        );

        ChronotypeAnalyzer analyzer = new ChronotypeAnalyzer();
        SleepAnalysisResult result = analyzer.apply(sessions);

        assertEquals(Chronotype.PIGEON, result.getValue());
    }

    private SleepingSession sessionCrossMidnight(
            int day,
            int startHour,
            int startMinute,
            int endHour
    ) {
        LocalDateTime start = LocalDateTime.of(2025, 10, day, startHour, startMinute);

        // Сон всегда заканчивается на следующий день
        LocalDateTime end = start.plusDays(1).withHour(endHour).withMinute(0);

        return new SleepingSession(start, end, SleepQuality.GOOD);
    }
}
