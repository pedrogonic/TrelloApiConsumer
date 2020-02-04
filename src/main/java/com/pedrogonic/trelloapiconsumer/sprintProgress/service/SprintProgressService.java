package com.pedrogonic.trelloapiconsumer.sprintProgress.service;

import com.pedrogonic.trelloapiconsumer.service.TrelloService;
import com.pedrogonic.trelloapiconsumer.sprintProgress.model.parameter.SprintProgressServiceRequestBody;
import com.pedrogonic.trelloapiconsumer.sprintProgress.model.response.SprintProgressServiceResponseBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SprintProgressService extends TrelloService {


    public SprintProgressServiceResponseBody run(SprintProgressServiceRequestBody body) {

        log.info("Rodando serviço de cálculo do progresso da sprint!");

        return null;
    }
}
