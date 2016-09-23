<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

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
              <li><a href="${contextPath}/admin" >Your account</a></li>
              <li class="has-sub">
                <a href="#">menu</a>
                <ul>
                   <li><a href="#">menu</a></li>
                   <li><a href="#">menu</a></li>
                   <li><a href="#">menu</a></li>
                   <li><a href="#">menu</a></li>
                </ul>
              </li>
              
              <li><a href="${contextPath}/admin/console" class="position">Admin Console</a></li>
            </ul>
          </nav>
        
        <div id="contents">
            <h2 class="title">
                ADMIN CONSOLE
            </h2>
            <div class="bigbox">
                <h3>
                    Users list panel
                </h3>
                <fieldset>
                    <c:if test="${not empty message}">
			<div class="msg">${message}</div>
		    </c:if>
                    <table class="usersTable">
                        <thead>
                        <tr>
                            <th>Username</th>
                            <th>E-mail</th>
                            <th>Role</th>
                            <th>Verificated</th>
                            <th>Online</th>
                            <th>Options</th>
                        </tr>
                    </thead> 
                    <tbody>
                        <c:if test="${not empty usersList}">
                       <c:forEach var="user" items="${usersList}">
                           
                             <tr>
                             <td>${user.username}</td>
                             <td>${user.email}</td>
                             <td>
                                 <c:forEach var="role" items="${user.roles}">
                                 
                                 <c:if test = "${role.name == 'ROLE_ADMIN'}">
                                 Admin
                                 </c:if>
                                 <c:if test = "${role.name == 'ROLE_USER'}">
                                 User
                                 </c:if>
                                 </c:forEach>
                             </td>
                             <td>
                                 <c:if test="${user.enabled}">
                                 <input type="checkbox" name="remember-me" class="accept" id="accept" disabled="disabled" checked="checked"/><label for="accept" class="label_item"><img src=<c:url value="/resources/images/pentagram_checked.png" />></label>
                                 </c:if>
                                 <c:if test="${!user.enabled}">
                                 <input type="checkbox" name="remember-me" class="accept" id="accept" disabled="disabled"/><label for="accept" class="label_item"><img src=<c:url value="/resources/images/pentagram_checked.png" />></label>
                                 </c:if>
                             </td>
                             <td>
                                 <c:if test="${user.online}">
                                 <input type="checkbox" name="remember-me" class="accept" id="accept" disabled="disabled" checked="checked"/><label for="accept" class="label_item"><img src=<c:url value="/resources/images/pentagram_checked.png" />></label>
                                 </c:if>
                                 <c:if test="${!user.online}">
                                 <input type="checkbox" name="remember-me" class="accept" id="accept" disabled="disabled"/><label for="accept" class="label_item"><img src=<c:url value="/resources/images/pentagram_checked.png" />></label>
                                 </c:if>
                             </td>
                             <td class="options">
                                 <input onclick="window.location='${contextPath}/admin/console/delete/${user.id}'" name="Delete" type="submit" value="Delete" class="button1"/>
                                 <c:if test="${!user.banned}">
                                 <input onclick="window.location='${contextPath}/admin/console/ban/${user.id}'" name="Ban" type="submit" value="Ban" class="button1"/>
                                 </c:if>
                                 <c:if test="${user.banned}">
                                 <input onclick="window.location='${contextPath}/admin/console/unban/${user.id}'" name="Ban" type="submit" value="Unban" class="button1"/>
                                 </c:if>
                                 <input onclick="window.location='${contextPath}/admin/console/upgrade/${user.id}'" name="Upgrade" type="submit" value="Upgrade" class="button1"/>
                             </td>
                             </tr> 
                           
                        </c:forEach>
                        </c:if>
                     </tbody>  
                    </table>
                </fieldset>
            </div>
        </div>
    </div>

</body>
</html>
