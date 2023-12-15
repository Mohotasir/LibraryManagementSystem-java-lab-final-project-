package application;

import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import library.DataHandler;
import library.Library;

public class Loader {
	public static Library library = null;
	public static VBox rootContainer = null;
	public static String currentPage = "Home";
	public static boolean isAdmin = false;

	public static void startServer() {
		try {
			library = DataHandler.loadData();
		} catch (Exception e) {
			System.out.println("Failed to load!");
			library = new Library("Library");
		}
	}

	public static void closeServer() {
		DataHandler.saveData(library);

	}

	public static void nevigateTo(Parent page) {
		rootContainer.getChildren().clear();
		rootContainer.getChildren().add(page);
	}
}
