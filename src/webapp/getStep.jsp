<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Step Search</title>
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

        ::placeholder {
            font-family: 'Arial', sans-serif;
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

        .steps-table {
            margin-top: 30px;
        }

        .steps-table h2 {
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
    <h1>Step Search</h1>

    <form action="getStep" method="get">
        <div class="form-group">
            <label>Get Step by ID:</label>
            <div class="input-group">
                <input type="number" id="StepId" name="id" required min="1"
                       placeholder="Enter Step ID (e.g. 1)">
                <input type="submit" value="Search by ID">
            </div>
        </div>
    </form>

    <form action="getStep" method="get">
        <label>Get all Steps:</label>
        <input type="hidden" name="action" value="all">
        <input type="submit" value="Show all steps">
    </form>

    <c:if test="${not empty steps}">
        <div class="steps-table">
            <h2>Steps List</h2>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Description</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="step" items="${steps}">
                    <tr>
                        <td>${step.id}</td>
                        <td>${step.name}</td>
                        <td>${step.description}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </c:if>

    <div class="actions">
        <a href="manageStep.jsp" class="back-link">← Back</a>
    </div>
</div>
</body>
</html>
