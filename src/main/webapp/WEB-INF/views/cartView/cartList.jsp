<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Your Cart</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            background-color: #f5f5f7;
        }

        .apple-font {
            font-family: -apple-system, BlinkMacSystemFont, "SF Pro Display", "SF Pro Text", sans-serif;
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


<div class="relative max-w-6xl mx-auto mt-24 px-6">

    <div id="cart-message"
         class="absolute hidden p-[10px] mt-6 rounded-[5px] w-fit left-1/2 -translate-x-1/2 top-4 shadow-lg transition-all duration-300 z-50"></div>


    <h1 class="text-3xl font-semibold mb-8">Your Cart</h1>

    <c:choose>
        <c:when test="${empty cart.cartItems}">
            <p class="text-gray-500 text-center mt-20">Your cart is empty.</p>
            <div class="text-center mt-6">
                <a href="product"
                   class="bg-black text-white px-6 py-3 rounded-xl hover:bg-gray-800 transition">Browse Products</a>
            </div>
        </c:when>
        <c:otherwise>

            <!-- Cart Table -->
            <div class="overflow-x-auto">
                <table class="min-w-full bg-white rounded-2xl shadow-md">
                    <thead>
                    <tr class="text-left border-b border-gray-200">
                        <th class="py-4 px-6 text-gray-700">Product</th>
                        <th class="py-4 px-6 text-gray-700">Price</th>
                        <th class="py-4 px-6 text-gray-700">Quantity</th>
                        <th class="py-4 px-6 text-gray-700">Subtotal</th>
                        <th class="py-4 px-6 text-gray-700">Remove</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${cart.cartItems}">
                        <tr class="border-b border-gray-100" data-id="${item.product.id}">
                            <!-- Product info -->
                            <td class="py-4 px-6 flex items-center gap-4">
                                <div class="w-16 h-16 bg-gray-200 rounded-xl flex items-center justify-center">
                                    <c:choose>
                                        <c:when test="${not empty item.product.image}">
                                            <img src="${pageContext.request.contextPath}/image?name=${item.product.image}"
                                                 alt="${item.product.label}"
                                                 class="w-full h-full object-cover rounded-xl"/>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="text-gray-400 text-sm">No Image</span>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <span class="font-medium">${item.product.label}</span>
                            </td>

                            <!-- Price -->
                            <td class="py-4 px-6">$${item.product.price}</td>

                            <!-- Quantity -->
                            <td class="py-4 px-6 flex items-center gap-2">
                                <button class="decreaseQty w-8 h-8 bg-gray-200 rounded-full hover:bg-gray-300">−
                                </button>
                                <input type="text" readonly
                                       class="w-12 text-center border border-gray-300 rounded-lg p-1 quantity"
                                       value="${item.quantity}" data-price="${item.product.price}">
                                <button class="increaseQty w-8 h-8 bg-gray-200 rounded-full hover:bg-gray-300">+
                                </button>
                            </td>

                            <!-- Subtotal -->
                            <td class="py-4 px-6 subtotal">$<c:out value="${item.quantity * item.product.price}"/></td>

                            <!-- Remove -->
                            <td class="py-4 px-6">
                                <a
                                        href="${pageContext.request.contextPath}/cart?action=delete&productId=${item.product.id}"
                                        class="removeItem bg-red-600 text-white px-3 py-1 rounded-lg hover:bg-red-500 transition">
                                    Remove
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Total & Checkout -->
            <div class="mt-8 flex flex-col md:flex-row justify-between items-center gap-4">
                <div class="text-2xl font-semibold">
                    Total: $<span id="cartTotal">${cartTotal}</span>
                </div>
                <button
                        id="checkoutBtn"
                        class="bg-black text-white px-6 py-3 rounded-xl hover:bg-gray-800 transition">Checkout
                </button>
            </div>

        </c:otherwise>
    </c:choose>

</div>

<!-- jQuery Logic for Quantity and Subtotal -->
<script>
    $(document).ready(function () {

        $("#checkoutBtn").on("click", function () {
            window.location.href = `order?action=add`;
        });

        $("#checkoutBtn").on("click", function () {
            window.location.href = `order?action=add`;
        });

        function updateSubtotal(row) {
            let qty = parseInt(row.find('.quantity').val());
            let price = parseFloat(row.find('.quantity').data('price'));
            row.find('.subtotal').text('$' + (qty * price).toFixed(2));
            updateCartTotal();
        }

        function updateCartTotal() {
            let total = 0;
            $('.subtotal').each(function () {
                total += parseFloat($(this).text().replace('$', ''));
            });
            $('#cartTotal').text(total.toFixed(2));
        }

        function ajaxUpdateQty(productId, qty) {
            $.ajax({
                url: '${pageContext.request.contextPath}/cart?action=update',
                method: 'POST',
                data: {productId: productId, quantity: qty, cartId: ${cart.id}},
                success: function () {
                    showMessage('Quantity updated successfully', 'success');
                },
                error: function () {
                    showMessage('Failed to update quantity', 'error');
                }
            });
        }

        // Increase quantity
        $('.increaseQty').on('click', function () {
            let row = $(this).closest('tr');
            let input = row.find('.quantity');
            let qty = parseInt(input.val()) + 1;
            input.val(qty);
            updateSubtotal(row);
            ajaxUpdateQty(row.data('id'), qty);
        });

        // Decrease quantity
        $('.decreaseQty').on('click', function () {
            let row = $(this).closest('tr');
            let input = row.find('.quantity');
            let qty = parseInt(input.val());
            if (qty > 1) {
                input.val(qty - 1);
                updateSubtotal(row);
                ajaxUpdateQty(row.data('id'), qty - 1);
            }
        });


        function showMessage(msg, type) {
            const box = $('#cart-message');

            // Reset and apply Tailwind styles
            box
                .removeClass()
                .addClass('absolute text-center font-medium rounded-lg py-3 transition-all duration-300');

            if (type === 'success') {
                box.addClass('bg-green-100 text-green-800 border border-green-400');
            } else if (type === 'error') {
                box.addClass('bg-red-100 text-red-800 border border-red-400');
            } else {
                box.addClass('bg-gray-100 text-gray-800 border border-gray-300');
            }

            // Set the message text and show it
            box.text(msg).hide().fadeIn(200);

            // Auto-hide after 3 seconds
            setTimeout(() => box.fadeOut(400), 3000);
        }

    });
</script>
</body>
</html>
