
CREATE TABLE ktservice_drill (
  id CHAR(32) PRIMARY KEY,
  created TIMESTAMP NOT NULL,
  modified TIMESTAMP NOT NULL,
  deleted BOOLEAN NOT NULL,
  name VARCHAR(255) NOT NULL
);