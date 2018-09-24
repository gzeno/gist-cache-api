package com.gzeno.gistCacheApi;

import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

public class GistControllerTest {
    
    @Mock
    private GistRepository gistRepository;
    
    @Mock
    private GithubGistApiClient apiClient;
    
    public static Long testGistId = 2841832L;
    
    private Gist gist = new Gist();
    
    private GistController instance;
    
    public GistControllerTest() {
    }
    
    @Before
    public void setUp() {
        gist.gistId = testGistId;
        MockitoAnnotations.initMocks(this);
        instance = new GistController();
        instance.apiClient = apiClient;
        instance.gistRepository = gistRepository;
        when(gistRepository.findByGistId(testGistId)).thenReturn(gist);
        when(apiClient.getSingleGist(testGistId)).thenReturn(gist);
    }
    
    @Test
    public void updateGistFromNoChangeTest() {
        instance.updateGist(testGistId);
        verify(gistRepository, never()).save(gist);
    }
    
    @Test(expected=ResourceNotFoundException.class)
    public void updateGistDoesNotExistTest() {
        instance.updateGist(testGistId + 1);
    }
    
    @Test
    public void updateGistChangeGistTest() {
        Gist remoteGist = new Gist();
        remoteGist.files = new ArrayList<>();
        when(apiClient.getSingleGist(testGistId)).thenReturn(remoteGist);
        instance.updateGist(testGistId);
        verify(gistRepository).save(remoteGist);
    }
    
    @Test
    public void getGistFromCacheTest() throws IOException {
        instance.getGist(testGistId);
        verify(apiClient, never()).getSingleGist(testGistId);
    }
    
    @Test
    public void getGistFromApiClientTest() throws IOException {
        when(apiClient.getSingleGist(testGistId + 1)).thenReturn(gist);
        instance.getGist(testGistId + 1);
        verify(gistRepository).save(gist);
    }
    
    
    
}
