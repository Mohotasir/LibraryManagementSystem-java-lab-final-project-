package controllers;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import application.Loader;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import library.CheckOutRecord;
import library.CheckedOutException;
import library.InvalidItemException;
import library.InvalidMemberException;
import library.Item;

public class ViewDetails implements Initializable {
	private String itemId;

	@FXML
	private Label title, author, category, publisher, publishYear, lastCheckInDate, lastCheckOutDate;

	@FXML
	private ImageView imageView;

	@FXML
	private Button checkoutBtn, checkinBtn, extendBtn;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		checkinBtn.managedProperty().bind(checkinBtn.visibleProperty());
		checkoutBtn.managedProperty().bind(checkoutBtn.visibleProperty());
		extendBtn.managedProperty().bind(extendBtn.visibleProperty());
	}

	@FXML
	void checkin(ActionEvent event) {
		try {
			Loader.library.checkIn(itemId);
			display(itemId); // reload
		} catch (InvalidItemException e) {
			System.out.println(e.getMessage());
		}
	}

	@FXML
	void checkout(ActionEvent event) {
		String memberId = getInputForMemberid();
		try {
			Loader.library.checkOut(itemId, memberId);
			display(itemId); // reload
		} catch (CheckedOutException | InvalidItemException | InvalidMemberException e) {
			System.out.println(e.getMessage());
		}
	}

	@FXML
	void extendCheckout(ActionEvent event) {
		try {
			Loader.library.extendCheckOut(itemId);
			display(itemId); // reload
		} catch (InvalidItemException | CheckedOutException e) {
			System.out.println(e.getMessage());
		}
	}

	public void display(String itemId) {
		this.itemId = itemId;

		try {
			// find item
			Item item = Loader.library.findItem(itemId);

			// display item
			imageView.setImage(new Image(item.getImageURI()));
			title.setText(item.getTitle());
			category.setText(item.getCategory());
			author.setText(item.getAuthors().toString());
			publisher.setText(item.getPublisherName());
			publishYear.setText(String.valueOf(item.getPublishYear()));

			// last checkout history
			CheckOutRecord latestRecord = item.getLatestCheckOutRecord();
			if (latestRecord != null) {
				LocalDate checkinDate = latestRecord.getCheckInDate();
				LocalDate checkoutDate = latestRecord.getCheckOutDate();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
				lastCheckOutDate.setText(checkoutDate != null ? checkoutDate.format(formatter) : "NA");
				lastCheckInDate.setText(checkinDate != null ? checkinDate.format(formatter) : "NA");
			}

			// decide which button will be shown
			if (item.isCheckedOut()) {
				checkinBtn.setVisible(true);
				extendBtn.setVisible(true);
				checkoutBtn.setVisible(false);
			} else {
				checkinBtn.setVisible(false);
				extendBtn.setVisible(false);
				checkoutBtn.setVisible(true);
			}
		} catch (InvalidItemException e) {
			System.out.println(e.getMessage());
		}
	}

	private String getInputForMemberid() {
		String memeberID = "";

		TextInputDialog inputDialog = new TextInputDialog();
		inputDialog.setTitle("Member ID Prompt");
		inputDialog.setHeaderText("Enter Member ID");
		while (true) {
			memeberID = inputDialog.showAndWait().get();
			if (!(memeberID.isEmpty() || memeberID.isBlank())) {
				break;
			}
		}
		return memeberID;
	}
}
