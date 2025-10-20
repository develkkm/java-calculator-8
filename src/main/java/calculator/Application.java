package calculator;

import calculator.controller.CalculatorController;
import calculator.domain.Delimiters;
import calculator.domain.Operands;
import calculator.service.CalculatorService;
import calculator.view.ConsoleView;

public class Application {
    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView();
        Delimiters delimiters = new Delimiters();
        Operands operands = new Operands();
        CalculatorService calculatorService = new CalculatorService(delimiters, operands);

        CalculatorController calculatorController = new CalculatorController(calculatorService, consoleView);
        calculatorController.run();
    }
}
