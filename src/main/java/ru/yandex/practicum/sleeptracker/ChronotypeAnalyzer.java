package ru.yandex.practicum.sleeptracker;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ChronotypeAnalyzer implements SleepAnalyzer {

    @Override
    public SleepAnalysisResult apply(List<SleepingSession> sessions) {

        int owls = 0;
        int larks = 0;
        int pigeons = 0;

        for (SleepingSession session : sessions) {

            if (!isNightSession(session)) {
                continue;
            }

            LocalTime start = session.getStart().toLocalTime();
            LocalTime end = session.getEnd().toLocalTime();

            boolean isOwl = start.isAfter(LocalTime.of(23, 0)) && end.isAfter(LocalTime.of(9, 0));

            boolean isLark = start.isBefore(LocalTime.of(22, 0)) && end.isBefore(LocalTime.of(7, 0));

            if (isOwl) {
                owls++;
            } else if (isLark) {
                larks++;
            } else {
                pigeons++;
            }
        }

        Chronotype result;
        if (owls > larks && owls > pigeons) {
            result = Chronotype.OWL;
        } else if (larks > owls && larks > pigeons) {
            result = Chronotype.LARK;
        } else {
            result = Chronotype.PIGEON;
        }

        return new SleepAnalysisResult("Хронотип: ", result);
    }

    private boolean isNightSession(SleepingSession session) {

        LocalDateTime start = session.getStart();
        LocalDateTime end = session.getEnd();

        LocalDateTime nightStart = start.toLocalDate().atStartOfDay();
        LocalDateTime nightEnd = nightStart.plusHours(6);

        if (start.toLocalTime().isAfter(LocalTime.NOON)) {
            nightStart = start.toLocalDate().plusDays(1).atStartOfDay();
            nightEnd = nightStart.plusHours(6);
        }

        return start.isBefore(nightEnd) && end.isAfter(nightStart);
    }
}
