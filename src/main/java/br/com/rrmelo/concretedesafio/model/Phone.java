package br.com.rrmelo.concretedesafio.model;

import javax.persistence.*;

@Embeddable
public class Phone {
    private String number;
    private String ddd;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }
}

