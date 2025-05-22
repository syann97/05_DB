package org.scoula.jdbc_ex.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.scoula.jdbc_ex.common.JDBCUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionTest {

    // (중략)

    @Test
    @DisplayName("jdbc_ex에 접속한다.(자동 닫기)")
    public void testConnection2() throws SQLException {

        // try-with-resources 구문을 사용하여 자동 리소스 해제
        // JDBCUtil 클래스의 getConnection() 메서드로 연결 획득
        try(Connection conn = JDBCUtil.getConnection()) {

            // 연결 성공 메시지 출력
            System.out.println("DB 연결 성공");

            // try 블록이 종료되면 자동으로 Connection이 닫힘 (close() 호출)
        }
        // SQLException은 메서드 선언부에서 throws로 처리
    }

}
