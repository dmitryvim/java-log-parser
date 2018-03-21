package com.ef;

import java.io.*;
import java.util.stream.Stream;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
public class LogReader implements Closeable {

    private final BufferedReader reader;

    public LogReader(File file) throws FileNotFoundException {
        this.reader = new BufferedReader(new FileReader(file));
    }

    public Stream<Log> logStream() {
        return this.reader.lines().map(Log::new);
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}
