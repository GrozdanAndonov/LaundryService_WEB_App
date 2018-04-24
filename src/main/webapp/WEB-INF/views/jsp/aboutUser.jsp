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
<jsp:include page="staticContent.jsp"></jsp:include>
</head>
<body class="profile-page">
<jsp:include page="headerLogged.jsp"></jsp:include>
 <div class="page-header header-filter" data-parallax="true" style="background-image: url('<c:url value="/img/bg2.jpg"/>');"></div>
    <div class="main main-raised">
        <div class="profile-content">
            <div class="container">
                <div class="row">
                    <div class="col-md-6 ml-auto mr-auto">
                        <div class="profile">
                            <div class="avatar">
                                <img src="<c:url value="/img/faces/christian.jpg"/>" alt="Circle Image" class="img-raised rounded-circle img-fluid">
                            </div>
                            <div class="name">
                                <h3 class="title">${ sessionScope.User.firstName } ${ sessionScope.User.lastName }</h3>
                            </div>
                        </div>
                    </div>
                </div>  
   
             </div>
             <div class="container">
              <div class="col-md-6 ml-auto mr-auto">            
    <table class="table">
    <tbody>
        <tr>
            <td><strong>Date of registration : </strong> ${ sessionScope.User.dateCreated }</td>
            <td><strong>Total number of orders:</strong>${ numOrder }</td>      
        </tr>
        <tr>
            <td><strong>Address : </strong> ${ sessionScope.User.address }</td>
            <td><strong>Total number of discounts:</strong>${ numDiscounts }</td>
        </tr>
        <tr>
            <td><strong>Email -</strong>${ sessionScope.User.email }</td>
            <td><strong>Total number of comments:</strong>${ numComments }</td>
        </tr>
    </tbody>
</table>   
</div>  
             </div>
       </div>
     </div>



<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>