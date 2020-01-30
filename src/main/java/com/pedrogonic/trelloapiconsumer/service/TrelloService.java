package com.pedrogonic.trelloapiconsumer.service;

import com.pedrogonic.trelloapiconsumer.config.AppConfig;
import org.springframework.web.client.RestTemplate;

public abstract class TrelloService {

    protected RestTemplate restTemplate = new RestTemplate();

    protected static String TRELLO_API_URL = "https://api.trello.com/1/";

    protected static String API_AND_TOKEN_PARAMS = "token=" + AppConfig.API_TOKEN + "&key=" + AppConfig.API_KEY;

}
