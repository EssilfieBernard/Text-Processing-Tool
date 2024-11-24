module com.project.textprocessingfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project.textprocessingfx to javafx.fxml;
    exports com.project.textprocessingfx;
}