package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class PizzaController implements Initializable {

    @FXML
    private Label name;

    @FXML
    private Label price;

    @FXML
    private ImageView image;

    @FXML
    private void click(MouseEvent mouseEvent){

        listener.onClickListener(pizza);
    }

    private Pizza pizza;
    private Listener listener;

    public void setData(Pizza pizza, Listener listener){
        this.pizza= pizza;
        this.listener = listener;
        name.setText(pizza.getName());
        price.setText(String.valueOf(pizza.getPrice()));
        Image img = new Image(getClass().getResourceAsStream(pizza.getImg()));
        image.setImage(img);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }
}
