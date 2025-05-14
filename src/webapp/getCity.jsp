<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>City Search</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            background-color: #f5f5f5;
        }

        .container {
            width: 80%;
            max-width: 600px;
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        h1 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 25px;
            padding-bottom: 15px;
            border-bottom: 1px solid #eaeaea;
        }

        form {
            margin-top: 20px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 8px;
            font-weight: bold;
            color: #34495e;
        }

        .input-group {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        input[type="number"] {
            padding: 10px;
            width: 200px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        input[type="submit"] {
            padding: 10px 20px;
            background-color: #3497da;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
            white-space: nowrap;
        }

        input[type="submit"]:hover {
            background-color: #287eb6;
        }

        .back-link {
            display: inline-block;
            padding: 8px 15px;
            background-color: #f0f0f0;
            color: #333;
            text-decoration: none;
            border-radius: 4px;
            border: 1px solid #ddd;
            transition: all 0.3s;
            margin-top: 15px;
        }

        .back-link:hover {
            background-color: #e0e0e0;
            border-color: #ccc;
        }

        .actions {
            text-align: center;
        }

        .cities-table {
            margin-top: 30px;
        }

        .cities-table h2 {
            color: #2c3e50;
            text-align: center;
            margin-bottom: 15px;
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #3497da;
            color: white;
        }

        tr:hover {
            background-color: #f5f5f5;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>City Search</h1>

    <form action="getCity" method="get">
        <div class="form-group">
            <label>Get City by ID:</label>
            <div class="input-group">
                <input type="number" id="CityId" name="id" required min="1">
                <input type="submit" value="Search by ID">
            </div>
        </div>
    </form>

    <form action="getCity" method="get">
        <label>Get all cities:</label>
        <input type="hidden" name="action" value="all">
        <input type="submit" value="Show all cities">
    </form>

    <c:if test="${not empty cities}">
        <div class="cities-table">
            <h2>Cities List</h2>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Delivery Time</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="city" items="${cities}">
                    <tr>
                        <td>${city.id}</td>
                        <td>${city.name}</td>
                        <td>${city.deliveryTime}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <div class="actions">
        <a href="manageCity.jsp" class="back-link">← Back</a>
    </div>
</div>
</body>
</html>
