package com.blog.blogger.payload;

import java.util.Date;

public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String Details;

    public ErrorDetails(Date timestamp, String message, String details) {
        this.timestamp = timestamp;
        this.message = message;
        Details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return Details;
    }
}
