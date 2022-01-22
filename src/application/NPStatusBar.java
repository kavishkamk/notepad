package application;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class NPStatusBar extends HBox {
	
	private Label txtPos;

	public NPStatusBar() {
		createStatusBar();
	}
	
	private void createStatusBar() {
		txtPos = new Label("Ln: 1  Col: 1");
		this.setPadding(new Insets(0, 5, 0, 5));
		this.getChildren().add(txtPos);
	}

}
