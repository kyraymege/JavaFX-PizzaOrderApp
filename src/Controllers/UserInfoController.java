package Controllers;

import DB.DbHelper;
import com.mysql.cj.log.Log;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class UserInfoController implements Initializable {

    @FXML
    private Label txt_name;

    @FXML
    private Label txt_mail;

    @FXML
    private TextArea txt_address;

    PreparedStatement preparedStatement;
    ResultSet resultSet;
    public static String currentUserAddress;
    private String currentPassword;

    @FXML
    private PasswordField txt_oldPassword;

    @FXML
    private PasswordField txt_newPassword;

    Connection connection;
    Statement statement;

    @FXML
    void btn_changePassword(ActionEvent event) throws SQLException {
        if (txt_oldPassword.getText().equals(null) || txt_newPassword.getText().equals(null) || txt_newPassword.getText().equals("") || txt_oldPassword.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Lütfen Boş Bırakmayınız!");
        } else {
            String query = "SELECT * FROM `users` WHERE `user_mail` =" + "\"" + LoginController.currentUserMail + "\"";
            try {
                preparedStatement = DbHelper.getConnection().prepareStatement(query);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    currentPassword = resultSet.getString("user_password");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                preparedStatement.close();
                resultSet.close();
            }
            if(txt_oldPassword.getText().equals(currentPassword)){
                String changePasswordQuery = "update users set user_password="+"\""+txt_newPassword.getText()+"\""+"where user_mail="+"\""+LoginController.currentUserMail+"\"";

                try{
                    connection = DbHelper.getConnection();
                    statement = connection.createStatement();
                    statement.executeUpdate(changePasswordQuery);
                    JOptionPane.showMessageDialog(null, "Şifreniz başarıyla değiştirilmiştir!");
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
            }else{
                JOptionPane.showMessageDialog(null, "Eski şifreniz ile girdiğiniz şifre uyuşmuyor!");
            }
        }
    }


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

    private void getUserData(){
        String query = "SELECT * FROM `users` WHERE `user_mail` ="+"\""+ LoginController.currentUserMail +"\"" ;
        try{
            preparedStatement = DbHelper.getConnection().prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                txt_name.setText(resultSet.getString("user_name"));
                txt_mail.setText(resultSet.getString("user_mail"));
                txt_address.setText(resultSet.getString("user_address"));
                currentUserAddress = resultSet.getString("user_address");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public  void initialize(URL url, ResourceBundle resourceBundle) {
        getUserData();
    }
}
