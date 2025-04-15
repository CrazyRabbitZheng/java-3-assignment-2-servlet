package org.example.java3assignment2servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * This servlet puts the original book database into a TomCat servlet
 * You can view all books / authors/ adding a book/ adding an author
 */
@WebServlet("/LibraryData")
public class LibraryData extends HttpServlet {
    private BookDatabaseManager dbManager;

    /**
     * Initialize the servlet
     * build JDBC
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        try {

            Class.forName("org.mariadb.jdbc.Driver");
            dbManager = new BookDatabaseManager(
                    "jdbc:mariadb://localhost:3300/books",
                    "root",
                    "Helloworld0"
            );
        }catch(ClassNotFoundException e) {
            throw new ServletException("MariaDB JDBC driver not found",e);
        }catch (SQLException e) {
            throw new ServletException("Database connection failed", e);
        }
    }

    /**
     * A handler.  handle book and author “views”
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String view = request.getParameter("view");
        if (view == null) {
            request.setAttribute("message", "Missing view parameter.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        try {
            if ("books".equalsIgnoreCase(view)) {
                List<Book> books = dbManager.getAllBooks();
                request.setAttribute("books", books);
                request.getRequestDispatcher("bookview.jsp").forward(request, response);
            } else if ("authors".equalsIgnoreCase(view)) {
                List<Author> authors = dbManager.getAllAuthors();
                request.setAttribute("authors", authors);
                request.getRequestDispatcher("authorview.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Unknown view type: " + view);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            request.setAttribute("message", "Error loading data: " + e.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    /**
     * Handle submissions of form data, one for each of a book and an author.
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String type = request.getParameter("type");
        request.setCharacterEncoding("UTF-8");

        try {
            if ("book".equalsIgnoreCase(type)) {
                String isbn = request.getParameter("isbn").trim();
                String title = request.getParameter("title").trim();
                int edition = Integer.parseInt(request.getParameter("editionNumber").trim());
                int copyright = Integer.parseInt(request.getParameter("copyright").trim());
                String firstName = request.getParameter("firstName").trim();
                String lastName = request.getParameter("lastName").trim();

                // Add author. Author ID is auto-incremented. We don't type in author ID
                Author author = new Author(0, firstName, lastName);
                dbManager.addAuthor(author);

                // Add book with author
                Book book = new Book(isbn, title, edition, copyright);
                book.addAuthor(author);
                dbManager.addBook(book);

                request.setAttribute("message", "New book added successfully!");
            } else if ("author".equalsIgnoreCase(type)) {
                String firstName = request.getParameter("firstName").trim();
                String lastName = request.getParameter("lastName").trim();

                Author author = new Author(0, firstName, lastName);
                dbManager.addAuthor(author);

                request.setAttribute("message", "New author added successfully!");
            } else {
                request.setAttribute("message", "Unknown form submission type.");
            }
        } catch (SQLException e) {
            request.setAttribute("message", "Database error: " + e.getMessage());
        } catch (NumberFormatException e) {
            request.setAttribute("message", "Invalid number format in form fields.");
        }

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
