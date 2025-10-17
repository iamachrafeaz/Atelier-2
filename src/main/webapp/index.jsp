<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ShopLite — Home</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <style>
        body {
            background-color: #f5f5f7;
            font-family: -apple-system, BlinkMacSystemFont, "SF Pro Display", "SF Pro Text", sans-serif;
        }
        .btn-apple {
            background-color: black;
            color: white;
            padding: 0.75rem 2rem;
            border-radius: 9999px;
            font-weight: 500;
            transition: all 0.2s ease;
        }
        .btn-apple:hover {
            background-color: #333;
            transform: scale(1.05);
        }
        .btn-outline {
            border: 2px solid black;
            color: black;
            padding: 0.75rem 2rem;
            border-radius: 9999px;
            font-weight: 500;
            transition: all 0.2s ease;
        }
        .btn-outline:hover {
            background-color: black;
            color: white;
            transform: scale(1.05);
        }
    </style>
</head>

<body class="min-h-screen flex flex-col">

<!-- Navbar -->
<jsp:include page="WEB-INF/views/navbar.jsp" />

<!-- Hero Section -->
<section class="flex-grow flex flex-col items-center justify-center text-center px-6 pt-24">
    <div class="w-full max-w-3xl mb-8">
        <img src="${pageContext.request.contextPath}/assets/banner.jpeg" alt="Shop Banner" class="rounded-3xl shadow-lg w-full object-cover" />
    </div>

    <h1 class="text-4xl md:text-5xl font-semibold mb-6 text-gray-900">
        Welcome to <span class="text-black">ShopLite</span>
    </h1>
    <p class="text-gray-600 text-lg mb-10 max-w-2xl">
        Discover quality products at the best prices. Simple, elegant, and built for you.
    </p>

    <div class="flex flex-col sm:flex-row gap-4">
        <a href="product" class="btn-outline">Browse Products</a>
    </div>
</section>

<!-- Footer -->
<footer class="text-center text-gray-500 py-6 text-sm border-t border-gray-200">
    © <%= java.time.Year.now() %> ShopLite. All rights reserved.
</footer>

</body>
</html>
