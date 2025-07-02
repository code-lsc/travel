package cn.ecut.travel.dto;

public class Email {

    private String title;
    private String content;
    private String toUser;

    public Email() {
    }

    public Email(String title, String content, String toUser) {
        this.title = title;
        this.content = content;
        this.toUser = toUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }
}
