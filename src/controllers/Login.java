package controllers;

import java.io.IOException;

import application.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login {
	private Stage stage;
	@FXML
	private TextField idField;
	@FXML
	private TextField passField;

	public void alertmsg(String name, String value) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Message Here...");
		alert.setHeaderText("Log in " + value + "!");
		alert.setContentText("Log in as a " + name);
		alert.showAndWait();
	}

	void addFail() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText("required to fill !");
		alert.showAndWait();
	}

	@FXML
	void login(ActionEvent event) {
		String id = idField.getText();
		String pass = passField.getText();
		String type = ((Button) event.getSource()).getText();
		String page = "";
		if ((id == null || id.isBlank()) && (pass == null || pass.isBlank())) {
			addFail();
		} else if (type.equals("Login as a Librarian") && id.equals("admin") && pass.equals("a")) {
			Loader.isAdmin = true;
			page = "AdminPanel";
			alertmsg("Librarian", "Successful");
		} else if (type.equals("Login as a Member") && id.equals("admin") && pass.equals("a")) {
			Loader.isAdmin = false;
			page = "UserPanel";
			alertmsg("Member", "Successfull");
		}

		try {
			Parent root = FXMLLoader.load(getClass().getResource("../resources/" + page + ".fxml"));
			Scene scene = new Scene(root);
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
