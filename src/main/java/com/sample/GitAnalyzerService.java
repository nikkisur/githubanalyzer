package com.sample;

import com.sample.model.CommitDto;
import com.sample.model.RepositoryDto;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHException;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class GitAnalyzerService {

    //TODO add token here
    private final String TOKEN = "";
    private final Logger LOGGER = Logger.getLogger("Surtida-Git-Analyzer");

    public List<RepositoryDto> searchGitRepositories(String searchCategory, String searchKey){
        try{
            LOGGER.info("Searching Git repositories for " + searchCategory + ": " + searchKey);
            if (StringUtils.isEmpty(searchKey) || StringUtils.isEmpty(searchCategory)) {
                LOGGER.warning("Invalid search input " + searchCategory + ": " + searchKey);
                return new ArrayList<>();
            }

            GitHub github = new GitHubBuilder().withOAuthToken(TOKEN).build();
            Iterator itr = github.searchRepositories().q(searchCategory + ":" + searchKey).list().iterator();

            List<RepositoryDto> repositoryDtos = new ArrayList<>();
            while(itr.hasNext()){
                repositoryDtos.add(new RepositoryDto((GHRepository) itr.next()));
            }
            Collections.sort(repositoryDtos, Comparator.comparing(RepositoryDto::getName));
            LOGGER.info("Retrieved Git repositories for " + searchCategory + ": " + searchKey);
            return repositoryDtos;
        }catch(IOException | GHException e){
            e.printStackTrace();
            LOGGER.warning(e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<CommitDto> fetchRepositoryByOwnerAndName(String owner, String repoName){
        try {
            LOGGER.info("Fetching Git repository for " + owner + ": " + repoName);
            if (StringUtils.isEmpty(owner) || StringUtils.isEmpty(repoName)) {
                LOGGER.warning("Invalid fetch input " + owner + ": " + repoName);
                return new ArrayList<>();
            }

            GitHub github = new GitHubBuilder().withOAuthToken(TOKEN).build();
            Iterator itr = github.getRepository(owner + "/" + repoName).listCommits().withPageSize(100).iterator();

            List<CommitDto> commitDtos = new ArrayList<>();
            int index = 0;
            while (itr.hasNext() && index < 100) {
                commitDtos.add(new CommitDto((GHCommit) itr.next()));
                index++;
            }
            LOGGER.info("Fetched Git repository for " + owner + ": " + repoName);
            return commitDtos;
        }catch(IOException | GHException e){
            e.printStackTrace();
            LOGGER.warning(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void analyzeCommitDataPerUser(HttpServletRequest request, List<CommitDto> commitDtos){
        LOGGER.info("Analyzing data");
        if(commitDtos.isEmpty()){
            LOGGER.warning("Invalid commit data for analysis");
            return;
        }

        Collections.sort(commitDtos, Comparator.comparing(CommitDto::getCommitDate));
        Map<String, Long> numCommitsPerUser = getNumberOfCommitsPerUser(commitDtos);
        request.setAttribute("numCommitsPerUser", numCommitsPerUser);
        request.setAttribute("impactPerUser", getImpactPerUser(numCommitsPerUser, commitDtos.size()));
        LOGGER.info("Finished analyzing data");
        setCommitTimeline(commitDtos, request);
    }

    private Map<String, Long> getNumberOfCommitsPerUser(List<CommitDto> commitDtos){
        return commitDtos.stream().collect(Collectors.groupingBy(CommitDto::getCommitterName, Collectors.counting()));
    }

    private Map<String, String> getImpactPerUser(Map<String, Long> numCommitsPerUser, double totalCommits){
        Map<String, String> impactPerUser = new HashMap<>();
        for(Map.Entry<String, Long> entry : numCommitsPerUser.entrySet()){
            double percentage = entry.getValue() / totalCommits;
            impactPerUser.put(entry.getKey(), Math.round(percentage * 100.0) + "%");
        }

        return impactPerUser;
    }

    private void setCommitTimeline(List<CommitDto> commitDtos, HttpServletRequest req){
        TreeMap<LocalDate, Long> commitsPerMonth = commitDtos.stream().collect(Collectors.groupingBy(commit -> commit.getCommitLocalDate().with(TemporalAdjusters.firstDayOfMonth()), TreeMap::new, Collectors.counting()));

        List<Map<String, String>> commitTimeline = new ArrayList<>();
        for(Map.Entry<LocalDate, Long> entry : commitsPerMonth.entrySet()){
            Map<String, String> dataPoint = new HashMap<>();
            dataPoint.put("label", entry.getKey().getYear() + "-" + entry.getKey().getMonth().name());
            dataPoint.put("y", entry.getValue().toString());
            commitTimeline.add(dataPoint);
        }
        req.setAttribute("dataPoints", commitTimeline);
    }
}
