
module org.harjoitustyoD {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens org.harjoitustyoD to javafx.fxml;
    exports org.harjoitustyoD;
}