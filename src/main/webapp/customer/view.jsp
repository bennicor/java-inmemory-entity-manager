<%@ page contentType="text/html;charset=UTF-8" %> <%@ taglib prefix="c"
uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>Информация о пользователе</title>
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
				<h1 class="mb-0">
					<i class="bi bi-person"></i> Информация о пользователе
				</h1>
			</div>
		</header>

		<div class="container mt-4">
			<a href="/store" class="btn btn-secondary mb-3"
				><i class="bi bi-house"></i> На главную</a
			>
			<a
				href="customers?page=${param.page}"
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
								<span class="badge bg-secondary">${customer.id}</span>
							</p>
							<p><strong>Имя:</strong> ${customer.firstName}</p>
							<p><strong>Фамилия:</strong> ${customer.lastName}</p>
							<p>
								<strong>Отчество:</strong> ${customer.middleName != null ?
								customer.middleName : '-'}
							</p>
						</div>
						<div class="col-md-6">
							<h5 class="card-title mb-4">
								<i class="bi bi-telephone"></i> Контактная информация
							</h5>
							<p>
								<strong>Номер телефона:</strong> ${customer.phone != null ?
								customer.phone : '-'}
							</p>
							<p>
								<strong>Email:</strong>
								<a href="mailto:${customer.email}">${customer.email}</a>
							</p>
							<p><strong>Адрес:</strong> ${customer.address}</p>
						</div>
					</div>
				</div>
				<div class="card-footer bg-light">
					<a
						href="customers?action=edit&id=${customer.id}&page=${param.page}"
						class="btn btn-warning"
						><i class="bi bi-pencil"></i> Редактировать</a
					>
					<a
						href="customers?action=remove&id=${customer.id}&page=${param.page}"
						class="btn btn-danger"
						onclick="return confirm('Вы уверены, что хотите удалить этого пользователя?')"
						><i class="bi bi-trash"></i> Удалить</a
					>
				</div>
			</div>
		</div>

		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>
