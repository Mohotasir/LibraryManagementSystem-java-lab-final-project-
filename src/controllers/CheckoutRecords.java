package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import application.Loader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import library.CheckOutRecord;
import library.InvalidItemException;
import library.InvalidMemberException;
import library.Item;
import library.Member;

public class CheckoutRecords implements Initializable {

	@FXML
	private TableColumn<CheckOutRecord, LocalDate> checkInDate;

	@FXML
	private TableColumn<CheckOutRecord, LocalDate> checkOutDate;

	@FXML
	private TableColumn<CheckOutRecord, LocalDate> expectedCheckInDate;

	@FXML
	private TableColumn<CheckOutRecord, String> itemId;

	@FXML
	private TableColumn<CheckOutRecord, String> memberId;

	@FXML
	private HBox searchBox;

	@FXML
	private TextField searchByItemId;

	@FXML
	private TextField searchByMemberId;

	@FXML
	private TableView<CheckOutRecord> table;

	@FXML
	private Label tableName;

	@FXML
	void search(ActionEvent event) {
		String inputItemId = searchByItemId.getText();
		String inputMemberId = searchByMemberId.getText();

		// show the checkout records
		if (inputItemId != null) {
			showRecords(getItemCheckOutRecords(inputItemId));
		}

		if (inputMemberId != null) {
			showRecords(getMemberCheckOutRecords(inputMemberId));
		}
	}

	private void showRecords(ObservableList<CheckOutRecord> checkoutRecords) {
		if (!checkoutRecords.isEmpty()) {
			table.setItems(checkoutRecords);
		} else
			System.out.println("Empty record!");
	}

	private ObservableList<CheckOutRecord> getMemberCheckOutRecords(String inputMemberId) {
		ObservableList<CheckOutRecord> checkOutRecords = FXCollections.observableArrayList();

		if (!(inputMemberId == null || inputMemberId.isBlank())) {
			try {
				Member member = Loader.library.findMember(inputMemberId);
				checkOutRecords.addAll(member.getChekOutRecords());
			} catch (InvalidMemberException e) {
				System.out.println(e.getMessage());
			}
		}

		return checkOutRecords;
	}

	private ObservableList<CheckOutRecord> getItemCheckOutRecords(String inputItemId) {
		ObservableList<CheckOutRecord> checkOutRecords = FXCollections.observableArrayList();

		if (!(inputItemId == null || inputItemId.isBlank())) {
			try {
				Item item = Loader.library.findItem(inputItemId);
				checkOutRecords.addAll(item.getCheckOutRecords());
			} catch (InvalidItemException e) {
				System.out.println(e.getMessage());
			}
		}
		return checkOutRecords;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		searchBox.managedProperty().bind(searchBox.visibleProperty());
		if (!Loader.isAdmin) {
			tableName.setText("My Checkout Records");
			searchBox.setVisible(false);
		}

		itemId.setCellValueFactory(new PropertyValueFactory<>("itemId"));
		memberId.setCellValueFactory(new PropertyValueFactory<>("memberId"));
		checkOutDate.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
		checkInDate.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
		expectedCheckInDate.setCellValueFactory(new PropertyValueFactory<>("expectedCheckInDate"));
	}
}
