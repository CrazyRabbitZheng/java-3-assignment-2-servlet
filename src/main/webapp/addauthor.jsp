<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add New Author</title>
</head>
<body>
<h2>Add a New Author</h2>

<form action="LibraryData" method="post">
    <!-- Hidden field to indicate this is an author submission -->
    <input type="hidden" name="type" value="author" />

    <label>First Name:</label><br/>
    <input type="text" name="firstName" required/><br/><br/>

    <label>Last Name:</label><br/>
    <input type="text" name="lastName" required/><br/><br/>

    <input type="submit" value="Add Author"/>
</form>

<br/>

<a href="index.jsp">Return to Menu</a>
</body>
</html>
