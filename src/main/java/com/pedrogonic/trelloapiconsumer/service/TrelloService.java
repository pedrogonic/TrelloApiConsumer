package com.pedrogonic.trelloapiconsumer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

public abstract class TrelloService {

    protected RestTemplate restTemplate = new RestTemplate();

    protected static String TRELLO_API_URL = "https://api.trello.com/1/";

    public abstract void run(String[] args);

}
