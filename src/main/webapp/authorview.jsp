<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.example.java3assignment2servlet.Author" %>
<%@ page import="org.example.java3assignment2servlet.Book" %>
<%@ page import="java.util.List" %>
<html>
<head>
  <title>Author List</title>
</head>
<body>
<h2>Author List</h2>

<%
  List<Author> authors = (List<Author>) request.getAttribute("authors");
  if (authors != null && !authors.isEmpty()) {
    for (Author author : authors) {
%>
<div style="margin-bottom: 20px;">
  <b>Name:</b> <%= author.getFirstName() %> <%= author.getLastName() %><br/>
  <b>Books:</b>
  <ul>
    <%
      for (Book book : author.getBookList()) {
    %>
    <li><%= book.getTitle() %> (ISBN: <%= book.getIsbn() %>)</li>
    <%
      }
    %>
  </ul>
</div>
<%
  }
} else {
%>
<p>No authors found.</p>
<%
  }
%>

<br/>
<a href="index.jsp">Return to Menu</a>
</body>
</html>
