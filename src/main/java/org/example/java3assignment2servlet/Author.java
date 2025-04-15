/**
 * Represents an author with their ID, name, and books they wrote
 */
package org.example.java3assignment2servlet;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private int authorID;
    private String firstName;
    private String lastName;
    private List<Book> bookList = new ArrayList<>();

    /**
     * Constructs a new Author object
     *
     * @param authorID
     * @param firstName
     * @param lastName
     */
    public Author(int authorID, String firstName, String lastName) {
        this.authorID = authorID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Gets the ID of the author
     *
     * @return author's ID
     */
    public int getAuthorID() {
        return authorID;
    }

    /**
     * Gets author's firstname
     *
     * @return firstname
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets author's lastname
     *
     * @return lastname
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the firstname of the author
     *
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the author's lastname
     *
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the list of books written by the author
     *
     * @return list of books
     */
    public List<Book> getBookList() {
        return bookList;
    }

    /**
     * Sets the ID of the author
     *
     * @param authorID
     */
    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    /**
     * Adds a book to the author's book list, and associates the author with the book
     *
     * @param book
     */
    public void addBook(Book book) {
        if (!bookList.contains(book)) {
            bookList.add(book);
            book.addAuthor(this);
        }
    }
}
