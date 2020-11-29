package com.sample.model;

import org.kohsuke.github.GHRepository;

public class RepositoryDto {
    private String name;
    private String homepage;
    private String owner;
    private String description;

    public RepositoryDto(){

    }

    public RepositoryDto(GHRepository ghRepository){
        super();
        if(ghRepository == null){
            throw new IllegalArgumentException();
        }

        this.name = ghRepository.getName() != null ? ghRepository.getName() : "";
        this.homepage = ghRepository.getHomepage();
        this.owner = ghRepository.getOwnerName() != null ? ghRepository.getOwnerName() : "";
        this.description = ghRepository.getDescription();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
