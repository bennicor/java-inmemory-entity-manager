<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${customer.id == null ? "Добавить" : "Редактировать"} пользователя</title>
</head>
<body>
<h2>${customer.id == null ? "Добавить" : "Редактировать"} пользователя</h2>

<form action="${pageContext.request.contextPath}/customers" method="post">
    <input type="hidden" name="action" value="${customer.id == null ? "new" : "edit"}"/>
    <input type="hidden" name="id" value="${customer.id}"/>

    <label>Имя:</label><input type="text" name="firstName" value="${customer.firstName}" required><br>
    <label>Фамилия:</label><input type="text" name="lastName" value="${customer.lastName}" required><br>
    <label>Отчество:</label><input type="text" name="middleName" value="${customer.middleName}"><br>
    <label>Номер телефона:</label><input type="text" name="phone" value="${customer.phone}"><br>
    <label>Email:</label><input type="text" name="email" value="${customer.email}" required><br>
    <label>Адрес:</label><input type="text" name="address" value="${customer.address}" required><br>
    <input type="submit" value="Сохранить">
</form>

<br>
<a href="${pageContext.request.contextPath}/customers?action=list">На главную</a>

</body>
</html>
