# DROP
DROP TABLE IF EXISTS tbl_travel_image;
DROP TABLE IF EXISTS tbl_travel;


CREATE TABLE tbl_travel
(
    no  INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    district    VARCHAR(50) NOT NULL,
    title    VARCHAR(512) NOT NULL,
    description TEXT,
    address VARCHAR(512),
    phone VARCHAR(256)
);


# tbl_travel_image 테이블
CREATE TABLE tbl_travel_image
(
    no INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    filename VARCHAR(512) NOT NULL,
    travel_no INT,
    CONSTRAINT FOREIGN KEY (travel_no) REFERENCES tbl_travel (no)
        ON DELETE CASCADE
);

SELECT * FROM tbl_travel_image;
SELECT count(*) FROM tbl_travel_image;


SELECT * FROM tbl_travel
ORDER BY district, title
LIMIT 20, 10;
