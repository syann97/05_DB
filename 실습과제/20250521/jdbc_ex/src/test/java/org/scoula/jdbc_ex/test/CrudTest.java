package org.scoula.jdbc_ex.test;

import org.junit.jupiter.api.*;
import org.scoula.jdbc_ex.common.JDBCUtil;

import java.sql.*;

// 테스트 메서드 실행 순서를 @Order 어노테이션 값에 따라 정렬
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CrudTest {

    // JDBCUtil로부터 데이터베이스 연결 객체 획득
    Connection conn = JDBCUtil.getConnection();

    /**
     * 모든 테스트 완료 후 실행되는 메서드
     * - 데이터베이스 연결을 닫고 리소스 정리
     * - @AfterAll: 모든 테스트 메서드 실행 후 한 번 호출됨
     */
    @AfterAll
    static void tearDown() {
        JDBCUtil.close();
    }

    /**
     * INSERT 작업: 새로운 사용자 등록 테스트 (CRUD 중 C, create)
     * - PreparedStatement 사용하여 성능 향상, SQL 인젝션 방지
     * - @Order(1): 첫 번째로 실행되는 테스트
     */
    @Test
    @DisplayName("새로운 user를 등록한다.")
    @Order(1)
    public void insertUser() throws SQLException {

        // 파라미터화된 SQL 문 준비 (? 자리에 값 바인딩)
        String sql = "INSERT INTO users(id, password, name, role) VALUES(?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // 파라미터 바인딩
            pstmt.setString(1, "scoula");       // 사용자 ID
            pstmt.setString(2, "scoula3");      // 비밀번호
            pstmt.setString(3, "스콜라");       // 이름
            pstmt.setString(4, "USER");         // 역할

            // SQL 실행 및 영향받은 행 수 반환
            int count = pstmt.executeUpdate();
            // 정확히 1개 행이 삽입되었는지 검증
            Assertions.assertEquals(1, count);
        }
    }

    /**
     * SELECT 작업(전체 조회): 모든 사용자 목록 조회 테스트 (CRUD 중 R, read)
     * - Statement와 ResultSet 사용
     * - @Order(2): 두 번째로 실행되는 테스트
     */
    @Test
    @DisplayName("user 목록을 추출한다.")
    @Order(2)
    public void selectUser() throws SQLException {

        // 전체 사용자 조회 쿼리
        String sql = "SELECT * FROM users";

        // Statement 생성 및 ResultSet 획득(try-with-resources로 자동 리소스 해제)
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql);
        ) {
            // 결과셋 순회하며 각 사용자 정보 출력
            while (rs.next()) {
                System.out.println(rs.getString("name"));
            }
        }
    }

    /**
     * SELECT 작업(개별 조회): 특정 ID로 사용자 검색 테스트 (CRUD 중 R, read)
     * - PreparedStatement와 ResultSet 사용
     * - @Order(3): 세 번째로 실행되는 테스트
     */
    @Test
    @DisplayName("특정 user 검색한다.")
    @Order(3)
    public void selectUserById() throws SQLException {

        // 검색할 사용자 ID
        String userid = "scoula";
        // ID로 검색하는 쿼리(파라미터화)
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // ID 파라미터 바인딩
            stmt.setString(1, userid);
            // 쿼리 실행 및 결과셋 획득
            try (ResultSet rs = stmt.executeQuery()) {
                // 결과가 있으면 사용자 이름 출력
                if (rs.next()) {
                    System.out.println(rs.getString("name"));
                } else {
                    // 결과가 없으면 예외 발생
                    throw new SQLException("scoula not found");
                }
            }
        }
    }

    /**
     * UPDATE 작업: 사용자 정보 수정 테스트 (CRUD중 U, update)
     * - PreparedStatement를 사용한 데이터 업데이트
     * - @Order(4): 네 번째로 실행되는 테스트
     */
    @Test
    @DisplayName("특정 user 수정한다.")
    @Order(4)
    public void updateUser() throws SQLException {

        // 수정할 사용자 ID
        String userid = "scoula";

        // 사용자 이름 수정 쿼리
        String sql = "UPDATE users SET name= ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // 파라미터 바인딩
            stmt.setString(1, "스콜라 수정");  // 새 이름
            stmt.setString(2, userid);         // 사용자 ID
            // 쿼리 실행 및 영향받은 행 수 반환
            int count = stmt.executeUpdate();
            // 정확히 1개 행이 수정되었는지 검증
            Assertions.assertEquals(1, count);
        }
    }

    /**
     * DELETE 작업: 사용자 삭제 테스트 (CRUD 중 D, delete)
     * - PreparedStatement를 사용한 데이터 삭제
     * - @Order(5): 다섯 번째(마지막)로 실행되는 테스트
     */
    @Test
    @DisplayName("지정한 사용자를 삭제한다.")
    @Order(5)
    public void deleteUser() throws SQLException {

        // 삭제할 사용자 ID
        String userid = "scoula";

        // 사용자 삭제 쿼리
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // ID 파라미터 바인딩
            stmt.setString(1, userid);
            // 쿼리 실행 및 영향받은 행 수 반환
            int count = stmt.executeUpdate();
            // 정확히 1개 행이 삭제되었는지 검증
            Assertions.assertEquals(1, count);
        }
    }
}
