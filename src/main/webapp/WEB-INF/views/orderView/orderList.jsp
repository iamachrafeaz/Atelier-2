<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>My Orders</title>
  <script src="https://cdn.tailwindcss.com"></script>
  <style>
    body { background-color: #f5f5f7; }
    .apple-font { font-family: -apple-system, BlinkMacSystemFont, "SF Pro Display", "SF Pro Text", sans-serif; }
  </style>
</head>

<body class="apple-font text-gray-800">

<!-- Navbar -->
<jsp:include page="../navbar.jsp" />


<c:if test="${not empty sessionScope.successMessage}">
  <div id="successToast"
       class="fixed top-6 left-1/2 transform -translate-x-1/2 bg-green-600 text-white font-semibold px-6 py-3 rounded-2xl shadow-lg z-50">
      ${sessionScope.successMessage}
  </div>
  <c:remove var="successMessage" scope="session"/>
</c:if>

<c:if test="${not empty sessionScope.errorMessage}">
  <div id="errorToast"
       class="fixed top-6 left-1/2 transform -translate-x-1/2 bg-red-600 text-white font-semibold px-6 py-3 rounded-2xl shadow-lg z-50">
      ${sessionScope.errorMessage}
  </div>
  <c:remove var="errorMessage" scope="session"/>
</c:if>


<div class="max-w-6xl mx-auto mt-24 px-6">

  <h1 class="text-3xl font-semibold mb-8">My Orders</h1>

  <c:choose>
    <c:when test="${empty orders}">
      <p class="text-gray-500 text-center mt-20">You have not placed any orders yet.</p>
      <div class="text-center mt-6">
        <a href="productsList.jsp" class="bg-black text-white px-6 py-3 rounded-xl hover:bg-gray-800 transition">Browse Products</a>
      </div>
    </c:when>
    <c:otherwise>

      <!-- Orders Table -->
      <div class="overflow-x-auto">
        <table class="min-w-full bg-white rounded-2xl shadow-md">
          <thead>
          <tr class="text-left border-b border-gray-200">
            <th class="py-4 px-6 text-gray-700">Order ID</th>
            <th class="py-4 px-6 text-gray-700">Date</th>
            <th class="py-4 px-6 text-gray-700">Status</th>
            <th class="py-4 px-6 text-gray-700">Total</th>
            <th class="py-4 px-6 text-gray-700">Action</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="order" items="${orders}">
            <tr class="border-b border-gray-100 hover:bg-gray-50 transition">
              <td class="py-4 px-6 font-medium">#${order.id}</td>
              <td class="py-4 px-6"><c:out value="${order.createdAt}" /></td>
              <td class="py-4 px-6">
                <c:choose>
                  <c:when test="${order.status.name() == 'PENDING'}">
                    <span class="bg-yellow-100 text-yellow-800 px-3 py-1 rounded-full text-sm font-semibold">Pending</span>
                  </c:when>
                  <c:when test="${order.status.name() == 'PROCESSING'}">
                    <span class="bg-blue-100 text-blue-800 px-3 py-1 rounded-full text-sm font-semibold">Processing</span>
                  </c:when>
                  <c:when test="${order.status.name() == 'COMPLETED'}">
                    <span class="bg-green-100 text-green-800 px-3 py-1 rounded-full text-sm font-semibold">Completed</span>
                  </c:when>
                  <c:when test="${order.status.name() == 'CANCELED'}">
                    <span class="bg-red-100 text-red-800 px-3 py-1 rounded-full text-sm font-semibold">Canceled</span>
                  </c:when>
                  <c:otherwise>
                    <span class="bg-gray-100 text-gray-800 px-3 py-1 rounded-full text-sm font-semibold">Unknown</span>
                  </c:otherwise>
                </c:choose>
              </td>
              <td class="py-4 px-6 font-medium">$<c:out value="${order.total}" /></td>
              <td class="py-4 px-6">
                <a href="order?action=show&id=${order.id}"
                   class="bg-black text-white px-4 py-2 rounded-xl hover:bg-gray-800 transition text-sm">
                  View
                </a>
              </td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>

    </c:otherwise>
  </c:choose>

</div>

</body>
</html>
