<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${product.id == null ? "Добавить" : "Редактировать"} товар</title>
</head>
<body>
<a href="/store">На главную</a>
<br>

<h2>${product.id == null ? "Добавить" : "Редактировать"} товар</h2>
<form action="${pageContext.request.contextPath}/products" method="post">
    <input type="hidden" name="action" value="${product.id == null ? "new" : "edit"}"/>
    <input type="hidden" name="id" value="${product.id}"/>

    <label>Артикул:</label><input type="text" name="code" value="${product.code}" required><br>
    <label>Название:</label><input type="text" name="name" value="${product.name}" required><br>
    <label>Стоимость:</label><input type="number" name="price" value="${product.price}" required><br>
    <label>Вес, кг:</label><input type="number" name="weightKg" value="${product.weightKg}" required><br>
    <label>Длина, см:</label><input type="number" name="L" value="${product.lengthCm}"><br>
    <label>Ширина, см:</label><input type="number" name="W" value="${product.widthCm}"><br>
    <label>Высота, см:</label><input type="number" name="H" value="${product.heightCm}"><br>
    <label>Описание:</label><input type="text" name="description" value="${product.description}"><br>
    <input type="submit" value="Сохранить">
</form>

<br>
<a href="${pageContext.request.contextPath}/product?action=list">Назад</a>

</body>
</html>
