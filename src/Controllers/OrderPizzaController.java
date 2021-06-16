package Controllers;

import DB.DbHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderPizzaController implements Initializable {
    @FXML
    private VBox chosenPizzaCard;

    @FXML
    private Label pizzaName;

    @FXML
    private Label pizzaPrice;

    @FXML
    private ImageView pizzaImage;

    @FXML
    private ComboBox<Integer> cmb_adet;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;

    private double x,y;

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

    @FXML
    void click_cart(MouseEvent event) throws IOException {
        Stage cart = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Cart.fxml"));
        Scene scene = new Scene(root);
        cart.initStyle(StageStyle.UNDECORATED);

        root.setOnMousePressed(e ->{
            x=e.getSceneX();
            y=e.getSceneY();
        });
        root.setOnMouseDragged(e-> {
            cart.setX(e.getScreenX() - x);
            cart.setY(e.getScreenY() - y);
        });

        cart.setScene(scene);
        cart.show();
        cart.setResizable(false);
    }

    @FXML
    void btn_addToCart(ActionEvent event) {
        if(cmb_adet.getSelectionModel().isEmpty()){
            JOptionPane.showMessageDialog(null, "Lütfen Adet seçiniz!");
        }else{
        PreparedStatement preparedStatement;
        String pizza_name = pizzaName.getText();
        double pizza_price = Double.parseDouble(pizzaPrice.getText());
        String currentUser = LoginController.currentUserMail;
        int pizza_unit = cmb_adet.getValue();

        String query = "INSERT INTO `cart`(`pizza_name`,`pizza_unit`,`pizza_price`,`user_mail`) VALUES (?,?,?,?)";
        try {
            preparedStatement = DbHelper.getConnection().prepareStatement(query);
            preparedStatement.setString(1,pizza_name);
            preparedStatement.setInt(2,pizza_unit);
            preparedStatement.setDouble(3,pizza_price);
            preparedStatement.setString(4,currentUser );
            int result = preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Sepete Başarıyla Eklendi!");
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Hata! " + e.getMessage());
        }}
    }

    private List<Pizza> pizzas = new ArrayList<>();
    private  Image image;
    private Listener listener;


    private List<Pizza> getPizzas(){
       List<Pizza> pizzas = new ArrayList<>();
       Pizza pizza;

        pizza = new Pizza();
        pizza.setName("Anadolum");
        pizza.setPrice(32);
        pizza.setImg("/images/anadoolu.png");
        pizzas.add(pizza);

        pizza = new Pizza();
        pizza.setName("Davuklu");
        pizza.setPrice(30);
        pizza.setImg("/images/davuk.png");
        pizzas.add(pizza);

        pizza = new Pizza();
        pizza.setName("Fit");
        pizza.setPrice(25);
        pizza.setImg("/images/fit.png");
        pizzas.add(pizza);

        pizza = new Pizza();
        pizza.setName("Karışık");
        pizza.setPrice(35);
        pizza.setImg("/images/karisik.png");
        pizzas.add(pizza);

        pizza = new Pizza();
        pizza.setName("Mozerellam");
        pizza.setPrice(28);
        pizza.setImg("/images/lemargarita.png");
        pizzas.add(pizza);

        pizza = new Pizza();
        pizza.setName("Mangal");
        pizza.setPrice(33);
        pizza.setImg("/images/mangal.png");
        pizzas.add(pizza);

        pizza = new Pizza();
        pizza.setName("Peynirim");
        pizza.setPrice(29);
        pizza.setImg("/images/peynirkarmasasi.png");
        pizzas.add(pizza);

        pizza = new Pizza();
        pizza.setName("Sucuk Bayıldı");
        pizza.setPrice(26);
        pizza.setImg("/images/sucukbayildi.png");
        pizzas.add(pizza);


       return pizzas;
    };

    private void setChosenPizza(Pizza pizza){
        pizzaName.setText(pizza.getName());
        pizzaPrice.setText(String.valueOf(pizza.getPrice()));
        image = new Image(getClass().getResourceAsStream(pizza.getImg()));
        pizzaImage.setImage(image);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cmb_adet.getItems().setAll(1,2,3,4,5,6,7,8,9,10);
        pizzas.addAll(getPizzas());

        if(pizzas.size()>0){
            setChosenPizza(pizzas.get(0));
            listener = new Listener() {
                @Override
                public void onClickListener(Pizza pizza) {
                    setChosenPizza(pizza);
                }
            };
        }

        int column=1;
        int row=1;

        try {
        for(int i=0;i<pizzas.size();i++){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/FXML/Pizza.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();

            PizzaController pizzaController = fxmlLoader.getController();
            pizzaController.setData(pizzas.get(i),listener);

            if(column==3){
                column= 1;
                row++;
            }
            grid.add(anchorPane,column++,row);

            //GridPane genişliği
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);

            //GridPane Yüksekliği
            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);

            GridPane.setMargin(anchorPane, new Insets(10));

            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
