SELECT * FROM (
    SELECT ip, count(*) as log_count FROM Logs
    WHERE log_date > '2017-01-01.13:00:00'
    AND log_date < '2017-01-02.13:00:00'
    GROUP BY ip
) AS ip_count
WHERE log_count > 100;