package br.com.diih.controllers;

import br.com.diih.exceptions.UnsupportedMathOperationException;
import br.com.diih.math.simpleMath;
import br.com.diih.request.converts.numberConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/math")
public class MathController {
    private final simpleMath math = new simpleMath();
    @RequestMapping("/sum/{numberOne}/{numberTwo}")

    public Double sum(@PathVariable("numberOne") String numberOne,
                      @PathVariable("numberTwo") String numberTwo) {
        isNum(numberOne, numberTwo);
        return math.sum(numberConverter.convertToDouble(numberOne), numberConverter.convertToDouble(numberTwo));
    }

    @RequestMapping("/subtraction/{numberOne}/{numberTwo}")
    public Double subtraction(@PathVariable String numberOne,
                              @PathVariable String numberTwo) {
        isNum(numberOne, numberTwo);
        return math.subtraction(numberConverter.convertToDouble(numberOne), numberConverter.convertToDouble(numberTwo));
    }

    @RequestMapping("/multiplication/{numberOne}/{numberTwo}")
    public Double multiplication(@PathVariable String numberOne,
                                 @PathVariable String numberTwo) {
        isNum(numberOne, numberTwo);
        return math.multiplication(numberConverter.convertToDouble(numberOne), numberConverter.convertToDouble(numberTwo));
    }

    @RequestMapping("/division/{numberOne}/{numberTwo}")
    public Double division(@PathVariable String numberOne,
                           @PathVariable String numberTwo) {
        isNum(numberOne, numberTwo);
        if (numberConverter.convertToDouble(numberTwo) == 0)
            throw new UnsupportedMathOperationException("Can't division for 0");
        return math.division(numberConverter.convertToDouble(numberOne), numberConverter.convertToDouble(numberTwo));
    }

    @RequestMapping("/mean/{numberOne}/{numberTwo}")
    public Double mean(@PathVariable String numberOne,
                       @PathVariable String numberTwo) {
        isNum(numberOne, numberTwo);
        return math.mean(numberConverter.convertToDouble(numberOne), numberConverter.convertToDouble(numberTwo));
    }

    @RequestMapping("/square-root/{number}")
    public Double squareRoot(@PathVariable String number) {
        if (!numberConverter.isNumeric(number))
            throw new UnsupportedMathOperationException("Please type numeric value!");
        return math.squareRoot(numberConverter.convertToDouble(number));
    }

    private static void isNum(String numberOne, String numberTwo) {
        if (!numberConverter.isNumeric(numberOne) || !numberConverter.isNumeric(numberTwo))
            throw new UnsupportedMathOperationException("Please type numeric value!");
    }
}