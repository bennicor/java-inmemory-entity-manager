<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Список корзин пользователей</title>
</head>
<body>
<a href="/store">На главную</a>
<br>

<h1>Список корзин пользователей</h1>
<table border="1" cellpadding="6">
    <tr>
        <th>ID</th>
        <th>ID пользователя</th>
        <th>Количество товаров</th>
        <th>Управление</th>
    </tr>
        <c:forEach var="cart" items="${carts}">
            <tr>
                <td>${cart.id}</td>
                <td>${cart.customerId}</td>
                <td>${cart.items.size()}</td>
                <td>
                    <a href="carts?action=view&id=${cart.customerId}&page=${currentPage}">Подробнее</a>
                </td>
            </tr>
        </c:forEach>
</table>
<br><br>

<div>
    <c:if test="${currentPage > 1}">
        <a href="carts?page=${currentPage - 1}">Предыдущая</a>
    </c:if>

    <span>Страница ${currentPage} из ${totalPages}</span>

    <c:if test="${currentPage < totalPages}">
        <a href="carts?page=${currentPage + 1}">Следующая</a>
    </c:if>
</div>

</body>
</html>
