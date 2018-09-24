package com.gzeno.gistCacheApi;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gists")
public class GistController {
    
    @Autowired
    public GithubGistApiClient apiClient;
    
    @Autowired
    public GistRepository gistRepository;
    
    private Gist getGistFromRepository(Long gistId) {
        Gist gist = gistRepository.findByGistId(gistId);
        if (gist == null) {
            throw new ResourceNotFoundException();
        }
        return gist;
    }

    // If gist isn't cached, cache & return the remote gist
    @GetMapping("/{gistId}")
    public GistDto getGist(@PathVariable("gistId") Long gistId) throws IOException {
        Gist gist = gistRepository.findByGistId(gistId);
        if (gist == null) {
            gist = apiClient.getSingleGist(gistId);
            gist.gistId = gistId;
            gistRepository.save(gist);
        }
        return gist.toDto();
    }

    @DeleteMapping("/{gistId}")
    public void deleteGist(@PathVariable("gistId") Long gistId) {
        gistRepository.delete(getGistFromRepository(gistId));
    }
    
    // If the remote gist differs from the cached gist, replace the gist.
    @PutMapping("/{gistId}")
    public void updateGist(@PathVariable("gistId") Long gistId) {
        Gist localGist = getGistFromRepository(gistId);
        Gist remoteGist = apiClient.getSingleGist(gistId);
        remoteGist.gistId = gistId;
        if (!(localGist.equals(remoteGist))) {
            gistRepository.save(remoteGist);
        }
    }
    
}
