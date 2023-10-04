module biblioteca {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens biblioteca to javafx.fxml;
    exports biblioteca;
}
