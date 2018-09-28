<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Orders</title>
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
						<h1>Orders</h1>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="main main-raised">
		<div class="section section-basic">
			<div class="container">
				<div class="title text-center">
					<h2>Choose type of orders:</h2>
						<a href="/LaundryService/viewUncheckedNormalOrders">
						<button class="btn btn-primary btn-round">Unchecked orders</button>
						</a>
						<a href="/LaundryService/viewCheckedNormalOrders">
						<button class="btn btn-primary btn-round">Checked orders</button>
						</a>
						<a href="/LaundryService/viewFinishedNormalOrders">
						<button class="btn btn-primary btn-round">Finished orders</button>
						</a>
				</div>
			</div>
		</div>
	</div>




	<jsp:include page="../../footer.jsp"></jsp:include>
</body>
</html>