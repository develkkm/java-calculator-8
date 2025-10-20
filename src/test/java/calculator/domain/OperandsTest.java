package calculator.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class OperandsTest {

    private Operands create(String... rawNumbers) {
        return new Operands(rawNumbers);
    }

    @Nested
    @DisplayName("정상 케이스")
    class SuccessCases {

        @Test
        @DisplayName("정수 문자열 배열을 합산한다")
        void shouldSumIntegerStrings() {
            Operands operands = create("1", "2", "3");
            assertEquals(new BigDecimal("6"), operands.sum());
        }

        @Test
        @DisplayName("소수 문자열도 합산 가능하다")
        void shouldSumDecimalStrings() {
            Operands operands = create("1.5", "2.0", "3.14");
            assertEquals(new BigDecimal("6.64"), operands.sum());
        }

        @Test
        @DisplayName("최대 허용값(10^15)은 허용된다")
        void shouldAllowMaxBoundary() {
            Operands operands = create("1000000000000000");
            assertEquals(new BigDecimal("1000000000000000"), operands.sum());
        }
    }

    @Nested
    @DisplayName("예외 케이스")
    class FailureCases {

        @Test
        @DisplayName("연속된 구분자로 인한 빈 토큰은 예외 발생")
        void shouldThrowWhenEmptyTokenFromSplit() {
            assertThrows(IllegalArgumentException.class,
                    () -> create("1", "", "2"));
        }

        @Test
        @DisplayName("숫자 형식이 아니면 예외 발생")
        void shouldThrowWhenNotNumeric() {
            assertThrows(IllegalArgumentException.class,
                    () -> create("a1"));
        }

        @Test
        @DisplayName("0이면 예외 발생")
        void shouldThrowWhenZero() {
            assertThrows(IllegalArgumentException.class,
                    () -> create("0"));
        }

        @Test
        @DisplayName("음수면 예외 발생")
        void shouldThrowWhenNegative() {
            assertThrows(IllegalArgumentException.class,
                    () -> create("-5"));
        }

        @Test
        @DisplayName("최대 허용값 초과 시 예외 발생")
        void shouldThrowWhenExceedMaxAllowed() {
            assertThrows(IllegalArgumentException.class,
                    () -> create("1000000000000001"));
        }

        @Test
        @DisplayName("잘못된 소수점 형식이면 예외 발생")
        void shouldThrowWhenMalformedDecimal() {
            assertThrows(IllegalArgumentException.class,
                    () -> create("1..2"));
        }

        @Test
        @DisplayName("공백이 포함되는 형식이면 예외 발생")
        void shouldThrowWhenContainsWhitespace() {
            assertThrows(IllegalArgumentException.class,
                    () -> create("2 "));
        }
    }
}
