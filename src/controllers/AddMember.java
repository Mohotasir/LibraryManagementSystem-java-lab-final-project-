package controllers;

import application.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class AddMember {

	@FXML
	private TextField memberId, memberName;

	void addSuccess() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setContentText("Member added Successfully !");
		alert.showAndWait();
	}

	void addFail() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setContentText("required to fill !");
		alert.showAndWait();
	}

	@FXML
	void clickAddButton(ActionEvent event) {
		String id = memberId.getText();
		String name = memberName.getText();
		if ((id == null || id.isBlank()) && (name == null || name.isBlank())) {
			addFail();
		} else {
			Loader.library.addMember(id, name);
			addSuccess();
		}
		memberId.clear();
		memberName.clear();
	}

}
