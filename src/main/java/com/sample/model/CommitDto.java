package com.sample.model;

import org.kohsuke.github.GHCommit;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class CommitDto {
    private String committerName;
    private String committerEmail;
    private Date commitDate;
    private LocalDate commitLocalDate;

    public CommitDto(){

    }

    public CommitDto(GHCommit ghCommit) throws IOException{
        super();
        if(ghCommit == null)
            throw new IllegalArgumentException();

        this.committerName = ghCommit.getCommitter() != null && ghCommit.getCommitter().getName() != null ? ghCommit.getCommitter().getName() : "";
        this.committerEmail = ghCommit.getCommitter() != null ? ghCommit.getCommitter().getEmail() : "";
        this.commitDate = ghCommit.getCommitDate();
        this.commitLocalDate = convertToLocalDateViaMilisecond(ghCommit.getCommitDate());
    }

    private LocalDate convertToLocalDateViaMilisecond(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public String getCommitterName() {
        return committerName;
    }

    public void setCommitterName(String committerName) {
        this.committerName = committerName;
    }

    public String getCommitterEmail() {
        return committerEmail;
    }

    public void setCommitterEmail(String committerEmail) {
        this.committerEmail = committerEmail;
    }

    public Date getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Date commitDate) {
        this.commitDate = commitDate;
    }

    public LocalDate getCommitLocalDate() {
        return commitLocalDate;
    }

    public void setCommitLocalDate(LocalDate commitLocalDate) {
        this.commitLocalDate = commitLocalDate;
    }
}
