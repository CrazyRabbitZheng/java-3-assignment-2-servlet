<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add New Book</title>
</head>
<body>
<h2>Add a New Book with Author</h2>

<form action="LibraryData" method="post">
    <!-- Hidden field to tell servlet this is a book submission -->
    <input type="hidden" name="type" value="book" />

    <!-- Book Info -->
    <label>ISBN:</label><br/>
    <input type="text" name="isbn" required/><br/><br/>

    <label>Title:</label><br/>
    <input type="text" name="title" required/><br/><br/>

    <label>Edition Number:</label><br/>
    <input type="number" name="editionNumber" required/><br/><br/>

    <label>Copyright Year:</label><br/>
    <input type="number" name="copyright" required/><br/><br/>

    <!-- Author Info -->
    <h4>Author Information</h4>

    <label>First Name:</label><br/>
    <input type="text" name="firstName" required/><br/><br/>

    <label>Last Name:</label><br/>
    <input type="text" name="lastName" required/><br/><br/>

    <input type="submit" value="Add Book"/>
</form>

<br/>
<a href="index.jsp">Return to Menu</a>
</body>
</html>
