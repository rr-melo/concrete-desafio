package br.com.rrmelo.concretedesafio.controller.form;

import br.com.rrmelo.concretedesafio.util.SecurityUtils;

import javax.validation.constraints.NotNull;

public class LoginForm {
    @NotNull private String email;
    @NotNull private String password;

    public LoginForm() { }

    public LoginForm(String email, String password) {
        this.email = email;
        this.password = SecurityUtils.encrypt(password);
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
        this.password = SecurityUtils.encrypt(password);
    }
}
