<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
    <head>
<title>Profile</title>


<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/menu.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/form.css" />" rel="stylesheet">

<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/menu.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/profile.js" />"></script>

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
              <li><a href="#">Home</a></li>
              <li class="has-sub">
                <a href="#" class="position">My profile</a>
                <ul>
                   <li><a href="${contextPath}/profile/${user.id}">Profile</a></li>
                   <li><a href="${contextPath}/profile/friends">Friends</a></li>
                   <li><a href="#">Blog</a></li>
                   <li><a href="#">Pictures</a></li>
                   <li><a href="#">Videos</a></li>
                </ul>
              </li>
              <li class="has-sub">
                <a href="#">My messages</a>
                <ul>
                   <li><a href="#">All</a></li>
                   <li><a href="#">Sended</a></li>
                   <li><a href="#">Received</a></li>
                </ul>
              </li>
              
              <li><sec:authorize access="hasRole('ROLE_ADMIN')"><a href="${contextPath}/admin/console">Admin Console</a></sec:authorize>
            <sec:authorize access="hasRole('ROLE_USER')"><a href="">User Console</a></sec:authorize></li>
              
              <li><a href="${contextPath}/search" >Members</a></li>
               <li><a  onclick="javascript:formSubmit()">Logout</a></li>
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
            </ul>
          </nav>
        
        
            
        <div id="contents">
            <div class="title position">
                <a href="${contextPath}/profile/${user.id}">PROFILE</a>
            </div>
            <div class="title">
                <li><a href="${contextPath}/profile/friends">FRIENDS</a></li>
            </div>
            <div class="title">
                <a href="#">BLOG</a>
            </div>
            <div class="title">
                <a href="#">PICTURES</a>
            </div>
            <div class="title">
                <a href="#">VIDEOS</a>
            </div>
            <div class="bigbox">
                <h3>
                 ${pageContext.request.userPrincipal.name}
                </h3>
                <fieldset>
               
                
		<form:form method='POST' action='${contextPath}/profile/doUpload?${_csrf.parameterName}=${_csrf.token}'
		enctype='multipart/form-data'>
                
		<div class="image-upload">
                <label for="file-input">
                <span class="helper"></span>
                <img src="<c:url value="/resources/images/camera.png" />" class="cameraImage">
                <img src='${contextPath}/profile/imageDisplay/${150}/${150}/${user.id}' class="avatar"/>
                
                </label>

                <input id="file-input" type="file" onchange="this.form.submit()" name="file" />
                </div>

	</form:form>

                    
                <p class="discription">${user.discription}<img src="<c:url value="/resources/images/write-icon.png" />" class="writeImage">
                </p>
                <form:form method='POST' action="${contextPath}/profile/changeDiscription?${_csrf.parameterName}=${_csrf.token}" id="discriptionForm">
                <textarea name="discription" onkeyup="countChar(this)" name="discription" rows="6" cols="90" placeholder="Your discription">${user.discription}</textarea>
                <input type="submit" value="accept" class="button"> <input onclick="window.location='${contextPath}/profile'" type="button" value="cancel" class="button"> <div id="charNum"></div>
                </form:form>
                </fieldset>
            </div>
        </div>
    </div>

</body>
</html>