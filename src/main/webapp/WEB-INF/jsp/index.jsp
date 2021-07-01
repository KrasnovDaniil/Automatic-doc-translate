<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Перевод документации Java проектов</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
</head>

<body>
<div class="header">
	<h1>ПЕРЕВОД ДОКУМЕНТАЦИИ JAVA ПРОЕКТОВ</h1>
</div> <!-- header ender -->

<div class="contents">
	<form action="FindAndTranslate" method="post" autocomplete="off">
	<table>
		<tr>
			<td class="right">URL GitHub проекта</td>
			<td class="left"><input type="text" name="githubUrl" required></td>		
		</tr>
		<tr>
			<td class="right">Логин GitHub</td>
			<td class="left"><input type="text" name="login" id="login_field" autocapitalize="off" autofocus="autofocus" required></td>
		</tr>
		<tr>
			<td class="right">Пароль GitHub</td>
			<td class="left"><input type="password" name="password" id="password_field" required></td>
		</tr>
	</table>
	<table>
		<tr>
			<td class="right">С</td>
			<td class="left"><select size="1" name="langFrom" id="langFrom" class="select1">
			<option disabled>Выберите язык</option>
			<option selected value="rus">Русский</option>
			<option value="eng">Английский</option>
			<option value="span">Испанский</option>
			</select></td>
			<td class="right">На</td>
			<td class="left"><select size="1" name="langTo" id="langTo" class="select2">
			<option disabled>Выберите язык</option>
			<option value="rus">Русский</option>
			<option selected value="eng">Английский</option>
			<option value="span">Испанский</option>
			</select></td>
		</tr>
	</table>
	<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/selectDisable.js"></script>
	<small>
    	<input type="submit" name="translate" value="Перевести">
	</small>
</form>
</div>

<div class="errors">
	<!-- Контейнер для вывода ошибок (не удалось перевести, нет репозитория и т.д.) -->
	<!-- <c:if test="${not empty msg }"> -->
		<!-- <h3>${msg}</h3> -->
		<!-- <c:remove var="msg"/> -->
	<!-- </c:if> -->
</div>
</body>
</html>