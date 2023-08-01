package com.beixian.Calculator;

import com.beixian.Calculator.service.Calculator;
import com.beixian.Calculator.util.ValidateUtil;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: 贝先 [ Cason mo ]
 * Date: 2023/8/1
 * Time: 18:23
 */
public class CalculatorApplication {

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);
        String input;

        System.out.println("####Calculator####");
        System.out.println(">>How many digits to keep after the decimal point(max:10): ");
        input = scanner.nextLine();
        //获取小数精确度
        if (StringUtils.isNotBlank(input) && ValidateUtil.isInteger(input)) {
            int scale = Integer.parseInt(input);
            if (scale > 10) {
                System.out.println("Warning:scale must less than 10.default set scale as 2");
                calculator.setScale(2);
            }else {
                calculator.setScale(scale);
                System.out.println("Tips:scale set:" + calculator.getScale());
            }

        } else {
            System.out.println("Tips:Default scale:2");
            calculator.setScale(2);
        }
        System.out.println(">>Please select Rounding Mode(default DOWN): \n 1:UP \n 2:DOWN \n 3:CEILING \n 4:FLOOR \n 5:HALF_UP \n 6:HALF_DOWN \n 7:HALF_EVEN \n 8:UNNECESSARY");
        input = scanner.nextLine();
        calculator.setRoundingMode(RoundingMode.DOWN);
        //获取舍入模式
        if (StringUtils.isNotBlank(input) && ValidateUtil.isInteger(input)) {
            int roundingModeNum = Integer.parseInt(input);
            switch (roundingModeNum) {
                case 1:
                    calculator.setRoundingMode(RoundingMode.UP);
                    break;
                case 2:
                    calculator.setRoundingMode(RoundingMode.DOWN);
                    break;
                case 3:
                    calculator.setRoundingMode(RoundingMode.CEILING);
                    break;
                case 4:
                    calculator.setRoundingMode(RoundingMode.FLOOR);
                    break;
                case 5:
                    calculator.setRoundingMode(RoundingMode.HALF_UP);
                    break;
                case 6:
                    calculator.setRoundingMode(RoundingMode.HALF_DOWN);
                    break;
                case 7:
                    calculator.setRoundingMode(RoundingMode.HALF_EVEN);
                    break;
                case 8:
                    calculator.setRoundingMode(RoundingMode.UNNECESSARY);
                    break;
                default:
                    calculator.setRoundingMode(RoundingMode.DOWN);
            }
            System.out.println("Tips:rounding mode set : " + calculator.getRoundingMode());
        } else {
            System.out.println("Tips:Default rounding mode is DOWN");
        }
        while (true) {
            System.out.println("\nCurrent Result: " + calculator.getResult());
            System.out.println(">>Enter command [+, -, *, /, undo, redo, quit] (for example:+10):");
            input = scanner.nextLine();
            if (StringUtils.isBlank(input)) {
                System.out.println("Warning:invalid operation");
                continue;
            }
            if (input.equalsIgnoreCase("quit")) {
                break;
            } else if (input.equalsIgnoreCase("undo")) {
                calculator.undo();
            } else if (input.equalsIgnoreCase("redo")) {
                calculator.redo();
            } else {
                try {
                    BigDecimal num = new BigDecimal(input.substring(1).trim());
                    char operator = input.charAt(0);
                    switch (operator) {
                        case Calculator.ADD:
                            calculator.add(num);
                            break;
                        case Calculator.SUBTRACT:
                            calculator.subtract(num);
                            break;
                        case Calculator.MULTIPLY:
                            calculator.multiply(num);
                            break;
                        case Calculator.DIVISION:
                            calculator.divide(num);
                            break;
                        default:
                            System.out.println("Warning:Invalid command.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Warning:Invalid number format.");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        scanner.close();
    }
}
