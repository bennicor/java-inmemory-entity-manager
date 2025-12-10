<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Список пользователей</title>
</head>
<body>
<a href="/store">На главную</a>
<br>
<a href="customers?action=new">Добавить пользователя</a>

<h1>Список пользователей</h1>
<table border="1" cellpadding="6">
    <tr>
        <th>ID</a></th>
        <th>Имя</th>
        <th>Фамилия</th>
        <th>Отчество</th>
        <th>Номер телефона</th>
        <th>Email</th>
        <th>Адрес</th>
        <th>Управление</th>
    </tr>
        <c:forEach var="customer" items="${customers}">
            <tr>
                <td>${customer.id}</td>
                <td>${customer.firstName}</td>
                <td>${customer.lastName}</td>
                <td>${customer.middleName}</td>
                <td>${customer.phone}</td>
                <td>${customer.email}</td>
                <td>${customer.address}</td>
                <td>
                    <a href="customers?action=view&id=${customer.id}&page=${currentPage}">Подробнее</a> |
                    <a href="customers?action=edit&id=${customer.id}&page=${currentPage}">Редактировать</a> |
                    <a href="customers?action=remove&id=${customer.id}&page=${currentPage}">Удалить</a>
                </td>
            </tr>
        </c:forEach>
</table>

<br><br>

<div>
    <c:if test="${currentPage > 1}">
        <a href="customers?page=${currentPage - 1}">Предыдущая</a>
    </c:if>

    <span>Страница ${currentPage} из ${totalPages}</span>

    <c:if test="${currentPage < totalPages}">
        <a href="customers?page=${currentPage + 1}">Следующая</a>
    </c:if>
</div>
</body>
</html>
