package org.scoula.jdbc_ex.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnitTest {

    /*
     * - @Test: JUnit 테스트 메서드임을 나타내는 어노테이션
     * - @DisplayName: 테스트 결과 리포트에 표시될 사용자 정의 테스트 이름
     * - Assertions.assertEquals: 예상값과 실제값이 일치하는지 검증하는 JUnit 어서션
     */
    @DisplayName("1+2는 3이다")
    @Test
    public void junitTest() {
        // 테스트 데이터 준비
        int a = 1;
        int b = 2;
        int sum = 3;

        // 덧셈 결과 검증
        // 첫 번째 인자(a+b)가 두 번째 인자(sum)와 같은지 검증
        Assertions.assertEquals(a + b, sum);
    }
}

