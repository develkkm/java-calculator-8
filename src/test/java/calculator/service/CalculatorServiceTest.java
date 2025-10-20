package calculator.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import calculator.domain.Delimiters;
import calculator.domain.Operands;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalculatorServiceTest {

    private final CalculatorService service = new CalculatorService(new Delimiters(), new Operands());

    @Test
    @DisplayName("입력이 비었다면 0을 반환한다")
    void shouldReturnZeroWhenInputIsEmptyOrNull() {
        assertEquals(BigDecimal.ZERO, service.calculate(""));
    }
}
