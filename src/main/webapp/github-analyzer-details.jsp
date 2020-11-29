<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>

<head>
    <title>GitHub Analyzer</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/util/css/style.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://canvasjs.com/assets/script/jquery.canvasjs.min.js"></script>
    <script src="<%=request.getContextPath()%>/util/js/github-analyzer.js"></script>
    <script>
        var DATAPOINTS = [];
        <c:forEach var="data" items="${dataPoints}">
        DATAPOINTS.push({
            label: "${data.label}",
            y: ${data.y}
        });
        </c:forEach>
    </script>
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
    <div class="card">
        <div class="card-body">
            <div class="jumbotron">
                <h1>${repoName}</h1>
                <p>By ${repoOwner}</p>
            </div>
            <div>
                <!-- Nav tabs -->
                <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a href="#" data-tab="analysis" aria-controls="analyzed commits" role="tab" data-toggle="tab">Analysis</a></li>
                    <li role="presentation"><a href="#" id="projectionsLink" data-tab="projections" aria-controls="projections" role="tab" data-toggle="tab">Projections</a></li>
                    <li role="presentation"><a href="#" data-tab="rawdata" aria-controls="raw data" role="tab" data-toggle="tab">Raw Data</a></li>
                </ul>

                <!-- Tab panes -->
                <div class="tab-content">
                    <div role="tabpanel" class="tab-pane active" id="analysis">
                        <br>
                        <c:if test="${not empty repoData}">
                            <table class="table table-striped">
                                <tr>
                                    <th>User</th>
                                    <th>Number of Commits</th>
                                    <th>Contribution</th>
                                </tr>
                                <c:forEach var="userCommit" items="${numCommitsPerUser}">
                                    <tr>
                                        <td>${userCommit.key}</td>
                                        <td>${userCommit.value}</td>
                                        <td>${impactPerUser.get(userCommit.key)}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="projections">
                        <br>
                        <div id="chartContainer">
                        </div>
                    </div>
                    <div role="tabpanel" class="tab-pane" id="rawdata">
                        <br>
                        <c:if test="${not empty repoData}">
                            <table class="table table-striped">
                                <tr>
                                    <th>No.</th>
                                    <th>User</th>
                                    <th>Email</th>
                                    <th>Commit Date</th>
                                </tr>
                                <c:forEach varStatus="commitStatus" var="commit" items="${repoData}">
                                    <tr>
                                        <td>${commitStatus.index + 1}</td>
                                        <td>${commit.committerName}</td>
                                        <td>${commit.committerEmail}</td>
                                        <td>${commit.commitDate}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>