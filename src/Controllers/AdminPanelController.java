package Controllers;

import DB.DbHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AdminPanelController implements Initializable {

    @FXML
    private TableView<OrderStatus> tbl_admin;

    @FXML
    private TableColumn<OrderStatus, String> col_order;

    @FXML
    private TableColumn<OrderStatus, String> col_orderID;

    @FXML
    private TableColumn<OrderStatus, String> col_orderAdress;

    @FXML
    private TableColumn<OrderStatus, String> col_orderPayment;

    @FXML
    private TableColumn<OrderStatus, String> col_orderTotalPrice;

    @FXML
    private TableColumn<OrderStatus, String> col_orderNote;

    @FXML
    private TableColumn<OrderStatus, String> col_orderStatus;

    @FXML
    private ComboBox<String> cmb_status;
    Connection connection;
    Statement statement;

    @FXML
    void btn_updateStatus(ActionEvent event) throws SQLException {
        OrderStatus orderStatus = tbl_admin.getSelectionModel().getSelectedItem();
        if(orderStatus==null || cmb_status.getSelectionModel().isEmpty()){
            JOptionPane.showMessageDialog(null, "Lütfen bir seçim yapın!");
        }else{

            orderStatus.setOrderStatus(cmb_status.getValue());
            tbl_admin.getItems().set(tbl_admin.getSelectionModel().getSelectedIndex(),orderStatus);
            String query ="update orders set order_status="+"\""+cmb_status.getValue()+"\""+"where order_id ="+"\""+orderStatus.getOrderID()+"\"";
            try{
                connection = DbHelper.getConnection();
                statement = connection.createStatement();
                statement.executeUpdate(query);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(statement != null){
                    connection.close();
                }
                if (connection != null){
                    connection.close();
                }
            }


        }
    }

    @FXML
    void btn_showInfo(ActionEvent event) {
        OrderStatus orderStatus = tbl_admin.getSelectionModel().getSelectedItem();
        if(orderStatus==null){
            JOptionPane.showMessageDialog(null, "Lütfen bir seçim yapın!");
        }else{
            JOptionPane.showMessageDialog(null, "Sipariş Numarası : "+orderStatus.getOrderID()+"\n"+"Sipariş : "+orderStatus.getOrder()+"\n"+"Sipariş Adresi : "+orderStatus.getOrderAdress()+"\n"+"Sipariş Ödeme Türü : "+orderStatus.getOrderPayment()+"\n"+"Sipariş Toplam Fiyatı : "+orderStatus.getOrderTotalPrice()+"\n"+"Sipariş Notu : "+orderStatus.getOrderNote()+"\n"+"Sipariş Durumu : "+orderStatus.getOrderStatus()+"\n");
        }
    }

    @FXML
    void click_logout(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        Stage main = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
        Scene scene = new Scene(root);
        main.setScene(scene);
        main.show();
        main.setResizable(false);
    }

    ObservableList<OrderStatus> observableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmb_status.getItems().setAll("Hazırlanıyor","Yolda","Tamamlandı");
        try {
            String query = "SELECT * FROM `orders` WHERE `order_status` ="+"\""+ "Hazırlanıyor" +"\""+" OR `order_status`="+"\""+ "Yolda" +"\"" ;
            Connection connection = DbHelper.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()){
                observableList.add(new OrderStatus(resultSet.getString("order_id"),resultSet.getString("order_order"),resultSet.getString("order_address"),resultSet.getString("order_payment"),resultSet.getString("order_totalprice"),resultSet.getString("order_status"),resultSet.getString("order_note")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        col_order.setCellValueFactory(new PropertyValueFactory<>("order"));
        col_orderID.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        col_orderAdress.setCellValueFactory(new PropertyValueFactory<>("orderAdress"));
        col_orderPayment.setCellValueFactory(new PropertyValueFactory<>("orderPayment"));
        col_orderTotalPrice.setCellValueFactory(new PropertyValueFactory<>("orderTotalPrice"));
        col_orderNote.setCellValueFactory(new PropertyValueFactory<>("orderNote"));
        col_orderStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        tbl_admin.setItems(observableList);
    }
}
