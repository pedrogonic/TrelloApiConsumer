package com.pedrogonic.trelloapiconsumer.controller;

import com.pedrogonic.trelloapiconsumer.sprintCalculator.model.parameter.SprintCalculatorServiceRequestBody;
import com.pedrogonic.trelloapiconsumer.sprintCalculator.service.SprintCalculatorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class MainController {

    final
    SprintCalculatorService sprintCalculatorService;

    public MainController(SprintCalculatorService sprintCalculatorService) {
        this.sprintCalculatorService = sprintCalculatorService;
    }

    @PostMapping("sprintCalculator")
    public String sprintCalculator(@RequestBody(required = false) SprintCalculatorServiceRequestBody body) throws Exception {

        return "Sobraram " + sprintCalculatorService.run(body) + " horas nesta sprint!";

    }

}
