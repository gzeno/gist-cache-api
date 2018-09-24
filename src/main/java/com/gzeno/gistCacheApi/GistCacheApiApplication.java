package com.gzeno.gistCacheApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class GistCacheApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GistCacheApiApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public ObjectReader gistReader() {
        return new ObjectMapper().readerFor(Gist.class);
    }
    
    @Bean
    public GithubGistApiClient githubGistApiClient(RestTemplate restTemplate, ObjectReader gistReader) {
        return new GithubGistApiClient(restTemplate, gistReader);
    }

}
