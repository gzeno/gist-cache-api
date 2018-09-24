package com.gzeno.gistCacheApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.data.annotation.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Gist {
    
    public Gist() {}
    
    @Id
    public Long gistId;
    
    public String owner;
    
    public String description;
    
    public String created_at;
    
    public List<GistFile> files;
    
    @SuppressWarnings("unchecked")
    @JsonProperty("owner")
    private void unpackNestedOwner(Map<String,Object> owner) {
        this.owner = (String)owner.get("login");
    }
    
    /**
     * Gist response json has a strange formatting for file list.
     * @param files 
     */
    @SuppressWarnings("unchecked")
    @JsonProperty("files")
    private void unpackNestedFiles(Map<String,GistFile> files) {
        ArrayList<GistFile> fileList = new ArrayList<>();
        files.forEach((k,v)-> fileList.add(v));
        this.files = fileList;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.gistId);
        hash = 41 * hash + Objects.hashCode(this.owner);
        hash = 41 * hash + Objects.hashCode(this.description);
        hash = 41 * hash + Objects.hashCode(this.created_at);
        hash = 41 * hash + Objects.hashCode(this.files);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Gist other = (Gist) obj;
        if (!Objects.equals(this.owner, other.owner)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.created_at, other.created_at)) {
            return false;
        }
        if (!Objects.equals(this.gistId, other.gistId)) {
            return false;
        }
        if (!Objects.equals(this.files, other.files)) {
            return false;
        }
        return true;
    }
    
    public GistDto toDto() {
        GistDto dto = new GistDto();
        dto.created_at = this.created_at;
        dto.description = this.description;
        dto.files = this.files;
        dto.owner = this.owner;
        return dto;
    }
    
}
