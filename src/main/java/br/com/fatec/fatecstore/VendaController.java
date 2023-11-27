/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package br.com.fatec.fatecstore;

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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Henri
 */
public class VendaController implements Initializable {
    
    @FXML
    private ComboBox<String> cbMarca;

    @FXML
    private ComboBox<String> cbModelo;

    @FXML
    private Label lbValor;

    @FXML
    private TextField txtCPFCliente;

    @FXML
    private TextField txtIDVendedor;

    @FXML
    private TextField txtQuantidade;

    /**
     * Initializes the controller class.
     */
    
    private void limpaCampos(){
        txtCPFCliente.setText("");
        txtIDVendedor.setText("");
        txtQuantidade.setText("");
        cbMarca.getSelectionModel().clearSelection();
        cbModelo.getSelectionModel().clearSelection();
        lbValor.setText("---");
    }
    
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
        
        //-----------------------------
        cbModelo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double valor = obterValor(newValue);
                lbValor.setText(String.valueOf(valor));
            } catch (SQLException e) {
                // Tratar exceção, se necessário
                e.printStackTrace();
            }
        });
        //-----------------------------
        try {
            Banco.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(Editar_dadosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public double obterValor(String modelo) throws SQLException {
        double valor = 0.0;

        // Conectar ao banco de dados
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        Banco.conectar();
        
        try {
            // Consultar o valor correspondente ao modelo na tabela de produtos
            String sql = "SELECT valor FROM PRODUTO WHERE modelo = ?";
            statement = Banco.obterConexao().prepareStatement(sql);
            statement.setString(1, modelo);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                valor = resultSet.getDouble("valor");
            }
        } finally {
            // Fechar recursos (ResultSet, PreparedStatement e Connection)
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }
        
        Banco.desconectar();
        return valor;
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
    private void switchToCadastrarVendedor() throws IOException {
        App.setRoot("cadastrar_vendedor");
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
    private void btnConfirmar() throws IOException, SQLException {
       if (txtCPFCliente.getText().isEmpty() || txtIDVendedor.getText().isEmpty() || txtQuantidade.getText().isEmpty() || cbMarca.getSelectionModel().isEmpty() || cbModelo.getSelectionModel().isEmpty()) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("PREENCHA TODOS OS CAMPOS");
        alerta.setHeaderText("INFORMACOES");
        alerta.setContentText("Preencha Todos os campos!");
        alerta.showAndWait();
        } else {
            String cpfCliente = txtCPFCliente.getText();
            String idVendedor = txtIDVendedor.getText();
            String marcaProduto = cbMarca.getValue();
            String modeloProduto = cbModelo.getValue();
            int quantidade = Integer.parseInt(txtQuantidade.getText());

            Banco.conectar();

            try {
                // Consulta para obter a quantidade disponível do produto
                String selectSql = "SELECT QUANTIDADE FROM PRODUTO WHERE MODELO = ?";
                PreparedStatement selectStatement = Banco.obterConexao().prepareStatement(selectSql);
                selectStatement.setString(1, modeloProduto);
                ResultSet resultSet = selectStatement.executeQuery();

                if (resultSet.next()) {
                    int quantidadeDisponivel = resultSet.getInt("QUANTIDADE");

                    if (quantidade <= quantidadeDisponivel) {
                        // A quantidade digitada é menor ou igual à quantidade disponível
                        // Continuar com a inserção da venda e atualização da quantidade no banco de dados

                        String insertSql = "INSERT INTO venda (CPF_CLIENTE, MARCA_PRODUTO, ID_VENDEDOR, MODELO_PRODUTO, QUANTIDADE) VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement insertStatement = Banco.obterConexao().prepareStatement(insertSql);
                        insertStatement.setString(1, cpfCliente);
                        insertStatement.setString(2, marcaProduto);
                        insertStatement.setString(3, idVendedor);
                        insertStatement.setString(4, modeloProduto);
                        insertStatement.setInt(5, quantidade);

                        int rowsInserted = insertStatement.executeUpdate();

                        if (rowsInserted > 0) {
                            // Venda gravada com sucesso
                            // Agora, subtrair a quantidade no banco de dados

                            String updateSql = "UPDATE PRODUTO SET QUANTIDADE = QUANTIDADE - ? WHERE MODELO = ?";
                            PreparedStatement updateStatement = Banco.obterConexao().prepareStatement(updateSql);
                            updateStatement.setInt(1, quantidade);
                            updateStatement.setString(2, modeloProduto);

                            int rowsUpdated = updateStatement.executeUpdate();

                            if (rowsUpdated > 0) {
                                // Atualização bem-sucedida
                                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                                alerta.setTitle("GRAVADO");
                                alerta.setHeaderText("INFORMACOES");
                                alerta.setContentText("VENDA GRAVADA COM SUCESSO");
                                alerta.showAndWait();
                                limpaCampos();
                            } else {
                                // Tratar o caso em que nenhuma linha foi atualizada
                                Alert alerta = new Alert(Alert.AlertType.ERROR);
                                alerta.setTitle("ERRO");
                                alerta.setHeaderText("INFORMACOES");
                                alerta.setContentText("Erro ao atualizar a quantidade do produto");
                                alerta.showAndWait();
                            }

                            updateStatement.close();
                        } else {
                            // Trate o caso em que nenhum registro foi inserido
                            Alert alerta = new Alert(Alert.AlertType.ERROR);
                            alerta.setTitle("ERRO");
                            alerta.setHeaderText("INFORMACOES");
                            alerta.setContentText("Erro ao gravar a venda");
                            alerta.showAndWait();
                        }

                        insertStatement.close();
                    } else {
                        // A quantidade digitada é maior que a quantidade disponível
                        Alert alerta = new Alert(Alert.AlertType.WARNING);
                        alerta.setTitle("QUANTIDADE INSUFICIENTE");
                        alerta.setHeaderText("INFORMACOES");
                        alerta.setContentText("A quantidade disponível do produto é menor que a quantidade digitada");
                        alerta.showAndWait();
                    }
                } else {
                    // Produto não encontrado
                    Alert alerta = new Alert(Alert.AlertType.WARNING);
                    alerta.setTitle("PRODUTO NÃO ENCONTRADO");
                    alerta.setHeaderText("INFORMACOES");
                    alerta.setContentText("O produto selecionado não foi encontrado");
                    alerta.showAndWait();
                }

                resultSet.close();
                selectStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Trate a exceção adequadamente
            }

            Banco.desconectar();
        }
        
        limpaCampos();
    }
    
    @FXML
    private void btnCancelar() throws IOException {
        limpaCampos();
    }
}
