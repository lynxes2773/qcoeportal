<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>QCOE Portal</title>
</head>
<body>
<h1>QCOE Portal: List of Requirements</h1>

	<table>
	<c:forEach items="${requirements}" var="req">
		<tr class="listing">
			<td align='top' valign='top'>${req.requirementId}</td>
			<td align='top' valign='top'>${req.requirementText}</td>
			<td valign='top'><img src="${pageContext.servletContext.contextPath}/getRequirement/${req.requirementId}.htm" /></td>
		</tr>
	</c:forEach>
	</table>
</body>
</html>