<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Список товаров</title>
</head>
<body>
<a href="/store">На главную</a>
<br>

<h1>Список товаров</h1>
<table border="1" cellpadding="6">
    <tr>
        <th>ID</a></th>
        <th>Артикул</th>
        <th>Название</th>
        <th>Стоимость</th>
        <th>Вес, кг</th>
        <th>Длина, см</th>
        <th>Ширина, см</th>
        <th>Высота, см</th>
        <th>Описание</th>
        <th>Добавить этот товар пользователю с указанным id</th>
        <th>Управление</th>
    </tr>
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.id}</td>
                <td>${product.code}</td>
                <td>${product.name}</td>
                <td>${product.price}</td>
                <td>${product.weightKg}</td>
                <td>${product.lengthCm}</td>
                <td>${product.widthCm}</td>
                <td>${product.heightCm}</td>
                <td>${product.description}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/carts" method="post">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" name="amount" value=1>
                        <input type="hidden" name="productId" value="${product.id}">
                        <input type="number" name="customerId">
                        <input type="submit" value="+">
                    </form>
                </td>
                <td>
                    <a href="products?action=view&id=${product.id}">Подробнее</a> |
                    <a href="products?action=edit&id=${product.id}">Редактировать</a> |
                    <a href="products?action=remove&id=${product.id}">Удалить</a>
                </td>
            </tr>
        </c:forEach>
</table>

<br><br>
<a href="products?action=new">Добавить товар</a>

</body>
</html>
