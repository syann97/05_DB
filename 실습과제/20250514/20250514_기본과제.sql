-- 다음을 처리하는 쿼리문을 작성하세요
-- 현재 서버에 존재하는 데이터베이스 확인
SHOW DATABASES;

-- 현재 데이터베이스를 employees로 설정하기
USE employees;

-- employees 데이터베이스의 테이블 목록보기
SHOW TABLES;

-- employees 테이블의 열 목록 출력하기
SHOW COLUMNS FROM employees;


-- employees 테이블에서 다음 쿼리를 작성하세요.
--  titles 테이블의 데이터 출력하기
select * from titles;

-- employees 테이블에서 first_name 컬럼만 출력하기
select first_name from employees;

-- employees 테이블에서 first_name 컬럼, last_name 컬럼, gender 컬럼 출력하기
select first_name, last_name, gender from employees;


-- employees 테이블 출력시 다음과 같이 나오도록 쿼리를 작성하세요
select first_name as '이름', gender as '성별', hire_date as '회사 입사일' from employees;

-- 배포된 sqldb.sql 파일을 이용하여 sqldb 데이터베이스를 구축하세요.
DROP DATABASE IF EXISTS sqldb; -- 만약 sqldb가 존재하면 우선 삭제한다.
CREATE DATABASE sqldb;

USE sqldb;
CREATE TABLE usertbl -- 회원 테이블
( userID  	CHAR(8) NOT NULL PRIMARY KEY, -- 사용자 아이디(PK)
  name    	VARCHAR(10) NOT NULL, -- 이름
  birthYear   INT NOT NULL,  -- 출생년도
  addr	  	CHAR(2) NOT NULL, -- 지역(경기,서울,경남 식으로 2글자만입력)
  mobile1	CHAR(3), -- 휴대폰의 국번(011, 016, 017, 018, 019, 010 등)
  mobile2	CHAR(8), -- 휴대폰의 나머지 전화번호(하이픈제외)
  height    	SMALLINT,  -- 키
  mDate    	DATE  -- 회원 가입일
);
CREATE TABLE buytbl -- 회원 구매 테이블(Buy Table의 약자)
(  num 		INT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 순번(PK)
   userID  	CHAR(8) NOT NULL, -- 아이디(FK)
   prodName 	CHAR(6) NOT NULL, --  물품명
   groupName 	CHAR(4)  , -- 분류
   price     	INT  NOT NULL, -- 단가
   amount    	SMALLINT  NOT NULL, -- 수량
   FOREIGN KEY (userID) REFERENCES usertbl(userID)
);

INSERT INTO usertbl VALUES('LSG', '이승기', 1987, '서울', '011', '1111111', 182, '2008-8-8');
INSERT INTO usertbl VALUES('KBS', '김범수', 1979, '경남', '011', '2222222', 173, '2012-4-4');
INSERT INTO usertbl VALUES('KKH', '김경호', 1971, '전남', '019', '3333333', 177, '2007-7-7');
INSERT INTO usertbl VALUES('JYP', '조용필', 1950, '경기', '011', '4444444', 166, '2009-4-4');
INSERT INTO usertbl VALUES('SSK', '성시경', 1979, '서울', NULL  , NULL      , 186, '2013-12-12');
INSERT INTO usertbl VALUES('LJB', '임재범', 1963, '서울', '016', '6666666', 182, '2009-9-9');
INSERT INTO usertbl VALUES('YJS', '윤종신', 1969, '경남', NULL  , NULL      , 170, '2005-5-5');
INSERT INTO usertbl VALUES('EJW', '은지원', 1972, '경북', '011', '8888888', 174, '2014-3-3');
INSERT INTO usertbl VALUES('JKW', '조관우', 1965, '경기', '018', '9999999', 172, '2010-10-10');
INSERT INTO usertbl VALUES('BBK', '바비킴', 1973, '서울', '010', '0000000', 176, '2013-5-5');
INSERT INTO buytbl VALUES(NULL, 'KBS', '운동화', NULL   , 30,   2);
INSERT INTO buytbl VALUES(NULL, 'KBS', '노트북', '전자', 1000, 1);
INSERT INTO buytbl VALUES(NULL, 'JYP', '모니터', '전자', 200,  1);
INSERT INTO buytbl VALUES(NULL, 'BBK', '모니터', '전자', 200,  5);
INSERT INTO buytbl VALUES(NULL, 'KBS', '청바지', '의류', 50,   3);
INSERT INTO buytbl VALUES(NULL, 'BBK', '메모리', '전자', 80,  10);
INSERT INTO buytbl VALUES(NULL, 'SSK', '책'    , '서적', 15,   5);
INSERT INTO buytbl VALUES(NULL, 'EJW', '책'    , '서적', 15,   2);
INSERT INTO buytbl VALUES(NULL, 'EJW', '청바지', '의류', 50,   1);
INSERT INTO buytbl VALUES(NULL, 'BBK', '운동화', NULL   , 30,   2);
INSERT INTO buytbl VALUES(NULL, 'EJW', '책'    , '서적', 15,   1);
INSERT INTO buytbl VALUES(NULL, 'BBK', '운동화', NULL   , 30,   2);

SELECT * FROM usertbl;
SELECT * FROM buytbl;

-- usertbl 테이블에서 이름이 '김경호'인 행을 출력하세요.
USE sqldb;
SELECT * FROM usertbl WHERE name = '김경호';

-- usertbl 테이블에서 생년이 1970이상이고 키가 182이상인 데이터를 출력하세요.
SELECT * FROM usertbl WHERE (birthYear >= 1970 AND height >= 182);

-- usertbl 테이블에서 키가 180~183 범위인 데이터를 출력하세요.
SELECT * FROM usertbl WHERE height BETWEEN 180 AND 183;

-- usertbl 테이블에서 주소가 '경남' 또는 '전남' 또는 '경북'인 데이터를 출력하세요.
SELECT * FROM usertbl WHERE addr IN ('경남', '전남', '경북');

-- usertbl 테이블에서 이름이 '김'으로 시작하는 데이터를 출력하세요.
SELECT * FROM usertbl WHERE name LIKE '김%';

-- usertbl에서 김경호보다 큰 사람들의 이름과 키를 출력하세요.
-- 서브 쿼리를 이용하여 쿼리를 작성하세요.
SELECT name, height FROM usertbl WHERE height > (SELECT height FROM usertbl WHERE name = '김경호');


-- usertbl을 mdate의 오름차순으로 정렬하여 출력하세요.
SELECT * FROM usertbl ORDER BY mdate;

-- usertbl을 mdate의 내림차순으로 정렬하여 출력하세요.
SELECT * FROM usertbl ORDER BY mdate DESC;

-- usertbl을 height의 내림차순으로 정렬하고, 동률인 경우 name의 내림차순으로 정렬하여 출력하세요.
SELECT * FROM usertbl ORDER BY height DESC, name ASC;

-- usertbl의 주소지를 중복없이 오름차순으로 출력하세요
SELECT DISTINCT * FROM usertbl ORDER BY addr;

-- World 데이터베이스에서 다음 질문을 처리하세요.
USE world;

-- 국가코드가 'KOR' 인 도시를 찾아 인구수를 역순으로 표시하세요.
SELECT Population FROM city WHERE CountryCode = 'KOR';

-- city 테이블에서 국가코드와 인구 수를 출력하라.
-- 정렬은 국가 코드별로 오름차순으로, 동일한 코드(국가) 안에서는 인구수의 역순으로 표시하세요.
SELECT CountryCode, Population FROM city ORDER BY CountryCode, Population DESC;

-- city 테이블에서 국가코드가 'KOR' 인 도시의 수를 표시하세요.
SELECT count(*) FROM city WHERE CountryCode = 'KOR';


-- city 테이블에서 국가코드가 'KOR', 'CHN', 'JPN' 인 도시를 찾으세요.
SELECT * FROM city WHERE CountryCode IN ('KOR', 'CHN', 'JPN');

-- 국가코드가 'KOR' 이면서 인구가 100만 이상인 도시를 찾으세요.
SELECT * FROM city WHERE CountryCode = 'KOR' AND Population >= 1000000;


-- 국가코드가 'KOR' 인 도시중 인구수가 많은 순서로 상위 10개만 표시하세요.
SELECT * FROM CITY WHERE CountryCode = 'KOR' ORDER BY Population DESC LIMIT 10;

-- city 테이블에서 국가코드가 'KOR' 이고, 인구가 100만이상 500만이하인 도시를 찾으세요.
SELECT * FROM city WHERE CountryCode = 'KOR' AND Population BETWEEN 1000000 AND 5000000;