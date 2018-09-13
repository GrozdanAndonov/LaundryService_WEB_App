<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Order detail</title>
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
						<h1>Order details</h1>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="main main-raised">
		<div class="section section-basic">
			<div class="container">
				<div class="title text-centre">
					<h2>Order information</h2>
				</div>
				<form>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="inputEmail4">First Name</label> 
							<input readonly type="text" class="form-control" id="inputEmail4" value="${order.firstName}">
						</div>
						<div class="form-group col-md-6">
							<label for="inputPassword4">Last Name</label> <input  readonly type="text" class="form-control" id="inputPassword4" value="${order.lastName}">
						</div>
					</div>
					<div class="form-row">
					<div class="form-group col-md-6">
						<label for="inputAddress">Email</label> <input readonly type="email" class="form-control" id="inputAddress"  value="${order.email}">
					</div>
					<div class="form-group col-md-6">
						<label for="inputAddress2">Street address</label> <input readonly type="text" class="form-control" id="inputAddress2" value="${order.streetAddress}">
					</div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="inputCity">City</label> 
							<input type="text" class="form-control" id="inputCity" value="${order.city}"  readonly>
						</div>
						<div class="form-group col-md-6">
							<label for="inputZip">Telephone number</label> 
							<input type="text" class="form-control" id="inputZip"  value="${order.telNumber}" readonly>
						</div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="inputCity">Date of creation</label> 
							<input type="text" class="form-control" id="inputCity" value="${order.dateCreatedForView}"  readonly>
						</div>
						<c:if test="${!order.dateFinishedForView.isEmpty()}">
						<div class="form-group col-md-6">
							<label for="inputZip">Date of finished</label> 
							<input type="text" class="form-control" id="inputZip"  value="${order.dateFinishedForView}" readonly>
						</div>
						</c:if>
					</div>
					<div class="form-row">
					  <c:if test="${order.cost!=0}">
					<div class="form-group col-md-6">
							<label for="inputCity">Total cost</label> 
							<input type="text" class="form-control" id="inputCity" value="${order.cost}lv"  readonly>
						</div>
						</c:if>
						  <c:if test="${order.totalDiscount!=0}">
						<div class="form-group col-md-6">
							<label for="inputZip">Total discount</label> 
							<input type="text" class="form-control" id="inputZip"  value="${order.totalDiscount}lv" readonly>
						</div>
						</c:if>
					</div>
					
					  <c:if test="${!order.note.isEmpty()}">
					<div class="form-row">
						<div class="form-group col-md-12">
				    <label>Enter note for order</label>
				    <textarea type="text" class="form-control" id="exampleFormControlTextarea1" rows="6"  readonly>${order.note}</textarea>
				  </div>
				  </div>
				  </c:if>
				  <c:if test="${order.isExpress == true }">
					<div class="form-row">
					<div class="form-check">
				      <label class="form-check-label">
				          <input id="checkbox" class="form-check-input" type="checkbox" value="${order.isExpress}" disabled>
				          Express order
				          <span class="form-check-sign">
				              <span class="check"></span>
				          </span>
				      </label>
			  		</div>
			  		</div>
			  		</c:if>
				</form>
					<div class="col-md-12">
			  		<a href="/LaundryService/backToViewCheckedOrdersForUserDetails">
					<button class="btn btn-primary">Back</button>
					</a>
					</div>
			</div>
		</div>
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
<script>
let checkbox = document.getElementById('checkbox');
let val = checkbox.value;
if(val === 'true'){
	checkbox.checked = true;
}
</script>
</html>