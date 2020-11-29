package com.sample;

import org.junit.Assert;
import org.junit.Test;
import java.util.List;

public class GitAnalyzerServiceTest{
    private GitAnalyzerService gitAnalyzerService = new GitAnalyzerService();

    @Test
    public void testSearchGitRepositoriesInvalid1(){
        List result = gitAnalyzerService.searchGitRepositories(null, null);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchGitRepositoriesInvalid2(){
        List result = gitAnalyzerService.searchGitRepositories(null, "");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchGitRepositoriesInvalid3(){
        List result = gitAnalyzerService.searchGitRepositories("", null);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchGitRepositoriesInvalid4(){
        List result = gitAnalyzerService.searchGitRepositories("", "");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchGitRepositoriesValid(){
        List result = gitAnalyzerService.searchGitRepositories("topic", "nikki");
        Assert.assertTrue(!result.isEmpty());
    }

    @Test
    public void testFetchRepositoryInvalid1(){
        List result = gitAnalyzerService.fetchRepositoryByOwnerAndName(null, null);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testFetchRepositoryInvalid2(){
        List result = gitAnalyzerService.fetchRepositoryByOwnerAndName(null, "");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testFetchRepositoryInvalid3(){
        List result = gitAnalyzerService.fetchRepositoryByOwnerAndName("", null);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testFetchRepositoryInvalid4(){
        List result = gitAnalyzerService.fetchRepositoryByOwnerAndName("", "");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testFetchRepositoryValid(){
        List result = gitAnalyzerService.fetchRepositoryByOwnerAndName("nikkisur", "githubanalyzer");
        Assert.assertTrue(!result.isEmpty());
    }
}
