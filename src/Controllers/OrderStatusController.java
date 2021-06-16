package Controllers;

import DB.DbHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrderStatusController implements Initializable {

    @FXML
    private TableView<OrderStatus> tbl_orderStatus;

    @FXML
    private TableColumn<OrderStatus,String> col_order;

    @FXML
    private TableColumn<OrderStatus,String> col_orderAdress;

    @FXML
    private TableColumn<OrderStatus,String> col_orderPayment;

    @FXML
    private TableColumn<OrderStatus,String> col_orderTotalPrice;

    @FXML
    private TableColumn<OrderStatus,String> col_orderNote;

    @FXML
    private TableColumn<OrderStatus,String> col_orderStatus;

    @FXML
    void click_back(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        Stage main = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Main.fxml"));
        Scene scene = new Scene(root);
        main.setScene(scene);
        main.show();
        main.setResizable(false);
    }

    ObservableList<OrderStatus> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            String query = "SELECT * FROM `orders` WHERE `order_user` ="+"\""+ LoginController.currentUserMail +"\"" ;
            Connection connection = DbHelper.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()){
                observableList.add(new OrderStatus(resultSet.getString("order_order"),resultSet.getString("order_address"),resultSet.getString("order_payment"),resultSet.getString("order_totalprice"),resultSet.getString("order_status"),resultSet.getString("order_note")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        col_order.setCellValueFactory(new PropertyValueFactory<>("order"));
        col_orderAdress.setCellValueFactory(new PropertyValueFactory<>("orderAdress"));
        col_orderPayment.setCellValueFactory(new PropertyValueFactory<>("orderPayment"));
        col_orderTotalPrice.setCellValueFactory(new PropertyValueFactory<>("orderTotalPrice"));
        col_orderNote.setCellValueFactory(new PropertyValueFactory<>("orderNote"));
        col_orderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        tbl_orderStatus.setItems(observableList);

    }
}
