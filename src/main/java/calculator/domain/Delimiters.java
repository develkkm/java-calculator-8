package calculator.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Delimiters {
    private static final String DEFAULT_DELIMITERS = "[,:]";
    private static final Pattern CUSTOM_PATTERN = Pattern.compile("^//(.+)\\\\n(.*)$");
    private static final int CUSTOM_DELIMITER_PART = 1;
    private static final int NUMBER_PART = 2;
    private static final int CUSTOM_DELIMITER_MAX_LENGTH = 1;

    public Delimiters() {
    }

    public String[] extractRawNumbers(String inputValue) {
        Matcher m = CUSTOM_PATTERN.matcher(inputValue);

        if (m.matches()) {
            String rawCustomDelimiter = m.group(CUSTOM_DELIMITER_PART);
            String rawNumberLine = m.group(NUMBER_PART);

            validateDelimiter(rawCustomDelimiter);
            String splitRegex = DEFAULT_DELIMITERS + "|" + Pattern.quote(rawCustomDelimiter);
            return rawNumberLine.split(splitRegex, -1);
        }

        return inputValue.split(DEFAULT_DELIMITERS, -1);
    }

    private void validateDelimiter(String customDelimiter) {
        validateLength(customDelimiter);
        validateNumeric(customDelimiter);
        validateNotDefault(customDelimiter);
        validateNumericSymbol(customDelimiter);
    }

    private void validateLength(String customSeparator) {
        if (customSeparator.length() != CUSTOM_DELIMITER_MAX_LENGTH) {
            throw new IllegalArgumentException();
        }
    }

    private void validateNumeric(String customDelimiter) {
        if (customDelimiter.matches("\\d")) {
            throw new IllegalArgumentException("커스텀 구분자는 숫자일 수 없습니다.");
        }
    }

    private void validateNotDefault(String customDelimiter) {
        if (customDelimiter.equals(",") || customDelimiter.equals(":")) {
            throw new IllegalArgumentException("커스텀 구분자는 기본 구분자와 달라야 합니다.");
        }
    }

    private void validateNumericSymbol(String customDelimiter) {
        if (customDelimiter.matches("[.\\-]")) {
            throw new IllegalArgumentException("커스텀 구분자로 숫자 표기에 사용되는 문자는 사용할 수 없습니다: " + customDelimiter);
        }
    }
}
