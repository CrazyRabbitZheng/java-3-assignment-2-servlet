package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages interaction with a MariaDB book database, providing methods to retrieve, add, and update books and authors
 */
public class BookDatabaseManager {

    private final Connection conn;

    /**
     * Construct a BookDatabaseManager with database connection details
     *
     * @param url
     * @param user
     * @param pass
     * @throws SQLException
     */
    public BookDatabaseManager(String url, String user, String pass) throws SQLException {
        conn = DriverManager.getConnection(url, user, pass);
    }


    /**
     * Retrieves a list of all books with theirs authors
     *
     * @return A list of Books objects
     * @throws SQLException
     */
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<Book>();
        String sql = "SELECT t.isbn, t.title, t.editionNumber, t.copyright, a.authorID, a.firstName, a.lastName " +
                "FROM titles t " +
                "JOIN authorisbn ai ON t.isbn = ai.isbn " +
                "JOIN authors a ON ai.authorID = a.authorID";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        Map<String, Book> bookMap = new HashMap<>();
        Map<Integer, Author> authorMap = new HashMap<>();

        while (rs.next()) {
            String isbn = rs.getString("isbn");
            String title = rs.getString("title");
            int editionNumber = rs.getInt("editionNumber");
            int copyright = rs.getInt("copyright");
            int authorId = rs.getInt("authorID");
            String authorFirstName = rs.getString("firstName");
            String authorLastName = rs.getString("lastName");

            Book book = bookMap.computeIfAbsent(isbn, id -> new Book(id, title, editionNumber, copyright));
            Author author = authorMap.computeIfAbsent(authorId, id -> new Author(id, authorFirstName, authorLastName));

            book.addAuthor(author);
        }
        return new ArrayList<>(bookMap.values());
    }


    /**
     * Retrieves a list of all authors with books they written
     *
     * @return A list of Author objects
     * @throws SQLException
     */
    public List<Author> getAllAuthors() throws SQLException {
        List<Author> authors = new ArrayList<Author>();
        String sql = "SELECT t.isbn, t.title, t.editionNumber, t.copyright, a.authorID, a.firstName, a.lastName FROM titles t " +
                "JOIN authorisbn ai ON t.isbn = ai.isbn " +
                "JOIN authors a ON ai.authorID = a.authorID";

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        Map<String, Book> bookMap = new HashMap<>();
        Map<Integer, Author> authorMap = new HashMap<>();

        while (rs.next()) {
            String isbn = rs.getString("isbn");
            String title = rs.getString("title");
            int editionNumber = rs.getInt("editionNumber");
            int copyright = rs.getInt("copyright");
            int authorId = rs.getInt("authorID");
            String authorFirstName = rs.getString("firstName");
            String authorLastName = rs.getString("lastName");


            Author author = authorMap.computeIfAbsent(authorId, id -> new Author(id, authorFirstName, authorLastName));
            Book book = bookMap.computeIfAbsent(isbn, id -> new Book(isbn, title, editionNumber, copyright));

            author.addBook(book);
        }
        return new ArrayList<>(authorMap.values());
    }

    /**
     * Add a new book to the database, including its author
     *
     * @param book
     * @throws SQLException
     */
    public void addBook(Book book) throws SQLException {
        String bookSql = "INSERT INTO titles (isbn, title, editionNumber, copyright) VALUES (?, ?, ?, ?)";
        String authorIsbnSql = "INSERT INTO authorisbn (authorID, isbn) VALUES (?, ?)";
        try (PreparedStatement bookStmt = conn.prepareStatement(bookSql);
             PreparedStatement authorIsbnStmt = conn.prepareStatement(authorIsbnSql)) {
            bookStmt.setString(1, book.getIsbn());
            bookStmt.setString(2, book.getTitle());
            bookStmt.setInt(3, book.getEditionNumber());
            bookStmt.setInt(4, book.getCopyright());
            bookStmt.executeUpdate();

            for (Author author : book.getAuthorList()) {
                authorIsbnStmt.setInt(1, author.getAuthorID());
                authorIsbnStmt.setString(2, book.getIsbn());
                authorIsbnStmt.executeUpdate();
            }
        }
    }

    /**
     * Retrieves an author by their ID
     *
     * @param authorID
     * @return The Author object, or null if not found
     * @throws SQLException
     */
    public Author getAuthorById(int authorID) throws SQLException {
        String sql = "SELECT authorID, firstName, lastName FROM authors WHERE authorID = ?";
        try (PreparedStatement authorStmt = conn.prepareStatement(sql)) {
            authorStmt.setInt(1, authorID);
            ResultSet rs = authorStmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                return new Author(authorID, firstName, lastName);
            }

            return null;
        }
    }

    /**
     * Retrieves a book by its ISBN
     *
     * @param isbn
     * @return The Book object, or null if not found
     * @throws SQLException
     */
    public Book getBookByIsbn(String isbn) throws SQLException {
        String sql = "SELECT isbn, title, editionNUmber, copyright FROM titles WHERE isbn = ?";
        try (PreparedStatement bookStmt = conn.prepareStatement(sql)) {
            bookStmt.setString(1, isbn);
            ResultSet rs = bookStmt.executeQuery();

            if (rs.next()) {
                String title = rs.getString("title");
                int editionNumber = rs.getInt("editionNumber");
                int copyright = rs.getInt("copyright");
                return new Book(isbn, title, editionNumber, copyright);
            }

            return null;
        }
    }

    /**
     * Adds a new author to the database
     *
     * @param author
     * @throws SQLException
     */
    public void addAuthor(Author author) throws SQLException {
        String sql = "INSERT INTO authors (firstName, lastName) VALUES (?, ?)";//authorID is auto incremented
        try (PreparedStatement authorStmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            authorStmt.setString(1, author.getFirstName());
            authorStmt.setString(2, author.getLastName());
            authorStmt.executeUpdate();

            try (ResultSet generatedKeys = authorStmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    author.setAuthorID(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Failed to insert author");
                }
            }
        }
    }

    /**
     * Updates an existing book in the database
     *
     * @param book
     * @throws SQLException
     */
    public void updateBook(Book book) throws SQLException {
        String sql = "UPDATE titles SET title = ?, editionNumber = ?, copyright = ? WHERE isbn = ?";
        try (PreparedStatement bookStmt = conn.prepareStatement(sql)) {
            bookStmt.setString(1, book.getTitle());
            bookStmt.setInt(2, book.getEditionNumber());
            bookStmt.setInt(3, book.getCopyright());
            bookStmt.setString(4, book.getIsbn());
            bookStmt.executeUpdate();
        }
    }

    /**
     * Updates an existing author in the database
     *
     * @param author
     * @throws SQLException
     */
    public void updateAuthor(Author author) throws SQLException {
        String sql = "UPDATE authors SET firstName = ?, lastName = ? WHERE authorID = ?";
        try (PreparedStatement authorStmt = conn.prepareStatement(sql)) {
            authorStmt.setString(1, author.getFirstName());
            authorStmt.setString(2, author.getLastName());
            authorStmt.setInt(3, author.getAuthorID());
            authorStmt.executeUpdate();
        }
    }
}

