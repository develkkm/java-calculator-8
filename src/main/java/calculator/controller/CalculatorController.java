package calculator.controller;

import calculator.service.CalculatorService;
import calculator.view.ConsoleView;
import java.math.BigDecimal;

public class CalculatorController {
    private final CalculatorService service;
    private final ConsoleView view;

    public CalculatorController(CalculatorService service, ConsoleView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        String input = view.readInput();
        BigDecimal result;
        result = service.calculate(input);
        view.printResult(result);
    }
}

