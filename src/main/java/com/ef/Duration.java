package com.ef;

import java.time.temporal.ChronoUnit;

/**
 * @author dmitry.mikhaylovich@bostongene.com
 */
public enum Duration {
    DAILY(ChronoUnit.DAYS), HOURLY(ChronoUnit.HOURS);

    private final ChronoUnit units;

    Duration(ChronoUnit units) {
        this.units = units;
    }

    public ChronoUnit units() {
        return this.units;
    }
}
