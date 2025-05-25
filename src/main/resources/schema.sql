CREATE TABLE IF NOT EXISTS sensor_data (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       sensor_id INTEGER NOT NULL,
       face TEXT NOT NULL,
       timestamp INTEGER NOT NULL,
       temperature REAL NOT NULL
);
