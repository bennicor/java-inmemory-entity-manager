<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Список пользователей</title>
</head>
<body>
<a href="/store">На главную</a>
<br>

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
                    <a href="customers?action=view&id=${customer.id}">Подробнее</a> |
                    <a href="customers?action=edit&id=${customer.id}">Редактировать</a> |
                    <a href="customers?action=remove&id=${customer.id}">Удалить</a>
                </td>
            </tr>
        </c:forEach>
</table>

<br><br>
<a href="customers?action=new">Добавить пользователя</a>

</body>
</html>
