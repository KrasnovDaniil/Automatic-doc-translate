<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Успех - Перевод документации Java проектов</title>
	<link rel="stylesheet" type="text/css" href="css/main.css">
</head>
<body>

<div class="header">
	<h1>ПЕРЕВОД ДОКУМЕНТАЦИИ JAVA ПРОЕКТОВ</h1>
</div> <!-- header ender -->

<div class="contents">
	<h2>Перевод успешно выполнен!</h2>
	<class="right">Переведено комментариев:
			<td class="left">${comments_amount}</td>
		</tr>
		<tr>
			<td class="right">Перевод занял </td>
			<td class="left">${time_spent}</td><br>
		</tr>
	</table>
	<a href="${pr_link}" class="center">Ссылка на pull request</a>
</div>
</body>
</html>