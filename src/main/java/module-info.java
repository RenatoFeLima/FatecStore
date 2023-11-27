module br.com.fatec.fatecstore {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires java.desktop;
    requires mysql.connector.java;
    opens br.com.fatec.fatecstore.MODEL to javafx.base;
    opens br.com.fatec.fatecstore to javafx.fxml;
    exports br.com.fatec.fatecstore;
}
