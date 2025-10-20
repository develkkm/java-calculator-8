package calculator.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Operands 클래스 테스트")
class OperandsTest {

    private final Operands operands = new Operands();

    @Nested
    @DisplayName("정상 케이스")
    class SuccessCases {

        @Test
        @DisplayName("정수 문자열 배열을 BigDecimal 배열로 변환한다")
        void shouldConvertIntegerStringsToBigDecimal() {
            String[] input = {"1", "2", "3"};
            BigDecimal[] result = operands.parseToBigDecimal(input);

            assertEquals(3, result.length);
            assertEquals(new BigDecimal("1"), result[0]);
            assertEquals(new BigDecimal("2"), result[1]);
            assertEquals(new BigDecimal("3"), result[2]);
        }

        @Test
        @DisplayName("소수 문자열도 변환 가능하다")
        void shouldConvertDecimalStringsToBigDecimal() {
            String[] input = {"1.5", "2.0", "3.14"};
            BigDecimal[] result = operands.parseToBigDecimal(input);

            assertEquals(new BigDecimal("1.5"), result[0]);
            assertEquals(new BigDecimal("2.0"), result[1]);
            assertEquals(new BigDecimal("3.14"), result[2]);
        }

        @Test
        @DisplayName("최대 허용값(10^15)은 허용된다")
        void shouldAllowMaxBoundary() {
            String[] input = {"1000000000000000"};
            BigDecimal[] result = operands.parseToBigDecimal(input);
            assertEquals(new BigDecimal("1000000000000000"), result[0]);
        }
    }

    @Nested
    @DisplayName("예외 케이스")
    class FailureCases {

        @Test
        @DisplayName("연속된 구분자로 인한 빈 토큰은 예외 발생")
        void shouldThrowWhenEmptyTokenFromSplit() {
            assertThrows(IllegalArgumentException.class,
                    () -> operands.parseToBigDecimal(new String[]{"1", "", "2"}));
        }

        @Test
        @DisplayName("숫자 형식이 아니면 예외 발생")
        void shouldThrowWhenNotNumeric() {
            assertThrows(IllegalArgumentException.class,
                    () -> operands.parseToBigDecimal(new String[]{"a1"}));
        }

        @Test
        @DisplayName("0이면 예외 발생")
        void shouldThrowWhenZero() {
            assertThrows(IllegalArgumentException.class,
                    () -> operands.parseToBigDecimal(new String[]{"0"}));
        }

        @Test
        @DisplayName("음수면 예외 발생")
        void shouldThrowWhenNegative() {
            assertThrows(IllegalArgumentException.class,
                    () -> operands.parseToBigDecimal(new String[]{"-5"}));
        }

        @Test
        @DisplayName("최대 허용값 초과 시 예외 발생")
        void shouldThrowWhenExceedMaxAllowed() {
            assertThrows(IllegalArgumentException.class,
                    () -> operands.parseToBigDecimal(new String[]{"1000000000000001"}));
        }

        @Test
        @DisplayName("잘못된 소수점 형식이면 예외 발생")
        void shouldThrowWhenMalformedDecimal() {
            assertThrows(IllegalArgumentException.class,
                    () -> operands.parseToBigDecimal(new String[]{"1..2"}));
        }
    }
}
