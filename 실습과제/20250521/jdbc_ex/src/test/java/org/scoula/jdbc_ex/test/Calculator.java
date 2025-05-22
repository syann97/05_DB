package org.scoula.jdbc_ex.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.scoula.jdbc_ex.temp.Calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
 * @DisplayName - 테스트 실행 시 보여질 테스트 이름을 지정하는 어노테이션. 테스트 결과 리포트에 표시됨
 */
@DisplayName("계산기 테스트")
class CalculatorTest {

    Calculator calc;

    /**
     * 각 테스트 메서드 실행 전에 수행되는 초기화 메서드
     * - 매 테스트마다 새로운 Calculator 인스턴스 생성
     * - @BeforeEach: 각 테스트 메서드 실행 전에 자동으로 호출됨
     */
    @BeforeEach
    void setUp() {
        calc = new Calculator();
    }

  /*  @Test - JUnit 테스트 메서드임을 나타내는 어노테이션.
      이 어노테이션이 붙은 메서드는 테스트 러너에 의해 실행됨 */
    /**
     * 덧셈 기능 테스트
     * - 1 + 2 = 3 연산 결과 검증
     * - assertEquals: 예상값과 실제값이 일치하는지 검증하는 JUnit 어서션
     */
    @Test
    @DisplayName("10 + 20 = 30")
    void testAdd() {
        // 10과 20를 더한 결과가 30인지 검증
        assertEquals(30, calc.add(10, 20));
    }

    /**
     * 0으로 나누기 예외 테스트
     * - 1 / 0 연산 시 ArithmeticException 발생 검증
     * - assertThrows: 지정된 예외가 발생하는지 검증하는 JUnit 어서션
     */
    @Test
    @DisplayName("나눗셈 예외 테스트")
    void testDivideByZero() {
        // 1을 0으로 나눌 때 ArithmeticException이 발생하는지 검증
        assertThrows(ArithmeticException.class, () -> {
            calc.divide(1, 0);
        });
    }
}