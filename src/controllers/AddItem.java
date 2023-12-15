package controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;

import application.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import library.Item;

public class AddItem {
	private String form = "BOOK";
	private String imageURI = "";

	@FXML
	private Button book, publication, movie;

	@FXML
	private VBox bookBox, publicationBox, movieBox;

	@FXML
	private ToggleGroup isJournal;

	@FXML
	private TextField itemTitle, itemCategory, bookAuthors, bookPublishYear, bookPublisher, pubAuthors, pubPublishYear,
			pubPublisher, movieDirectors, movieReleaseYear, itemImage;

	@FXML
	void clickAddButton(ActionEvent event) {
		String title = itemTitle.getText();
		String category = itemCategory.getText();
		switch (form) {
		case "BOOK":
			addBook(title, category);
			break;
		case "PUBLICATION":
			addPublication(title, category);
			break;
		case "MOVIE":
			addMovie(title, category);
			break;
		}
		resetForm();
	}

	@FXML
	void switchForm(ActionEvent event) {
		form = ((Button) event.getSource()).getText();

		switch (form) {
		case "BOOK":
			book.getStyleClass().add("active");
			publication.getStyleClass().removeAll("active");
			movie.getStyleClass().removeAll("active");
			bookBox.setVisible(true);
			publicationBox.setVisible(false);
			movieBox.setVisible(false);
			break;

		case "PUBLICATION":
			book.getStyleClass().removeAll("active");
			publication.getStyleClass().add("active");
			movie.getStyleClass().removeAll("active");
			bookBox.setVisible(false);
			publicationBox.setVisible(true);
			movieBox.setVisible(false);
			break;

		case "MOVIE":
			book.getStyleClass().removeAll("active");
			publication.getStyleClass().removeAll("active");
			movie.getStyleClass().add("active");
			bookBox.setVisible(false);
			publicationBox.setVisible(false);
			movieBox.setVisible(true);
			break;
		}
	}

	@FXML
	boolean chooseImage(ActionEvent event) {
		boolean isSuccessful = false;
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters()
				.add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg"));
		File selectedFile = fileChooser.showOpenDialog(null);

		if (selectedFile != null) {
			itemImage.setText(selectedFile.getName());
			try {
				File destinationFolder = new File("src/images");
				if (!destinationFolder.exists()) {
					destinationFolder.mkdirs();
				}
				Path destinationPath = new File(destinationFolder, selectedFile.getName()).toPath();
				Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
				imageURI = destinationPath.toUri().toString();
				isSuccessful = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return isSuccessful;
	}

	private void resetForm() {
		itemTitle.clear();
		itemCategory.clear();
		bookAuthors.clear();
		bookPublishYear.clear();
		bookPublisher.clear();
		pubAuthors.clear();
		pubPublishYear.clear();
		pubPublisher.clear();
		movieDirectors.clear();
		movieReleaseYear.clear();
	}

	public void alertmsg(String name, String id) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Message Here...");
		alert.setHeaderText("Item added successfully!");
		alert.setContentText("New" + name + "added in library");
		alert.setContentText("Item id:" + id);
		alert.showAndWait();
	}

	private void addBook(String title, String category) {
		ArrayList<String> authors = new ArrayList<>(Arrays.asList(bookAuthors.getText().split(", ")));
		String year = bookPublishYear.getText();
		int publishYear = year.matches("\\d{4}") ? Integer.parseInt(year) : 2000;
		String publisherName = bookPublisher.getText();
		Item item = Loader.library.addItem(title, category, authors, publishYear, publisherName, imageURI);
		String id = item.getItemId();
		alertmsg("BOOK", id);

	}

	private void addPublication(String title, String category) {
		ArrayList<String> authors = new ArrayList<>(Arrays.asList(pubAuthors.getText().split(", ")));
		String year = pubPublishYear.getText();
		int publishYear = year.matches("\\d{4}") ? Integer.parseInt(year) : 2000;
		String publisherName = pubPublisher.getText();
		boolean isJournalPaper = isJournal.getSelectedToggle().toString().contains("YES");
		Item item = Loader.library.addItem(title, category, authors, publishYear, isJournalPaper, publisherName,
				imageURI);
		String id = item.getItemId();
		alertmsg("BOOK", id);
	}

	private void addMovie(String title, String category) {
		ArrayList<String> directors = new ArrayList<>(Arrays.asList(movieDirectors.getText().split(", ")));
		String year = movieReleaseYear.getText();
		int releaseYear = year.matches("\\d{4}") ? Integer.parseInt(year) : 2000;
		Item item = Loader.library.addItem(title, category, directors, releaseYear, imageURI);
		String id = item.getItemId();
		alertmsg("BOOK", id);
	}
}
