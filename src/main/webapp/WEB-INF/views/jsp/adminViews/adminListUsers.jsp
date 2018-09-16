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
<style>
.avatar {
    vertical-align: middle;
    width: fixed;
    height: 70px;
    border-radius: 50%;
}
.tr {
	height:10px;
}
</style>
</head>
<body class="index-page">
	<jsp:include page="adminHeader.jsp"></jsp:include>
	<div class="page-header header-filter" data-parallax="true"
		style="background-image: url('<c:url value="/img/2.png"/>');">
		<div class="container">
			<div class="row">
				<div class="col-md-8 ml-auto mr-auto">
					<div class="brand">
						<h1>Users</h1>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="main main-raised">
		<div class="section section-basic">
			<div class="container">
				<c:if test="${msgDeletedUserSuccess != null && msgDeletedUserError == null }">
						<div class="alert alert-success">
							    <div class="container-fluid">
								  <div class="alert-icon">
									<i class="material-icons">check</i>
								  </div>
								  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
									<span aria-hidden="true"><i class="material-icons">clear</i></span>
								  </button>
							      <b>Success:</b> <c:out value="${ msgDeletedUserSuccess }"></c:out>
							    </div>
							</div>
						</c:if>
						<c:if test="${msgDeletedUserError != null && msgDeletedUserSuccess == null }">
						<div class="alert alert-danger">
						    <div class="container-fluid">
							  <div class="alert-icon">
							    <i class="material-icons">error_outline</i>
							  </div>
							  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
								<span aria-hidden="true"><i class="material-icons">clear</i></span>
							  </button>
						      <b>Error: </b> <c:out value="${ msgDeletedUserError }"></c:out>
						    </div>
						</div>
						</c:if>
				<c:choose>
				<c:when test="${users != null &&  !users.isEmpty()}">
					<table class="table table-striped">
						<thead>
							<tr>
								<th class="text-center">#</th>
								<th>Name</th>
								<th>Date creation</th>
								<th>Number of orders</th>
								<th>Days from last login</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
						<c:forEach items="${ users }" var="user">
								<tr>
									<th scope="row">
									<div class="profile-photo-small">
                                                <img src="/LaundryService/getAvatarsForUsers/userId=${user.id}" alt="Circle Image" class="rounded-circle img-fluid avatar">
                                            </div></th>
                                    <td><c:out value="${ user.firstName }"></c:out> <c:out value="${ user.lastName }"></c:out></td>
									<td><c:out value="${ user.dateCreated }"></c:out></td>
									<td><c:out value="${ user.orders.size() }"></c:out></td>
									<td><c:out value="${ user.daysFromLastLogin }"></c:out></td>
									<td class="td-actions text-right"><a
										href="/LaundryService/userDetails/${ user.id }"><button
												type="button" rel="tooltip" title="View Profile"
												class="btn btn-info btn-simple btn-xs btn-link">
												<i class="fa fa-user"></i>
											</button></a>
											 <a
												href="/LaundryService/deleteUser/${ user.id }">
													<button type="button" rel="tooltip" title="Remove"
														class="btn btn-danger btn-simple btn-xs btn-link">
														<i class="fa fa-times"></i>
													</button>
											</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<div class="title text-center">
						<h3>You have no users.</h3>
					</div>
				</c:otherwise>
				</c:choose>
				</div>
			</div>
		</div>




	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>