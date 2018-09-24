package com.gzeno.gistCacheApi;

import com.fasterxml.jackson.databind.ObjectReader;
import java.io.IOException;
import org.springframework.web.client.RestTemplate;

public class GithubGistApiClient {

    private RestTemplate restTemplate;

    private ObjectReader gistReader;

    private final String githubGistApiUrlBase = "https://api.github.com/gists/";

    public GithubGistApiClient(RestTemplate template, ObjectReader reader) {
        this.restTemplate = template;
        this.gistReader = reader;
    }

    public Gist getSingleGist(Long gistId) {
        String jsonResponse = restTemplate.getForObject(githubGistApiUrlBase + gistId.toString(), String.class);
        try {
            return gistReader.readValue(jsonResponse);
        } catch (IOException ex) {
            throw new RuntimeException("Unable to parse Gist json response.");
        }
    }

}
