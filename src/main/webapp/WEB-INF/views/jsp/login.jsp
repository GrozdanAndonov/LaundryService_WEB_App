<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
<jsp:include page="staticContent.jsp"></jsp:include>
</head>
<body class="signup-page"> 
<jsp:include page="headerNotLogged.jsp"></jsp:include>
    <div class="page-header header-filter" filter-color="purple" style="background-image: url(&apos;..<c:url value="/img/free/bg7.jpg"/>&apos;); background-size: cover; background-position: top center;">
        <div class="container">															
            <div class="row">
                <div class="col-md-5 ml-auto mr-auto">
                    <div class="card card-signup">
                        <h2 class="card-title text-center">Login</h2>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-12 ml-auto">
                                <div class="col-md-12 mr-auto">
                                    <form class="contact-form" method="POST" action="/LaundryService/logged">
                                        <div class="form-group">
                                            <div class="input-group">
                                              	<div class="input-group-prepend">
                                                    <span class="input-group-text">
                                                        <i class="material-icons">mail</i>
                                                    </span>
                                                </div>
                                                <input type="email" class="form-control" placeholder="Email..." name="email" value="${email}" required>
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
                                                <input type="password" placeholder="Password..." class="form-control" name="password" value="${password}" required/>
                                            </div>
                                             <p class="text-danger"><strong>${errorPassword}</strong></p>
                                        </div>
                                        <div class="text-center">                           
                                            <input class="btn btn-primary btn-round" type="submit" value="Log in">
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