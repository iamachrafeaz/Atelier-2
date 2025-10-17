<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>${product.label} - Product</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            background-color: #f5f5f7; /* Apple soft gray */
        }

        .apple-font {
            font-family: -apple-system, BlinkMacSystemFont, "SF Pro Display", "SF Pro Text", sans-serif;
        }

        .glass {
            backdrop-filter: blur(16px);
            background-color: rgba(255, 255, 255, 0.65);
        }
    </style>
</head>

<body class="apple-font text-gray-800">

<!-- Navbar -->
<jsp:include page="../navbar.jsp"/>

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

<!-- Product Section -->
<div class="max-w-6xl mx-auto mt-20 grid grid-cols-1 md:grid-cols-2 gap-12 p-6">


    <!-- Product Image Placeholder -->
    <div class="flex justify-center items-center">
        <div class="bg-gray-200 rounded-3xl w-[400px] h-[400px] flex items-center justify-center overflow-hidden">
            <c:choose>
                <c:when test="${not empty product.image}">
                    <img
                            src="${pageContext.request.contextPath}/image?name=${product.image}"
                            alt="Product image"
                            class="object-cover w-full h-full"
                    />
                </c:when>
                <c:otherwise>
                    <span class="text-gray-400 text-lg font-medium">No Image</span>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <!-- Product Details -->
    <div class="flex flex-col justify-center">
        <h1 class="text-4xl font-semibold mb-3">${product.label}</h1>

        <p class="text-gray-500 text-lg mb-6">${product.description}</p>

        <p class="text-3xl font-bold mb-4">$${product.price}</p>

        <p class="text-sm text-gray-500 mb-8">
            <c:choose>
                <c:when test="${product.inventoryQty > 0}">
                    In Stock — ${product.inventoryQty} left
                </c:when>
                <c:otherwise>
                    <span class="text-red-500 font-semibold">Out of Stock</span>
                </c:otherwise>
            </c:choose>
        </p>

        <!-- Quantity Selector -->
        <div class="flex items-center gap-4 mb-6">
            <button id="decreaseQty" class="w-10 h-10 bg-gray-200 rounded-full hover:bg-gray-300 text-lg">−</button>
            <input id="quantity" type="text" value="1" readonly
                   class="w-12 text-center border border-gray-300 rounded-lg p-1">
            <button id="increaseQty" class="w-10 h-10 bg-gray-200 rounded-full hover:bg-gray-300 text-lg">+</button>
        </div>

        <!-- Add to Cart -->
        <button id="addToCartBtn"
                class="bg-black text-white py-3 px-6 rounded-xl hover:bg-gray-800 transition">
            Add to Cart
        </button>
    </div>
</div>


<!-- jQuery Quantity Logic -->
<script>
    $(function () {
        $("#increaseQty").on("click", function () {
            let q = parseInt($("#quantity").val());
            $("#quantity").val(q + 1);
        });

        $("#decreaseQty").on("click", function () {
            let q = parseInt($("#quantity").val());
            if (q > 1) $("#quantity").val(q - 1);
        });

        $("#addToCartBtn").on("click", function () {
            const qty = $("#quantity").val();

            window.location.href = `cart?action=add&id=${product.id}&qty=` + qty;
        });

        setTimeout(function () {
            $('#successToast').fadeOut(600);
            $('#errorToast').fadeOut(600);
        }, 3000);
    });
</script>

</body>
</html>
