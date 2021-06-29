package br.com.rrmelo.concretedesafio.model;

import br.com.rrmelo.concretedesafio.util.SecurityUtils;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String value;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Token() { }

    public Token(User user) {
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = SecurityUtils.encrypt(value);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
