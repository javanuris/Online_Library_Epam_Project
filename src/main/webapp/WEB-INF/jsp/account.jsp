<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 09.04.2017
  Time: 12:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<my:design-pattern role="${sessionScope.role}">
 <my:role-account role="${sessionScope.role}"/>
</my:design-pattern>