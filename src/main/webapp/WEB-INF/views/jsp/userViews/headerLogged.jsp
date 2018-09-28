<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-color-on-scroll navbar-transparent    fixed-top  navbar-expand-lg " color-on-scroll="100" id="sectionsNav">
        <div class="container">
            <div class="navbar-translate">
                <!--  <a class="navbar-brand" href="/LaundryService/">Index</a>-->
                <button class="navbar-toggler" type="button" data-toggle="collapse" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                    <span class="navbar-toggler-icon"></span>
                    <span class="navbar-toggler-icon"></span>
                </button>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ml-auto">
                 <li class = "nav-item">
                        <a href="/LaundryService/orderList/showOpenListOrderPage" class="nav-link">
                            VIEW OPEN ORDERS
                        </a>
                    </li>
                    <li class = "nav-item">
                        <a href="/LaundryService/orderList/showFinishedOrderListPage" class="nav-link">
                            VIEW FINISHED ORDERS
                        </a>
                    </li>
                    <li class = "nav-item">
                        <a href="/LaundryService/orderCreate/showCreateOrderPage" class="nav-link">
                            CREATE ORDER
                        </a>
                    </li>
                      <li class = "nav-item">
                        <a href="/LaundryService/contacts/contactWithAdmin" class="nav-link">
                           CONTACT ADMIN
                        </a>
                    </li>
                       <li class="dropdown nav-item">
                                        <a href="#pablo" class="profile-photo dropdown-toggle nav-link" data-toggle="dropdown">
                                            <div class="profile-photo-small">
                                                <img src="/LaundryService/getAvatar" alt="Circle Image" class="rounded-circle img-fluid">
                                            </div>
                                        </a>
                                        <div class="dropdown-menu dropdown-menu-right">
                                            <h6 class="dropdown-header">Welcome, ${ sessionScope.User.lastName }</h6>
                                            <a href="/LaundryService/aboutUser" class="dropdown-item">My profile</a>
                                            <a href="/LaundryService/settings" class="dropdown-item">Settings</a>
                                            <a href="/LaundryService/logout" class="dropdown-item">Sign out</a>
                                        </div>
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
