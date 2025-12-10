<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head><title>Содержимое корзины</title></head>
<body>
<a href="/store">На главную</a>
<br>

<h1>Содержимое корзины пользователя ${customer.lastName}</h1>
<p><b>ID корзины:</b> ${cart.id}</p>
<p><b>ID пользователя:</b> ${customer.id}</p>
<br>
<h3>Товары</h3>

<table border="1" cellpadding="6">
    <tr>
        <th>ID</th>
        <th>Артикул</th>
        <th>Название</th>
        <th>Стоимость</th>
        <th>Вес, кг</th>
        <th>Описание</th>
        <th>Количество</th>
        <th>Сумма</th>
        <th>Добавить</th>
        <th>Убавить</th>
        <th>Удалить из корзины</th>
        <th>Управление</th>
    </tr>

    <c:forEach var="item" items="${cartDetails}">
        <tr>
            <td>${item.product.id}</td>
            <td>${item.product.code}</td>
            <td>${item.product.name}</td>
            <td>${item.product.price} руб.</td>
            <td>${item.product.weightKg}</td>
            <td>${item.product.description}</td>
            <td>${item.quantity}</td>
            <td>${item.lineCost} руб.</td>
            <td>
                <form action="${pageContext.request.contextPath}/carts" method="post">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="productId" value="${item.product.id}">
                    <input type="hidden" name="customerId" value="${customer.id}">
                    <input type="hidden" name="amount" value="1">
                    <input type="submit" value="+">
                </form>
            </td>
            <td>
                <form action="${pageContext.request.contextPath}/carts" method="post">
                    <input type="hidden" name="action" value="reduce">
                    <input type="hidden" name="productId" value="${item.product.id}">
                    <input type="hidden" name="customerId" value="${customer.id}">
                    <input type="hidden" name="amount" value="1">
                    <input type="submit" value="-">
                </form>
            </td>
                <td>
                    <form action="${pageContext.request.contextPath}/carts" method="post">
                        <input type="hidden" name="action" value="removeFromCart">
                        <input type="hidden" name="productId" value="${item.product.id}">
                        <input type="hidden" name="customerId" value="${customer.id}">
                        <input type="submit" value="Удалить">
                    </form>
                </td>
            <td>
                <a href="products?action=view&id=${item.product.id}">Подробнее</a>
            </td>
        </tr>
    </c:forEach>
</table>

<h2>Сделать заказ</h2>
<form action="${pageContext.request.contextPath}/orders" method="post">
    <input type="hidden" name="customerId" value="${customer.id}">
    <input type="hidden" name="action" value="createOrderFromCart">
    <label>Стоимость доставки</label>
    <input type="number" name="deliveryCost" value="${deliveryCost}" readonly> руб.
    <label>Способ оплаты</label>
    <select name="paymentMethod">
        <option value="CASH">CASH</option>
        <option value="CARD">CARD</option>
        <option value="ONLINE" selected>ONLINE</option>
    </select>
    <input type="submit" value="Заказать">
</form>
<h3>Итоговая стоимость(без доставки): ${totalCost} руб.</h3>
<h3>Итоговая стоимость(с учетом доставки): ${totalCostWithDelivery} руб.</h3>
<br>
<a href="carts?&page=${param.page}">Назад к списку</a>
</body>
</html>
