package com.ef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_TIME;

/**
 * @author dmitry.mikhailovich@gmail.com
 */
@Entity
@Table(name = "Logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "log_date")
    private LocalDateTime logDate;

    private String ip;

    private String request;

    private int status;

    private String agent;

    public Log(String line) {
        String[] params = line.split("[|]", 5);
        if (params.length < 5) {
            throw new IllegalArgumentException("Unable to create log line. Expected log format: Date|IP|Request|Status|User Agent");
        }
        this.logDate = LocalDateTime.parse(params[0], logDateTimeFormatter());
        this.ip = params[1];
        this.request = ofQuotes(params[2]);
        this.status = Integer.parseInt(params[3]);
        this.agent = ofQuotes(params[4]);
    }

    private static String ofQuotes(String withQuotes) {
        if (withQuotes.startsWith("\"") && withQuotes.endsWith("\"")) {
            return withQuotes.substring(1, withQuotes.length() - 1);
        } else {
            return withQuotes;
        }
    }

    private static DateTimeFormatter logDateTimeFormatter() {
        return new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .append(ISO_LOCAL_DATE)
                .appendLiteral(' ')
                .append(ISO_LOCAL_TIME)
                .toFormatter();
    }

    public Log() {
        // ignored, for hibernate
    }

    public LocalDateTime getLogDate() {
        return logDate;
    }

    public String getIp() {
        return ip;
    }

    public String getRequest() {
        return request;
    }

    public int getStatus() {
        return status;
    }

    public String getAgent() {
        return agent;
    }

    public void setLogDate(LocalDateTime logDate) {
        this.logDate = logDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }
}
