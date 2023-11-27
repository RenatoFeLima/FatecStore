/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.fatecstore.DAO;

import br.com.fatec.fatecstore.MODEL.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Renato Ferreira
 */
public class ProdutoDAO {
    private final Connection connection;

    public ProdutoDAO(Connection connection) {
        this.connection = connection;
    }

   public boolean insere(Produto p) {
    try {
        String sql = "INSERT INTO produto (marca, modelo, valor, quantidade) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, p.getMarca());
        stmt.setString(2, p.getModelo());
        stmt.setDouble(3, p.getValor());
        stmt.setInt(4, p.getQuantidade());
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    } catch (SQLException e) {
        System.out.println("Erro ao inserir produto no banco de dados: " + e.getMessage());
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
