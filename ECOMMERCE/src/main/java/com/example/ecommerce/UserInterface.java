package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

public class UserInterface {

    GridPane loginPage;
      HBox headerBar;

      HBox footerBar;
      Customer loggedInCustomer;
Button signInButton;
      VBox body;
      Label welcomeLabel;


      ProductLIst productLIst=new ProductLIst();
      VBox productPage;
      Button placeOrderButton=new Button("Place Order");

      ObservableList<Product> itemsInCart = FXCollections.observableArrayList();
     public BorderPane createContent(){
        BorderPane root =new BorderPane();
        root.setPrefSize(800,600);
       // root.getChildren().add(loginPage);///method to add nodes as children to pane
         root.setTop(headerBar);
        // root.setCenter(loginPage);
         body=new VBox();
         body.setPadding(new Insets(10));
         body.setAlignment(Pos.CENTER);
         root.setCenter(body);
         productPage=productLIst.getDummyTable();
        body.getChildren().add(productPage);

        root.setBottom(footerBar);
        return root;
    }

    ///constructor
public UserInterface(){
         createLoginPage();
    createHeaderBar();
    createFooterBar();


}
    private void createLoginPage(){

        Text userNameText =new Text("User Name");
        Text passwordText =new Text("Password");

        TextField userName =new TextField("himansu@gmail");
        userName.setPromptText("Type Your user name here");
        PasswordField password =new PasswordField();
        password.setText("abc234");
        password.setPromptText("Type Your Password");
        Label messageLabel=new Label("Hi");

        Button loginButton =new Button("Login");

   loginPage=new GridPane();
   //loginPage.setStyle(" -fx-background-color: grey");
   loginPage.setAlignment(Pos.CENTER);
   loginPage.setVgap(10);
   loginPage.setHgap(10);
   loginPage.add(userNameText,0,0);
   loginPage.add(userName,1,0);
   loginPage.add(passwordText,0,1);
   loginPage.add(password,1,1);
   loginPage.add(messageLabel,0,2);
loginPage.add(loginButton,1,2);

loginButton.setOnAction(new EventHandler<ActionEvent>() {//////impppp
    @Override
    public void handle(ActionEvent actionEvent) {
        String name=userName.getText();
        String pass=password.getText();
        Login login=new Login();
        loggedInCustomer=login.customerLogin(name,pass);

        if(loggedInCustomer!=null){
            messageLabel.setText("Welcome " + loggedInCustomer.getName());
            welcomeLabel.setText("Welcome-"+loggedInCustomer.getName());
            headerBar.getChildren().add(welcomeLabel);
            body.getChildren().clear();
            body.getChildren().add(productPage);
        }
        else {
            messageLabel.setText("Login Failed !! please give correct user name and password.");
        }

    }
});

    }


    ///Hbox
    private void createHeaderBar(){
         Button homeButton=new Button();
         Image image=new Image("C:\\Users\\ADMIN\\IdeaProjects\\ECOMMERCE\\src\\main\\Flipkart-Logo.png");
        ImageView imageView=new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(80);
        imageView.setFitHeight(20);
        homeButton.setGraphic(imageView);


         TextField searchBar=new TextField();
         searchBar.setPromptText("Search here");
         searchBar.setPrefWidth(280);

         Button searchButton=new Button("Search");

         signInButton=new Button("Sign In");
         welcomeLabel=new Label();

         Button cartButton=new Button("Cart");
         Button orderButton=new Button("Orders");


         headerBar=new HBox(20);
         headerBar.setPadding(new Insets(10));
         headerBar.setSpacing(10);
         //headerBar
         headerBar.setAlignment(Pos.CENTER);
         headerBar.getChildren().addAll(homeButton,searchBar,searchButton,signInButton,cartButton,orderButton);

         signInButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 body.getChildren().clear();
                 body.getChildren().add(loginPage);//put login page
                 headerBar.getChildren().remove(signInButton);
             }
         });

         cartButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 body.getChildren().clear();
                 VBox prodPage= productLIst.getProductsInCart(itemsInCart);
                 prodPage.setAlignment(Pos.CENTER);
                 prodPage.setSpacing(10);
                 prodPage.getChildren().add(placeOrderButton);
                 body.getChildren().add(prodPage);
                 footerBar.setVisible(false);//
             }
         });

         placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 //need list of product and customer

                 if(itemsInCart==null){
                     //please select a product first to place order
                     showDialog("please add some product in cart to place order!");
                     return;
                 }
                 if(loggedInCustomer==null){
                     showDialog("please login first to place order!");
                     return;
                 }
                 int count=Order.placrMultipleOrder(loggedInCustomer,itemsInCart);
                 if(count!=0){
                     showDialog("Order for "+count+" products placed sucessfully");
                 }
                 else{
                     showDialog("Order failed");
                 }
             }
         });

         homeButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 body.getChildren().clear();
                 body.getChildren().add(productPage);
                 footerBar.setVisible(true);
                 if (loggedInCustomer==null && headerBar.getChildren().indexOf(signInButton)==-1){
                     headerBar.getChildren().add(signInButton);
                 }
             }
         });

    }
    private void createFooterBar(){


        Button buyNowButton=new Button("BuyNow");
        Button addToCartButton =new Button("Add to Cart");

        footerBar=new HBox();
        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        //headerBar
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton,addToCartButton);


        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product=productLIst.getSelectedProduct();
                if(product==null){
                    //please select a product first to place order
                    showDialog("please select a product first to place order!");
                    return;
                }
                if(loggedInCustomer==null){
                    showDialog("please login first to place order!");
                    return;
                }
                boolean status=Order.placrOrder(loggedInCustomer,product);
                if(status==true){
                    showDialog("Order placed sucessfully");
                }
                else{
                    showDialog("Order failed");
                }
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product=productLIst.getSelectedProduct();
                if(product==null){
                    //please select a product first to place order
                    showDialog("please select a product first to add it to cart ");
                    return;
                }
                itemsInCart.add(product);
                showDialog("Selected item has been added to the cart succesfully. ");
            }
        });
    }

private void showDialog(String message){
    Alert alert =new Alert(Alert.AlertType.INFORMATION);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.setTitle("Message");
    alert.showAndWait();
}

}
