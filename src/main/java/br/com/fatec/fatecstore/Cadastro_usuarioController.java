package br.com.fatec.fatecstore;

import br.com.fatec.fatecstore.PERSISTENCIA.Banco;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Cadastro_usuarioController {
    
    @FXML
    private Button botao;

    @FXML
    private Label title;

    @FXML
    private TextField txtEmail;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private PasswordField txtSenhaConfirma;

    @FXML
    private void switchToLogin() throws IOException {
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
    private void btnCadastrar() throws IOException, SQLException {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();
        String senhaConfirma = txtSenhaConfirma.getText();
        
        // Verifica se os campos estão preenchidos
        if (email.isEmpty() || senha.isEmpty() || senhaConfirma.isEmpty()) {
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("PREENCHA TODOS OS CAMPOS");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("Preencha Todos os campos!");
            alerta.showAndWait();
            return;
        }

        if(txtSenha.getText().equals(txtSenhaConfirma.getText())){
            // Conectar ao banco de dados
            Banco.conectar();

            try {
                // Montar e executar a query de inserção
                String query = "INSERT INTO USUARIO (email, senha) VALUES (?, ?)";
                PreparedStatement statement = Banco.obterConexao().prepareStatement(query);
                statement.setString(1, email);
                statement.setString(2, senha);
                statement.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                // Tratar a exceção ou exibir uma mensagem de erro
            }
            App.setRoot("login");
            // Desconectar do banco de dados
            Banco.desconectar();
        }else{
            Alert alerta = new Alert(Alert.AlertType.INFORMATION);
            alerta.setTitle("AS SENHAS NAO CORRESPONDEM");
            alerta.setHeaderText("INFORMACOES");
            alerta.setContentText("As senhas nao sao compativeis!");
            alerta.showAndWait();
        }
        
    }

}