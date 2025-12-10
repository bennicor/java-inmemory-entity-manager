<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head><title>Информация о товаре</title></head>
<body>
<a href="/store">На главную</a>
<br>

<h1>Информация о товаре</h1>
<p><b>ID:</b> ${product.id}</p>
<p><b>Артикул:</b> ${product.code}</p>
<p><b>Название:</b> ${product.name}</p>
<p><b>Стоимость:</b> ${product.price}</p>
<p><b>Вес, кг:</b> ${product.weightKg}</p>
<p><b>Длина, см:</b> ${product.lengthCm}</p>
<p><b>Ширина, см:</b> ${product.widthCm}</p>
<p><b>Высота, см:</b> ${product.heightCm}</p>
<p><b>Описание:</b> ${product.description}</p>

<h3>Добавить товар пользователю по id</h3>
<form action="${pageContext.request.contextPath}/carts" method="post">
    <input type="hidden" name="action" value="add">
    <input type="hidden" name="productId" value="${product.id}">
    <label for="customerId">ID пользователя</label><input type="number" id="customerId" name="customerId">
    <label for="amount">Количество</label><input type="number" name="amount" id="amount" value=1>
    <input type="submit" value="Добавить">
</form>

<a href="products?page=${param.page}">Назад к списку</a>
</body>
</html>
