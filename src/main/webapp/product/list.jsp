<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список товаров</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .table {
            background-color: white;
        }
    </style>
</head>
<body>
    <header class="bg-success text-white py-4">
        <div class="container">
            <h1 class="mb-0"><i class="bi bi-box-seam"></i> Список товаров</h1>
        </div>
    </header>

    <div class="container mt-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <a href="/store" class="btn btn-secondary"><i class="bi bi-house"></i> На главную</a>
            <a href="products?action=new" class="btn btn-primary"><i class="bi bi-plus-circle"></i> Добавить товар</a>
        </div>

        <div class="card">
            <div class="card-body">
                <table class="table table-hover mt-2">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Артикул</th>
                    <th>Название</th>
                    <th>Стоимость</th>
                    <th>Вес, кг</th>
                    <th>Длина, см</th>
                    <th>Ширина, см</th>
                    <th>Высота, см</th>
                    <th>Описание</th>
                    <th>Добавить пользователю по ID</th>
                    <th>Управление</th>
                </tr>
            </thead>
            <tbody>
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
                            <form action="${pageContext.request.contextPath}/carts" method="post" class="d-flex gap-1">
                                <input type="hidden" name="action" value="add">
                                <input type="hidden" name="amount" value=1>
                                <input type="hidden" name="productId" value="${product.id}">
                                <input type="number" name="customerId" class="form-control form-control-sm" placeholder="ID клиента" required style="width: 80px;">
                                <button type="submit" class="btn btn-sm btn-success" title="Добавить в корзину"><i class="bi bi-cart-plus"></i></button>
                            </form>
                        </td>
                        <td>
                            <div class="btn-group" role="group">
                                <a href="products?action=view&id=${product.id}&page=${currentPage}" class="btn btn-sm btn-info"><i class="bi bi-eye"></i></a>
                                <a href="products?action=edit&id=${product.id}&page=${currentPage}" class="btn btn-sm btn-warning"><i class="bi bi-pencil"></i></a>
                                <a href="products?action=remove&id=${product.id}&page=${currentPage}" class="btn btn-sm btn-danger" onclick="return confirm('Вы уверены?')"><i class="bi bi-trash"></i></a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
            </div>
        </div>

        <nav aria-label="Навигация по страницам" class="mt-4">
            <ul class="pagination justify-content-center">
                <c:if test="${currentPage > 1}">
                    <li class="page-item">
                        <a class="page-link" href="products?page=${currentPage - 1}"><i class="bi bi-chevron-left"></i> Предыдущая</a>
                    </li>
                </c:if>
                <li class="page-item active">
                    <span class="page-link">Страница ${currentPage} из ${totalPages}</span>
                </li>
                <c:if test="${currentPage < totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="products?page=${currentPage + 1}">Следующая <i class="bi bi-chevron-right"></i></a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
