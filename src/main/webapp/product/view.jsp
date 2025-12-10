<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>Информация о товаре</title>
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
			.info-card {
				box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
				border: none;
			}
		</style>
	</head>
	<body>
		<header class="bg-success text-white py-4">
			<div class="container">
				<h1 class="mb-0"><i class="bi bi-box-seam"></i> Информация о товаре</h1>
			</div>
		</header>

		<div class="container mt-4">
			<a href="/store" class="btn btn-secondary mb-3"
				><i class="bi bi-house"></i> На главную</a
			>
			<a
				href="products?page=${param.page}"
				class="btn btn-outline-secondary mb-3"
				><i class="bi bi-arrow-left"></i> Назад к списку</a
			>

			<div class="card info-card">
				<div class="card-body">
					<div class="row">
						<div class="col-md-6">
							<h5 class="card-title mb-4">
								<i class="bi bi-info-circle"></i> Основная информация
							</h5>
							<p>
								<strong>ID:</strong>
								<span class="badge bg-secondary">${product.id}</span>
							</p>
							<p><strong>Артикул:</strong> ${product.code}</p>
							<p><strong>Название:</strong> ${product.name}</p>
							<p>
								<strong>Стоимость:</strong>
								<span class="text-success fw-bold">${product.price} руб.</span>
							</p>
						</div>
						<div class="col-md-6">
							<h5 class="card-title mb-4">
								<i class="bi bi-rulers"></i> Характеристики
							</h5>
							<p><strong>Вес:</strong> ${product.weightKg} кг</p>
							<p><strong>Длина:</strong> ${product.lengthCm} см</p>
							<p><strong>Ширина:</strong> ${product.widthCm} см</p>
							<p><strong>Высота:</strong> ${product.heightCm} см</p>
						</div>
					</div>
					<hr />
					<div class="row">
						<div class="col-12">
							<h5 class="card-title mb-3">
								<i class="bi bi-file-text"></i> Описание
							</h5>
							<p>
								${product.description != null ? product.description : 'Описание
								отсутствует'}
							</p>
						</div>
					</div>
				</div>
				<div class="card-footer bg-light">
					<a
						href="products?action=edit&id=${product.id}&page=${param.page}"
						class="btn btn-warning"
						><i class="bi bi-pencil"></i> Редактировать</a
					>
					<a
						href="products?action=remove&id=${product.id}&page=${param.page}"
						class="btn btn-danger"
						onclick="return confirm('Вы уверены, что хотите удалить этот товар?')"
						><i class="bi bi-trash"></i> Удалить</a
					>
				</div>
			</div>
		</div>

		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>
