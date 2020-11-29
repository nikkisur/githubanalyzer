package com.sample;

import com.sample.model.CommitDto;
import com.sample.model.RepositoryDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(urlPatterns = { "/index.html", "/search", "/select" })
public class GitAnalyzerServlet extends HttpServlet {

    private GitAnalyzerService gitAnalyzerService;
    private final Logger LOGGER = Logger.getLogger("Surtida-Git-Analyzer");

    public void init(){
        gitAnalyzerService = new GitAnalyzerService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getServletPath();

        try {
            if ("/search".equals(action)) {
                searchProjects(req, resp);
            } else if ("/select".equals(action)) {
                selectProject(req, resp);
            } else if("/index.html".equals(action)) {
                displayHomePage(req, resp);
            }
        }catch(IOException | ServletException e){
            e.printStackTrace();
            req.setAttribute("hasEncounteredError", true);
        }
    }

    private void searchProjects(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        LOGGER.info("Search Projects action triggered");
        String searchCategory = req.getParameter("searchCategory");
        String searchKey = req.getParameter("searchKey");

        List<RepositoryDto> resultList = gitAnalyzerService.searchGitRepositories(searchCategory, searchKey);

        req.setAttribute("resultList", resultList);
        req.setAttribute("searchCategory", searchCategory);
        req.setAttribute("searchKey", searchKey);
        req.setAttribute("hasResults", !resultList.isEmpty());
        RequestDispatcher dispatcher = req.getRequestDispatcher("github-analyzer-form.jsp");
        dispatcher.forward(req, resp);
    }

    private void selectProject(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        LOGGER.info("Select Projects action triggered");
        String owner = req.getParameter("owner");
        String name = req.getParameter("name");
        List<CommitDto> repoData = gitAnalyzerService.fetchRepositoryByOwnerAndName(owner, name);
        gitAnalyzerService.analyzeCommitDataPerUser(req, repoData);

        req.setAttribute("repoData", repoData);
        req.setAttribute("repoName", name);
        req.setAttribute("repoOwner", owner);
        RequestDispatcher dispatcher = req.getRequestDispatcher("github-analyzer-details.jsp");
        dispatcher.forward(req, resp);
    }

    private void displayHomePage(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        LOGGER.info("Displaying home page");
        RequestDispatcher dispatcher = req.getRequestDispatcher("github-analyzer-form.jsp");
        dispatcher.forward(req, resp);
    }
}