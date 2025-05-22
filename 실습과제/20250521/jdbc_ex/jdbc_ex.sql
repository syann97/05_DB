/* root 계정*/
CREATE DATABASE jdbc_ex;

-- scoula계정에 scoula_db 모든 권한 부여
GRANT ALL PRIVILEGES ON jdbc_ex.* TO 'scoula'@'%';

-- 권한 적용
FLUSH PRIVILEGES;

use jdbc_ex;
DROP TABLE IF EXISTS USERS;
CREATE TABLE USERS (
                       ID VARCHAR(12) NOT NULL PRIMARY KEY,
                       PASSWORD VARCHAR(12) NOT NULL,
                       NAME VARCHAR(30) NOT NULL,
                       ROLE VARCHAR(6) NOT NULL
);

INSERT INTO USERS(ID, PASSWORD, NAME, ROLE)
VALUES('guest', 'guest123', '방문자', 'USER');
INSERT INTO USERS(ID, PASSWORD, NAME, ROLE)
VALUES('admin', 'admin123', '관리자', 'ADMIN');
INSERT INTO USERS(ID, PASSWORD, NAME, ROLE)
VALUES('member', 'member123', '일반회원', 'USER');
SELECT * FROM USERS;