<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> <spring:message code="title"/> </a>
            <div class="pull-right">
            	<a class="navbar-brand" href="<spring:message code="lang.url"/>"><spring:message code="lang"/></a>
            </div>
        </div>
    </header>
    
    <c:set var="searchValue" value="" />
	<c:if test="${search != null && search != ''}">
		<c:set var="searchValue" value="&search=${search}" />
	</c:if>
	
	<c:set var="nbByPageValue" value="" />
	<c:if test="${page.itemsByPage != null && page.itemsByPage != ''}">
		<c:set var="nbByPageValue" value="&nbByPage=${page.itemsByPage}" />
	</c:if>
	
	<c:set var="orderValue" value="" />
	<c:if test="${page.order != null && page.order != ''}">
		<c:set var="orderValue" value="&order=${page.order}" />
	</c:if>
	
	<c:set var="ascendingValue" value="" />
	<c:if test="${page.ascending != null && page.ascending != ''}">
		<c:set var="ascendingValue" value="&ascending=${page.ascending}" />
	</c:if>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
               <c:out value="${nbComputers}"/> <spring:message code="dashboard.computer.found"/>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="dashboard" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="<spring:message code="dashboard.search.placeholder"/>" value="${search}"/>
                        <input type="submit" id="searchsubmit" value="<spring:message code="dashboard.filter.button"/>"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer"><spring:message code="dashboard.addComputer.button"/></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><spring:message code="dashboard.edit.button"/></a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="dashboard" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                        	<c:if test="${page.order == 'computer.name' && page.ascending == 'ASC'}"><a href="dashboard?order=computer.name&ascending=DESC${nbByPageValue}${searchValue}" ><spring:message code="dashboard.computerName"/></a></c:if>
                        	<c:if test="${page.order == 'computer.name' && page.ascending == 'DESC'}"><a href="dashboard?order=computer.name&ascending=ASC${nbByPageValue}${searchValue}" ><spring:message code="dashboard.computerName"/></a></c:if>
                        	<c:if test="${page.order != 'computer.name'}"><a href="dashboard?order=computer.name&ascending=ASC${nbByPageValue}${searchValue}" ><spring:message code="dashboard.computerName"/></a></c:if>
                            
                        </th>
                        <th>
                        	<c:if test="${page.order == 'computer.introduced' && page.ascending == 'ASC'}"><a href="dashboard?order=computer.introduced&ascending=DESC${nbByPageValue}${searchValue}"><spring:message code="dashboard.introduced"/></a></c:if>
                        	<c:if test="${page.order == 'computer.introduced' && page.ascending == 'DESC'}"><a href="dashboard?order=computer.introduced&ascending=ASC${nbByPageValue}${searchValue}"><spring:message code="dashboard.introduced"/></a></c:if>
                        	<c:if test="${page.order != 'computer.introduced'}"><a href="dashboard?order=computer.introduced&ascending=ASC${nbByPageValue}${searchValue}"><spring:message code="dashboard.introduced"/></a></c:if>
                            
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                        	<c:if test="${page.order == 'computer.discontinued' && page.ascending == 'ASC'}"><a href="dashboard?order=computer.discontinued&ascending=DESC${nbByPageValue}${searchValue}"><spring:message code="dashboard.discontinued"/></a></c:if>
                        	<c:if test="${page.order == 'computer.discontinued' && page.ascending == 'DESC'}"><a href="dashboard?order=computer.discontinued&ascending=ASC${nbByPageValue}${searchValue}"><spring:message code="dashboard.discontinued"/></a></c:if>
                        	<c:if test="${page.order != 'computer.discontinued'}"><a href="dashboard?order=computer.discontinued&ascending=ASC${nbByPageValue}${searchValue}"><spring:message code="dashboard.discontinued"/></a></c:if>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                        	<c:if test="${page.order == 'cp.name' && page.ascending == 'ASC'}"> <a href="dashboard?order=cp.name&ascending=DESC${nbByPageValue}${searchValue}"><spring:message code="dashboard.company"/></a></c:if>
                        	<c:if test="${page.order == 'cp.name' && page.ascending == 'DESC'}"> <a href="dashboard?order=cp.name&ascending=ASC${nbByPageValue}${searchValue}"><spring:message code="dashboard.company"/></a></c:if>
                        	<c:if test="${page.order != 'cp.name'}"> <a href="dashboard?order=cp.name&ascending=ASC${nbByPageValue}${searchValue}"><spring:message code="dashboard.company"/></a></c:if>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach items="${computers}" var="computer">
                		<tr>
                			<td class="editMode">
                            	<input type="checkbox" name="cb" class="cb" value="${computer.idComputer}">
                        	</td>
                        	<td>
                            	<a href="editComputer?id=${computer.idComputer}" onclick=""><c:out value="${computer.name}"/></a>
                        	</td>
                        	<td><c:if test="${not empty computer.introduced}"><c:out value="${computer.introduced}"/></c:if></td>
                        	<td><c:if test="${not empty computer.discontinued}"><c:out value="${computer.discontinued}"/></c:if></td>
                        	<td><c:if test="${not empty computer.companyDto}"><c:out value="${computer.companyDto.name}"/></c:if></td>
                		</tr>
                	</c:forEach>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
                <li>
                	<c:if test="${page.currentPage > 1}">
                    	<a href="dashboard?page=${page.currentPage-1}${nbByPageValue}${searchValue}${orderValue}${ascendingValue}" aria-label="Previous">
                      		<span aria-hidden="true">&laquo;</span>
                  		</a>
                	</c:if>
              	</li>
              	<c:forEach var="i" begin="1" end="2">
              		<c:if test="${page.currentPage-(3-i) >= 1}">
              			<li><a href="dashboard?page=${page.currentPage-(3-i)}${nbByPageValue}${searchValue}${orderValue}${ascendingValue}"><c:out value="${page.currentPage-(3-i)}"/></a></li>
              		</c:if>
              	</c:forEach>
              	<c:forEach var="i" begin="0" end="2">
              		<c:if test="${page.currentPage+i <= nbPagesMax}">
              			<c:set var="active" value=""/>
                        <c:if test = "${page.currentPage+i ==  page.currentPage}">
                          <c:set var="active" value="active"/>
                         </c:if>
              			<li class="${active}"><a href="dashboard?page=${page.currentPage+i}${nbByPageValue}${searchValue}${orderValue}${ascendingValue}"><c:out value="${page.currentPage+i}"/></a></li>
              		</c:if>
              	</c:forEach>
              	<li>
              		<c:if test="${page.currentPage < nbPagesMax}">
                		<a href="dashboard?page=${page.currentPage+1}${nbByPageValue}${searchValue}${orderValue}${ascendingValue}" aria-label="Next">
                    		<span aria-hidden="true">&raquo;</span>
                		</a>
                	</c:if>
            	</li>
        	</ul>
        	<div class="btn-group btn-group-sm pull-right" role="group" >
        	<c:set var="active" value=""/>
            <c:if test = "${page.itemsByPage == 10}">
               <c:set var="active" value="active"/>
            </c:if>
            <a href="dashboard?nbByPage=10${searchValue}${orderValue}${ascendingValue}"><button type="button" class="btn btn-default ${active}">10</button></a>
            <c:set var="active" value=""/>
            <c:if test = "${page.itemsByPage == 50}">
               <c:set var="active" value="active"/>
            </c:if>
            <a href="dashboard?nbByPage=50${searchValue}${orderValue}${ascendingValue}"><button type="button" class="btn btn-default ${active}">50</button></a>
            <c:set var="active" value=""/>
            <c:if test = "${page.itemsByPage == 100}">
               <c:set var="active" value="active"/>
            </c:if>
            <a href="dashboard?nbByPage=100${searchValue}${orderValue}${ascendingValue}"><button type="button" class="btn btn-default ${active}">100</button></a>
        	</div>
        </div>

    </footer>
<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/bootstrap.min.js"></script>
<script src="resources/js/dashboard.js"></script>

</body>
</html>