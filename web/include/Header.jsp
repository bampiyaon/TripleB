<%-- 
    Document   : Header
    Created on : Nov 13, 2018, 10:07:46 AM
    Author     : piyao
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="container-fluid" style="background-color: ${cookie.bgColor.value}">
    <table class="table">
        <tr>
            <td><a href="index.html" title="Back To Menu"><img src="logo.jpg" width="150"></a></td>
            <td><h1>${param.title}</h1></td>
            <td>
                <c:if test="${cart != null}">
                    <a href="ShowCartView.jsp">Your Cart: (${cart.totalQuantity})</a>
                </c:if>
                &nbsp;&nbsp;&nbsp;
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                        Hello ${sessionScope.user.name}
                    </c:when>
                    <c:otherwise>
                        Hello Guest
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </table>
</div>
<hr>
Session Id : ${cookie.JSESSIONID.value}
<hr>
