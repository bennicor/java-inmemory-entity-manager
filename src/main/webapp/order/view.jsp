<%@ page contentType="text/html;charset=UTF-8" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>Заказ №${order.id}</title>
		<link
			href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
			rel="stylesheet"
		/>
		<link
			href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css"
			rel="stylesheet"
		/>
		<style>
			body {
				background-color: #f8f9fa;
			}
			.order-card {
				box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
				border: none;
			}
			.summary-card {
				background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
				color: white;
			}
		</style>
	</head>
	<body>
		<header class="bg-success text-white py-4">
			<div class="container">
				<h1 class="mb-0"><i class="bi bi-receipt"></i> Заказ №${order.id}</h1>
			</div>
		</header>

		<div class="container mt-4">
			<a href="/store" class="btn btn-secondary mb-3"
				><i class="bi bi-house"></i> На главную</a
			>
			<a href="orders?page=${param.page}" class="btn btn-outline-secondary mb-3"
				><i class="bi bi-arrow-left"></i> Назад к списку</a
			>

			<div class="card order-card mb-4">
				<div class="card-header bg-light">
					<h5 class="mb-0">
						<i class="bi bi-info-circle"></i> Информация о заказе
					</h5>
				</div>
				<div class="card-body">
					<div class="row">
						<div class="col-md-6">
							<p>
								<strong>ID пользователя:</strong>
								<span class="badge bg-primary">${order.customerId}</span>
							</p>
							<p><strong>Дата заказа:</strong> ${order.orderDate}</p>
						</div>
						<div class="col-md-6">
							<p><strong>Метод оплаты:</strong> ${order.paymentMethod}</p>
							<p>
								<strong>Статус:</strong>
								<c:choose>
									<c:when test="${order.status == 'NEW'}">
										<span class="badge bg-primary">NEW</span>
									</c:when>
									<c:when test="${order.status == 'PAID'}">
										<span class="badge bg-success">PAID</span>
									</c:when>
									<c:when test="${order.status == 'SHIPPED'}">
										<span class="badge bg-warning">SHIPPED</span>
									</c:when>
									<c:when test="${order.status == 'DELIVERED'}">
										<span class="badge bg-success">DELIVERED</span>
									</c:when>
									<c:when test="${order.status == 'CANCELED'}">
										<span class="badge bg-danger">CANCELED</span>
									</c:when>
									<c:otherwise>
										<span class="badge bg-secondary">${order.status}</span>
									</c:otherwise>
								</c:choose>
							</p>
						</div>
					</div>
				</div>
			</div>

			<div class="card order-card mb-4">
				<div class="card-header bg-light">
					<h5 class="mb-0"><i class="bi bi-box-seam"></i> Товары в заказе</h5>
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
								<c:forEach var="item" items="${orderDetails}">
									<tr>
										<td>
											<span class="badge bg-secondary">${item.product.id}</span>
										</td>
										<td>${item.product.code}</td>
										<td>${item.nameSnapshot}</td>
										<td>${item.priceSnapshot} руб.</td>
										<td>${item.product.weightKg}</td>
										<td><span class="badge bg-info">${item.quantity}</span></td>
										<td><strong>${item.lineCost} руб.</strong></td>
										<td>
											<a
												href="products?action=view&id=${item.product.id}"
												class="btn btn-sm btn-info"
												><i class="bi bi-eye"></i> Подробнее</a
											>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>

			<div class="card summary-card">
				<div class="card-body">
					<h5 class="card-title mb-4">
						<i class="bi bi-calculator"></i> Итоговая стоимость
					</h5>
					<div class="row">
						<div class="col-md-6">
							<p class="mb-2"><strong>Стоимость доставки:</strong></p>
							<p class="fs-4">${order.deliveryCost} руб.</p>
						</div>
						<div class="col-md-6">
							<p class="mb-2">
								<strong>Итоговая стоимость (с доставкой):</strong>
							</p>
							<p class="fs-3 text-warning">${totalCostWithDelivery} руб.</p>
						</div>
					</div>
				</div>
			</div>
		</div>

		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>
