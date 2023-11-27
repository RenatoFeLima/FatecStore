/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.fatecstore.MODEL;

/**
 *
 * @author Renato Ferreira
 */
public class Vendas {
    private String cpfCliente;
    private Produto produto;
    private int idVendedor;
    private String formaPagamento;

    public Vendas(String cpfCliente, Produto produto, int idVendedor, String formaPagamento) {
        this.cpfCliente = cpfCliente;
        this.produto = produto;
        this.idVendedor = idVendedor;
        this.formaPagamento = formaPagamento;
    }

    public Vendas(String cpfCliente, String marcaProduto, String modeloProduto, int quantidade, double valorProduto, int id, int idVendedor) {
        
    }

    // Getters e setters
    

    public String getCpfCliente() {
        return cpfCliente;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}

