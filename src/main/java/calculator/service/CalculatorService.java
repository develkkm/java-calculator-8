package calculator.service;

import calculator.domain.Delimiters;
import calculator.domain.Operands;
import java.math.BigDecimal;

public class CalculatorService {

    public BigDecimal calculate(String inputValue) {
        if (inputValue == null || inputValue.trim().isEmpty()) {
            return BigDecimal.ZERO;
        }

        Delimiters delimiters = new Delimiters(inputValue);
        String[] rawNumbers = delimiters.extractRawNumbers();

        Operands operands = new Operands(rawNumbers);

        return operands.sum();
    }
}
