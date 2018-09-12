<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<nav class="navbar navbar-color-on-scroll navbar-transparent    fixed-top  navbar-expand-lg " color-on-scroll="100" id="sectionsNav">
        <div class="container">
            <div class="navbar-translate">
                <a class="navbar-brand" href="/LaundryService/">Index</a>
                <button class="navbar-toggler" type="button" data-toggle="collapse" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                    <span class="navbar-toggler-icon"></span>
                    <span class="navbar-toggler-icon"></span>
                </button>
            </div>
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav ml-auto">
                <li class = "nav-item">
                        <a href="/LaundryService/viewExpressOrders" class="nav-link">
                           EXPRESS ORDERS
                        </a>
                    </li>
                    <li class = "nav-item">
                        <a href="/LaundryService/viewNormalOrders" class="nav-link">
                            ORDERS
                        </a>
                    </li>
                    <li class = "nav-item">
                        <a href="/LaundryService/showUsers" class="nav-link">
                            SHOW USERS
                        </a>
                    </li>
                       <li class="dropdown nav-item">
                                        <a href="#pablo" class="profile-photo dropdown-toggle nav-link" data-toggle="dropdown">
                                            <div class="profile-photo-small">
                                                <img src="/LaundryService/getAvatarForAdmin" alt="Circle Image" class="rounded-circle img-fluid">
                                            </div>
                                        </a>
                                        <div class="dropdown-menu dropdown-menu-right">
                                            <h6 class="dropdown-header">Welcome, Admin</h6>
                                            <a href="/LaundryService/aboutAdminPage" class="dropdown-item">Me</a>
                                            <a href="/LaundryService/settingsAdmin" class="dropdown-item">Settings</a>
                                            <a href="/LaundryService/logout" class="dropdown-item">Sign out</a>
                                        </div>
                                    </li>
                </ul>
            </div>
        </div>
    </nav>
