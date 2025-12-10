<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>Магазин</title>
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
				min-height: 100vh;
				display: flex;
				flex-direction: column;
			}
			header {
				box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
			}
			.main-content {
				flex: 1;
				padding: 2rem 0;
			}
			.card-link {
				text-decoration: none;
				color: inherit;
				transition: transform 0.2s;
			}
			.card-link:hover {
				transform: translateY(-5px);
				color: inherit;
			}
			.card {
				border: none;
				box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
				transition: box-shadow 0.3s;
			}
			.card:hover {
				box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
			}
			footer {
				background-color: #343a40;
				color: white;
				margin-top: auto;
			}
		</style>
	</head>
	<body>
		<header class="bg-success text-white py-4">
			<div class="container">
				<h1 class="mb-0"><i class="bi bi-shop"></i> Магазин</h1>
				<p class="mb-0 mt-2">Система управления магазином</p>
			</div>
		</header>

		<div class="main-content">
			<div class="container">
				<div class="row g-4">
					<div class="col-md-6 col-lg-3">
						<a href="customers" class="card-link">
							<div class="card h-100 text-center">
								<div class="card-body">
									<i class="bi bi-people fs-1 text-primary"></i>
									<h5 class="card-title mt-3">Пользователи</h5>
									<p class="card-text text-muted">Управление пользователями</p>
								</div>
							</div>
						</a>
					</div>
					<div class="col-md-6 col-lg-3">
						<a href="products" class="card-link">
							<div class="card h-100 text-center">
								<div class="card-body">
									<i class="bi bi-box-seam fs-1 text-success"></i>
									<h5 class="card-title mt-3">Товары</h5>
									<p class="card-text text-muted">Управление товарами</p>
								</div>
							</div>
						</a>
					</div>
					<div class="col-md-6 col-lg-3">
						<a href="carts" class="card-link">
							<div class="card h-100 text-center">
								<div class="card-body">
									<i class="bi bi-cart fs-1 text-warning"></i>
									<h5 class="card-title mt-3">Корзины</h5>
									<p class="card-text text-muted">Просмотр корзин</p>
								</div>
							</div>
						</a>
					</div>
					<div class="col-md-6 col-lg-3">
						<a href="orders" class="card-link">
							<div class="card h-100 text-center">
								<div class="card-body">
									<i class="bi bi-receipt fs-1 text-danger"></i>
									<h5 class="card-title mt-3">Заказы</h5>
									<p class="card-text text-muted">Управление заказами</p>
								</div>
							</div>
						</a>
					</div>
				</div>
			</div>
		</div>

		<footer class="py-3 mt-5">
			<div class="container text-center">
				<p class="mb-0">&copy; 2024 Магазин. Все права защищены.</p>
			</div>
		</footer>

		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>
