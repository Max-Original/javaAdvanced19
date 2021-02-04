<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Users</title>
<link rel="stylesheet" href="/css/styles.css">
<link rel="stylesheet" href="/css/test.css">
</head>
<body style="background-color: #F0F3F4;">

	<section class="signup">

		<br>
		<!--  add our html table here -->
		<c:set var="count" value="0" scope="page" />
		<br>
		<table border="1" style="border: 1px solid black;">
		<tr>
						
						<th>Name</th>
						<th>Last Name</th>
						<th>Age</th>
						<th>File Name</th>
						<th>File Size</th>
						<th>File Type</th>
						<th>Image</th>
						<th>Action</th>
					</tr>
			<c:choose>
				<c:when test="${not empty usersList}">
					<!-- loop over and print our employees -->
				<c:forEach var="theUser" items="${usersList}">		
		<c:set var="count" value="${count + 1}" scope="page"/>
					<tr>
						<td>${count}</td>
						<td> ${theUser.name} </td>
						<td> ${theUser.lastName} </td>
						<td> ${theUser.age} </td>
						<td> ${theUser.fileName} </td>
						<td> ${theUser.fileSize} </td>
						<td> ${theUser.fileType} </td>
						<td><img src="/uploads/${theUser.fileName}" width="270" height="250" /> </td>
		<td> 
		<a href="${pageContext.request.contextPath}/image-upload/removeFile/${theUser.id}/${theUser.fileName}" onclick="return confirm('Are you sure you want to delete ${theUser.name}?');">Delete</a>
		</td>
					</tr>
				</c:forEach>
				</c:when>
				<c:otherwise>
					<center><h1>No User Found.</h1></center>
				</c:otherwise>
			</c:choose>	
</table>

<br><br>
				<div style="display: flex; justify-content: space-between;">
						<a href="${pageContext.request.contextPath}/image-upload/users"
							class="signup-image-link">View Users</a>
							<a href="${pageContext.request.contextPath}/image-upload/sign-up" class="signup-image-link">Sign Up</a> 
							 <a
							href="${pageContext.request.contextPath}/image-upload/home"
							class="signup-image-link">Go Home</a>
					</div>
			
			<br> <br> <br>
	
	</section>
</body>
</html>