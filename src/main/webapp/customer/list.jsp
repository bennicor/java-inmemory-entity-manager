<%@ page contentType="text/html; charset=UTF-8" %> <%@ taglib
uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>Список пользователей</title>
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
			.table {
				background-color: white;
			}
		</style>
	</head>
	<body>
		<header class="bg-success text-white py-4">
			<div class="container">
				<h1 class="mb-0"><i class="bi bi-people"></i> Список пользователей</h1>
			</div>
		</header>

		<div class="container mt-4">
			<div class="d-flex justify-content-between align-items-center mb-3">
				<a href="/store" class="btn btn-secondary"
					><i class="bi bi-house"></i> На главную</a
				>
				<a href="customers?action=new" class="btn btn-primary"
					><i class="bi bi-plus-circle"></i> Добавить пользователя</a
				>
			</div>

			<div class="card">
				<div class="card-body">
					<table class="table table-hover">
						<thead class="table-light">
							<tr>
								<th>ID</th>
								<th>Имя</th>
								<th>Фамилия</th>
								<th>Отчество</th>
								<th>Номер телефона</th>
								<th>Email</th>
								<th>Адрес</th>
								<th>Управление</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="customer" items="${customers}">
								<tr>
									<td>
										<span class="badge bg-secondary">${customer.id}</span>
									</td>
									<td>${customer.firstName}</td>
									<td>${customer.lastName}</td>
									<td>
										${customer.middleName != null ? customer.middleName : '-'}
									</td>
									<td>${customer.phone != null ? customer.phone : '-'}</td>
									<td>${customer.email}</td>
									<td>${customer.address}</td>
									<td>
										<div class="btn-group" role="group">
											<a
												href="customers?action=view&id=${customer.id}&page=${currentPage}"
												class="btn btn-sm btn-info"
												><i class="bi bi-eye"></i
											></a>
											<a
												href="customers?action=edit&id=${customer.id}&page=${currentPage}"
												class="btn btn-sm btn-warning"
												><i class="bi bi-pencil"></i
											></a>
											<a
												href="customers?action=remove&id=${customer.id}&page=${currentPage}"
												class="btn btn-sm btn-danger"
												onclick="return confirm('Вы уверены?')"
												><i class="bi bi-trash"></i
											></a>
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
							<a class="page-link" href="customers?page=${currentPage - 1}"
								><i class="bi bi-chevron-left"></i> Предыдущая</a
							>
						</li>
					</c:if>
					<li class="page-item active">
						<span class="page-link"
							>Страница ${currentPage} из ${totalPages}</span
						>
					</li>
					<c:if test="${currentPage < totalPages}">
						<li class="page-item">
							<a class="page-link" href="customers?page=${currentPage + 1}"
								>Следующая <i class="bi bi-chevron-right"></i
							></a>
						</li>
					</c:if>
				</ul>
			</nav>
		</div>

		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	</body>
</html>
