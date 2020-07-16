<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="springForm"%>
<!DOCTYPE html>
<html>
<head>
<title><spring:message code="title"/></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="resources/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="resources/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"><spring:message code="title"/></a>
            <div class="pull-right">
            	<a class="navbar-brand" href="<spring:message code="lang.url"/>"><spring:message code="lang"/></a>
            </div>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="addComputer.title"/></h1>
                    <c:if test="${error != null}"><div class="alert alert-danger"><c:out value="${error}" /></div></c:if>
					<c:if test="${success != null}"><div class="alert alert-success"><c:out value="${success}"/></div></c:if>
                    <springForm:form action="addComputer" method="POST" modelAttribute="computerDto" onsubmit="return checkDate()">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><spring:message code="form.input.name"/></label>
                                <springForm:input type="text" class="form-control" id="computerName" path="name" name="computerName" placeholder="Computer name"/>
                                <springForm:errors path="name" cssClass="alert-danger"/>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="form.input.introduced"/></label>
                                <springForm:input type="date" class="form-control" id="introduced" path="introduced" name="introduced" placeholder="Introduced date"/>
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="form.input.discontinued"/></label>
                                <springForm:input type="date" class="form-control" id="discontinued" path="discontinued" name="discontinued" placeholder="Discontinued date"/>
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="form.input.company"/></label>
                                <springForm:select class="form-control" path="companyDto.idCompany" id="companyId" name="idCompany">                                	
                                	<option value="0">--</option>
                                	<c:forEach items="${companies}" var="company">
                                		<c:set var="selected" value=""/>
                                		<c:if test = "${company.idCompany == computerDto.companyDto.idCompany}">
                                			<c:set var="selected" value="selected"/>
                                		</c:if>
                                		<option value="${company.idCompany}" ${selected}><c:out value="${company.name}"/></option>
                                	</c:forEach>
                                </springForm:select>
                            </div>                  
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="form.button.add"/>" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default"><spring:message code="form.button.cancel"/></a>
                        </div>
                    </springForm:form>
                </div>
            </div>
        </div>
    </section>
<script src="resources/js/jquery.min.js"></script>
<script src="resources/js/addComputer.js"></script>
</body>
</html>