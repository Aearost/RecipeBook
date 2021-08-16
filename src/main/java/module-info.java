module com.aearost.recipebook {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.aearost.recipebook to javafx.fxml;
    exports com.aearost.recipebook;
    
}
