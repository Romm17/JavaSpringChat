package model;

import javax.persistence.*;

/**
 * @author Roman Usik
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "pass", nullable = false)
    private String pass;

    public User() {
        id = null;
        login = null;
        pass = null;
    }

    public User(String login, String pass) {
        id = -1;
        this.login = login;
        this.pass = pass;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String toString() {
        return "{ id : " + id + ", login : " + login + ", pass : " + pass + " }";
    }

}
