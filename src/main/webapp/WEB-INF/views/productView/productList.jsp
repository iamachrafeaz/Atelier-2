<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Products</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            background-color: #f5f5f7;
        }
        .apple-font {
            font-family: -apple-system, BlinkMacSystemFont, "SF Pro Text", sans-serif;
        }
    </style>
</head>
<body class="apple-font min-h-screen">

<!-- Include your navbar -->
<jsp:include page="../navbar.jsp" />

<!-- Container -->
<div class="pt-24 px-8 md:px-20">

    <!-- Header -->
    <div class="flex flex-col md:flex-row md:items-center md:justify-between mb-8">
        <h1 class="text-3xl font-semibold text-gray-900 mb-4 md:mb-0">Our Products</h1>

        <!-- Search bar -->
        <div class="relative w-full md:w-1/3">
            <form id="searchForm" action="${pageContext.request.contextPath}/product" method="get" class="relative w-full">
                <input type="hidden" name="action" value="search" />
                <input
                        id="searchInput"
                        name="term"
                        type="text"
                        placeholder="Search products..."
                        class="w-full px-4 py-2 rounded-xl border border-gray-300 focus:outline-none focus:ring-2 focus:ring-black transition"
                />
                <button type="submit">
                    <svg xmlns="http://www.w3.org/2000/svg"
                         class="w-5 h-5 absolute right-3 top-3 text-gray-500"
                         fill="none"
                         viewBox="0 0 24 24"
                         stroke-width="2"
                         stroke="currentColor"
                         >
                        <circle cx="11" cy="11" r="7" stroke-linecap="round" stroke-linejoin="round"/>
                        <line x1="16.65" y1="16.65" x2="21" y2="21" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                </button>
            </form>
        </div>
    </div>

    <!-- Product grid -->
    <div id="productGrid" class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-8">
        <c:forEach var="p" items="${products}">
            <div class="product-card bg-white rounded-3xl border border-gray-200 shadow-md hover:shadow-lg transition p-6 flex flex-col items-center text-center">
                <div class="h-40 w-40 bg-gray-100 rounded-2xl flex items-center justify-center mb-4">
                    <c:choose>
                        <c:when test="${not empty p.image}">
                            <img
                                    src="${pageContext.request.contextPath}/image?name=${p.image}"
                                    alt="Product image"
                                    class="object-cover w-full h-full"
                            />
                        </c:when>
                        <c:otherwise>
                            <span class="text-gray-400 text-sm">No Image</span>
                        </c:otherwise>
                    </c:choose>
                </div>


                <h2 class="text-lg font-semibold text-gray-900 mb-2 product-label"><c:out value="${p.label}" /></h2>
                <p class="text-gray-600 text-sm mb-1">$<c:out value="${p.price}" /></p>

                <c:choose>
                    <c:when test="${p.inventoryQty > 0}">
                        <p class="text-green-600 text-xs mb-3">In Stock</p>
                    </c:when>
                    <c:otherwise>
                        <p class="text-red-500 text-xs mb-3">Out of Stock</p>
                    </c:otherwise>
                </c:choose>

                <a href="${pageContext.request.contextPath}/product?action=show&id=${p.id}" class="bg-black text-white text-sm py-2 px-5 rounded-xl hover:bg-gray-800 transition">
                    View
                </a>
            </div>
        </c:forEach>
    </div>
</div>

<!-- jQuery search filter -->
<script>
    /*$(document).ready(function() {
        $('#searchInput').on('keyup', function() {
            const query = $(this).val().toLowerCase();
            $('.product-card').each(function() {
                const label = $(this).find('.product-label').text().toLowerCase();
                $(this).toggle(label.includes(query));
            });
        });
    });*/
</script>

</body>
</html>
