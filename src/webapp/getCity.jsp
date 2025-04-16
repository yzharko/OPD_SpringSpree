<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>GET request</title>
</head>
<body>
<h1>Getting city</h1>
<form action="getCity" method="get">
    // form action="поле_name_из_аннотации_@WebServlet_над_контроллером"
    id:<input type="text" name="id">
    // поле для ввода параметра id
    <input type="submit" value="Отправить GET">
    // рисуем кнопку для отправки запроса с указанным аргументом
</form>
</body>
</html>
