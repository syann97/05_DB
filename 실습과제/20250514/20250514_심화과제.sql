-- 다음 결과가 나오도록 buytbl에 대한 SQL 문을 각각 작성하세요.
USE sqldb;

-- buytbl을 userID 기준 그룹화 -> userID와 userID의 총 구매 개수를 출력
SELECT userID AS '사용자 아이디', SUM(amount) AS '총 구매 개수'
FROM buytbl
GROUP BY userID;

-- buytbl을 userID 기준으로 그룹화 -> userID와 userID가 총 구매한 금액을 출력
SELECT userID AS '사용자 아이디', SUM(price * amount) AS '총 구매 금액'
FROM buytbl
GROUP BY userID;

-- 평균 구매 개수를 출력
SELECT AVG(amount) AS '평균 구매 개수' FROM buytbl;

-- userID 별 평균 구매 개수를 출력
SELECT AVG(amount) AS '평균 구매 개수' FROM buytbl GROUP BY userID;


-- 다음 결과가 나오도록 usertbl에 대한 SQL 문을 작성하세요
-- 가장 키가 크거나 가장 작은 사람의 이름과 키를 출력
SELECT name, height
FROM usertbl
WHERE height = (SELECT MAX(height) FROM usertbl)
   OR height = (SELECT MIN(height) FROM usertbl);


-- 휴대폰이 있는 사용자
SELECT count(*) AS '휴대폰이 있는 사용자'
FROM usertbl
WHERE mobile1 IS NOT NULL;

-- buytbl 테이블로 다음을 처리하세요
-- 사용자별 총 구매 금액을 출력하세요
SELECT userID AS '사용자', SUM(price * amount) AS '총 구매액'
FROM buytbl
GROUP BY userID;

-- 총 구매액이 1000 이상인 사용자만 출력하세요.
SELECT userID AS '사용자 아이디', SUM(price * amount) AS '총 구매 금액'
FROM buytbl
GROUP BY userID
HAVING SUM(price * amount) >= 1000;


-- world 데이터베이스에서 다음 질문을 처리하세요.
USE world;

-- city 테이블에서 국가코드가 'KOR' 인 도시들의 인구수 총합을 구하시오.
SELECT SUM(Population) FROM city WHERE CountryCode = 'KOR';

-- city 테이블에서 국가코드가 'KOR' 인 도시들의 인구수 중 최소값을 구하시오.
-- 단 결과를 나타내는 테이블의 필드는 "최소값"으로 표시하시오.
SELECT MIN(Population) AS '최소값' FROM city WHERE CountryCode = 'KOR';


-- city 테이블에서 국가코드가 'KOR' 인 도시들의 평균을 구하시오.
SELECT AVG(Population) FROM city WHERE CountryCode = 'KOR';

-- city 테이블에서 국가코드가 'KOR' 인 도시들의 인구수 중 최대값을 구하시오.
-- 단 결과를 나타내는 테이블의 필드는 "최대값"으로 표시하시오.
SELECT MAX(Population) AS '최대값' FROM city WHERE CountryCode = 'KOR';

-- country 테이블 각 레코드의 Name 칼럼의 글자 수를 표시하시오.
SELECT LENGTH(NAME) AS '글자 수' FROM country;

-- country 테이블의 나라명(Name 칼럼)을 앞 세글자만 대문자로 표시하시오.
SELECT UPPER(LEFT(NAME, 3)) FROM country;

-- country 테이블의 기대 수명(Life Expectancy)을 소수점 첫째자리에서 반올림해서 표시하시오.
SELECT ROUND(LifeExpectancy) FROM country;

-- 모든 문제는 employees 데이터베이스에서 수행한다.
USE employees;
-- employees db에서 각부서별 관리자를 출력하세요.(단, 현재직자만 출력한다.)
SELECT
    d.dept_name AS 부서명,
    e.first_name AS 이름,
    e.last_name AS 성,
    dm.from_date AS 시작일,
    dm.to_date AS 종료일
FROM dept_manager dm
    JOIN departments d ON dm.dept_no = d.dept_no
    JOIN employees e ON dm.emp_no = e.emp_no
WHERE dm.to_date = '9999-01-01';

-- 부서번호 d005부서의 핸재 관리자정보를 출력하세요.
SELECT
    dm.dept_no AS 부서번호,
    e.first_name AS 이름,
    e.last_name AS 성,
    dm.from_date AS 시작일,
    dm.to_date AS 종료일
FROM dept_manager dm
    JOIN employees e ON dm.emp_no = e.emp_no
WHERE dm.dept_no = 'd005' AND dm.to_date = '9999-01-01';


-- employees 테이블에서 페이지네이션으로 페이지를 추출하려고 한다.
-- 다음 조건하에서 8번페이지의 데이터를 출력하세요.
-- 입사일을 내림차순으로 정렬한다.
-- 한페이지당 20명의 정보를 출력한다.

SELECT * FROM employees
         ORDER BY hire_date DESC
         LIMIT 20 OFFSET 140;


-- employees db에서 재직자의 총수를 구하시오
-- 재직자의 to_date 값은 '9999-01-01'로 저장되어 있음
SELECT COUNT(*)
FROM salaries
WHERE to_date = '9999-01-01';

-- employees db에서 재직자의 평균 급여를 출력하시오
SELECT AVG(salary)
FROM salaries
WHERE to_date = '9999-01-01';

-- 재직자 전체 평균급여 보다 급여를 더 많이 받는 재직자를 출력하세요.
SELECT e.emp_no, e.first_name, e.last_name, s.salary
FROM employees e
    JOIN salaries s ON e.emp_no = s.emp_no
WHERE s.to_date = '9999-01-01'
  AND s.salary > (
  SELECT AVG(salary)
  FROM salaries
  WHERE to_date = '9999-01-01'
);


-- employees db에서 각부서별 재직자의 수를 구하시오
-- 부서 번호로 구분하고, 부서 번호로 오름차순 정렬하여 출력한다.
SELECT dept_no, COUNT(*) AS num_employees
FROM dept_emp
WHERE to_date = '9999-01-01'
GROUP BY dept_no
ORDER BY dept_no ASC;
