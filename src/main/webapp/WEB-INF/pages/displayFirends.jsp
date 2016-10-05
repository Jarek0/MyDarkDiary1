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
                   <li><a href="${contextPath}/profile">Profile</a></li>
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
            <div class="title">
                <a href="${contextPath}/profile/${user.id}">PROFILE</a>
            </div>
            <div class="title position">
                <a href="${contextPath}/profile/friends">FRIENDS</a>
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
                 <form:form method='POST' action="${contextPath}/search?${_csrf.parameterName}=${_csrf.token}">
                    <input id="text" type="text" name="text" placeholder="Search users"/> 
                    <select name="searchBy" required>
                    <option value="" disabled selected hidden>search by</option>
		    <option value="username">username</option>
		    <option value="e-mail">e-mail</option>
	            </select>
                    <select name="online" required>
                    <option value="" disabled selected hidden>online</option>
                    <option value="${0}">yes</option>
		    <option value="${1}">no</option>
                    <option value="${2}">nvm</option>
	            </select>
                    <select name="role" required>
                    <option value="" disabled selected hidden>role</option>
                    <option value="ROLE_ADMIN">Admin</option>
		    <option value="ROLE_USER">User</option>
                    <option value="nvm">nvm</option>
	            </select>
                    <input width="15" height="15" type="image" src="<c:url value="/resources/images/search.jpg" />" onclick="this.form.submit()">
                    </form:form>
                </h3>
                <fieldset>
                <c:if test="${not empty usersList}">
                       <c:forEach var="user" items="${usersList}">
                           <div class="user">
                               <div class="image-upload"><a href="${contextPath}/profile/${user.id}" class="displayImage"><img src='${contextPath}/profile/imageDisplay/${100}/${100}/${user.id}' class="avatar"/></a></div>
                               <a href="${contextPath}/profile/${user.id}" class="displayProfile">${user.username}</a>
                               
                               <c:if test="${user.online}">
                                 <input type="checkbox" name="online" class="accept" id="accept" disabled="disabled" checked="checked"/><label for="accept" class="label_item"><img src=<c:url value="/resources/images/pentagram_checked.png" />></label>
                                 </c:if>
                                 <c:if test="${!user.online}">
                                 <input type="checkbox" name="online" class="accept" id="accept" disabled="disabled"/><label for="accept" class="label_item"><img src=<c:url value="/resources/images/pentagram_checked.png" />></label>
                                 </c:if>
                                 <c:forEach var="role" items="${user.roles}">
                                 
                                 <c:if test = "${role.name == 'ROLE_ADMIN'}">
                                     <div class="userRole">Admin</div>
                                 </c:if>
                                 <c:if test = "${role.name == 'ROLE_USER'}">
                                 <div class="userRole">User</div>
                                 </c:if>
                                 </c:forEach>
                                 <c:if test="${user.id!=userId}">
                                 <input onclick="window.location='${contextPath}/addFriend/${user.id}/${displayFriends}'" name="addFriend" type="submit" value="Add friend" class="button1"/>
                                 <input onclick="window.location='${contextPath}/writeMessage/${user.id}'" name="writeMessage" type="submit" value="Write message" class="button1"/>
                                 </c:if>
                                 </div>
                       </c:forEach>
                </c:if>
                </fieldset>
            </div>
        </div>
    </div>

</body>
</html>
