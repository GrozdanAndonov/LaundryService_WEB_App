<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Index</title>
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<jsp:include page="staticContent.jsp"></jsp:include>
</head>
<body class="index-page">
	<jsp:include page="headerLogged.jsp"></jsp:include>
	<div class="page-header header-filter" data-parallax="true"
		style="background-image: url('<c:url value="/img/bg2.jpg"/>');">
		<div class="container">
			<div class="row">
				<div class="col-md-8 ml-auto mr-auto">
					<div class="brand">
						<h1>View open orders</h1>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="main main-raised">
		<div class="section section-basic">
			<div class="container">
				<div class="title text-center">
					<h2>Search orders</h2>
				</div>
				<div class="section">
					<form method="POST"
						action="/LaundryService/orderList/findCheckedAndUncheckedOrders">
						<div class="form-row">
							<div class="form-group col-md-4">
								<label class="label-control">From date</label> <input
									id="datetimepicker1" type="text"
									class="form-control datetimepicker" name="firstDate"
									value="${ firstDate }" />
							</div>
							<div class="form-group col-md-4">
								<label class="label-control">To date</label> <input
									id="datetimepicker2" type="text"
									class="form-control datetimepicker" name="secondDate"
									value="${ secondDate }" />
							</div>
							<div class="form-group col-md-4 text-center">
								<input class="btn btn-primary btn-round" type="submit"
									value="Search">
							</div>
						</div>
					</form>
				</div>

				<c:if test="${showContent != null}">
					<div class="col-md-8 ml-auto mr-auto">
						<div class="title text-center">
							<h2>Unchecked Orders list</h2>
						</div>
					</div>
					<c:choose>
						<c:when
							test="${uncheckedOrders != null &&  !uncheckedOrders.isEmpty()}">
							<table class="table">
								<thead>
									<tr>
										<th class="text-center">#</th>
										<th>Name</th>
										<th>Date creation</th>
										<th>Date finished</th>
										<th>Price</th>
										<th class="text-right">Total:<c:out value="${ total }"></c:out>lv
										</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${ uncheckedOrders }" var="order">
										<tr>
											<td class="text-center"><c:out value="${ order.id }"></c:out></td>
											<td><c:out value="${ order.firstName }"></c:out> <c:out
													value="${ order.lastName }"></c:out></td>
											<td><c:out value="${ order.dateCreatedForView }"></c:out></td>
											<td><c:out value="${ order.dateFinishedForView }"></c:out></td>
											<td class="text-right"><c:out value="${ order.cost }"></c:out>lv</td>
											<td class="td-actions text-right"><a
												href="/LaundryService/orderList/unfinishedOrderDetails/${ order.id }"><button
														type="button" rel="tooltip" title="View Profile"
														class="btn btn-info btn-simple btn-xs btn-link">
														<i class="fa fa-user"></i>
													</button></a> <a
												href="/LaundryService/orderList/editOrder/${ order.id }">
													<button type="button" rel="tooltip" title="Edit Profile"
														class="btn btn-success btn-simple btn-xs btn-link">
														<i class="fa fa-edit"></i>
													</button>
											</a> <a
												href="/LaundryService/orderList/deleteUncheckedOrder/${ order.id }">
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
								<h3>You have no unchecked orders.</h3>
							</div>
						</c:otherwise>
					</c:choose>
						<c:if test="${msgDeletedOrderSuccess != null && msgDeletedOrderError == null }">
						<div class="alert alert-success">
							    <div class="container-fluid">
								  <div class="alert-icon">
									<i class="material-icons">check</i>
								  </div>
								  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
									<span aria-hidden="true"><i class="material-icons">clear</i></span>
								  </button>
							      <b>Success:</b> <c:out value="${ msgDeletedOrderSuccess }"></c:out>
							    </div>
							</div>
						</c:if>
						<c:if test="${msgDeletedOrderError != null && msgDeletedOrderSuccess == null }">
						<div class="alert alert-danger">
						    <div class="container-fluid">
							  <div class="alert-icon">
							    <i class="material-icons">error_outline</i>
							  </div>
							  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
								<span aria-hidden="true"><i class="material-icons">clear</i></span>
							  </button>
						      <b>Error: </b> <c:out value="${ msgDeletedOrderError }"></c:out>
						    </div>
						</div>
						</c:if>
					
					
					<div class="section">
						<div class="col-md-8 ml-auto mr-auto">
							<div class="title text-center">
								<h2>Checked Orders list</h2>
							</div>
						</div>
						<c:choose>
							<c:when
								test="${checkedOrders != null && !checkedOrders.isEmpty() }">
								<table class="table">
									<thead>
										<tr>
											<th class="text-center">#</th>
											<th>Name</th>
											<th>Date creation</th>
											<th>Date finished</th>
											<th>Price</th>
											<th class="text-right">Total:<c:out value="${ total }"></c:out>lv
											</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${ checkedOrders }" var="order">
											<tr>
												<td class="text-center"><c:out value="${ order.id }"></c:out></td>
												<td><c:out value="${ order.firstName }"></c:out> <c:out
														value="${ order.lastName }"></c:out></td>
												<td><c:out value="${ order.dateCreatedForView }"></c:out></td>
												<td><c:out value="${ order.dateFinishedForView }"></c:out></td>
												<td class="text-right"><c:out value="${ order.cost }"></c:out>lv</td>
												<td class="td-actions text-right"><a
													href="/LaundryService/orderList/orderDetails/${ order.id }"><button
															type="button" rel="tooltip" title="View Profile"
															class="btn btn-info btn-simple btn-xs btn-link">
															<i class="fa fa-user"></i>
														</button></a> <a
													href="/LaundryService/orderList/editOrder/${ order.id }">
														<button type="button" rel="tooltip" title="Edit Profile"
															class="btn btn-success btn-simple btn-xs btn-link">
															<i class="fa fa-edit"></i>
														</button>
												</a> <a
													href="/LaundryService/orderList/deleteOrder/${ order.id }">
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
									<h3>You have no checked orders.</h3>
								</div>
							</c:otherwise>
						</c:choose>
					</div>
				</c:if>

			</div>
		</div>
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
	<script>
		$('.datetimepicker').datetimepicker({
			icons : {
				time : "fa fa-clock-o",
				date : "fa fa-calendar",
				up : "fa fa-chevron-up",
				down : "fa fa-chevron-down",
				previous : 'fa fa-chevron-left',
				next : 'fa fa-chevron-right',
				today : 'fa fa-screenshot',
				clear : 'fa fa-trash',
				close : 'fa fa-remove'
			}
		});
	</script>
</body>
</html>