package com.equipe17.biblioteca.users;

/*
 *   Developed by Lucas do Nascimento Souza - 2023011395
 */

public class User {
    public String name, cpf, password, deficiency;
    public boolean isPd;

    public User(String name, String cpf, String password, String deficiency, boolean isPd) {
        this.name = name;
        this.cpf = cpf;
        this.password = password;
        this.deficiency = deficiency;
        this.isPd = isPd;
    }

    public User(String name, String cpf, String password) {
        this.name = name;
        this.cpf = cpf;
        this.password = password;
    }

    public User(String cpf, String password) {
        this.cpf = cpf;
        this.password = password;
    }
}
