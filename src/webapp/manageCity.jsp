<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>City Management</title>
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
            max-width: 800px;
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

        .actions {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin-top: 30px;
        }

        .action-card {
            padding: 20px;
            border: 1px solid #eaeaea;
            border-radius: 6px;
            text-align: center;
            transition: transform 0.3s, box-shadow 0.3s;
        }

        .action-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .action-card h2 {
            color: #34495e;
            margin-top: 0;
            font-weight: bold;
        }

        .action-card a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #3497da;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.3s;
        }

        .action-card a:hover {
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

        .back-container {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>City Management</h1>

    <div class="actions">
        <div class="action-card">
            <h2>Get City Information</h2>
            <p>Search for existing cities by ID or view all cities</p>
            <a href="getCity.jsp">Go to Get City</a>
        </div>

        <div class="action-card">
            <h2>Add New City</h2>
            <p>Create a new city entry in the database</p>
            <a href="postCity.jsp">Go to Add City</a>
        </div>

        <div class="action-card">
            <h2>Delete City</h2>
            <p>Remove a city from the database</p>
            <a href="deleteCity.jsp">Go to Delete City</a>
        </div>

        <div class="action-card">
            <h2>Update City</h2>
            <p>Modify existing city information</p>
            <a href="updateCity.jsp">Go to Update City</a>
        </div>
    </div>

    <div class="back-container">
        <a href="index.jsp" class="back-link">← Back</a>
    </div>
</div>
</body>
</html>
