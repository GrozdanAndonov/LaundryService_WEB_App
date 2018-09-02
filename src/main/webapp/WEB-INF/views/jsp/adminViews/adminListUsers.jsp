<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index-Admin</title>
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<jsp:include page="../staticContent.jsp"></jsp:include>
</head>
<body class="index-page">
	<jsp:include page="adminHeader.jsp"></jsp:include>
	<div class="page-header header-filter" data-parallax="true"
		style="background-image: url('<c:url value="/img/bg2.jpg"/>');">
		<div class="container">
			<div class="row">
				<div class="col-md-8 ml-auto mr-auto">
					<div class="brand">
						<h1>Users</h1>
						<h3>A Badass Bootstrap 4 UI Kit based on Material Design.</h3>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="main main-raised">
		<div class="section section-basic">
			<div class="container">
				<div class="title">
					<h1>Admin</h1>
					<h2>Basic Elements</h2>
					<table class="table table-striped">
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">First</th>
								<th scope="col">Last</th>
								<th scope="col">Handle</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ users }" var="user">
								<tr>
									<th scope="row">
									<div class="profile-photo-small">
                                                <img src="/LaundryService/getAvatarsForUsers/adminId=${user.id}" alt="Circle Image" class="rounded-circle img-fluid">
                                            </div></th>
                                    <td><c:out value="${ user.firstName }"></c:out></td>
									<td><c:out value="${ user.lastName }"></c:out></td>
									<td><c:out value="${ user.email }"></c:out></td>
									<td><c:out value="${ user.city }"></c:out></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>




	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>