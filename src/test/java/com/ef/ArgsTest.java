package com.ef;

import org.junit.Test;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class ArgsTest {

    @Test
    public void testArgsConstructor() throws Exception {
        // when
        Args args = new Args(
                "--startDate=2017-01-01.13:00:00",
                "--duration=hourly",
                "--threshold=100",
                "--file=access.log"
        );

        // then
        assertEquals(Duration.HOURLY, args.duration());
        assertEquals(LocalDateTime.of(2017, 01, 01, 13, 0, 0, 0), args.startDate());
        assertEquals(100, args.threshold());
        assertEquals(new File("access.log"), args.file());
    }
}