package com.pedrogonic.trelloapiconsumer.controller;

import com.pedrogonic.trelloapiconsumer.model.parameter.SprintCalculatorServiceRequestBody;
import com.pedrogonic.trelloapiconsumer.service.SprintCalculatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class MainController {

    @Autowired
    SprintCalculatorService sprintCalculatorService;

    @PostMapping("sprintCalculator")
    public void sprintCalculator(@RequestBody(required = false) SprintCalculatorServiceRequestBody body) throws Exception {

        sprintCalculatorService.run(body);

    }

}
