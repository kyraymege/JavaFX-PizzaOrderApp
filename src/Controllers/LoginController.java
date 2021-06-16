package Controllers;

import DB.DbHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    PreparedStatement preparedStatement;
    ResultSet resultSet;
    public static String currentUserMail = "";

    @FXML
    private TextField txt_email;

    @FXML
    private PasswordField txt_password;

    @FXML
    private void lbl_click(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        Stage register = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Register.fxml"));
        Scene scene = new Scene(root);
        register.setScene(scene);
        register.show();
        register.setResizable(false);
    }

    @FXML
    private void btn_login(ActionEvent event) throws IOException {
        if(txt_email.getText().equals("admin") && txt_password.getText().equals("admin")){
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
            Stage main = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/AdminPanel.fxml"));
            Scene scene = new Scene(root);
            main.setScene(scene);
            main.show();
            main.setResizable(false);
        }else{
        String query = "SELECT * FROM `users` WHERE `user_mail` =? AND `user_password` =?";

        try {
            String userMail = txt_email.getText();
            String userPassword = txt_password.getText();

            preparedStatement = DbHelper.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userMail);
            preparedStatement.setString(2, userPassword);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Giriş Başarılı!");
                currentUserMail = resultSet.getString("user_mail");
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                Stage main = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/Main.fxml"));
                Scene scene = new Scene(root);
                main.setScene(scene);
                main.show();
                main.setResizable(false);
            } else {
                JOptionPane.showMessageDialog(null, "Kullanıcı adı veya Şifre Hatalı!");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }}
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

}
