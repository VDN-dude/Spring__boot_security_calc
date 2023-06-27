package com.example.spring__boot_security_calc.controller;

import com.example.spring__boot_security_calc.entity.Operation;
import com.example.spring__boot_security_calc.entity.User;
import com.example.spring__boot_security_calc.service.CalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/calc")
public class CalculatorController {

    @Autowired
    private CalculatorService calculatorService;

    @GetMapping
    public String calc(Model model) {
        model.addAttribute("newOperation" , new Operation());
        return "calc";
    }

    @PostMapping
    public String calculate(@ModelAttribute("newOperation") @Valid Operation operation,
                            BindingResult bindingResult,
                            @AuthenticationPrincipal User user,
                            Model model) {

        if (bindingResult.hasErrors()) return "calc";

        Optional<Operation> calculate = calculatorService.calculate(operation, user);

        if (calculate.isPresent()) {

            Operation calculatedOperation = calculate.get();
            model.addAttribute("calculatedOperation", calculatedOperation);
            return "calc";
        }
        model.addAttribute("calculatedError", "Something goes wrong, try again.");
        return "calc";
    }

    @GetMapping("/history")
    public String history(@ModelAttribute("newOperation") @Valid Operation operation,
                          BindingResult bindingResult,
                          @RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "5") int size,
                          @AuthenticationPrincipal User user,
                          Model model){

        if (bindingResult.hasErrors()) return "calc";

        int countOfPages = calculatorService.getCountOfPages(user, size);
        model.addAttribute("countOfPages", countOfPages);

        List<Operation> operationList = calculatorService.findAll(user, page, size);
        model.addAttribute("page", page);
        model.addAttribute("operationList", operationList);
        return "calc";
    }
}
