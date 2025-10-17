<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Page Not Found</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            background-color: #f5f5f7; /* Apple soft gray */
        }
        .apple-font {
            font-family: -apple-system, BlinkMacSystemFont, "SF Pro Text", sans-serif;
        }
        .glass {
            backdrop-filter: blur(14px);
            background-color: rgba(255, 255, 255, 0.65);
        }
    </style>
</head>
<body class="apple-font flex flex-col items-center justify-center min-h-screen text-center">

<!-- Optional navbar -->
<jsp:include page="../navbar.jsp" />

<div class="mt-20 glass border border-gray-200 shadow-lg rounded-3xl p-12 w-[28rem]">
    <h1 class="text-7xl font-bold text-gray-900 mb-4">404</h1>
    <h2 class="text-2xl font-medium text-gray-800 mb-4">Page Not Found</h2>
    <p class="text-gray-600 mb-8">
        The page you’re looking for doesn’t exist or may have been moved.
    </p>

    <div class="flex flex-col sm:flex-row gap-4 justify-center">
        <a href="index.jsp"
           class="bg-black text-white px-6 py-3 rounded-xl hover:bg-gray-800 transition">
            Go Home
        </a>
        <a href="${pageContext.request.contextPath}/product"
           class="border border-gray-400 px-6 py-3 rounded-xl hover:bg-gray-100 transition">
            Browse Products
        </a>
    </div>
</div>

<p class="mt-10 text-gray-400 text-sm">
    &copy; <c:out value="${pageContext.request.serverName}" /> — ShopLite
</p>

</body>
</html>
