<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Order #${order.id} Details</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body { background-color: #f5f5f7; }
        .apple-font { font-family: -apple-system, BlinkMacSystemFont, "SF Pro Display", "SF Pro Text", sans-serif; }
    </style>
</head>

<body class="apple-font text-gray-800">

<!-- Navbar -->
<jsp:include page="../navbar.jsp" />

<div class="max-w-6xl mx-auto mt-24 px-6">

    <!-- Header -->
    <div class="flex flex-col md:flex-row justify-between items-start md:items-center mb-8">
        <h1 class="text-3xl font-semibold mb-4 md:mb-0">Order #${order.id}</h1>
        <div>
            <c:choose>
                <c:when test="${order.status.name() == 'PENDING'}">
                    <span class="bg-yellow-100 text-yellow-800 px-4 py-1 rounded-full text-sm font-semibold">Pending</span>
                </c:when>
                <c:when test="${order.status.name() == 'PROCESSING'}">
                    <span class="bg-blue-100 text-blue-800 px-4 py-1 rounded-full text-sm font-semibold">Processing</span>
                </c:when>
                <c:when test="${order.status.name() == 'COMPLETED'}">
                    <span class="bg-green-100 text-green-800 px-4 py-1 rounded-full text-sm font-semibold">Completed</span>
                </c:when>
                <c:when test="${order.status.name() == 'CANCELED'}">
                    <span class="bg-red-100 text-red-800 px-4 py-1 rounded-full text-sm font-semibold">Canceled</span>
                </c:when>
                <c:otherwise>
                    <span class="bg-gray-100 text-gray-800 px-4 py-1 rounded-full text-sm font-semibold">Unknown</span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Order Table -->
    <div class="overflow-x-auto mb-6">
        <table class="min-w-full bg-white rounded-2xl shadow-md">
            <thead>
            <tr class="text-left border-b border-gray-200">
                <th class="py-4 px-6 text-gray-700">Product</th>
                <th class="py-4 px-6 text-gray-700">Price</th>
                <th class="py-4 px-6 text-gray-700">Quantity</th>
                <th class="py-4 px-6 text-gray-700">Subtotal</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="item" items="${order.orderItems}">
                <tr class="border-b border-gray-100 hover:bg-gray-50 transition">
                    <td class="py-4 px-6 flex items-center gap-4">
                        <div class="w-16 h-16 bg-gray-200 rounded-xl flex items-center justify-center">
                            <c:choose>
                                <c:when test="${not empty item.product.image}">
                                    <img src="${pageContext.request.contextPath}/image?name=${item.product.image}"
                                         alt="${item.product.label}" class="w-full h-full object-cover rounded-xl"/>
                                </c:when>
                                <c:otherwise>
                                    <span class="text-gray-400 text-sm">No Image</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <span class="font-medium">${item.product.label}</span>
                    </td>

                    <td class="py-4 px-6">$${item.product.price}</td>
                    <td class="py-4 px-6">${item.quantity}</td>
                    <td class="py-4 px-6 font-semibold">$<c:out value="${item.quantity * item.product.price}" /></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Order Summary -->
    <div class="flex flex-col md:flex-row justify-end items-center gap-6 mt-6">
        <div class="text-xl font-semibold">
            Total: $<c:out value="${order.total}" />
        </div>
        <a href="${pageContext.request.contextPath}/order" class="bg-black text-white px-6 py-3 rounded-xl hover:bg-gray-800 transition">
            Back to Orders
        </a>
    </div>

</div>

</body>
</html>
