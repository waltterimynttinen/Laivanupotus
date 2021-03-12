module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.harjoitustyoD to javafx.fxml;
    exports org.harjoitustyoD;
}