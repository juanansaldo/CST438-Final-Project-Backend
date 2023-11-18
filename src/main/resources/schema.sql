CREATE TABLE user_table (
  userId INT AUTO_INCREMENT NOT NULL,
  username VARCHAR(255) UNIQUE,
  password VARCHAR(100),
  role VARCHAR(25),
  PRIMARY KEY(userId)
);

CREATE TABLE actors (
  actorId INT AUTO_INCREMENT NOT NULL,
  name VARCHAR(25),
  age INT,
  portrait VARCHAR(1000),
  about VARCHAR(1000),
  PRIMARY KEY(actorId)
);
