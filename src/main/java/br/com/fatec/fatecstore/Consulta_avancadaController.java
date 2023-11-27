package br.com.fatec.fatecstore;
import br.com.fatec.fatecstore.MODEL.Vendas;
import br.com.fatec.fatecstore.PERSISTENCIA.Banco;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Henri
 */
public class Consulta_avancadaController implements Initializable {
    
    private void limpaCampos(){
        cbFiltro.getSelectionModel().clearSelection();
        txtFiltro.setText("");
    }
    
    @FXML
    private TableColumn<Vendas, String> CPFCliente;

    @FXML
    private TableColumn<Vendas, String> MarcaProduto;

    @FXML
    private TableColumn<Vendas, String> ModeloProduto;

    @FXML
    private TableColumn<Vendas, String> Quantidade;

    @FXML
    private TableColumn<Vendas, String> Valor;

    @FXML
    private TableColumn<Vendas, String> idVenda;

    @FXML
    private TableColumn<Vendas, String> idVendedor;

    @FXML
    private TableView<Vendas> tbResultado;
    
    @FXML
    private ComboBox<String> cbFiltro;

    @FXML
    private TextField txtFiltro;

    @FXML
    void btnLogout(ActionEvent event) throws IOException {
        App.setRoot("login");
    }

    @FXML
    void switchToCadastrarVendedor(ActionEvent event) throws IOException {
        App.setRoot("cadastrar_vendedor");
    }

    @FXML
    void switchToCadastroProduto(ActionEvent event) throws IOException {
        App.setRoot("cadastro_produto");
    }

    @FXML
    void switchToEditarDados(ActionEvent event) throws IOException {
        App.setRoot("editar_dados");
    }

    @FXML
    void switchToMenu(ActionEvent event) throws IOException {
        App.setRoot("menu");
    }

    @FXML
    void switchToVenda(ActionEvent event) throws IOException {
        App.setRoot("venda");
    }
    
    @FXML
    void btnSuporte(ActionEvent event) throws IOException {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("SUPORTE");
        alerta.setHeaderText("INFORMACOES");
        alerta.setContentText("Se algo nao esta funcionando, nos tambem nao sabemos o motivo, por favor culpe o JavaFX!");
        alerta.showAndWait();
    }

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // Adicionar os itens na ComboBox cbFiltro
        ObservableList<String> items = FXCollections.observableArrayList(
                "ID", "CPF_CLIENTE", "MARCA_PRODUTO", "ID_VENDEDOR", "MODELO_PRODUTO", "QUANTIDADE", "VALOR_PRODUTO");
        cbFiltro.setItems(items);
        
        CPFCliente.setCellValueFactory(new PropertyValueFactory<>("cpfCliente"));
        MarcaProduto.setCellValueFactory(new PropertyValueFactory<>("marcaProduto"));
        ModeloProduto.setCellValueFactory(new PropertyValueFactory<>("modeloProduto"));
        Quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        Valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        idVenda.setCellValueFactory(new PropertyValueFactory<>("idvenda"));
        idVendedor.setCellValueFactory(new PropertyValueFactory<>("idvendedor"));
    }    
    
    // Método para exibir um alerta
    private void exibirAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
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
    private void switchToVenda() throws IOException {
        App.setRoot("venda");
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
    private void btnPesquisar() throws IOException, SQLException {
        String coluna = cbFiltro.getValue(); // Obtém o valor selecionado na ComboBox
        String filtro = txtFiltro.getText(); // Obtém o valor do campo de filtro

        // Verifica se a coluna e o filtro foram especificados
        if (coluna != null && !coluna.isEmpty() && filtro != null && !filtro.isEmpty()) {
            try {
                // Conectar ao banco de dados
                Banco.conectar();

                // Realizar a consulta ao banco de dados
                String sql = "SELECT CPF_CLIENTE, MARCA_PRODUTO, MODELO_PRODUTO, QUANTIDADE, VALOR_PRODUTO, ID, ID_VENDEDOR FROM VENDA WHERE " + coluna + " = ?";
                PreparedStatement statement = Banco.obterConexao().prepareStatement(sql);
                statement.setString(1, filtro);

                ResultSet resultSet = statement.executeQuery();

                // Verifica se algum item foi encontrado
                if (resultSet.next()) {
                    // Limpa os itens existentes na tabela
                    tbResultado.getItems().clear();

                    do {
                        // Obtém os valores das colunas do resultado da consulta
                        String cpfCliente = resultSet.getString("CPF_CLIENTE");
                        String marcaProduto = resultSet.getString("MARCA_PRODUTO");
                        String modeloProduto = resultSet.getString("MODELO_PRODUTO");
                        int quantidade = resultSet.getInt("QUANTIDADE");
                        int valor = resultSet.getInt("VALOR_PRODUTO");
                        int id = resultSet.getInt("ID");
                        int idVendedor = resultSet.getInt("ID_VENDEDOR");

                        // Cria um objeto Venda com os valores obtidos
                        Vendas venda = new Vendas(cpfCliente, marcaProduto, modeloProduto, quantidade, valor, id, idVendedor);

                        // Adiciona o objeto Venda à tabela
                        tbResultado.getItems().add(venda);
                        
                    } while (resultSet.next());
                } else {
                    // Nenhum item encontrado
                    exibirAlerta("Nenhum item encontrado", "A pesquisa não retornou resultados.");
                    limpaCampos();
                }

                // Fechar recursos
                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
                // Tratar exceção
            } finally {
                // Desconectar do banco de dados
                Banco.desconectar();
            }
        } else {
            // Valores não especificados
            exibirAlerta("Valores inválidos", "Por favor, selecione uma coluna e insira um valor de filtro.");
        }
    }
    
    @FXML
    private void btnApagar() throws IOException {
        limpaCampos();
    }
}