<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head><title>Заказ №${order.id}</title></head>
<body>
<a href="/store">На главную</a>
<br>

<h1>Заказ №${order.id}</h1>
<p><b>ID пользователя:</b> ${order.customerId}</p>
<p><b>Дата заказа:</b> ${order.orderDate}</p>
<p><b>Метод оплаты:</b> ${order.paymentMethod}</p>
<p><b>Статус:</b> ${order.status}</p>

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
        <th>Управление</th>
    </tr>

    <c:forEach var="item" items="${orderDetails}">
        <tr>
            <td>${item.product.id}</td>
            <td>${item.product.code}</td>
            <td>${item.nameSnapshot}</td>
            <td>${item.priceSnapshot} руб.</td>
            <td>${item.product.weightKg}</td>
            <td>${item.product.description}</td>
            <td>${item.quantity}</td>
            <td>${item.lineCost} руб.</td>
            <td>
                <a href="products?action=view&id=${item.product.id}">Подробнее</a>
            </td>
        </tr>
    </c:forEach>
</table>

<h3>Стоимость доставки: ${order.deliveryCost} руб.</h3>
<h3>Итоговая стоимость(с учетом доставки): ${totalCostWithDelivery} руб.</h3>

<br>
<a href="orders?page=${param.page}">Назад к списку</a>
</body>
</html>
