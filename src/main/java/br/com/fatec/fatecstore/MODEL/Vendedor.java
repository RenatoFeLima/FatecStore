/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.fatecstore.MODEL;

import java.sql.Date;

/**
 *
 * @author Renato Ferreira
 */
public class Vendedor {
    private String nome;
    private String cpf;
    private double salario;
    private String telefone;
    private String cargo;
    private String email;
    private String endereco;
    private String dataNascimento;

    public Vendedor(String nome, String cpf, String dataNascimento, double salario, String telefone,
                    String cargo, String email, String endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.salario = salario;
        this.telefone = telefone;
        this.cargo = cargo;
        this.email = email;
        this.endereco = endereco;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    // Getters e setters
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

}

