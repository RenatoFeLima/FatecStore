package br.com.fatec.fatecstore;

import br.com.fatec.fatecstore.PERSISTENCIA.Banco;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class LoginController {

    
    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btnLogin;

    @FXML
    private TextField txtEmail;

    @FXML
    private Label title;

        
    @FXML
    private void switchToCadastroUsuario() throws IOException {
        App.setRoot("cadastro_usuario");
    }
    
    @FXML
    private void btnLogin() throws IOException, SQLException {
        String email = txtEmail.getText();
        String senha = txtSenha.getText();

        // Montar e executar a query de seleção
        String query = "SELECT * FROM USUARIO WHERE email = ?";
        Banco.conectar();
        try (PreparedStatement statement = Banco.obterConexao().prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String senhaArmazenada = resultSet.getString("senha");

                if (senha.equals(senhaArmazenada)) {
                    // A senha está correta
                    Banco.desconectar();
                    App.setRoot("menu");
                } else {
                    // A senha está incorreta
                    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                    alerta.setTitle("ERRO");
                    alerta.setHeaderText("INFORMACOES");
                    alerta.setContentText("Senha incorreta!");
                    alerta.showAndWait();
                }
            } else {
                // O email não está cadastrado
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("ERRO");
                alerta.setHeaderText("INFORMACOES");
                alerta.setContentText("Email nao cadastrado");
                alerta.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Tratar a exceção ou exibir uma mensagem de erro
        }
        Banco.desconectar();
    }
}
    
    
