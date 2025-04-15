<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.java3assignment2servlet.Book" %>
<%@ page import="org.example.java3assignment2servlet.Author" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Book List</title>
</head>
<body>
<h2>Book List</h2>

<%
    List<Book> books = (List<Book>) request.getAttribute("books");
    if (books != null && !books.isEmpty()) {
        for (Book book : books) {
%>
<div style="margin-bottom: 20px;">
    <b>Title:</b> <%= book.getTitle() %><br/>
    <b>ISBN:</b> <%= book.getIsbn() %><br/>
    <b>Edition:</b> <%= book.getEditionNumber() %><br/>
    <b>Copyright:</b> <%= book.getCopyright() %><br/>
    <b>Authors:</b>
    <ul>
        <%
            for (Author author : book.getAuthorList()) {
        %>
        <li><%= author.getFirstName() %> <%= author.getLastName() %></li>
        <%
            }
        %>
    </ul>
</div>
<%
    }
} else {
%>
<p>No books found.</p>
<%
    }
%>

<br/>
<a href="index.jsp">Return to Menu</a>
</body>
</html>
