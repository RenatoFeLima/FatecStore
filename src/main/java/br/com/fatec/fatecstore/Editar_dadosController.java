/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.fatecstore;

import br.com.fatec.fatecstore.DAO.ProdutoDAO;
import br.com.fatec.fatecstore.MODEL.Produto;
import br.com.fatec.fatecstore.PERSISTENCIA.Banco;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Henri
 */
public class Editar_dadosController implements Initializable {
    
    @FXML
    private ComboBox<String> cbModelo;

    @FXML
    private ComboBox<String> cbMarca;
    
    @FXML
    private TextField txtValor;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            Banco.conectar();
        } catch (SQLException ex) {
            Logger.getLogger(Editar_dadosController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
        String sql = "SELECT DISTINCT marca FROM PRODUTO";
        Statement statement = Banco.obterConexao().createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // Criar lista para armazenar os valores de marca
        List<String> marcas = new ArrayList<>();
        while (resultSet.next()) {
            String marca = resultSet.getString("marca");
            marcas.add(marca);
        }
        // Alimentar a ComboBox com os valores de marca
        cbMarca.setItems(FXCollections.observableArrayList(marcas));

        // Fechar recursos
        resultSet.close();
        statement.close();

    } catch (SQLException e) {
        e.printStackTrace();
        // Tratar a exceção adequadamente
    }
        
        cbMarca.valueProperty().addListener((observable, oldValue, newValue) -> {
        cbModelo.getSelectionModel().clearSelection();

        try {
            List<String> modelos = obterModelosDaMarca(newValue);
            cbModelo.setItems(FXCollections.observableArrayList(modelos));
        } catch (SQLException e) {
            e.printStackTrace();
            // Trate a exceção adequadamente
        }
    });
        
        try {
            Banco.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(Editar_dadosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private List<String> obterModelosDaMarca(String marca) throws SQLException {
        // Aqui você pode implementar a lógica para obter os modelos da marca selecionada
        // Consultar o banco de dados ou utilizar uma fonte de dados existente
        List<String> modelos = new ArrayList<>();
            Banco.conectar();
            try {
                String sql = "SELECT DISTINCT modelo FROM PRODUTO WHERE marca = ?";
                PreparedStatement statement = Banco.obterConexao().prepareStatement(sql);
                statement.setString(1, marca);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String modelo = resultSet.getString("modelo");
                    modelos.add(modelo);
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Tratar a exceção adequadamente
            }
            Banco.desconectar();
        return modelos;
    }
    
    @FXML
    private void switchToCadastroProduto() throws IOException {
        App.setRoot("cadastro_produto");
    }
    
    @FXML
    private void switchToVenda() throws IOException {
        App.setRoot("venda");
    }
    
    @FXML
    private void switchToConsultaAvancada() throws IOException {
        App.setRoot("consulta_avancada");
    }
    
    @FXML
    private void switchToCadastrarVendedor() throws IOException {
        App.setRoot("cadastrar_vendedor");
    }
    
    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }
    
    @FXML
    private void btnLogout() throws IOException {
        App.setRoot("login");
    }
    
    @FXML
    void btnSuporte(ActionEvent event) throws IOException {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("SUPORTE");
        alerta.setHeaderText("INFORMACOES");
        alerta.setContentText("Se algo nao esta funcionando, nos tambem nao sabemos o motivo, por favor culpe o JavaFX!");
        alerta.showAndWait();
    }
    
    private void limpaCampos(){
        cbMarca.getSelectionModel().clearSelection();
        cbModelo.getSelectionModel().clearSelection();
        txtValor.setText("");
    }
    
    @FXML
    private void btnGravar() throws IOException, SQLException {
        if (txtValor.getText().isEmpty() || cbMarca.getSelectionModel().isEmpty() || cbModelo.getSelectionModel().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("PREENCHA TODOS OS CAMPOS");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("Preencha Todos os campos!");
            alerta.showAndWait();
        }else{
            String marca = cbMarca.getValue();
            String modelo = cbModelo.getValue();
            String valor = txtValor.getText();

            Banco.conectar();

            try {
                String sql = "UPDATE PRODUTO SET valor = ? WHERE marca = ? AND modelo = ?";
                PreparedStatement statement = Banco.obterConexao().prepareStatement(sql);
                statement.setString(1, valor);
                statement.setString(2, marca);
                statement.setString(3, modelo);

                int rowsUpdated = statement.executeUpdate();

                if (rowsUpdated > 0) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("ATUALIZADO");
                    alerta.setHeaderText("INFORMACOES");
                    alerta.setContentText("PRODUTO ATUALIZADO COM SUCESSO");
                    alerta.showAndWait();
                    limpaCampos();
                } else {
                    // Trate o caso em que nenhum registro foi atualizado
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("ERRO");
                    alerta.setHeaderText("INFORMACOES");
                    alerta.setContentText("Nenhum produto encontrado para atualizar");
                    alerta.showAndWait();
                }

                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Trate a exceção adequadamente
            }
            
            Banco.desconectar();
            
            limpaCampos();
        }
    }
    
    @FXML
    private void btnApagar() throws IOException, SQLException {
        if (cbMarca.getSelectionModel().isEmpty() || cbModelo.getSelectionModel().isEmpty()) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("PREENCHA TODOS OS CAMPOS");
        alerta.setHeaderText("INFORMACOES");
        alerta.setContentText("Preencha Todos os campos!");
        alerta.showAndWait();
        } else {
            String marca = cbMarca.getValue();
            String modelo = cbModelo.getValue();

            Banco.conectar();

            try {
                String sql = "DELETE FROM PRODUTO WHERE marca = ? AND modelo = ?";
                PreparedStatement statement = Banco.obterConexao().prepareStatement(sql);
                statement.setString(1, marca);
                statement.setString(2, modelo);

                int rowsDeleted = statement.executeUpdate();

                if (rowsDeleted > 0) {
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("APAGADO");
                    alerta.setHeaderText("INFORMACOES");
                    alerta.setContentText("PRODUTO APAGADO COM SUCESSO");
                    alerta.showAndWait();
                    limpaCampos();
                } else {
                    // Trate o caso em que nenhum registro foi apagado
                    Alert alerta = new Alert(Alert.AlertType.ERROR);
                    alerta.setTitle("ERRO");
                    alerta.setHeaderText("INFORMACOES");
                    alerta.setContentText("Nenhum produto encontrado para apagar");
                    alerta.showAndWait();
                }

                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Trate a exceção adequadamente
            }

            Banco.desconectar();
        }
            limpaCampos();
        }
}
