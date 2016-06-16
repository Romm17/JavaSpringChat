package model;

import model.User;

import javax.persistence.*;

/**
 * @author Roman Usik
 */
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(name = "text")
    private String text;
    public Message() {

    }

    public Message(User user, String text) {
        this.user = user;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toString() {
        return "{ id : " + id + ", user : " + user + ", text : " + text + " }";
    }

}
