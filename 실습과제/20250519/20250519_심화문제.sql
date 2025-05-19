-- 다음 컬럼을 가지는 userTBL과 buyTBL을 정의하세요.
USE tabledb;

DROP TABLE IF EXISTS buyTBL, userTBL;

CREATE TABLE userTBL(
    userID    CHAR(8) NOT NULL PRIMARY KEY,
    name      VARCHAR(10) NOT NULL,
    birthYear  INT NOT NULL
);


-- buyTbl의 userID 컬럼에 FK 제약 조건 설정(userTBL의 userID 컬럼 값 참조)
CREATE TABLE buyTBL(
    num INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    userID  CHAR(8) NOT NULL,
    prodName CHAR(6) NOT NULL,
    FOREIGN KEY(userID) REFERENCES userTBL(userID)
);


-- 다음 조건을 만족하는 userTBL 테이블을 정의하세요.
DROP TABLE IF EXISTS buytbl, usertbl;

CREATE TABLE userTBL(
                        userID    CHAR(8) PRIMARY KEY,
                        name      VARCHAR(10) ,
                        birthYear  INT CHECK  (birthYear >= 1900 AND birthYear <= 2023),
                        mobile1    char(3) NULL,
                        CONSTRAINT CK_name CHECK ( name IS NOT NULL)
);

-- 다음 조건을 만족하는 userTBL 테이블을 정의하세요
DROP TABLE IF EXISTS userTBL;

CREATE TABLE userTBL (
                         userID      CHAR(8) NOT NULL PRIMARY KEY,
                         name        VARCHAR(10) NOT NULL,
                         birthYear   INT NOT NULL DEFAULT -1,
                         addr        CHAR(2) NOT NULL DEFAULT '서울',
                         mobile1     CHAR(3) NULL,
                         mobile2     CHAR(8) NULL,
                         height      SMALLINT NULL DEFAULT 170,
                         mDate       DATE NULL
);


-- 앞에서 만든 userTBL에 대해서 다음 조건을 처리하도록 수정하세요.
-- mobile1 컬럼을 삭제함
ALTER TABLE userTBL DROP COLUMN mobile1;

-- name 컬럼명을 uName으로 변경함
ALTER TABLE userTBL CHANGE name uName VARCHAR(100);

-- 기본키를 제거함
ALTER TABLE userTBL DROP PRIMARY KEY;


-- 모든 문제는 employees 데이터베이스에서 수행한다.
USE employees;
-- 다음 정보를 가지는 직원 정보를 출력하는 EMPLOYEES_INFO 뷰를 작성하세요
SELECT * FROM employees E
    RIGHT OUTER JOIN salaries S ON E.emp_no = S.emp_no;


-- EMPLOYEES_INFO 뷰에서 재직자의 현재 정보만 출력하세요.
SELECT * FROM employees E
    INNER JOIN salaries S ON E.emp_no = S.emp_no
WHERE S.to_date = '9999-01-01';


-- 다음 정보를 가지는 부서 정보를 출력하는 EMP_DEPT_INFO 뷰를 작성하세요
DROP VIEW IF EXISTS EMP_DEPT_INFO;

CREATE VIEW EMP_DEPT_INFO
AS
    SELECT * FROM dept_emp;


-- EMP_DEPT_INFO로 현재 재직자의 부서 정보를 출력하세요.
SELECT * FROM EMP_DEPT_INFO
WHERE to_date = '9999-01-01';