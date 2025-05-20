-- sqldb에서 usertbl 테이블에서 다음 내용들을 확인하세요
-- usertbl의 내용 확인
-- usertbl의 인덱스 목록 확인
-- usertbl의 데이터 크기와 인덱스 크기 확인
USE sqldb;
SELECT * FROM usertbl;
SHOW INDEX FROM usertbl;
SHOW TABLE STATUS LIKE 'usertbl';


-- usertbl의 addr 컬럼에 대해 idx_usertbl_addr 이름으로 인덱스를 만들고,
-- 인덱스 목록을 확인하세요.
CREATE INDEX idx_usertbl_addr ON usertbl(addr);

SHOW INDEX FROM usertbl;


-- usertbl의 상태를 출력하여 인덱스의 내용이 만들어졌는지 확인하고,
-- 내용이 없다면 실제 적용 되도록 한 후, 인덱스의 크기를 확인하세요.
SHOW TABLE STATUS LIKE 'usertbl';
ANALYZE TABLE usertbl;
SHOW TABLE STATUS LIKE 'usertbl';

-- usertbl에 대해 다음을 처리하세요.
-- birthday 컬럼에 대해 idx_usertbl_birthYear 이름의 인덱스를 만드세요
-- - 에러가 난다면 그 이유를 설명하세요
-- name 컬럼에 대해 idx_usertbl_name 이름의 인덱스를 만드세요
-- 생성된 인덱스의 목록을 확인하세요

-- 중복 값이 있어 인덱스 생성 에러
# CREATE UNIQUE INDEX idx_usertbl_birthYear ON usertbl(birthYear);

CREATE UNIQUE INDEX idx_usertbl_name ON usertbl(name);

SHOW INDEX FROM usertbl;


-- usertbl에 대해 다음을 처리하세요.
-- name 컬럼에 대한 보조 인덱스를 삭제하세요.
-- name과 birthYear 컬럼 조합으로 idx_usertbl_name_birthYear 이름의 인덱스를 생성하세요
-- 인덱스 목록을 확인하세요.
DROP INDEX idx_usertbl_name ON usertbl;

CREATE UNIQUE INDEX idx_usertbl_name_birthYear ON usertbl(name, birthYear);

SHOW INDEX FROM usertbl;


-- usertbl에서 앞에서 만든 인덱스를 삭제하세요.
SHOW INDEX FROM usertbl;

DROP INDEX idx_usertbl_addr ON usertbl;
DROP INDEX idx_usertbl_name_birthYear ON usertbl;



-- 다음과 같이 실습 데이터베이스 및 사용자를 생성하고,
-- 해당 데이터베이스에 대한 모든 권한을 부여하세요.
-- 데이터베이스명 : scoula_db
-- 사용자명 : scoula
-- 비밀번호 : 1234
-- 접속 호스트 : 제한 없음

DROP TABLE IF EXISTS scoula_db;
DROP USER IF EXISTS 'scoula'@'%';

CREATE DATABASE scoula_db;
CREATE USER 'scoula'@'%' IDENTIFIED BY '1234';
GRANT ALL PRIVILEGES ON scoula_db.* TO 'scoula'@'%';
FLUSH PRIVILEGES;