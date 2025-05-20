USE sqldb;
-- sqldb 데이터베이스에 다음과 같은 컬럼을 가지는 테이블 tbl1을 생성하고,
-- 자동 생성된 인덱스 목록을 확인하세요.
-- 컬럼 목록
-- - a INT PRIMARY KEY
-- - b INT
-- - c INT

-- Primary Key 설정 시 자동으로 클러스터형 인덱스 생성
CREATE TABLE tbl1 (
    a INT PRIMARY KEY,-- 이 열에 클러스터형 인덱스 자동 생성
    b INT,
    c INT
);

SHOW INDEX FROM tbl1;

-- sqldb 데이터베이스에 다음과 같은 컬럼을 가지는 테이블 tbl2를 생성하고,
-- 자동 생성된 인덱스 목록을 확인하세요.
-- 컬럼 목록
-- - a INT PRIMARY KEY
-- - b INT UNIQUE
-- - c INT UNIQUE
-- - d INT

CREATE TABLE tbl2 (
    a INT PRIMARY KEY, -- 클러스터형 인덱스
    b INT UNIQUE, -- 보조 인덱스
    c INT UNIQUE, -- 보조 인덱스
    d INT
);

SHOW INDEX FROM tbl2;

-- sqldb 데이터베이스에 다음과 같은 컬럼을 가지는 테이블 tbl3를 생성하고,
-- 자동 생성된 인덱스 목록을 확인하세요.
-- 컬럼 목록
-- - a INT UNIQUE,
-- - b INT UNIQUE,
-- - c INT UNIQUE,
-- - d INT

CREATE TABLE tbl3 (
    a INT UNIQUE,-- 보조 인덱스
    b INT UNIQUE,-- 보조 인덱스
    c INT UNIQUE,-- 보조 인덱스
    d INT
);

SHOW INDEX FROM tbl3;

-- sqldb 데이터베이스에 다음과 같은 컬럼을 가지는 테이블 tbl4를 생성하고,
-- 자동 생성된 인덱스 목록을 확인하세요.
-- 컬럼 목록
-- - a INT UNIQUE NOT NULL,
-- - b INT UNIQUE,
-- - c INT UNIQUE,
-- - d INT

CREATE TABLE tbl4 (
    a INT UNIQUE NOT NULL,-- 클러스터형 인덱스로 생성
    b INT UNIQUE,
    c INT UNIQUE,
    d INT
);

SHOW INDEX FROM tbl4;

-- sqldb 데이터베이스에 다음과 같은 컬럼을 가지는 테이블 tbl5를 생성하고,
-- 자동 생성된 인덱스 목록을 확인하세요.
-- 컬럼 목록
-- - a INT UNIQUE NOT NULL,
-- - b INT UNIQUE,
-- - c INT UNIQUE,
-- - d INT PRIMARY KEY

CREATE TABLE tbl5 (
    a INT UNIQUE NOT NULL,
    b INT UNIQUE,
    c INT UNIQUE,
    d INT PRIMARY KEY
);

SHOW INDEX FROM tbl5;


-- testdb에 다음 컬럼 목록을 가지는 usertbl을 만드세요.
-- 컬럼 목록
-- - userID CHAR(8) NOT NULL PRIMARY KEY
-- - name VARCHAR(10) NOT NULL
-- - birthYear INT NOT NULL
-- - addr NCHAR(2) NOT NULL
DROP DATABASE IF EXISTS  testdb;
CREATE DATABASE testdb;
USE testdb;

DROP TABLE IF EXISTS tbl5;

CREATE TABLE tbl5 (
    userID CHAR(8) NOT NULL PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    birthYear INT NOT NULL,
    addr NCHAR(2) NOT NULL
);

-- 아래 데이터를 추가하고, 클러스터형 인덱스의 테이블 내용을 확인하세요.
-- - 'LSG', '이승기', 1987, '서울'
-- - 'KBS', '김범수', 1979, '경남'
-- - 'KKH', '김경호', 1971, '전남'
-- - 'JYP', '조용필', 1950, '경기'
-- - 'S나', '성시경', 1979, '서울'

INSERT INTO tbl5 (userID, name, birthYear, addr)
VALUES ('LSG', '이승기', 1987, '서울'),
       ('KBS', '김범수', 1979, '경남'),
       ('KKH', '김경호', 1971, '전남'),
       ('JYP', '조용필', 1950, '경기'),
       ('S나', '성시경', 1979, '서울');

SHOW INDEX FROM tbl5;


-- ALTER를 사용하여 usertbl에서 PRIMARY KEY 제약 조건을 제거하고,
-- name 컬럼에 pk_name이라는 제약 조건명으로 기본키를 설정하세요.
CREATE DATABASE IF NOT EXISTS testdb;

USE testdb;

DROP TABLE IF EXISTS usertbl;

CREATE TABLE usertbl(
    userID CHAR(8) NOT NULL PRIMARY KEY,
    name VARCHAR(10) NOT NULL,
    birthYear INT NOT NULL,
    addr NCHAR(2) NOT NULL
);

INSERT INTO usertbl VALUES('LSG', '이승기', 1987, '서울');
INSERT INTO usertbl VALUES('KBS', '김범수', 1979, '경남');
INSERT INTO usertbl VALUES('KKH', '김경호', 1971, '전남');
INSERT INTO usertbl VALUES('JYP', '조용필', 1950, '경기');
INSERT INTO usertbl VALUES('SSK', '성시경', 1979, '서울');

SELECT * FROM usertbl;

ALTER TABLE usertbl DROP PRIMARY KEY;
ALTER TABLE usertbl ADD CONSTRAINT pk_name PRIMARY KEY(name);


-- usertbl의 내용을 출력하여, 새로운 클러스터형 인덱스를 확인하세요
SELECT * FROM usertbl;
