/**
 * Represents a book with its ISBN, title, edition number , copyright year, and its author
 */
package org.example.java3assignment2servlet;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String isbn;
    private String title;
    private int editionNumber;
    private int copyright;
    private List<Author> authorList = new ArrayList<>();

    /**
     * Constructs a new Book object with details
     *
     * @param isbn
     * @param title
     * @param editionNumber
     * @param copyright
     */
    public Book(String isbn, String title, int editionNumber, int copyright) {
        this.isbn = isbn;
        this.title = title;
        this.editionNumber = editionNumber;
        this.copyright = copyright;
    }

    /**
     * Gets the edition number
     *
     * @return the edition number
     */
    public int getEditionNumber() {
        return editionNumber;
    }

    /**
     * Gets the copyright year
     *
     * @return the copyright year
     */
    public int getCopyright() {
        return copyright;
    }

    /**
     * Gets the ISBN
     *
     * @return ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Gets the title of the book
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the ISBN
     *
     * @param isbn
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Sets the title of the book
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the edition number
     *
     * @param editionNumber
     */
    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }

    /**
     * Sets the copyright year
     *
     * @param copyright
     */
    public void setCopyright(int copyright) {
        this.copyright = copyright;
    }

    /**
     * Gets the list of authors with the books they wrote
     *
     * @return the list of authors
     */
    public List<Author> getAuthorList() {
        return authorList;
    }

    /**
     * Adds a new author to the database
     *
     * @param author
     */
    public void addAuthor(Author author) {
        if (!authorList.contains(author)) {
            authorList.add(author);
            author.addBook(this);
        }
    }
}
