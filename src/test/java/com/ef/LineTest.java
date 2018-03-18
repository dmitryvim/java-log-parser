package com.ef;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class LineTest {

    @Test
    public void testLineConstructor() throws Exception {
        // given
        String initialLine = "2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"";

        // when
        Line line = new Line(initialLine);

        // then
        assertEquals(LocalDateTime.of(2017, 1, 1, 0, 0, 11, 763000000), line.getTimestamp());
        assertEquals("192.168.234.82", line.getIp());
        assertEquals("GET / HTTP/1.1", line.getRequest());
        assertEquals(200, line.getStatus());
        assertEquals("swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0", line.getAgent());
    }
}