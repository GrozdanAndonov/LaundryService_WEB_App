<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
<title>Register</title>
<jsp:include page="staticContent.jsp"></jsp:include>
</head>
<body class="signup-page"> 
<jsp:include page="headerNotLogged.jsp"></jsp:include>
    <div class="page-header header-filter" filter-color="purple" style="background-image: url(&apos;..<c:url value="/img/free/bg7.jpg"/>&apos;); background-size: cover; background-position: top center;">
        <div class="container">															
            <div class="row">
                <div class="col-md-5 ml-auto mr-auto">
                    <div class="card card-signup">
                        <h2 class="card-title text-center">Register</h2>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-12 ml-auto">
                                <div class="col-md-12 mr-auto">
                                
                                <form class="contact-form" method="POST" action="/LaundryService/register">
                                        <div class="form-group">
                                            <div class="input-group">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text">
                                                        <i class="material-icons">face</i>
                                                    </span>
                                                </div>
                                                 <input type="text" class="form-control" name="firstName" placeholder="First Name..." value="${firstName}" required><br>                                                 
                                            </div>
                                            <p class="text-danger"><strong>${errorFirstName}</strong></p>
                                        </div>
                                           <div class="form-group">
                                            <div class="input-group">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text">
                                                        <i class="material-icons">face</i>
                                                    </span>
                                                </div>
                                                 <input type="text" class="form-control" name="lastName" placeholder="Last name..." value="${lastName}" required><br>                                            
                                            </div>
                                             <p class="text-danger"><strong>${errorLastName}</strong></p>
                                        </div>
                                         <div class="form-group">
                                            <div class="input-group">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text">
                                                        <i class="material-icons">face</i>
                                                    </span>
                                                </div>
                                                <input type="text" class="form-control" name="city" placeholder="City..." value="${address}" required><br>                                                
                                            </div>
                                             <p class="text-danger"><strong>${errorCity}</strong></p>
                                        </div>
                                        <div class="form-group">
                                            <div class="input-group">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text">
                                                        <i class="material-icons">mail</i>
                                                    </span>
                                                </div>
                                                 <input type="email" class="form-control" name="email" placeholder="Email..." value="${email}" required><br>                                               
                                            </div>
                                             <p class="text-danger"><strong>${errorEmail}</strong></p>
                                        </div>
                                        <div class="form-group">
                                            <div class="input-group">
                                                <div class="input-group-prepend">
                                                    <span class="input-group-text">
                                                        <i class="material-icons">lock_outline</i>
                                                    </span>
                                                </div>
                                                <input type="password" class="form-control" name="password" placeholder="Password..." value="${password}" required><br>                                                     
                                            </div>
                                            <p class="text-danger"><strong>${errorPassword}</strong></p>
                                        </div>
                                        
                                        <div class="text-center">                           
                                            <input class="btn btn-primary btn-round" type="submit" value="Register">
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>