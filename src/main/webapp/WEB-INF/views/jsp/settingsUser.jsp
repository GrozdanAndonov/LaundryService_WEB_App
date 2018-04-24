<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Settings</title>
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<jsp:include page="staticContent.jsp"></jsp:include>
</head>
<body class="index-page">
<jsp:include page="headerLogged.jsp"></jsp:include>
 <div class="page-header header-filter" data-parallax="true" style="background-image: url('<c:url value="/img/bg2.jpg"/>');">
        <div class="container">			
            <div class="row">
                <div class="col-md-8 ml-auto mr-auto">
                    <div class="brand">
                        <h1>Settings</h1>
                        <h3>A Badass Bootstrap 4 UI Kit based on Material Design.</h3>
                        
                        
              <form action="uploadPicture" method="post" enctype="multipart/form-data">
					<div class="login-form">
						<div class="control-group">
							<input type="file" name="failche">
							<label class="login-field-icon fui-user" for="login-name"></label>
						</div>
						<p>Formats allowed: mp4, png, gif, jpg, jpeg, tiff, bmp</p>
						<input class="btn btn-primary btn-large btn-block" type="submit" value="Upload">
					</div>
				</form>
				
                    </div>
                </div>
            </div>
        </div>
    </div>

<div class="main main-raised">
        <div class="section section-basic">
            <div class="container">
                <div class="title">
                    <h2>Basic Elements</h2>
                </div>


</div>
</div>
</div>




<jsp:include page="footer.jsp"></jsp:include>
</body>
</html>