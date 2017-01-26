<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
    <head>
<title>Message</title>


<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/menu.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/form.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/message.css" />" rel="stylesheet">
<link href="<c:url value="/resources/css/perfect-scrollbar.min.css" />" rel="stylesheet">

<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript" src="<c:url value="/resources/js/menu.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/profile.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/chat.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/perfect-scrollbar.jquery.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/perfect-scrollbar.min.js" />"></script>
<script src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.1/sockjs.min.js" />"></script>
<script src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js" />"></script>

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
                <a href="${contextPath}/message" class="position">My messages</a>
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
        
        
            
        <div id="contentsNarrower">
            <div class="title position">
                <a href="${contextPath}/message">All messages</a>
            </div>
            
            
            <div class="bigbox">
                <h3>
                 Messages
                </h3>
                <fieldset>
                <div class="conteiner">
                <div class="content" id="content">
                
                </div>
                </div>
                     
                <textarea class="messageInput" onkeyup="countChar(this)" name="message" rows="3" cols="70" placeholder="write message"></textarea>
                <input type="submit" value="send" class="button"><div id="charNum"></div>
                </fieldset>
            </div>
        </div>
                <div id="sidePanel">
            <div class="box">
                <h2 class="title">
                    CONVERSATIONS
                </h2>
                <fieldset>
                <div class="conteiner2">
                    <div class="content">
                   <c:if test="${not empty conversations}">
                   <c:forEach var="conversation" items="${conversations}" varStatus = "loopCount">
                  <c:if test="${not empty conversation}">
                  
                  <div class="conversation" id="conversation{conversation.id}">
                  
                  <div class="image"><span class="displayAvatar"><img src='${contextPath}/profile/imageDisplay/${80}/${80}/${conversation.users[1].id}' class="avatar"/></span></div>
                  <span class="displayName">${conversation.users[1].username}</span>
                  
                  <c:if test="${conversation.users[1].online}">
                  <input type="checkbox" name="online" class="accept" id="accept" disabled="disabled" checked="checked"/><label for="accept" class="label_item"><img src=<c:url value="/resources/images/pentagram_checked.png" />></label>
                  </c:if>
                  <c:if test="${!conversation.users[1].online}">
                  <input type="checkbox" name="online" class="accept" id="accept" disabled="disabled"/><label for="accept" class="label_item"><img src=<c:url value="/resources/images/pentagram_checked.png" />></label>
                  </c:if><div class="date">${conversation.messages[0].displayDate()}</div>
                  <div class="last_message">${conversation.messages[0].content}</div>
                  
                      <c:if test="${loopCount.count eq 1}">
                      <span class="hyperspan" id="first" onclick="changeColor(this,'${contextPath}','${conversation.id}')">
                      </c:if>
                     <c:if test="${!(loopCount.count eq 1)}">
                      <span class="hyperspan" onclick="changeColor(this,'${contextPath}','${conversation.id}')">
                      </c:if>
                  </span>
                  </div>
                  </c:if>
                  
                  </c:forEach>
                 </c:if>
                 <c:if test="${empty conversations}">
                     You do not have any conversations.
                 </c:if>
                  </div>
                  </div>
                </fieldset>
            </div>
    </div>
                   
</body>
</html>
