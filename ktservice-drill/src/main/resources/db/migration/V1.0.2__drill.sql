
CREATE TABLE ktservice_drill (
  id VARCHAR(255) PRIMARY KEY,
  created TIMESTAMP NOT NULL,
  modified TIMESTAMP NOT NULL,
  deleted BOOLEAN NOT NULL,
  name VARCHAR(255) NOT NULL
);