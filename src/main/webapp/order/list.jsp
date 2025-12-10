<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список заказов пользователей</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .table {
            background-color: white;
        }
        .status-badge {
            font-size: 0.85rem;
        }
    </style>
</head>
<body>
    <header class="bg-success text-white py-4">
        <div class="container">
            <h1 class="mb-0"><i class="bi bi-receipt"></i> Список заказов пользователей</h1>
        </div>
    </header>

    <div class="container mt-4">
        <a href="/store" class="btn btn-secondary mb-3"><i class="bi bi-house"></i> На главную</a>

        <div class="card">
            <div class="card-body">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead class="table-light">
                            <tr>
                                <th>ID</th>
                                <th>ID пользователя</th>
                                <th>Количество товаров</th>
                                <th>Дата заказа</th>
                                <th>Стоимость доставки</th>
                                <th>Метод оплаты</th>
                                <th>Статус</th>
                                <th>Изменить статус</th>
                                <th>Действия</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="order" items="${orders}">
                                <tr>
                                    <td><span class="badge bg-secondary">${order.id}</span></td>
                                    <td><span class="badge bg-primary">${order.customerId}</span></td>
                                    <td><span class="badge bg-info">${order.items.size()}</span></td>
                                    <td>${order.orderDate}</td>
                                    <td>${order.deliveryCost} руб.</td>
                                    <td>${order.paymentMethod}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${order.status == 'NEW'}">
                                                <span class="badge bg-primary status-badge">NEW</span>
                                            </c:when>
                                            <c:when test="${order.status == 'PAID'}">
                                                <span class="badge bg-success status-badge">PAID</span>
                                            </c:when>
                                            <c:when test="${order.status == 'SHIPPED'}">
                                                <span class="badge bg-warning status-badge">SHIPPED</span>
                                            </c:when>
                                            <c:when test="${order.status == 'DELIVERED'}">
                                                <span class="badge bg-success status-badge">DELIVERED</span>
                                            </c:when>
                                            <c:when test="${order.status == 'CANCELED'}">
                                                <span class="badge bg-danger status-badge">CANCELED</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary status-badge">${order.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <form action="${pageContext.request.contextPath}/orders?page=${currentPage}" method="post" class="d-inline">
                                            <input type="hidden" name="action" value="updateStatus">
                                            <input type="hidden" name="orderId" value="${order.id}">
                                            <div class="input-group input-group-sm">
                                                <select name="status" class="form-select form-select-sm">
                                                    <option value="NEW" <c:if test="${order.status == 'NEW'}">selected</c:if>>NEW</option>
                                                    <option value="PAID" <c:if test="${order.status == 'PAID'}">selected</c:if>>PAID</option>
                                                    <option value="SHIPPED" <c:if test="${order.status == 'SHIPPED'}">selected</c:if>>SHIPPED</option>
                                                    <option value="DELIVERED" <c:if test="${order.status == 'DELIVERED'}">selected</c:if>>DELIVERED</option>
                                                    <option value="CANCELED" <c:if test="${order.status == 'CANCELED'}">selected</c:if>>CANCELED</option>
                                                </select>
                                                <button type="submit" class="btn btn-sm btn-outline-primary"><i class="bi bi-check"></i></button>
                                            </div>
                                        </form>
                                    </td>
                                    <td>
                                        <a href="orders?action=view&id=${order.id}&page=${currentPage}" class="btn btn-sm btn-info"><i class="bi bi-eye"></i> Подробнее</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <nav aria-label="Навигация по страницам" class="mt-4">
            <ul class="pagination justify-content-center">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="orders?page=${currentPage - 1}"><i class="bi bi-chevron-left"></i> Предыдущая</a>
                    </li>
                </c:if>
                <li class="page-item active">
                    <span class="page-link">Страница ${currentPage} из ${totalPages}</span>
                </li>
                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="orders?page=${currentPage + 1}">Следующая <i class="bi bi-chevron-right"></i></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
