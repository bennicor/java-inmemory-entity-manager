<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>${product.id == null ? "Добавить" : "Редактировать"} товар</title>
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
			.form-card {
				box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
				border: none;
			}
		</style>
	</head>
	<body>
		<header class="bg-success text-white py-4">
			<div class="container">
				<h1 class="mb-0">
					<i class="bi bi-box-seam"></i> ${product.id == null ? "Добавить" :
					"Редактировать"} товар
				</h1>
			</div>
		</header>

		<div class="container mt-4">
			<a href="/store" class="btn btn-secondary mb-3"
				><i class="bi bi-house"></i> На главную</a
			>
			<a
				href="${pageContext.request.contextPath}/products?page=${param.page}"
				class="btn btn-outline-secondary mb-3"
				><i class="bi bi-arrow-left"></i> Назад</a
			>

			<div class="card form-card">
				<div class="card-body">
					<form
						action="${pageContext.request.contextPath}/products"
						method="post"
					>
						<input type="hidden" name="action" value="${product.id == null ?
						"new" : "edit"}"/>
						<input type="hidden" name="id" value="${product.id}" />

						<div class="mb-3">
							<label for="code" class="form-label">Артикул</label>
							<input
								type="text"
								name="code"
								value="${product.code}"
								required
								class="form-control"
								id="code"
							/>
						</div>

						<div class="mb-3">
							<label for="name" class="form-label">Название</label>
							<input
								type="text"
								name="name"
								value="${product.name}"
								required
								class="form-control"
								id="name"
							/>
						</div>

						<div class="mb-3">
							<label for="price" class="form-label">Стоимость</label>
							<input
								type="number"
								name="price"
								value="${product.price}"
								required
								class="form-control"
								id="price"
							/>
						</div>

						<div class="mb-3">
							<label for="weightKg" class="form-label">Вес, кг</label>
							<input
								type="number"
								name="weightKg"
								value="${product.weightKg}"
								required
								class="form-control"
								id="weightKg"
							/>
						</div>

						<div class="mb-3">
							<label for="L" class="form-label">Длина, см</label>
							<input
								type="number"
								name="L"
								value="${product.lengthCm}"
								class="form-control"
								id="L"
							/>
						</div>

						<div class="mb-3">
							<label for="W" class="form-label">Ширина, см</label>
							<input
								type="number"
								name="W"
								value="${product.widthCm}"
								class="form-control"
								id="W"
							/>
						</div>

						<div class="mb-3">
							<label for="H" class="form-label">Высота, см</label>
							<input
								type="number"
								name="H"
								value="${product.heightCm}"
								class="form-control"
								id="H"
							/>
						</div>

						<div class="mb-3">
							<label for="description" class="form-label">Описание</label>
							<input
								type="text"
								name="description"
								value="${product.description}"
								class="form-control"
								id="description"
							/>
						</div>

						<div class="d-grid gap-2 d-md-flex justify-content-md-end mt-4">
							<button type="submit" class="btn btn-success">
								<i class="bi bi-check-circle"></i> Сохранить
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>

		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>
