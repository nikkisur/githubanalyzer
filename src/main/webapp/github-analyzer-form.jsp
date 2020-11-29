<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
    <title>GitHub Analyzer</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/util/css/style.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="<%=request.getContextPath()%>/util/js/github-analyzer.js"></script>
</head>

<body>
<header>
    <nav class="navbar navbar-default">
        <div>
            <a class="navbar-brand"> GitHub Analyzer </a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="<%=request.getContextPath()%>/" class="nav-link">Home</a></li>
        </ul>
    </nav>
</header>
<div id="loader" class="hide-on-load"></div>
<div class="container">
    <div class="searchRepo">
        <div class="jumbotron">
            <h1>Search GitHub Repositories</h1>
            <br>
            <form class="form-inline" action="search" method="get">
                <div class="form-group">
                    <label for="categoryList" class="control-label">Category</label>
                    <select class="form-control" name="searchCategory" id="categoryList" required="required">
                        <option value="topic">Topic</option>
                        <option value="user">User</option>
                        <option value="repo">Name</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="searchKey" class="control-label">Search Key</label>
                    <input type="text" class="form-control" id="searchKey" name="searchKey" required="required">
                </div>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary">Go!</button>
                </div>
            </form>
        </div>
    </div>
    <div class="selectRepo">
        <c:if test="${hasResults == 'true'}">
            <h4 class="text-success">Search results for ${searchCategory} : ${searchKey}</h4>
            <form id="selectRepoForm" action="select" method="get">
                <table class="table table-striped">
                    <tr>
                        <th>No.</th>
                        <th>Name</th>
                        <th>Owner</th>
                        <th>Description</th>
                        <th>Action</th>
                    </tr>
                    <c:forEach varStatus="repoStatus" var="repo" items="${resultList}">
                        <tr>
                            <td>${repoStatus.index + 1}</td>
                            <td>${repo.name}</td>
                            <td>${repo.owner}</td>
                            <td>${repo.description}</td>
                            <td><button type="submit" class="btn btn-default selectRepoBtn" data-owner="${repo.owner}" data-name="${repo.name}">Open</button></td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
        </c:if>
        <c:if test="${hasResults == 'false'}">
            <h4 class="text-danger">No results for ${searchCategory} : ${searchKey}. Please try again.</h4>
        </c:if>
        <c:if test="${hasEncounteredError == 'true'}">
            <h4 class="text-danger">Sorry, we encountered an error. Please try again.</h4>
        </c:if>
    </div>
</div>
</body>
</html>