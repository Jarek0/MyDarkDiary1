<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<title>Login Page</title>


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
                <li><a class="b" >Login</a></li>
              <li><a href="${contextPath}/registration">Register</a></li>
            </ul>
        </div>

	<div class="login-box">

		<form name='loginForm' 
			action="<c:url value='/auth/login_check?targetUrl=${targetUrl}' />"
			method='POST'>
                        <fieldset>
                <c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
               <c:if test="${param.error != null}">
                        <div class="alert alert-danger">
                        ${SPRING_SECURITY_LAST_EXCEPTION}
                        </div> 
                </c:if>
                
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
                            <%--<p>Welcome in My dark diary website, please login to your account</p>--%>
			<table>
				<tr>
                                    <td>
                                            <input type='text' name='username' placeholder="username"></td>
				</tr>
				<tr>
					<td>
					<input type='password' name='password' placeholder="password"/></td>
				</tr>

				<!-- if this is login for update, ignore remember me check -->
				<c:if test="${empty loginUpdate}">
					<tr>
						
                                            <td><input type="checkbox" name="remember-me" class="accept" id="accept"/><label for="accept" class="label_item"><img src=<c:url value="/resources/images/pentagram_checked.png" />>Remember me</label></td>
					</tr>
				</c:if>

				<tr>
					<td><input name="submit" type="submit"
						value="submit" class="button"/></td>
				</tr>

			</table>

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
                        </fieldset>
		</form>

	</div>
        
        </div>
</body>
</html>