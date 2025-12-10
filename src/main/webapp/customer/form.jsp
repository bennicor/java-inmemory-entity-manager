<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>
			${customer.id == null ? "Добавить" : "Редактировать"} пользователя
		</title>
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
					<i class="bi bi-person"></i> ${customer.id == null ? "Добавить" :
					"Редактировать"} пользователя
				</h1>
			</div>
		</header>

		<div class="container mt-4">
			<a href="/store" class="btn btn-secondary mb-3"
				><i class="bi bi-house"></i> На главную</a
			>
			<a
				href="${pageContext.request.contextPath}/customers?page=${param.page}"
				class="btn btn-outline-secondary mb-3"
				><i class="bi bi-arrow-left"></i> Назад</a
			>

			<div class="card form-card">
				<div class="card-body">
					<form
						action="${pageContext.request.contextPath}/customers?page=${param.page}"
						method="post"
					>
						<input type="hidden" name="action" value="${customer.id == null ?
						"new" : "edit"}"/>
						<input type="hidden" name="id" value="${customer.id}" />

						<div class="row">
							<div class="col-md-6">
								<div class="mb-3">
									<label for="firstName" class="form-label"
										>Имя <span class="text-danger">*</span></label
									>
									<input
										type="text"
										name="firstName"
										value="${customer.firstName}"
										required
										class="form-control"
										id="firstName"
									/>
								</div>
							</div>
							<div class="col-md-6">
								<div class="mb-3">
									<label for="lastName" class="form-label"
										>Фамилия <span class="text-danger">*</span></label
									>
									<input
										type="text"
										name="lastName"
										value="${customer.lastName}"
										required
										class="form-control"
										id="lastName"
									/>
								</div>
							</div>
						</div>

						<div class="mb-3">
							<label for="middleName" class="form-label">Отчество</label>
							<input
								type="text"
								name="middleName"
								value="${customer.middleName}"
								class="form-control"
								id="middleName"
							/>
						</div>

						<div class="row">
							<div class="col-md-6">
								<div class="mb-3">
									<label for="phone" class="form-label">Номер телефона</label>
									<input
										type="text"
										name="phone"
										value="${customer.phone}"
										class="form-control"
										id="phone"
									/>
								</div>
							</div>
							<div class="col-md-6">
								<div class="mb-3">
									<label for="email" class="form-label"
										>Email <span class="text-danger">*</span></label
									>
									<input
										type="email"
										name="email"
										value="${customer.email}"
										required
										class="form-control"
										id="email"
									/>
								</div>
							</div>
						</div>

						<div class="mb-3">
							<label for="address" class="form-label"
								>Адрес <span class="text-danger">*</span></label
							>
							<input
								type="text"
								name="address"
								value="${customer.address}"
								required
								class="form-control"
								id="address"
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
