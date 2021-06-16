package Controllers;

import DB.DbHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    private ImageView img_back;

    @FXML
    private TextField txt_name;

    @FXML
    private TextField txt_email;

    @FXML
    private PasswordField txt_password;

    @FXML
    private TextField txt_address;

    @FXML
    private void btn_signup(ActionEvent event) throws IOException {
        String name = txt_name.getText();
        String mail = txt_email.getText();
        String password = txt_password.getText();
        String address = txt_address.getText();

        if(name.equals("")||mail.equals("")||password.equals("")||address.equals("")){
            JOptionPane.showMessageDialog(null,"Lütfen boş bırakmayınız! ");
        }else {
            int control = checkUserMail(mail);
            if (control == 1) {
                PreparedStatement preparedStatement;
                String query = "INSERT INTO `users`(`user_name`,`user_mail`,`user_password`,`user_address`) VALUES (?,?,?,?)";

                try {
                    preparedStatement = DbHelper.getConnection().prepareStatement(query);
                    preparedStatement.setString(1, name);
                    preparedStatement.setString(2, mail);
                    preparedStatement.setString(3, password);
                    preparedStatement.setString(4, address);

                    if (preparedStatement.executeUpdate() > 0) {
                        JOptionPane.showMessageDialog(null, "Kayıt Başarılı!");
                    }

                } catch (Exception e) {
                    System.out.println(e);
                }

                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                Stage register = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
                Scene scene = new Scene(root);
                register.setScene(scene);
                register.show();
                register.setResizable(false);
            }else{
                JOptionPane.showMessageDialog(null, "Mail Adresi zaten kullanılmakta!");
            }
        }
    }

    @FXML
    private void img_click(MouseEvent event) throws IOException {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
        Stage register = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
        Scene scene = new Scene(root);
        register.setScene(scene);
        register.show();
        register.setResizable(false);
    }

    public int checkUserMail(String userMail) {
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        String query = "SELECT * FROM `users` where user_mail=?";

        try {
            preparedStatement = DbHelper.getConnection().prepareStatement(query);
            preparedStatement.setString(1, userMail);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return 0;
            } else {
                return 1;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
