package org.scoula.jdbc_ex.dao;

import org.scoula.jdbc_ex.common.JDBCUtil;
import org.scoula.jdbc_ex.domain.UserVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class UserDaoImpl implements UserDao {

    // JDBCUtil을 통해 싱글톤 Connection 인스턴스 획득
    Connection conn = JDBCUtil.getConnection();


    // SQL 쿼리문을 상수로 정의
    private final String USER_LIST = "select * from users";                             // 전체 사용자 조회
    private final String USER_GET = "select * from users where id = ?";                // 특정 사용자 조회
    private final String USER_INSERT = "insert into users values(?, ?, ?, ?)";         // 사용자 등록
    private final String USER_UPDATE = "update users set name = ?, role = ? where id = ?"; // 사용자 정보 수정
    private final String USER_DELETE = "delete from users where id = ?";               // 사용자 삭제


    /**
     * ResultSet에서 UserVO 객체로 매핑하는 메서드
     * @param rs 데이터베이스 쿼리 결과셋
     * @return 결과셋의 현재 행을 기반으로 생성된 UserVO 객체
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    private UserVO map(ResultSet rs) throws SQLException {
        UserVO user = new UserVO();
        user.setId(rs.getString("ID"));           // 사용자 ID
        user.setPassword(rs.getString("PASSWORD")); // 비밀번호
        user.setName(rs.getString("NAME"));       // 이름
        user.setRole(rs.getString("ROLE"));       // 역할
        return user;
    }

    /**
     * 새로운 사용자를 등록하는 메서드
     *
     * @param user 등록할 사용자 정보를 담은 UserVO 객체
     * @return 등록된 행의 수 (성공: 1, 실패: 0)
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    @Override
    public int create(UserVO user) throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(USER_INSERT)) {

            // 파라미터 바인딩
            stmt.setString(1, user.getId());        // 사용자 ID
            stmt.setString(2, user.getPassword());  // 비밀번호
            stmt.setString(3, user.getName());      // 이름
            stmt.setString(4, user.getRole());      // 역할
            return stmt.executeUpdate();            // 쿼리 실행 및 영향받은 행 수 반환
        }
    }

    /**
     * 모든 사용자 목록을 조회하는 메서드
     *
     * @return UserVO 객체 목록
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    @Override
    public List<UserVO> getList() throws SQLException {

        List<UserVO> userList = new ArrayList<UserVO>();  // 결과 저장할 리스트

        try (PreparedStatement pstmt = conn.prepareStatement(USER_LIST);
             ResultSet rs = pstmt.executeQuery()) {        // 쿼리 실행 및 결과셋 획득

            while(rs.next()) {                              // 결과셋 순회
                UserVO user = map(rs);                        // 결과를 UserVO로 매핑
                userList.add(user);                           // 리스트에 추가
            }
        }
        return userList;
    }

    /**
     * 특정 ID의 사용자 정보를 조회하는 메서드
     * - Optional을 사용하여 결과의 존재 여부 표현
     *
     * @param id 조회할 사용자 ID
     * @return 조회된 사용자 정보를 담은 Optional<UserVO>
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    @Override
    public Optional<UserVO> get(String id) throws SQLException {

        try (PreparedStatement pstmt = conn.prepareStatement(USER_GET)) {

            pstmt.setString(1, id);                          // ID 파라미터 바인딩
            try(ResultSet rs = pstmt.executeQuery()) {       // 쿼리 실행 및 결과셋 획득
                if(rs.next()) {                               // 결과가 있으면
                    return Optional.of(map(rs));                // UserVO로 매핑하여 Optional로 감싸 반환
                }
            }
        }
        return Optional.empty();                          // 결과가 없으면 빈 Optional 반환
    }

    /**
     * 사용자 정보를 수정하는 메서드
     * - 비밀번호는 수정하지 않음 (별도 처리 필요)
     *
     * @param user 수정할 정보를 담은 UserVO 객체
     * @return 수정된 행의 수 (성공: 1, 실패: 0)
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    @Override
    public int update(UserVO user) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(USER_UPDATE)) {
            // 파라미터 바인딩
            pstmt.setString(1, user.getName());              // 이름
            pstmt.setString(2, user.getRole());              // 역할
            pstmt.setString(3, user.getId());                // ID (조건절)
            return pstmt.executeUpdate();                    // 쿼리 실행 및 영향받은 행 수 반환
        }
    }

    /**
     * 특정 ID의 사용자를 삭제하는 메서드
     *
     * @param id 삭제할 사용자 ID
     * @return 삭제된 행의 수 (성공: 1, 실패: 0)
     * @throws SQLException DB 작업 중 오류 발생 시
     */
    @Override
    public int delete(String id) throws SQLException {
        try(PreparedStatement pstmt = conn.prepareStatement(USER_DELETE)) {
            pstmt.setString(1, id);                          // ID 파라미터 바인딩
            return pstmt.executeUpdate();                    // 쿼리 실행 및 영향받은 행 수 반환
        }
    }
}
