<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>About Us</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<jsp:include page="../staticContent.jsp"></jsp:include>
</head>
<body class="index-page">
<jsp:include page="headerNotLogged.jsp"></jsp:include>
 <div class="page-header header-filter" data-parallax="true"
		style="background-image: url('<c:url value="/img/8.jpg"/>');">
        <div class="container">			
            <div class="row">
                <div class="col-md-8 ml-auto mr-auto">
                    <div class="brand">
                    <br>
                    <br>
                        <h1><s:message code="aboutUs.header"></s:message></h1>
                    </div>
                </div>
            </div>
        </div>
    </div>

<div class="main main-raised">
        <div class="section section-basic">
            <div class="container">
                <div class="title">
                    
                   <center><h2><b><i><s:message code="aboutUs.name"></s:message></i></b></h2></center>
                    <br> 
                    <center><h2><b><i><s:message code="aboutUs.thanks"></s:message></i></b></h2></center>
                    <br>
                    <center><h3><s:message code="aboutUs.description"></s:message></h3></center>
                    
                </div>


</div>
</div>
</div>




<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>