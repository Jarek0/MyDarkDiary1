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
<script type="text/javascript" src="<c:url value="/resources/js/buttons.js" />"></script>

<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
</head>
<body>
    <div id="myConteiner">
        <div class="menu-trigger">Menu</div>
        <nav>  
            <ul class="egmenu">
              <li><a href="#">Home</a></li>
              <li class="has-sub">
                <a href="#">My profile</a>
                <ul>
                   <li><a href="${contextPath}/profile">Profile</a></li>
                   <li><a href="${contextPath}/profile/friends">Friends</a></li>
                   <li><a href="#">Blog</a></li>
                   <li><a href="#">Pictures</a></li>
                   <li><a href="#">Videos</a></li>
                </ul>
              </li>
              <li>
                <a href="${contextPath}/message">My messages</a>
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
                <a href="${contextPath}/profile/displayProfile/${user.id}">PROFILE</a>
            </div>
            <div class="title">
                <a href="${contextPath}/profile/displayFriendsOfUser/${user.id}">FRIENDS</a>
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
                 ${user.username}  <c:if test="${user.online}">
                                 <input type="checkbox" name="online" class="accept" id="accept" disabled="disabled" checked="checked"/><label for="accept" class="label_item"><img src=<c:url value="/resources/images/pentagram_checked.png" />></label>
                                 </c:if>
                                 <c:if test="${!user.online}">
                                 <input type="checkbox" name="online" class="accept" id="accept" disabled="disabled"/><label for="accept" class="label_item"><img src=<c:url value="/resources/images/pentagram_checked.png" />></label>
                                 </c:if>
                <c:if test="${!authUser.isFriend(user.id)}">
                                 <input onclick="addFriend('${contextPath}','${user.id}');" id="addFriend${user.id}" name="addFriend" type="submit" value="Add friend" class="button1"/>
                                 </c:if>
                                 <c:if test="${authUser.isFriend(user.id)}">
                                 <input onclick="deleteFriend('${contextPath}','${user.id}');" id="deleteFriend${user.id}" name="deleteFriend" type="submit" value="Delete friend" class="button1"/>
                                 </c:if>
                <input onclick="window.location='${contextPath}/message/startConversation/${user.id}'" name="writeMessage" type="submit" value="Write message" class="button1"/>
                 </h3>
                <fieldset>
               
                    <div class="imageShow">
                <span class="helper"></span>
                <img src='${contextPath}/profile/imageDisplay//${150}/${150}/${user.id}' class="avatar"/>
                
                

                </div>
                    
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
