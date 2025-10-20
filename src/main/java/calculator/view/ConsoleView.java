package calculator.view;

import camp.nextstep.edu.missionutils.Console;
import java.math.BigDecimal;

public class ConsoleView {
    private final static String READ_INPUT_MESSAGE = "덧셈할 문자열을 입력해주세요.";
    private final static String CALC_RESULT_MESSAGE = "결과 : ";

    public String readInput() {
        System.out.println(READ_INPUT_MESSAGE);
        return Console.readLine();
    }

    public void printResult(BigDecimal result) {
        System.out.println(CALC_RESULT_MESSAGE + result);
    }
}
