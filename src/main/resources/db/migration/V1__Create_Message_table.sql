
/* Initialize data base */

CREATE TABLE Message(
 id int NOT NULL PRIMARY KEY,
 sender VARCHAR(255) NOT NULL,
 receiver VARCHAR(255) NOT NULL,
 subject VARCHAR(255),
 body VARCHAR(255),
 sent_date DATE
);
 