/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.fatecstore;

import br.com.fatec.fatecstore.DAO.ProdutoDAO;
import br.com.fatec.fatecstore.DAO.VendedorDAO;
import br.com.fatec.fatecstore.MODEL.Produto;
import br.com.fatec.fatecstore.MODEL.Vendedor;
import br.com.fatec.fatecstore.PERSISTENCIA.Banco;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Henri
 */
public class Cadastrar_vendedorController implements Initializable {
    
    ObservableList<String> cargo = FXCollections.observableArrayList("Vendedor", "Gerente", "Aprendiz");
    
    @FXML
    private Button btnCancelar;

    @FXML
    private Button btnConfirmar;

    @FXML
    private ComboBox<String> cbCargo;

    @FXML
    private TextField txtCPFVendedor;

    @FXML
    private TextField txtDataNascimento;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEndereco;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtSalario;

    @FXML
    private TextField txtTelefone;

    /**
     * Initializes the controller class.
     */
    
    private void limpaCampos(){
        cbCargo.getSelectionModel().clearSelection();
        txtNome.setText("");
        txtDataNascimento.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtEndereco.setText("");
        txtCPFVendedor.setText("");
        txtSalario.setText("");
    }
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cbCargo.setItems(cargo);
    }    
    
    @FXML
    private void switchToCadastroProduto() throws IOException {
        App.setRoot("cadastro_produto");
    }
    
    @FXML
    private void switchToEditarDados() throws IOException {
        App.setRoot("editar_dados");
    }
    
    @FXML
    private void switchToMenu() throws IOException {
        App.setRoot("menu");
    }
    
    @FXML
    private void switchToConsultaAvancada() throws IOException {
        App.setRoot("consulta_avancada");
    }
    
    @FXML
    private void switchToVenda() throws IOException {
        App.setRoot("venda");
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
    
    @FXML
    private void btnCancelar() throws IOException {
        limpaCampos();
    }
    
    @FXML
    private void btnConfirmar() throws IOException, SQLException {
        if (txtNome.getText().isEmpty() || txtDataNascimento.getText().isEmpty() || txtTelefone.getText().isEmpty() || txtEmail.getText().isEmpty()
                 || txtEndereco.getText().isEmpty() || txtCPFVendedor.getText().isEmpty() || txtSalario.getText().isEmpty() || cbCargo.getSelectionModel().isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("PREENCHA TODOS OS CAMPOS");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("Preencha Todos os campos!");
            alerta.showAndWait();
        }else{
            Banco.conectar();

            Connection connection = Banco.obterConexao();
            
            Vendedor v = new Vendedor(txtNome.getText(),txtCPFVendedor.getText(), txtDataNascimento.getText(),Double.parseDouble(txtSalario.getText()),
                    txtTelefone.getText(), cbCargo.getValue(), txtEmail.getText(), txtEndereco.getText());

            VendedorDAO dao = new VendedorDAO(connection);

                if(dao.insere (v)){
                    System.out.println("INSERÇÃO OK");
                }else{
                    System.out.println("ERRO NA INCLUSAO");
                }
                
            Banco.desconectar();
            
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("GRAVADO");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("VENDEDOR CADASTRADO COM SUCESSO");
            alerta.showAndWait();
            
        }
        
        limpaCampos();
    }
    
}
