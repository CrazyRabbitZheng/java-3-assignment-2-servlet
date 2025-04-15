package org.example.java3assignment2servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/books")
public class BookServlet  extends HttpServlet {
    private BookDatabaseManager dbManager;

    @Override
    public void init(ServletConfig config) throws ServletException {
        try{
            dbManager = new BookDatabaseManager(
                    "jdbc:mariadb://localhost:3300/books",
                    "root",
                    "Helloworld0"
            );
        }catch(SQLException e){
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<Book> books = dbManager.getAllBooks();
            System.out.println("Books found: " + books.size());
            req.setAttribute("bookList", books);
            req.getRequestDispatcher("/books.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Error loading books", e);
        }
    }
}
