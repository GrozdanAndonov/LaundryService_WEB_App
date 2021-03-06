<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Finished orders</title>
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<jsp:include page="../../staticContent.jsp"></jsp:include>
</head>
<body class="index-page">
	<jsp:include page="../adminHeader.jsp"></jsp:include>
	<div class="page-header header-filter" data-parallax="true"
		style="background-image: url('<c:url value="/img/8.jpg"/>');">
		<div class="container">
			<div class="row">
				<div class="col-md-8 ml-auto mr-auto">
					<div class="brand">
						<h1>View Finished orders</h1>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="main main-raised">
		<div class="section section-basic">
			<div class="container">
				<div class="title text-center">
					<h2>Search Finished orders</h2>
				</div>
				<div class="section">
					<form method="POST"
						action="/LaundryService/searchFinishedExpressOrdersBetweenDates">
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
						<h2>Finished Orders list</h2>
					</div>
				</div>
				
				<c:choose>
				<c:when test="${orders != null &&  !orders.isEmpty()}">
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
							<c:forEach items="${ orders }" var="order">
								<tr>
									<td class="text-center"><c:out value="${ order.id }"></c:out></td>
									<td><c:out value="${ order.firstName }"></c:out> <c:out
											value="${ order.lastName }"></c:out></td>
									<td><c:out value="${ order.dateCreatedForView }"></c:out></td>
									<td><c:out value="${ order.dateFinishedForView }"></c:out></td>
									<td class="text-right"><c:out value="${ order.cost }"></c:out>lv</td>
									<td class="td-actions text-right"><a
										href="/LaundryService/finishedExpressOrderDetails/${ order.id }"><button
												type="button" rel="tooltip" title="Order details"
												class="btn btn-info btn-simple btn-xs btn-link">
												<i class="fa fa-user"></i>
											</button> </a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					<div class="title text-center">
						<h3>You have no finished orders.</h3>
					</div>
				</c:otherwise>
				</c:choose>
				</c:if>
					<div class="col-md-12 text-center">
			  		<a href="/LaundryService/viewNormalOrders">
					<button class="btn btn-primary">Back</button>
					</a>
					</div>
			</div>
		</div>
	</div>
	<jsp:include page="../../footer.jsp"></jsp:include>
</body>
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
</html>