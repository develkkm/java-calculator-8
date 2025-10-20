package calculator.config;

import calculator.controller.CalculatorController;
import calculator.domain.Delimiters;
import calculator.domain.Operands;
import calculator.service.CalculatorService;
import calculator.view.ConsoleView;

public class AppConfig {
    public CalculatorController calculatorController() {
        ConsoleView view = new ConsoleView();
        CalculatorService service = new CalculatorService(new Delimiters(), new Operands());
        return new CalculatorController(service, view);
    }
}
