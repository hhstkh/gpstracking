DROP TABLE IF EXISTS gps;

CREATE TABLE gps (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  user_id INT NOT NULL,
  gps_file_name VARCHAR(250) NOT NULL,
  created_date timestamp
);

INSERT INTO gps (user_id, gps_file_name, created_date) VALUES
  (1, 'sample.gpx', '2020-07-26 19:00:00'),
  (2, 'sample.gpx', '2020-07-26 19:00:00')