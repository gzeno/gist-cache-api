package com.gzeno.gistCacheApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

public class GithubGistApiClientTest {
    
    private RestTemplate restTemplate;
    
    private ObjectReader gistReader;
    
    protected GithubGistApiClient instance;
    
    public GithubGistApiClientTest() {
    }
    
    @Before
    public void setUp() {
        restTemplate = new RestTemplate();
        gistReader = new ObjectMapper().readerFor(Gist.class);
        instance = new GithubGistApiClient(restTemplate, gistReader);
    }
    
    @Test
    public void testCreateGist() {
        Gist gist = instance.getSingleGist(GistControllerTest.testGistId);
        assertEquals("jboner", gist.owner);
        assertEquals("Latency Numbers Every Programmer Should Know", gist.description);
        assertEquals("2012-05-31T08:15:11Z", gist.created_at);
        assertTrue(1 == gist.files.size());
        assertEquals("latency.txt", gist.files.get(0).filename);
        assertEquals("text/plain", gist.files.get(0).type);
    }
    
}
