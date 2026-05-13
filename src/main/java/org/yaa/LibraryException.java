package org.yaa;

public class LibraryException extends Exception {
    private String message;


    /** throws an exception message when there is an exception
     * @param message the message sent when there is an exception
     * @return a message to the exception caught
     */
    public LibraryException(String message) {
        this.message = message;
        super(message);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
