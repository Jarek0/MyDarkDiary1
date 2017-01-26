<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html>
    <head>
<title>Admin Console</title>


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
                   <a href="${contextPath}/profile/friends">Friends</a>
                   <li><a href="#">Blog</a></li>
                   <li><a href="#">Pictures</a></li>
                   <li><a href="#">Videos</a></li>
                </ul>
              </li>
              <li>
                <a href="${contextPath}/message">My messages</a>
              </li>
              <li><sec:authorize access="hasRole('ROLE_ADMIN')"><a href="${contextPath}/admin/console" class="position">Admin Console</a></sec:authorize>
            <sec:authorize access="hasRole('ROLE_USER')"><a href="" class="position">User Console</a></sec:authorize></li>
              <li><a href="${contextPath}/search" >Members</a></li>
               <li><a onclick="javascript:formSubmit()">Logout</a></li>
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
                <a href="${contextPath}/admin/console">ADMIN CONSOLE</a>
            </div>
            <div class="bigbox">
                <h3>
                    <form:form method='POST' action="${contextPath}/admin/console/search?${_csrf.parameterName}=${_csrf.token}">
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
                    <select name="verificated" required>
                    <option value="" disabled selected hidden>verificated</option>
                    <option value="${0}">yes</option>
		    <option value="${1}">no</option>
                    <option value="${2}">nvm</option>
	            </select>
                    <select name="banned" required>
                    <option value="" disabled selected hidden>banned</option>
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
                    <div class="msg" id="result"></div>
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
                           
                             <tr id="Row${user.id}">
                                 <c:if test="${!(authUser.id==user.id)}">
                                 <td><a href="${contextPath}/profile/displayProfile/${user.id}" class="displayProfile">${user.username}</a></td>
                                 </c:if>
                                 <c:if test="${(authUser.id==user.id)}">
                                 <td><a href="${contextPath}/profile" class="displayProfile">${user.username}</a></td>
                                 </c:if>
                             <td>${user.email}</td>
                             <td id="Role${user.id}">
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
                                 <input type="checkbox" name="enabled" class="accept" id="accept" disabled="disabled" checked="checked"/><label for="accept" class="label_item"><img src=<c:url value="/resources/images/pentagram_checked.png" />></label>
                                 </c:if>
                                 <c:if test="${!user.enabled}">
                                 <input type="checkbox" name="enabled" class="accept" id="accept" disabled="disabled"/><label for="accept" class="label_item"><img src=<c:url value="/resources/images/pentagram_checked.png" />></label>
                                 </c:if>
                             </td>
                             <td>
                                 <c:if test="${user.online}">
                                 <input type="checkbox" name="online" class="accept" id="accept" disabled="disabled" checked="checked"/><label for="accept" class="label_item"><img src=<c:url value="/resources/images/pentagram_checked.png" />></label>
                                 </c:if>
                                 <c:if test="${!user.online}">
                                 <input type="checkbox" name="online" class="accept" id="accept" disabled="disabled"/><label for="accept" class="label_item"><img src=<c:url value="/resources/images/pentagram_checked.png" />></label>
                                 </c:if>
                             </td>
                             <td class="options">
                                 
                                 <input onclick="deleteUser('${contextPath}','${user.id}');" id="Delete${user.id}" name="Delete" type="submit" value="Delete" class="button1"/>
                                 
                                 <c:if test="${!user.banned}">
                                 <input onclick="banUser('${contextPath}','${user.id}');" id="Ban${user.id}" name="Ban" type="submit" value="Ban" class="button1"/>
                                 
                                 </c:if>
                                 <c:if test="${user.banned}">
                                 <input onclick="unbanUser('${contextPath}','${user.id}');" id="Unban${user.id}" name="Unban" type="submit" value="Unban" class="button1"/>
                                 </c:if>
                                 <input onclick="upgradeUser('${contextPath}','${user.id}');" id="Upgrade${user.id}" name="Upgrade" type="submit" value="Upgrade" class="button1"/>
                                 
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
