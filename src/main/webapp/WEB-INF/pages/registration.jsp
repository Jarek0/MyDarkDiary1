<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<title>Registration Page</title>
<link href="<c:url value="/resources/css/loginAndRegistration.css" />" rel="stylesheet">
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>

<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">

</head>
<body>
    
    
        <div id="con">
	<div id="nav">  
            <ul class="egmenu">
                <li><a   href="${contextPath}/login">Login</a></li>
              <li><a class="b">Register</a></li>
            </ul>
        </div>

	<div class="login-box">

		<form:form method="POST" modelAttribute="userForm" class="form-signin">
                <fieldset>
        <table>
				<tr>
                                    <td>
        <spring:bind path="username">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="username" class="form-control" placeholder="username"
                            autofocus="true"></form:input>
                <form:errors path="username"></form:errors>
            </div>
        </spring:bind>
                                        </td>
</tr>
				<tr>
					<td>
        <spring:bind path="password">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="password" class="form-control" placeholder="password"></form:input>
                <form:errors path="password"></form:errors>
            </div>
        </spring:bind>
</td>
</tr>
				<tr>
					<td>
        <spring:bind path="passwordConfirm">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="password" path="passwordConfirm" class="form-control"
                            placeholder="Confirm your password"></form:input>
                <form:errors path="passwordConfirm"></form:errors>
            </div>
        </spring:bind>
    </td>
</tr>
				<tr>
					<td>            
        <spring:bind path="email">
            <div class="form-group ${status.error ? 'has-error' : ''}">
                <form:input type="text" path="email"
                            placeholder="e-mail"></form:input>
                <form:errors path="email"></form:errors>
            </div>
        </spring:bind>
</td>
</tr>
				<tr>
					<td>
        <button class="button" name="submit" type="submit" value="submit">Submit</button>
        </fieldset>
    </form:form>
</td>
</tr>
        </table>
	</div>
         
        </div>
</body>
</html>
