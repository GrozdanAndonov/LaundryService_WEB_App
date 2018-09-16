<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
     <%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
  <nav class="navbar navbar-color-on-scroll navbar-transparent    fixed-top  navbar-expand-lg " color-on-scroll="100" id="sectionsNav">
        <div class="container">
            <div class="navbar-translate">
                <button class="navbar-toggler" type="button" data-toggle="collapse" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                    <span class="navbar-toggler-icon"></span>
                    <span class="navbar-toggler-icon"></span>
                </button>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ml-auto">
                    <li class = "nav-item">
                        <a href="/LaundryService/index" class="nav-link">
                        <s:message code="notLogged.home"></s:message>
                        </a>
                    </li>
                    <li class = "nav-item">
                        <a href="/LaundryService/contacts" class="nav-link">
                        <s:message code="notLogged.contacts"></s:message>
                        </a>
                    </li>
                    <li class = "nav-item">
                        <a href="/LaundryService/aboutUs" class="nav-link">
                        <s:message code="notLogged.about"></s:message>
                        </a>
                    </li>
                    
                    <li class="nav-item">
                        <a class="btn btn-rose btn-raised btn-round " title="" data-placement="bottom" href="/LaundryService/register" data-original-title="Follow us on Instagram">
                            <s:message code="notLogged.register"></s:message>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-rose btn-raised btn-round " title="" data-placement="bottom" href="/LaundryService/login" data-original-title="Follow us on Instagram">
                            <s:message code="notLogged.login"></s:message>
                        </a>
                    </li>
                    <li class="nav-item dropdown">
				    <a id="header-languages" class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false"><img id="flagImage" src="<c:url value="/img/flags/GB.png" />"/><s:message code="notLogged.languages"></s:message></a>
				    <div class="dropdown-menu">
				      <a class="dropdown-item" href="?language=en" onclick="changeImage('<c:url value="/img/flags/GB.png" />');"><img src="<c:url value="/img/flags/GB.png" />"/> English(UK)</a>
				      <a class="dropdown-item" href="?language=bg" onclick="changeImage('<c:url value="/img/flags/BG.png" />');"><img src="<c:url value="/img/flags/BG.png" />"/> Български(BG)</a>
				    </div>
				  </li>
                </ul>
            </div>
        </div>
    </nav>
         <script>
		if(document.getElementById("header-languages").textContent=="languages"){
			document.getElementById("flagImage").src='<c:url value="/img/flags/GB.png" />';
		}else if(document.getElementById("header-languages").textContent=='езици'){
			document.getElementById("flagImage").src='<c:url value="/img/flags/BG.png" />';
		}
    </script>