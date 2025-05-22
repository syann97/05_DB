package org.scoula.jdbc_ex.dao;

import org.junit.jupiter.api.*;
import org.scoula.jdbc_ex.common.JDBCUtil;
import org.scoula.jdbc_ex.domain.UserVO;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

// 테스트 메서드 실행 순서를 @Order 어노테이션 값에 따라 정렬
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoTest {

    //테스트 대상 DAO 객체
    UserDao dao = new UserDaoImpl();


    @AfterAll // 모든 테스트 메서드 실행 후 한 번 호출됨
    static void tearDown() {
        JDBCUtil.close();
    }

    /**
     * 사용자 등록 기능 테스트
     * - UserVO 객체 생성 후 DAO를 통해 데이터베이스에 저장
     * - 영향받은 행 수가 1인지 검증
     *
     * @throws SQLException
     */
    @Test
    @DisplayName("user를 등록합니다.")
    @Order(1) // 첫 번째로 실행
    void create() throws SQLException {

        // 테스트용 사용자 객체 생성
        UserVO user = new UserVO("ssamz3", "ssamz123", "쌤즈", "ADMIN");

        // DAO 메서드 호출하여 사용자 생성
        int count = dao.create(user);

        // 정확히 1개 행이 삽입되었는지 검증
        Assertions.assertEquals(1, count);
    }

    /**
     * 사용자 목록 조회 기능 테스트
     * - DAO를 통해 모든 사용자 목록 조회
     * - 결과를 콘솔에 출력 확인
     *
     * @throws SQLException
     */
    @Test
    @DisplayName("UserDao User 목록을 추출합니다.")
    @Order(2) // 두 번째로 실행
    void getList() throws SQLException {

        // DAO 메서드 호출하여 모든 사용자 목록 조회
        List<UserVO> list = dao.getList();
        // 목록 순회하며 각 사용자 정보 출력
        for(UserVO vo: list) {
            System.out.println(vo);
        }
    }

    /**
     * 특정 사용자 조회 기능 테스트
     * - ID로 특정 사용자 조회 (Optional 처리)
     * - 결과가 null이 아닌지 검증
     * - 결과를 콘솔에 출력하여 확인
     *
     * @throws SQLException
     */
    @Test
    @DisplayName("특정 user 1건을 추출합니다.")
    @Order(3) // 세 번째로 실행
    void get() throws SQLException {

        // DAO 메서드 호출하여 특정 ID로 사용자 조회
        // orElseThrow: 결과가 없으면 NoSuchElementException 발생
        UserVO user = dao.get("ssamz3").orElseThrow(NoSuchElementException::new);

        // 조회 결과가 null이 아닌지 검증
        Assertions.assertNotNull(user);

        // 조회된 사용자 정보 출력
        System.out.println("user = " + user);
    }

    /**
     * 사용자 정보 수정 기능 테스트
     * - 먼저 기존 사용자 정보 조회
     * - 이름 필드 수정 후 DAO를 통해 업데이트
     * - 영향받은 행 수가 1인지 검증
     *
     * @throws SQLException
     */
    @Test
    @DisplayName("user의 정보를 수정합니다.")
    @Order(4) // 네 번째로 실행
    void update() throws SQLException {

        // 먼저 수정할 사용자 정보 조회
        UserVO user = dao.get("ssamz3").orElseThrow(NoSuchElementException::new);

        // 이름 필드 수정
        user.setName("쌤즈3");

        // DAO 메서드 호출하여 사용자 정보 업데이트
        int count = dao.update(user);

        // 정확히 1개 행이 수정되었는지 검증
        Assertions.assertEquals(1, count);
    }


    /**
     * 사용자 삭제 기능 테스트
     * - ID로 사용자 삭제
     * - 영향받은 행 수가 1인지 검증
     *
     * @throws SQLException
     */
    @Test
    @DisplayName("user를 삭제합니다.")
    @Order(5) // 다섯 번째(마지막)로 실행
    void delete() throws SQLException {

        // DAO 메서드 호출하여 특정 ID의 사용자 삭제
        int count = dao.delete("ssamz3");

        // 정확히 1개 행이 삭제되었는지 검증
        Assertions.assertEquals(1, count);
    }
}
