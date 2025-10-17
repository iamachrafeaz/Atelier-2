<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="bg-white/80 backdrop-blur-md border-b border-gray-200 px-6 py-3 flex justify-between items-center apple-font fixed top-0 w-full z-50">
    <!-- Left: Logo -->
    <a href="${pageContext.request.contextPath}"
       class="text-lg font-semibold text-gray-900 hover:text-black transition">
        ShopLite
    </a>

    <!-- Center (optional nav links) -->
    <div class="hidden md:flex space-x-6">
        <a href="${pageContext.request.contextPath}/product"
           class="text-gray-700 hover:text-black transition">Products</a>
    </div>

    <!-- Right: Account + Cart -->
    <div class="flex items-center space-x-5">
        <!-- Cart Icon -->
        <c:if test="${not empty sessionScope.user}">
            <a href="cart" class="relative">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.8"
                     stroke="currentColor" class="w-6 h-6 text-gray-700 hover:text-black transition">
                    <path stroke-linecap="round" stroke-linejoin="round"
                          d="M2.25 3h1.386c.51 0 .955.343 1.09.837l.562 2.111m0 0L6.75 14.25A2.25 2.25 0 009 16.5h9.75a2.25 2.25 0 002.183-1.665l1.5-6A2.25 2.25 0 0020.25 6H5.698m-.41 0L4.5 3.75M9 20.25a.75.75 0 110-1.5.75.75 0 010 1.5zm9.75 0a.75.75 0 110-1.5.75.75 0 010 1.5z"/>
                </svg>
            </a>
        </c:if>

        <!-- Account -->
        <div class="relative account-dropdown">
            <button class="flex items-center space-x-2 focus:outline-none account-btn">
                <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.8"
                     stroke="currentColor" class="w-6 h-6 text-gray-700 hover:text-black transition">
                    <path stroke-linecap="round" stroke-linejoin="round"
                          d="M15.75 6a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0zM4.5 20.25a8.25 8.25 0 1115 0v.75H4.5v-.75z"/>
                </svg>
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <span class="text-gray-800 text-sm">
                            <c:out value="${sessionScope.user.firstName} ${sessionScope.user.lastName}"/>
                        </span>
                    </c:when>
                    <c:otherwise>
                        <span class="text-gray-600 text-sm">Account</span>
                    </c:otherwise>
                </c:choose>
            </button>

            <!-- Dropdown -->
            <div class="dropdown-menu flex flex-col absolute right-0  bg-white border border-gray-200 rounded-xl shadow-lg py-2 w-40 hidden">
                <c:choose>
                    <c:when test="${not empty sessionScope.user}">
                        <a href="${pageContext.request.contextPath}/user?action=profile"
                           class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 text-left">Profile</a>
                        <a href="${pageContext.request.contextPath}/order"
                           class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 text-left">Orders</a>
                        <a href="${pageContext.request.contextPath}/user?action=logout"
                           class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 text-left">Log
                            out</a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/user"
                           class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 text-left">Sign in</a>
                        <a href="${pageContext.request.contextPath}/user?action=register"
                           class="px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 text-left">Register</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
        $(function () {
            // Handle hover on account section
            $('.account-dropdown').hover(
                function () {
                    $(this).find('.dropdown-menu')
                        .removeClass('hidden')

                },
                function () {
                    $(this).find('.dropdown-menu')
                        .addClass('hidden')
                }
            );
        });
    </script>

</nav>
