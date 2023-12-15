package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import application.Loader;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.Member;

public class Members implements Initializable {

    @FXML
    private TableView<Member> table;

    @FXML
    private TableColumn<Member, String> memberId, memberName;

    @FXML
    private TableColumn<Member, Integer> totalCheckouts;

    @FXML
    private TableColumn<Member, Parent> viewDetails;

    private ObservableList<Member> getMembers() {
        ObservableList<Member> members = FXCollections.observableArrayList();
        members.addAll(Loader.library.getMembers());
        return members;
    }

    private void loadMembers() {
        memberId.setCellValueFactory(new PropertyValueFactory<>("id"));
        memberName.setCellValueFactory(new PropertyValueFactory<>("name"));
        totalCheckouts.setCellValueFactory(cellData -> {
            Integer n = cellData.getValue().getChekOutRecords().size();
            return new SimpleIntegerProperty(n).asObject();
        });
        table.setItems(getMembers());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadMembers();
    }

}
