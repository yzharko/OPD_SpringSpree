<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Myth Mastery</title>
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
            margin-bottom: 20px;
            padding-bottom: 15px;
            border-bottom: 1px solid #eaeaea;
        }

        h2 {
            color: #34495e;
            text-align: center;
            margin-top: 25px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eaeaea;
        }

        ul {
            list-style-type: none;
            padding: 0;
            margin: 20px 0;
        }

        li {
            margin: 15px 0;
        }

        a {
            display: block;
            padding: 12px 20px;
            background-color: rgb(52, 151, 218);
            color: white;
            text-decoration: none;
            border-radius: 6px;
            text-align: center;
            transition: background-color 0.3s;
        }

        a:hover {
            background-color: rgb(40, 126, 182);
        }

    </style>
</head>
<body>
<div class="container">
    <h1>Myth Mastery Management (MMM)</h1>

    <h2>Navigation</h2>
    <ul>
        <li><a href="manageCity.jsp">City Management</a></li>
        <li><a href="manageCustomer.jsp">Customer Management</a></li>
    </ul>
    <ul>
        <li><a href="manageCategory.jsp">Category Management</a></li>
    </ul>
    <ul>
        <li><a href="manageMythology.jsp">Mythology Management</a></li>
    </ul>
    <ul>
        <li><a href="manageStep.jsp">Step Management</a></li>
    </ul>
</div>
</body>
</html>
