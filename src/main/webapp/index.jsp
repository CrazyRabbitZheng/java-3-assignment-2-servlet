<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Library Menu</title>
</head>
<body>
<h1>Library Management System</h1>

<ul>

    <li><a href="LibraryData?view=books">View Books</a></li>
    <li><a href="LibraryData?view=authors">View Authors</a></li>
    <li><a href="addbook.jsp">Add a New Book</a></li>
    <li><a href="addauthor.jsp">Add a New Author</a></li>
</ul>

<%
    String message = (String) request.getAttribute("message");
    if (message != null) {
%>
<p style="color:green;"><b><%= message %></b></p>
<%
    }
%>

</body>
</html>
