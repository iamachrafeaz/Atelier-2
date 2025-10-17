<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign In</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            background-color: #f5f5f7;
        }
        .apple-font {
            font-family: -apple-system, BlinkMacSystemFont, "SF Pro Text", sans-serif;
        }
        .glass {
            backdrop-filter: blur(12px);
            background-color: rgba(255, 255, 255, 0.6);
        }
    </style>
</head>
<body class="flex items-center justify-center min-h-screen apple-font">
<div class="glass shadow-xl rounded-3xl p-10 w-96 text-center border border-gray-200">
    <h1 class="text-3xl font-semibold text-gray-800 mb-6">Sign In</h1>

    <c:if test="${not empty error}">
        <div class="text-red-500 mb-3">${error}</div>
    </c:if>

    <form id="loginForm" method="post" action="${pageContext.request.contextPath}/user" class="flex flex-col space-y-4">
        <input
                type="text"
                name="email"
                placeholder="Email"
                required
                class="px-4 py-2 rounded-xl border border-gray-300 focus:outline-none focus:ring-2 focus:ring-black transition"
        />
        <input
                type="password"
                name="password"
                placeholder="Password"
                required
                class="px-4 py-2 rounded-xl border border-gray-300 focus:outline-none focus:ring-2 focus:ring-black transition"
        />
        <button
                type="submit"
                class="bg-black text-white py-2 rounded-xl hover:bg-gray-800 transition"
        >
            Continue
        </button>
    </form>

    <p class="text-sm text-gray-600 mt-6">
        Don’t have an account?
        <a href="user?action=register" class="text-black hover:underline">Sign up</a>
    </p>
</div>

<script>
    // Simple jQuery animation feedback
    $('#loginForm').on('submit', function(e) {
        const btn = $(this).find('button');
        btn.text('Signing in...');
        btn.prop('disabled', true);
    });
</script>
</body>
</html>
