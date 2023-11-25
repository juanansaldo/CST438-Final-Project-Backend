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
  dob DATE,
  portrait VARCHAR(1000),
  about VARCHAR(1000),
  PRIMARY KEY(actorId)
);

CREATE TABLE UsersMoviesToWatch (
  entryId INT AUTO_INCREMENT NOT NULL,
  userId INT,
  movieTitle VARCHAR(255),
  movieOverview VARCHAR(1000),
  releaseDate DATE,
  posterPath VARCHAR(1000),
  PRIMARY KEY(entryId),
  FOREIGN KEY (userId) REFERENCES user_table(userId)
);
