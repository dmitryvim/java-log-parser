package com.ef;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class LogTest {

    @Test
    public void testLineConstructor() throws Exception {
        // given
        String initialLine = "2017-01-01 00:00:11.763|192.168.234.82|\"GET / HTTP/1.1\"|200|\"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0\"";

        // when
        Log log = new Log(initialLine);

        // then
        assertEquals(LocalDateTime.of(2017, 1, 1, 0, 0, 11, 763000000), log.getTimestamp());
        assertEquals("192.168.234.82", log.getIp());
        assertEquals("GET / HTTP/1.1", log.getRequest());
        assertEquals(200, log.getStatus());
        assertEquals("swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0", log.getAgent());
    }
}