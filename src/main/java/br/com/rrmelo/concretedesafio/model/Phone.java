package br.com.rrmelo.concretedesafio.model;

import javax.persistence.*;

@Embeddable
public class Phone {
    private String number;
    private String ddd;

    public Phone() { }

    public Phone(String number, String ddd) {
        this.number = number;
        this.ddd = ddd;
    }

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

