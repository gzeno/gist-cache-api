
package com.gzeno.gistCacheApi;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GistRepository extends MongoRepository<Gist, String> {
    public Gist findByGistId(Long gistId);
}
