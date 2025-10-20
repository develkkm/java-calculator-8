package calculator.config;

import calculator.controller.CalculatorController;
import calculator.service.CalculatorService;
import calculator.view.ConsoleView;

public class AppConfig {
    public CalculatorController calculatorController() {
        ConsoleView view = new ConsoleView();
        CalculatorService service = new CalculatorService();
        return new CalculatorController(service, view);
    }
}
