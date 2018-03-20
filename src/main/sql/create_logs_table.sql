CREATE TABLE Logs (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    timespamp TIMESTAMP,
    ip VARCHAR(15),
    request VARCHAR(50),
    status INT,
    agent VARCHAR(255)
);
