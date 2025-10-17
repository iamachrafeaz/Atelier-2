<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>My Profile</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            background-color: #f5f5f7;
            font-family: -apple-system, BlinkMacSystemFont, "SF Pro Display", "SF Pro Text", sans-serif;
        }
        .apple-font { font-family: inherit; }
    </style>
</head>

<body class="apple-font">

<!-- Navbar -->
<jsp:include page="../navbar.jsp" />

<div class="max-w-3xl mx-auto mt-24 px-6">

    <h1 class="text-3xl font-semibold mb-8">My Profile</h1>

    <!-- Profile Form -->
    <form action="${pageContext.request.contextPath}/user?action=update" method="post" class="bg-white rounded-3xl shadow-md p-8 space-y-6">

        <!-- Success/Error Messages -->
        <c:if test="${not empty sessionScope.successMessage}">
            <div class="bg-green-600 text-white font-semibold px-6 py-3 rounded-2xl shadow-lg">
                    ${sessionScope.successMessage}
            </div>
            <c:remove var="successMessage" scope="session"/>
        </c:if>

        <c:if test="${not empty sessionScope.errorMessage}">
            <div class="bg-red-600 text-white font-semibold px-6 py-3 rounded-2xl shadow-lg">
                    ${sessionScope.errorMessage}
            </div>
            <c:remove var="errorMessage" scope="session"/>
        </c:if>

        <!-- Name -->
        <div class="flex flex-col md:flex-row gap-4">
            <div class="flex-1">
                <label class="block text-gray-700 font-medium mb-1" for="firstName">First Name</label>
                <input type="text" id="firstName" name="firstName" value="${user.firstName}"
                       class="w-full border border-gray-300 rounded-xl px-4 py-2 focus:outline-none focus:ring-2 focus:ring-black transition" required>
            </div>
            <div class="flex-1">
                <label class="block text-gray-700 font-medium mb-1" for="lastName">Last Name</label>
                <input type="text" id="lastName" name="lastName" value="${user.lastName}"
                       class="w-full border border-gray-300 rounded-xl px-4 py-2 focus:outline-none focus:ring-2 focus:ring-black transition" required>
            </div>
        </div>

        <!-- Email -->
        <div>
            <label class="block text-gray-700 font-medium mb-1" for="email">Email</label>
            <input type="email" id="email" name="email" value="${user.email}"
                   class="w-full border border-gray-300 rounded-xl px-4 py-2 focus:outline-none focus:ring-2 focus:ring-black transition" required>
        </div>

        <!-- Password -->
        <div>
            <label class="block text-gray-700 font-medium mb-1" for="password">Password</label>
            <input type="password" id="password" name="password" value="***********"
                   class="w-full border border-gray-300 rounded-xl px-4 py-2 focus:outline-none focus:ring-2 focus:ring-black transition" required>
        </div>

        <!-- Address -->
        <div>
            <label class="block text-gray-700 font-medium mb-1" for="address">Address</label>
            <input type="text" id="address" name="address" value="${user.address}"
                   class="w-full border border-gray-300 rounded-xl px-4 py-2 focus:outline-none focus:ring-2 focus:ring-black transition">
        </div>

        <!-- Phone Number -->
        <div>
            <label class="block text-gray-700 font-medium mb-1" for="phoneNumber">Phone Number</label>
            <input type="text" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}"
                   class="w-full border border-gray-300 rounded-xl px-4 py-2 focus:outline-none focus:ring-2 focus:ring-black transition">
        </div>

        <!-- Update Button -->
        <div class="flex justify-end">
            <button type="submit" class="bg-black text-white px-6 py-3 rounded-xl hover:bg-gray-800 transition">
                Update Profile
            </button>
        </div>

    </form>

</div>

</body>
</html>
