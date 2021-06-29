package br.com.rrmelo.concretedesafio.controller.form;

import br.com.rrmelo.concretedesafio.model.Phone;
import br.com.rrmelo.concretedesafio.model.User;
import br.com.rrmelo.concretedesafio.util.SecurityUtils;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

public class UserForm {

    @NotNull
    private String name;

    @NotNull @NotEmpty
    @Email
    private String email;

    @NotNull @NotEmpty
    private String password;

    @NotNull
    private Set<Phone> phones;

    public UserForm(String name, String email, String password, Set<Phone> phones) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phones = phones;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Phone> getPhones() {
        return phones;
    }

    public void setPhones(Set<Phone> phones) {
        this.phones = phones;
    }

    public User toUser() {
        return new User(
            name,
            email,
            password,
            phones
        );
    }
}
