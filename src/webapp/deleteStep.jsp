<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Delete Step</title>
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
            max-width: 500px;
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

        input[type="number"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }

        ::placeholder {
            font-family: 'Arial', sans-serif;
        }

        input[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: #e74c3c;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        input[type="submit"]:hover {
            background-color: #c0392b;
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

        .error-message {
            color: #e74c3c;
            margin-bottom: 15px;
            text-align: center;
        }

        .success-message {
            color: #27ae60;
            margin-bottom: 15px;
            text-align: center;
        }

        .actions {
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Delete Step</h1>

    <% if (request.getParameter("error") != null) { %>
    <div class="error-message">
        Error: <%= request.getParameter("error").replace("+", " ") %>
    </div>
    <% } %>

    <% if (request.getParameter("success") != null) { %>
    <div class="success-message">
        Success: Step was deleted successfully!
    </div>
    <% } %>

    <form action="deleteStep" method="post">
        <div class="form-group">
            <label for="stepId">Step ID:</label>
            <input type="number" id="stepId" name="id" required min="1" placeholder="Enter Step ID (e.g. 1)">
        </div>

        <input type="submit" value="Delete Step">
    </form>

    <div class="actions">
        <a href="manageStep.jsp" class="back-link">← Back</a>
    </div>
</div>
</body>
</html>
