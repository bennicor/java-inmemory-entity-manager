<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Список заказов пользователей</title>
</head>
<body>
<a href="/store">На главную</a>
<br>

<h1>Список заказов пользователей</h1>
<table border="1" cellpadding="6">
    <tr>
        <th>ID</th>
        <th>ID пользователя</th>
        <th>Количество товаров</th>
        <th>Дата заказа</th>
        <th>Стоимость доставки</th>
        <th>Метод оплаты</th>
        <th>Статус</th>
        <th>Изменить статус</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>${order.id}</td>
            <td>${order.customerId}</td>
            <td>${order.items.size()}</td>
            <td>${order.orderDate}</td>
            <td>${order.deliveryCost}</td>
            <td>${order.paymentMethod}</td>
            <td>${order.status}</td>
            <td>
                <form action="${pageContext.request.contextPath}/orders?page=${currentPage}" method="post">
                    <input type="hidden" name="action" value="updateStatus">
                    <input type="hidden" name="orderId" value="${order.id}">
                    <select name="status">
                        <option value="NEW" <c:if test="${order.status == 'NEW'}">selected</c:if>>NEW</option>
                        <option value="PAID" <c:if test="${order.status == 'PAID'}">selected</c:if>>PAID</option>
                        <option value="SHIPPED" <c:if test="${order.status == 'SHIPPED'}">selected</c:if>>SHIPPED</option>
                        <option value="DELIVERED" <c:if test="${order.status == 'DELIVERED'}">selected</c:if>>DELIVERED</option>
                        <option value="CANCELED" <c:if test="${order.status == 'CANCELED'}">selected</c:if>>CANCELED</option>
                    </select>
                    <input type="submit" value="ОК">
                </form>
            </td>
            <td>
                <a href="orders?action=view&id=${order.id}&page=${currentPage}">Подробнее</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br><br>

<div>
    <c:if test="${currentPage > 1}">
        <a href="orders?page=${currentPage - 1}">Предыдущая</a>
    </c:if>

    <span>Страница ${currentPage} из ${totalPages}</span>

    <c:if test="${currentPage < totalPages}">
        <a href="orders?page=${currentPage + 1}">Следующая</a>
    </c:if>
</div>
</body>
</html>
