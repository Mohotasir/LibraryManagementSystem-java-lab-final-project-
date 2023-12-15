package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Loader;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import library.Item;

public class Collections implements Initializable {

	@FXML
	private TableView<Item> table;
	@FXML
	private Label headling;
	@FXML
	private TableColumn<Item, String> title, category, authors, publiser;

	@FXML
	private TableColumn<Item, Integer> pyear;

	@FXML
	private TableColumn<Item, Button> action;

	@FXML
	private Button searchBtn;

	@FXML
	private TextField searchByAuthor;

	@FXML
	private TextField searchByTitle;

	@FXML
	void onSearch(ActionEvent event) {
		table.setItems(showItems());
	}

	private ObservableList<Item> showItems() {
		ObservableList<Item> items = FXCollections.observableArrayList();
		String getAuthor = searchByAuthor.getText();
		String getTitle = searchByTitle.getText();

		items.addAll(Loader.library.findItems(getTitle, getAuthor, true));

		return items;
	}

	private ObservableList<Item> getItems() {
		ObservableList<Item> items = FXCollections.observableArrayList();
		switch (Loader.currentPage) {
		case "Book":
			items.addAll(Loader.library.findItems("Book"));

			break;
		case "Publication":
			items.addAll(Loader.library.findItems("Publication"));
			break;
		case "Movie":
			items.addAll(Loader.library.findItems("Movie"));
			break;
		default:
			items.addAll(Loader.library.getItems());
			break;
		}
		return items;
	}

	public void loadItems() {
		title.setCellValueFactory(new PropertyValueFactory<>("title"));
		category.setCellValueFactory(new PropertyValueFactory<>("category"));
		authors.setCellValueFactory(cellData -> {
			String authorString = cellData.getValue().getAuthors().toString();
			return new SimpleStringProperty(authorString.substring(1, authorString.length() - 1));
		});
		pyear.setCellValueFactory(new PropertyValueFactory<>("publishYear"));
		publiser.setCellValueFactory(new PropertyValueFactory<>("publisherName"));
		action.setCellFactory(column -> new ViewDetailsButton());
		onSearch(null);
		table.setItems(getItems());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadItems();
	}

}

class ViewDetailsButton extends javafx.scene.control.TableCell<Item, Button> {
	private final Button btn;

	public ViewDetailsButton() {
		this.btn = new Button("View Details");

		this.btn.setOnAction(event -> {
			Item currentItem = getTableView().getItems().get(getIndex());

			FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/ViewDetails.fxml"));
			try {
				Parent page = loader.load();
				ViewDetails controller = loader.getController();
				controller.display(currentItem.getItemId());
				Loader.nevigateTo(page);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	@Override
	protected void updateItem(Button item, boolean empty) {
		super.updateItem(item, empty);
		if (empty) {
			setGraphic(null);
		} else {
			setGraphic(btn);
		}
	}
}
