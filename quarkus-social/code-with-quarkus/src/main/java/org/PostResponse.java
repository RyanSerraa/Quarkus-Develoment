package org;

import java.time.LocalDateTime;

import org.acme.Posts;

public class PostResponse {
    private String text;
    private LocalDateTime dateTime;
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public static PostResponse fromEntity(Posts post){
        PostResponse response= new PostResponse();
        response.setText(post.getText());
        response.setDateTime(response.getDateTime());
        return response;
    }
}
