package com.pedrogonic.trelloapiconsumer.controller;

import com.pedrogonic.trelloapiconsumer.prepareBoardForScrum.model.parameter.PrepareBoardForScrumServiceRequestBody;
import com.pedrogonic.trelloapiconsumer.prepareBoardForScrum.service.PrepareBoardForScrumService;
import com.pedrogonic.trelloapiconsumer.sprintCalculator.model.parameter.SprintCalculatorServiceRequestBody;
import com.pedrogonic.trelloapiconsumer.sprintCalculator.service.SprintCalculatorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class MainController {

    final
    SprintCalculatorService sprintCalculatorService;

    final
    PrepareBoardForScrumService prepareBoardForScrumService;

    public MainController(SprintCalculatorService sprintCalculatorService, PrepareBoardForScrumService prepareBoardForScrumService) {
        this.sprintCalculatorService = sprintCalculatorService;
        this.prepareBoardForScrumService = prepareBoardForScrumService;
    }

    @PostMapping("sprintCalculator")
    public String sprintCalculator(@RequestBody SprintCalculatorServiceRequestBody body) throws Exception {

        return "Sobraram " + sprintCalculatorService.run(body) + " horas nesta sprint!";

    }

    @PostMapping("prepareBoardForScrum")
    public void prepareBoardForScrumService(@RequestBody PrepareBoardForScrumServiceRequestBody body) {

        prepareBoardForScrumService.run(body);

    }

}
