<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Me</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<jsp:include page="../staticContent.jsp"></jsp:include>
</head>
<body class="profile-page">
<jsp:include page="adminHeader.jsp"></jsp:include>
<div class="page-header header-filter" data-parallax="true"
		style="background-image: url('<c:url value="/img/8.jpg"/>');"></div>
    <div class="main main-raised">
        <div class="profile-content">
            <div class="container">
                <div class="row">
                    <div class="col-md-6 ml-auto mr-auto">
                        <div class="profile">
                            <div class="avatar">
                                <img src="/LaundryService/getAvatarsForUsers/userId=${user.id }" alt="Circle Image" class="img-raised rounded-circle img-fluid">
                            </div>
                        </div>
                    </div>
                </div>  
   
             </div>
			<div class="container">
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="inputEmail4">First Name</label> <input type="text" readonly 
								class="form-control" id="inputEmail4" placeholder="First name..."
								name="firstName" value="${user.firstName}">
						</div>
						<div class="form-group col-md-6">
							<label for="inputPassword4">Last Name</label> <input type="text" readonly 
								class="form-control" id="inputPassword4" placeholder="Last name..."
								name="lastName" value="${user.lastName}">
						</div>
					</div>
					 <div class="form-row">
					<div class="form-group col-md-6">
						<label for="inputAddress">Email</label> <input type="email" readonly 
							class="form-control" id="inputAddress" placeholder="Email..."
							name="email" value="${user.email}">
					</div>
					<div class="form-group col-md-6">
						<label for="inputAddress">Telephone number</label> <input type="text" readonly 
							class="form-control" id="inputAddress" placeholder="Telephone number..."
							name="telNumber" value="${user.telNumber}">
					</div>
					</div>
					<div class="form-row">
					<div class="form-group col-md-6">
						<label for="inputAddress2">Street address</label> <input type="text" readonly 
							class="form-control" id="inputAddress2" placeholder="Street address..."
							name="streetAddress" value="${user.streetAddress}">
					</div>
					<div class="form-group col-md-6">
						<label for="inputAddress2">Bulstat</label> <input type="text" readonly 
							class="form-control" id="inputAddress2" placeholder="Bulstat..."
							name="bulstat" value="${user.bulstatNumber}">
					</div>
					</div>
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="inputCity">City</label> <input type="text" readonly 
								class="form-control" id="inputCity" name="city" value="${user.city}">
						</div>
						<div class="form-group col-md-3">
							<label for="inputState">Date of creation</label> <input
								type="text" class="form-control" id="date" readonly 
								value="${user.dateCreated}">
						</div>
						<div class="form-group col-md-2 ml-auto">
							<label for="inputZip">Zip</label> <input type="number" readonly 
								class="form-control" id="inputZip" name="zip" value="${user.zipCode}">
						</div>
					</div>
			</div>
			
			
			<div class ="container">
			<div class="form-row ">
					<div class="form-group col-md-4 text-center">
						<a href="/LaundryService/viewUncheckedOrdersForUser">
						<button type="button" class="btn btn-primary">View unchecked orders</button>
						</a>
					</div>
					<div class="form-group col-md-4 text-center">
					<a href="/LaundryService/viewCheckedOrdersForUserDetails">
						<button type="button" class="btn btn-primary">View checked orders</button>
						</a>
					</div>
					<div class="form-group col-md-4 text-center">
					<a href="/LaundryService/viewFinishedOrdersForUser">
						<button type="button" class="btn btn-primary">View finished orders</button>
						</a>
						
					</div>
					
					</div>
					<div class="form-row">
					<div class="form-group col-md-12 text-center">
						<a href="/LaundryService/showUsers">
						<button type="button" class="btn btn-primary">Back</button> 
						</a>
					</div>
					</div>
					
			</div>
			
       </div>
     </div>



<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>