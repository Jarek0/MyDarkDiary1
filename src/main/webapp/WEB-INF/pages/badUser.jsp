<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page session="true" %>
<html>
<head>
    
<link href="<c:url value="/resources/css/style.css" />" rel="stylesheet">
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <title>Bad user</title>
</head>
<body>
<h1>
    <div class="alert alert-error">
                   ${param.message}
</h1>
<br>
<a href="<c:url value="registration" />"><spring:message
code="label.form.loginSignUp"></spring:message></a>
 
<c:if test="${param.expired}">
<br>
<h1>${label.form.resendRegistrationToken}</h1>
<button onclick="resendToken()">
    <spring:message code="label.form.resendRegistrationToken"></spring:message>
</button>
 
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script type="text/javascript">
function resendToken(){
    $.get("<c:url value="/user/resendRegistrationToken">
        <c:param name="token" value="${param.token}"/></c:url>", function(data){
            window.location.href = 
                "<c:url value="/login.html"></c:url>" + "?message=" + data.message;
    })
    .fail(function(data) {
        if(data.responseJSON.error.indexOf("MailError") > -1) {
            window.location.href = "<c:url value="/emailError.html"></c:url>";
        }
        else {
            window.location.href = 
              "<c:url value="/login.html"></c:url>" + "?message=" + data.responseJSON.message;
        }
    });
}
</script>
</c:if>
</body>
</html>