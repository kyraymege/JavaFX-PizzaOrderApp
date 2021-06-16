package Controllers;

import DB.DbHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CartController implements Initializable {

    @FXML
    private ComboBox<String> cmb_odeme;

    @FXML
    private Label lbl_totalPrice;

    @FXML
    private TextArea txt_orderNote;

    @FXML
    private TableView<Cart> tbl_cart;

    @FXML
    private TableColumn<Cart, String> col_pizzaName;

    @FXML
    private TableColumn<Cart, Integer> col_pizzaUnit;

    @FXML
    private TableColumn<Cart, Double> col_pizzaPrice;

    @FXML
    void btn_dropCart(ActionEvent event) {
        try {
            String query = "delete from cart where user_mail=" +"\""+ LoginController.currentUserMail +"\"";
            PreparedStatement preparedStatement = DbHelper.getConnection().prepareStatement(query);
            preparedStatement.execute();
            if(preparedStatement.executeUpdate()>0){
                JOptionPane.showMessageDialog(null, "Sepet Başarıyla Boşaltıldı!");
            }
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void btn_goShopping(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void btn_buy(ActionEvent event) {
        String query = "INSERT INTO `orders`(`order_user`,`order_order`,`order_address`,`order_payment`,`order_totalprice`,`order_status`,`order_note`) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement;
        String user = LoginController.currentUserMail;
        String address = MainController.currentUserAddress2;
        String odeme = cmb_odeme.getValue();
        String orderNote = txt_orderNote.getText();
        if(cmb_odeme.getSelectionModel().isEmpty()){
            JOptionPane.showMessageDialog(null, "Lütfen ödeme türü belirtiniz!");
        }else{
            try {
                preparedStatement = DbHelper.getConnection().prepareStatement(query);
                preparedStatement.setString(1, user);
                preparedStatement.setString(2, order);
                preparedStatement.setString(3, address);
                preparedStatement.setString(4, odeme);
                preparedStatement.setString(5,String.valueOf(totalPrice));
                preparedStatement.setString(6,"Hazırlanıyor");
                preparedStatement.setString(7,orderNote);

                if (preparedStatement.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(null, "Satın Alma Başarılı!");
                }else{
                    JOptionPane.showMessageDialog(null, "Satın Alma Başarısız!");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            dropCart();
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }

    }
    private void dropCart(){
        String query = "delete from cart where user_mail=" +"\""+ LoginController.currentUserMail +"\"";
        PreparedStatement preparedStatement2 = null;
        try {
            preparedStatement2 = DbHelper.getConnection().prepareStatement(query);
            preparedStatement2.execute();
            if(preparedStatement2.executeUpdate()>0){
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    ObservableList<Cart> observableList = FXCollections.observableArrayList();
    Double totalPrice = 0.0;
    String order="";
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmb_odeme.getItems().setAll("Kredi Kartı", "Nakit", "Crypto");
        try {
            String query = "SELECT * FROM `cart` WHERE `user_mail` ="+"\""+ LoginController.currentUserMail +"\"" ;
            Connection connection = DbHelper.getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery(query);
            while (resultSet.next()){
                observableList.add(new Cart(resultSet.getString("pizza_name"),resultSet.getInt("pizza_unit"),resultSet.getDouble("pizza_price")));
                totalPrice += (resultSet.getDouble("pizza_price")*resultSet.getInt("pizza_unit"));
                order += resultSet.getString("pizza_name")+" , "+ String.valueOf(resultSet.getInt("pizza_unit"))+" , "+resultSet.getDouble("pizza_price")+" , ";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        lbl_totalPrice.setText(String.valueOf(totalPrice));
        col_pizzaName.setCellValueFactory(new PropertyValueFactory<>("pizza_name"));
        col_pizzaUnit.setCellValueFactory(new PropertyValueFactory<>("pizza_unit"));
        col_pizzaPrice.setCellValueFactory(new PropertyValueFactory<>("pizza_price"));
        tbl_cart.setItems(observableList);
    }
}
