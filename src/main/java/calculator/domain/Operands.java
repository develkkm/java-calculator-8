package calculator.domain;

import java.math.BigDecimal;
import java.util.Arrays;

public class Operands {

    private final BigDecimal[] numbers;

    public Operands(String[] rawNumbers) {
        numbers = parseToBigDecimal(rawNumbers);
    }

    public BigDecimal sum() {
        return Arrays.stream(numbers)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal[] parseToBigDecimal(String[] rawNumbers) {
        BigDecimal[] numbers = new BigDecimal[rawNumbers.length];

        for (int idx = 0; idx < rawNumbers.length; idx++) {
            String rawNumber = rawNumbers[idx];
            validateOperand(rawNumber);

            BigDecimal number = new BigDecimal(rawNumber);
            validateNumber(number);

            numbers[idx] = number;
        }

        return numbers;
    }

    private void validateNumber(BigDecimal number) {
        validatePositiveNumber(number);
        validateNumberScope(number);
    }

    private void validatePositiveNumber(BigDecimal number) {
        if (number.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("양수만 입력 가능합니다.");
        }
    }

    private void validateNumberScope(BigDecimal number) {
        BigDecimal maxAllowed = new BigDecimal("1000000000000000");
        if (number.compareTo(maxAllowed) > 0) {
            throw new IllegalArgumentException("입력 가능한 최대값은 " + maxAllowed + " 입니다.");
        }
    }

    private void validateOperand(String rawNumber) {
        validateEmptyNumber(rawNumber);
        validateNumeric(rawNumber);
    }

    private void validateNumeric(String s) {
        if (!s.matches("-?\\d+(\\.\\d+)?")) {
            throw new IllegalArgumentException("숫자 형식이 아닙니다. 입력값: '" + s + "'");
        }
    }

    private void validateEmptyNumber(String s) {
        if (s.isEmpty()) {
            throw new IllegalArgumentException("유효한 입력이 아닙니다.");
        }
    }
}
