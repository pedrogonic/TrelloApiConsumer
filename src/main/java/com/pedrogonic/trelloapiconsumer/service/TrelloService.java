package com.pedrogonic.trelloapiconsumer.service;

import org.springframework.web.client.RestTemplate;

public abstract class TrelloService {

    protected RestTemplate restTemplate = new RestTemplate();

    protected static String TRELLO_API_URL = "https://api.trello.com/1/";

}
