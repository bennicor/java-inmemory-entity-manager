<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head><title>Информация о пользователе</title></head>
<body>
<a href="/store">На главную</a>
<br>

<h1>Информация о пользователе</h1>
<p><b>ID:</b> ${customer.id}</p>
<p><b>Имя:</b> ${customer.firstName}</p>
<p><b>Фамилия:</b> ${customer.lastName}</p>
<p><b>Отчество:</b> ${customer.middleName}</p>
<p><b>Номер телефона:</b> ${customer.phone}</p>
<p><b>Email:</b> ${customer.email}</p>
<p><b>Адрес:</b> ${customer.address}</p>

<a href="customers">Назад к списку</a>
</body>
</html>
