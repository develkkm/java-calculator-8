package calculator.domain;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class DelimitersTest {

    private final Delimiters delimiters = new Delimiters();

    @Nested
    @DisplayName("기본 구분자 [, :]")
    class DefaultDelimiters {

        @Test
        @DisplayName("쉼표/콜론으로 분리")
        void shouldSplitByDefaultDelimiters() {
            String[] result = delimiters.extractRawNumbers("1,2:3");
            assertArrayEquals(new String[]{"1", "2", "3"}, result);
        }

        @Test
        @DisplayName("연속 구분자일 때 빈 토큰을 유지")
        void shouldKeepEmptyTokensWhenConsecutiveDelimiters() {
            String[] result = delimiters.extractRawNumbers("1,,2::3,");
            assertArrayEquals(new String[]{"1", "", "2", "", "3", ""}, result);
        }
    }

    @Nested
    @DisplayName("커스텀 구분자 라인 //X\\n...")
    class CustomDelimiter {

        @Test
        @DisplayName("커스텀 ; 과 기본 구분자 혼용 분리")
        void shouldSplitByCustomSemicolonAndDefaultDelimiters() {
            String input = "//;\\n1;2,3:4";
            String[] result = delimiters.extractRawNumbers(input);
            assertArrayEquals(new String[]{"1", "2", "3", "4"}, result);
        }

        @Test
        @DisplayName("정규식 메타문자도 Pattern.quote 로 안전하게 분리(*)")
        void shouldSafelySplitByMetaCharUsingPatternQuote() {
            String input = "//{\\n1{2,3";
            String[] result = delimiters.extractRawNumbers(input);
            assertArrayEquals(new String[]{"1", "2", "3"}, result);
        }

        @Test
        @DisplayName("역슬래시(\\)도 허용")
        void shouldAllowSingleBackslashAsCustomDelimiter() {
            String input = "//\\\\n10\\20:30";
            String[] result = delimiters.extractRawNumbers(input);
            assertArrayEquals(new String[]{"10", "20", "30"}, result);
        }
    }

    @Nested
    @DisplayName("커스텀 구분자 예외")
    class CustomDelimiterException {

        @Test
        @DisplayName("길이가 1이 아니면 예외")
        void shouldThrowExceptionWhenDelimiterLengthNotOne() {
            assertThrows(IllegalArgumentException.class,
                    () -> delimiters.extractRawNumbers("//;;\\n1;2"));
        }

        @Test
        @DisplayName("숫자인 경우 예외")
        void shouldThrowExceptionWhenDelimiterIsNumeric() {
            assertThrows(IllegalArgumentException.class,
                    () -> delimiters.extractRawNumbers("//1\\n1,2"));
        }

        @Test
        @DisplayName("기본 구분자(, :)인 경우 예외")
        void shouldThrowExceptionWhenDelimiterIsDefault() {
            assertThrows(IllegalArgumentException.class,
                    () -> delimiters.extractRawNumbers("//,\\n1,2"));
            assertThrows(IllegalArgumentException.class,
                    () -> delimiters.extractRawNumbers("//:\\n1:2"));
        }

        @Test
        @DisplayName("숫자 표기 문자 . 또는 - 인 경우 예외")
        void shouldThrowExceptionWhenDelimiterIsNumericSymbol() {
            assertThrows(IllegalArgumentException.class,
                    () -> delimiters.extractRawNumbers("//.\\n1.2"));
            assertThrows(IllegalArgumentException.class,
                    () -> delimiters.extractRawNumbers("//-\\n1-2"));
        }
    }
}
