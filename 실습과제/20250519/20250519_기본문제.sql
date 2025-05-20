-- tabledb 데이터베이스를 생성하세요.
-- 만일, 이미 존재한다면 삭제하세요.
-- DB 제거
DROP DATABASE IF EXISTS tabledb;

-- DB 생성
CREATE DATABASE tabledb;

USE tabledb;

-- 다음 컬럼을 가지는 usertbl 테이블을 만드세요.
DROP TABLE IF EXISTS usertbl;

CREATE TABLE usertbl(
    userID      CHAR(8) NOT NULL PRIMARY KEY,
    name        VARCHAR(10) NOT NULL,
    birthYear   INT NOT NULL,
    addr        CHAR(2) NOT NULL,
    mobile1     CHAR(3) NULL,
    mobile2     CHAR(8) NULL,
    height      SMALLINT NULL,
    mDate       DATE NULL
);


-- 다음 컬럼을 가지는 buytbl 테이블을 만드세요.
DROP TABLE IF EXISTS buytbl;

CREATE TABLE buytbl(
    num         INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    userid      CHAR(8) NOT NULL,
    prodName    CHAR(6) NOT NULL,
    groupName   CHAR(4) NULL,
    price       INT NOT NULL,
    amount      SMALLINT NOT NULL,
    FOREIGN KEY(userid) REFERENCES usertbl(userID)
);

-- 회원 테이블에 다음 데이터를 입력하세요.
-- 순서: userID, name, birthyear, addr, mobile1, mobile2, height, mDate
-- 'LSG', '이승기', 1987, '서울', '011', '1111111', 182, '2008-8-8'
-- 'KBS', '김범수', 1979, '경남', '011', '2222222', 173, '2012-4-4'
-- 'KKH', '김경호', 1971, '전남', '019', '3333333', 177, '2007-7-7'
INSERT INTO usertbl VALUES('LSG', '이승기', 1987, '서울', '011', '1111111', 182, '2008-8-8');
INSERT INTO usertbl VALUES('KBS', '김범수', 1979, '경남', '011', '2222222', 173, '2012-4-4');
INSERT INTO usertbl VALUES('KKH', '김경호', 1971, '전남', '019', '3333333', 177, '2007-7-7');


-- 구매 테이블에 다음 데이터를 입력하세요.
-- 에러가 난다면, 왜 발생하는지 설명하세요.
-- 순서:userid, prodName, groupName, price, amount
-- 'KBS', '운동화', NULL, 30, 2
-- 'KBS', '노트북', '전자', 1000, 1
-- 'JYP', '모니터', '전자', 200, 1

-- 구매 테이블 샘플 데이터
INSERT INTO buytbl VALUES(NULL, 'KBS', '운동화', NULL, 30, 2);
INSERT INTO buytbl VALUES(NULL, 'KBS', '노트북', '전자', 1000, 1);

-- 오류 발생 : userid 컬럼이 참조하는 usertbl테이블의 userID 컬럼에 'JYP'가 없음
# INSERT INTO buytbl VALUES(NULL, 'JYP', '모니터', '전자', 200, 1);


-- 다음 컬럼을 가지는 usertbl을 정의하세요.
-- 기존 usertbl이 존재하는 경우 삭제함
-- 기본키는 테이블 레벨에서 정의하고 제약조건명을 함께 지정함
-- usertbl 테이블 생성 + userID 컬럼 PK 지정
DROP TABLE IF EXISTS buytbl;
DROP TABLE IF EXISTS usertbl;

CREATE TABLE usertbl (
    userID CHAR(8) NOT NULL,
    name VARCHAR(10) NOT NULL,
    birthYear INT NOT NULL,
    CONSTRAINT PRIMARY KEY PK_userTBL_userID(userID)
);

-- 다음 컬럼을 가지는 prodTbl을 정의하세요.
-- 기존 prodTbl이 존재하는 경우 삭제함
DROP TABLE IF EXISTS prodtbl;

-- prodTbl 테이블 생성 + prodCode, prodID 복합PK 설정
-- 복합키로 설정된 컬럼의 값이 모두 같다면 중복!
CREATE TABLE prodTbl (
    prodCode  CHAR(3) NOT NULL,
    prodID    CHAR(4) NOT NULL,
    prodDate  DATETIME NOT NULL,
    prodCur   CHAR(10) NULL,

    CONSTRAINT PK_prodTbl_proCode_prodID
        PRIMARY KEY (prodCode, prodID)
);



-- usertbl과 buytbl을 바탕으로 다음 결과가 나오는 뷰를 정의하세요.
DROP TABLE IF EXISTS buytbl;
DROP TABLE IF EXISTS usertbl;

CREATE TABLE usertbl(
                        userID      CHAR(8) NOT NULL PRIMARY KEY,
                        name        VARCHAR(10) NOT NULL,
                        birthYear   INT NOT NULL,
                        addr        CHAR(2) NOT NULL,
                        mobile1     CHAR(3) NULL,
                        mobile2     CHAR(8) NULL,
                        height      SMALLINT NULL,
                        mDate       DATE NULL
);

-- usertbl 샘플 데이터
INSERT INTO usertbl VALUES('LSG', '이승기', 1987, '서울', '011', '1111111', 182, '2008-8-8');
INSERT INTO usertbl VALUES('KBS', '김범수', 1979, '경남', '011', '2222222', 173, '2012-4-4');
INSERT INTO usertbl VALUES('KKH', '김경호', 1971, '전남', '019', '3333333', 177, '2007-7-7');

-- buytbl 생성
CREATE TABLE buyTBL(
                       num INT AUTO_INCREMENT NOT NULL PRIMARY KEY ,
                       userID  CHAR(8) NOT NULL,
                       prodName CHAR(6) NOT NULL,
                       FOREIGN KEY(userID) REFERENCES userTBL(userID)
                           ON DELETE CASCADE
                           ON UPDATE CASCADE
);

-- buytbl 샘플 데이터
INSERT INTO buytbl VALUES(NULL, 'KBS', '운동화');
INSERT INTO buytbl VALUES(NULL, 'KBS', '노트북');
INSERT INTO buytbl VALUES(NULL, 'KBS', '청바지');


DROP VIEW IF EXISTS v_userbuytbl;

CREATE VIEW v_userbuytbl AS
    SELECT U.userid, U.name, B.prodName, U.addr, CONCAT(U.mobile1, U.mobile2) AS '연락처'
    FROM usertbl U
        INNER JOIN buytbl B ON U.userid = B.userid;


-- 위에서 정의한 뷰에서 userid가 '김범수'인 데이터만 출력하세요.
SELECT * FROM v_userbuytbl WHERE name = '김범수';