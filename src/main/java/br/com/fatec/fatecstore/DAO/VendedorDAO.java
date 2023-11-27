/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.fatecstore.DAO;

import br.com.fatec.fatecstore.MODEL.Vendedor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Renato Ferreira
 */
public class VendedorDAO {
    private final Connection connection;

    public VendedorDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean insere(Vendedor v) {
        try {
            String sql = "INSERT INTO vendedor (nome, cpf, data_nascimento, salario, telefone, cargo, email, endereco) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, v.getNome());
            stmt.setString(2, v.getCpf());
            stmt.setString(3, v.getDataNascimento());
            stmt.setDouble(4, v.getSalario());
            stmt.setString(5, v.getTelefone());
            stmt.setString(6, v.getCargo());
            stmt.setString(7, v.getEmail());
            stmt.setString(8, v.getEndereco());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir vendedor no banco de dados: " + e.getMessage());
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão com o banco de dados: " + e.getMessage());
            }
        }
    }
}