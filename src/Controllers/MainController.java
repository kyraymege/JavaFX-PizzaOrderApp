package Controllers;

import DB.DbHelper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private ImageView img_orderpizza;

    @FXML
    private Label txt_orderPizza;

    @FXML
    private Text lbl_welcome;

    @FXML
    void click_logout(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        Stage login = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
        Scene scene = new Scene(root);

        login.setScene(scene);
        login.show();
        login.setResizable(false);
    }

    @FXML
    void orderPizzaText_click(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        Stage orderPizza = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/OrderPizza.fxml"));
        Scene scene = new Scene(root);
        orderPizza.setScene(scene);
        orderPizza.show();
        orderPizza.setResizable(false);
    }

    @FXML
    void orderPizza_click(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        Stage orderPizza = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/OrderPizza.fxml"));
        Scene scene = new Scene(root);
        orderPizza.setScene(scene);
        orderPizza.show();
        orderPizza.setResizable(false);
    }

    @FXML
    void click_orderStatus(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        Stage orderPizza = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/OrderStatus.fxml"));
        Scene scene = new Scene(root);
        orderPizza.setScene(scene);
        orderPizza.show();
        orderPizza.setResizable(false);
    }

    @FXML
    void click_orderStatusText(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        Stage orderPizza = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/OrderStatus.fxml"));
        Scene scene = new Scene(root);
        orderPizza.setScene(scene);
        orderPizza.show();
        orderPizza.setResizable(false);
    }

    @FXML
    void click_userInfo(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        Stage userInfo = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/UserInfo.fxml"));
        Scene scene = new Scene(root);
        userInfo.setScene(scene);
        userInfo.show();
        userInfo.setResizable(false);
    }

    @FXML
    void click_userInfoText(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        Stage userInfo = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/UserInfo.fxml"));
        Scene scene = new Scene(root);
        userInfo.setScene(scene);
        userInfo.show();
        userInfo.setResizable(false);

    }

    public static String currentUserAddress2;

    private void getUserData(){
        String query = "SELECT * FROM `users` WHERE `user_mail` ="+"\""+ LoginController.currentUserMail +"\"" ;
        try{
            PreparedStatement preparedStatement;
            ResultSet resultSet;
            preparedStatement = DbHelper.getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                currentUserAddress2 = resultSet.getString("user_address");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getUserData();
    }
}
