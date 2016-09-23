<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
    <head>
<title>Admin Console</title>


<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/menu.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/form.css" />" rel="stylesheet">

<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/menu.js" />"></script>

<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
</head>
<body>
    
    <div id="conteiner">
        <div class="menu-trigger">Menu</div>
        <nav>  
            <ul class="egmenu">
              <li><a href="${contextPath}/admin" class="position">Your account</a></li>
              <li class="has-sub">
                <a href="#">menu</a>
                <ul>
                   <li><a href="#">menu</a></li>
                   <li><a href="#">menu</a></li>
                   <li><a href="#">menu</a></li>
                   <li><a href="#">menu</a></li>
                </ul>
              </li>
              
              <li><a href="${contextPath}/admin/console">Admin Console</a></li>
            </ul>
          </nav>
        
        <div id="contents">
            <h2 class="title">
                YOUR ACOUNT
            </h2>
            <div class="bigbox">
                <h3>
                    ${title}
                </h3>
                <fieldset>
                <p>
                    ${message}
                    <c:url value="/j_spring_security_logout" var="logoutUrl" />
	            <form action="${logoutUrl}" method="post" id="logoutForm">
		    <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	            </form>
	            <script>
		    function formSubmit() {
			document.getElementById("logoutForm").submit();
		    }
	            </script>
                    
                    <sec:authorize access="isRememberMe()">
		    # This user is login by "Remember Me Cookies".
	            </sec:authorize>

	            <sec:authorize access="isFullyAuthenticated()">
		    # This user is login by username / password.
	            </sec:authorize>
                    
                    <c:if test="${pageContext.request.userPrincipal.name != null}">
		
			Welcome : ${pageContext.request.userPrincipal.name} | 
                        <%-- <a href=""> Logout</a> --%>
                        <input onclick="javascript:formSubmit()" name="Logout" type="submit" value="Logout" class="button"/>
	            </c:if>
                </p>
                </fieldset>
            </div>
        </div>
    </div>

</body>
</html>