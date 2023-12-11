package com.example.myownapp;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class LoginController implements Initializable {





    static Employee emp = null;



    @FXML
    TextField emailIdField;



    @FXML
    PasswordField passwordField;


    @FXML
    Button submitButton;





    @FXML
    BorderPane borderpane_login ;


    Stage dialogStage = new Stage();
    Scene scene;

    static Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    Stage stage;




    public LoginController() {
        connection = ConnectionUtil.connectdb();
    }










    public void login(ActionEvent actionEvent) {

        //System.out.println("demande connection...");



        String email = emailIdField.getText().toString();
        String password = passwordField.getText().toString();


        String sql = "SELECT * FROM employee WHERE email = ? and password = ?";


        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();
            if(!resultSet.next()){
                infoBox("Veuillez entrer l'email et password correctement", null, "Echoué");
            }else{

             /*   System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(2));
                System.out.println(resultSet.getString(3));*/






                List<Employee> employees =new ArrayList<Employee>();


                    emp  = new Employee();
                    emp.setEmp_id(resultSet.getString(1));
                    emp.setNom(resultSet.getString(2));
                    emp.setPrenom(resultSet.getString(3));
                    emp.setEmail(resultSet.getString(4));
                    emp.setPassword(resultSet.getString(5));
                    emp.setSexe(resultSet.getString(6));
                    emp.setDatenaissance(resultSet.getString(7));
                    emp.setQualification(resultSet.getString(8));
                    emp.setPoste(resultSet.getString(9));
                    emp.setService(resultSet.getString(10));
                    emp.setDaterecrutement(resultSet.getString(11));


                    employees.add(emp);
                    System.out.println(emp.getNom());







               /* System.out.println(resultSet.getString(1));
                System.out.println(resultSet.getString(4));
                System.out.println(resultSet.getString(9));*/










                //infoBox("Login Successfull",null,"Success" );
                //Node node = (Node)actionEvent.getSource();
              //  dialogStage = (Stage) node.getScene().getWindow();
               // dialogStage.close();

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("main_menu.fxml"));

                Parent root = (Parent) fxmlLoader.load();

                MainMenuController mainMenuController = fxmlLoader.getController();

                stage =(Stage) borderpane_login.getScene().getWindow();

                System.out.println("controller " + mainMenuController);
                mainMenuController.myfunction(emp);

                Scene scene = new Scene(root,1362,695);


                //Scene scene = new Scene(fxmlLoader.load(), 1000, 650);






                //stage.resizableProperty().setValue(Boolean.TRUE);
                stage.setTitle("Menu principale");

                stage.setScene(scene);
                stage.sizeToScene();
                stage.centerOnScreen();
                //stage.setMaximized(true);
                stage.setResizable(true);
                stage.show();


               // mainctrl.setLogged_employee(emp);




                //scene = new Scene(FXMLLoader.load(getClass().getResource("FXMLMenu.fxml")));
                //dialogStage.setScene(scene);
               // dialogStage.show();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }











    }



    public static void infoBox(String infoMessage, String headerText, String title){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.show();


// configure UI for popup etc...

// hide popup after 3 seconds:
       /* PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> alert.hide());

        alert.show();
        delay.play();*/

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {













    }






}