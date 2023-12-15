package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserPanel implements Initializable {

	@FXML
	private Button allCollection, book, movie, publication, checkouts, logoutBtn;

	@FXML
	private VBox rootContainer;

	@FXML
	void logOut(ActionEvent event) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../resources/LoginPage.fxml"));
			Scene scene = new Scene(root);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void showCollection(ActionEvent event) {
		Loader.currentPage = ((Button) event.getSource()).getText();
		loadPage("Collections");
	}

	@FXML
	void onCheckout(ActionEvent event) {
		loadPage("CheckoutRecords");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Loader.rootContainer = rootContainer;
		loadPage("Collections");
	}

	private Object loadPage(String pageLocation) {
		Object pageController = null;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/" + pageLocation + ".fxml"));
			Parent page = loader.load();
			pageController = loader.getController();
			Loader.nevigateTo(page);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pageController;
	}

}
