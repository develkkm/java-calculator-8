package calculator.service;

import calculator.domain.Delimiters;
import calculator.domain.Operands;
import java.math.BigDecimal;

public class CalculatorService {
    private final Delimiters delimiters;
    private final Operands operands;

    public CalculatorService(Delimiters delimiters, Operands operands) {
        this.delimiters = delimiters;
        this.operands = operands;
    }

    public BigDecimal calculate(String inputValue) {
        if (inputValue == null || inputValue.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }

        String[] rawNumbers = delimiters.extractRawNumbers(inputValue);
        BigDecimal[] numbers = operands.parseToBigDecimal(rawNumbers);
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal number : numbers) {
            sum = sum.add(number);
        }

        return sum;
    }
}
