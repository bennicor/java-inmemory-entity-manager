<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Содержимое корзины</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .cart-card {
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            border: none;
        }
        .order-card {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
    </style>
</head>
<body>
    <header class="bg-success text-white py-4">
        <div class="container">
            <h1 class="mb-0"><i class="bi bi-cart"></i> Содержимое корзины пользователя ${customer.lastName}</h1>
        </div>
    </header>

    <div class="container mt-4">
        <a href="/store" class="btn btn-secondary mb-3"><i class="bi bi-house"></i> На главную</a>
        <a href="carts?page=${param.page}" class="btn btn-outline-secondary mb-3"><i class="bi bi-arrow-left"></i> Назад к списку</a>

        <div class="card cart-card mb-4">
            <div class="card-header bg-light">
                <h5 class="mb-0"><i class="bi bi-info-circle"></i> Информация о корзине</h5>
            </div>
            <div class="card-body">
                <p class="mb-1"><strong>ID корзины:</strong> <span class="badge bg-secondary">${cart.id}</span></p>
                <p class="mb-0"><strong>ID пользователя:</strong> <span class="badge bg-primary">${customer.id}</span></p>
            </div>
        </div>

        <div class="card cart-card mb-4">
            <div class="card-header bg-light">
                <h5 class="mb-0"><i class="bi bi-box-seam"></i> Товары в корзине</h5>
            </div>
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>Артикул</th>
                                <th>Название</th>
                                <th>Стоимость</th>
                                <th>Вес, кг</th>
                                <th>Количество</th>
                                <th>Сумма</th>
                                <th>Действия</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${cartDetails}">
                                <tr>
                                    <td><span class="badge bg-secondary">${item.product.id}</span></td>
                                    <td>${item.product.code}</td>
                                    <td>${item.product.name}</td>
                                    <td>${item.product.price} руб.</td>
                                    <td>${item.product.weightKg}</td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <form action="${pageContext.request.contextPath}/carts" method="post" class="d-inline">
                                                <input type="hidden" name="action" value="reduce">
                                                <input type="hidden" name="productId" value="${item.product.id}">
                                                <input type="hidden" name="customerId" value="${customer.id}">
                                                <input type="hidden" name="amount" value="1">
                                                <button type="submit" class="btn btn-sm btn-outline-secondary">-</button>
                                            </form>
                                            <span class="px-2 align-middle">${item.quantity}</span>
                                            <form action="${pageContext.request.contextPath}/carts" method="post" class="d-inline">
                                                <input type="hidden" name="action" value="add">
                                                <input type="hidden" name="productId" value="${item.product.id}">
                                                <input type="hidden" name="customerId" value="${customer.id}">
                                                <input type="hidden" name="amount" value="1">
                                                <button type="submit" class="btn btn-sm btn-outline-secondary">+</button>
                                            </form>
                                        </div>
                                    </td>
                                    <td><strong>${item.lineCost} руб.</strong></td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <a href="products?action=view&id=${item.product.id}" class="btn btn-sm btn-info" title="Подробнее"><i class="bi bi-eye"></i></a>
                                            <form action="${pageContext.request.contextPath}/carts" method="post" class="d-inline">
                                                <input type="hidden" name="action" value="removeFromCart">
                                                <input type="hidden" name="productId" value="${item.product.id}">
                                                <input type="hidden" name="customerId" value="${customer.id}">
                                                <button type="submit" class="btn btn-sm btn-danger" title="Удалить" onclick="return confirm('Удалить товар из корзины?')"><i class="bi bi-trash"></i></button>
                                            </form>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="card order-card">
            <div class="card-body">
                <h4 class="card-title mb-4"><i class="bi bi-receipt"></i> Оформление заказа</h4>
                <form action="${pageContext.request.contextPath}/orders" method="post">
                    <input type="hidden" name="customerId" value="${customer.id}">
                    <input type="hidden" name="action" value="createOrderFromCart">
                    
                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Стоимость доставки</label>
                            <input type="number" name="deliveryCost" value="${deliveryCost}" readonly class="form-control">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Способ оплаты</label>
                            <select name="paymentMethod" class="form-select">
                                <option value="CASH">Наличные (CASH)</option>
                                <option value="CARD">Карта (CARD)</option>
                                <option value="ONLINE" selected>Онлайн (ONLINE)</option>
                            </select>
                        </div>
                    </div>

                    <div class="alert alert-light">
                        <div class="row">
                            <div class="col-md-6">
                                <strong>Итоговая стоимость (без доставки):</strong> <span class="fs-5">${totalCost} руб.</span>
                            </div>
                            <div class="col-md-6">
                                <strong>Итоговая стоимость (с доставкой):</strong> <span class="fs-5 text-warning">${totalCostWithDelivery} руб.</span>
                            </div>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-light btn-lg w-100"><i class="bi bi-check-circle"></i> Оформить заказ</button>
                </form>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
