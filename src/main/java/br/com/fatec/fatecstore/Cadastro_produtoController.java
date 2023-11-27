/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.fatecstore;

import br.com.fatec.fatecstore.DAO.ProdutoDAO;
import br.com.fatec.fatecstore.MODEL.Produto;
import br.com.fatec.fatecstore.PERSISTENCIA.Banco;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Henri
 */
public class Cadastro_produtoController implements Initializable {
    
    ObservableList<String> marca = FXCollections.observableArrayList("Nike", "Adidas", "Puma", "Mizuno");
    
    @FXML
    private Button btnConfirmar;

    @FXML
    private ComboBox<String> cbMarca;
    
    @FXML
    private TextField txtValor;
    
    @FXML
    private TextField txtModelo;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private Button btnCancelar;

    @FXML
    void switchToMenu(ActionEvent event){

    }

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbMarca.setItems(marca);
    }
    
    @FXML
    private void btnLogout() throws IOException {
        App.setRoot("login");
    }
    
    @FXML
    private void switchToMenuPrincipal() throws IOException {
        App.setRoot("menu");
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
    private void switchToEditarDados() throws IOException {
        App.setRoot("editar_dados");
    }
    
    @FXML
    void btnSuporte(ActionEvent event) throws IOException {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("SUPORTE");
        alerta.setHeaderText("INFORMACOES");
        alerta.setContentText("Se algo nao esta funcionando, nos tambem nao sabemos o motivo, por favor culpe o JavaFX!");
        alerta.showAndWait();
    }
    
    @FXML
    private void btnConfirmar() throws IOException, SQLException {
        if (txtQuantidade.getText().isEmpty() || txtModelo.getText().isEmpty() || txtValor.getText().isEmpty() || cbMarca.getSelectionModel().isEmpty() || txtModelo.getText().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("PREENCHA TODOS OS CAMPOS");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("Preencha Todos os campos!");
            alerta.showAndWait();
        }else{
            Banco.conectar();

            Connection connection = Banco.obterConexao();
            
            Produto p = new Produto(cbMarca.getValue(),txtModelo.getText(),Double.parseDouble(txtValor.getText()), Integer.parseInt(txtQuantidade.getText()));

            ProdutoDAO dao = new ProdutoDAO(connection);

                if(dao.insere (p)){
                    System.out.println("INSERÇÃO OK");
                }else{
                    System.out.println("ERRO NA INCLUSAO");
                }
                
            Banco.desconectar();
            
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("GRAVADO");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("PRODUTO ADICIONADO COM SUCESSO");
            alerta.showAndWait();
            
        }
        
        txtValor.setText("");
        txtModelo.setText("");
        txtQuantidade.setText("");
        cbMarca.getSelectionModel().clearSelection();
    }
    
    @FXML
    private void btnCancelar() throws IOException {
        txtValor.setText("");
        txtModelo.setText("");
        txtQuantidade.setText("");
        cbMarca.getSelectionModel().clearSelection();
    }
}