package view;

import javafx.scene.control.Alert;

public class InfoDialog {
    static public void showInfoDIalog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
