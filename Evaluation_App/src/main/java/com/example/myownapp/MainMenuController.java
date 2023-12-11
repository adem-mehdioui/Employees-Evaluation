package com.example.myownapp;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

     static Employee logged_employee;




    @FXML
    BorderPane borderpane_mainmenu;



    @FXML
    ToggleButton btn_rate;
    @FXML
    ToggleButton btn_dashboard;
    @FXML
    ToggleButton btn_history;

    @FXML
    ToggleButton btn_disconnect;

    @FXML
    ToggleButton btn_update_employee;

    @FXML public Label userinfo;

    @FXML public Label userJob;







    // TableView<Employee> testTable;


    static ObservableList<Employee> employees;


    ToggleGroup toggleGroup;











    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        logged_employee = LoginController.emp;

        System.out.println("date de naissance de l'utilisateur authentifié " + logged_employee.getDatenaissance());





        toggleGroup = new ToggleGroup();

        btn_dashboard.setToggleGroup(toggleGroup);
        btn_rate.setToggleGroup(toggleGroup);
        btn_history.setToggleGroup(toggleGroup);
        btn_disconnect.setToggleGroup(toggleGroup);
        btn_update_employee.setToggleGroup(toggleGroup);



        toggleGroup.selectedToggleProperty().addListener((obsVal, oldVal, newVal) -> {
            if (newVal == null)
                oldVal.setSelected(true);

        });


        if(logged_employee.getPoste().equals("DRH")) {

            btn_update_employee.setDisable(false);
            btn_update_employee.setVisible(true);
        }


        btn_dashboard.setContentDisplay(ContentDisplay.TOP);
        btn_rate.setContentDisplay(ContentDisplay.TOP);
        btn_history.setContentDisplay(ContentDisplay.TOP);
        btn_update_employee.setContentDisplay(ContentDisplay.TOP);
        btn_disconnect.setContentDisplay(ContentDisplay.TOP);







        btn_dashboard.setSelected(true);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dashboard_view.fxml"));

        Parent parent_layout = null;
        try {
            parent_layout = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        borderpane_mainmenu.setCenter(parent_layout);







    }




    public void setLogged_employee(Employee myemp) {
        this.logged_employee = myemp;
    }

    public Employee getLogged_employee() {
        return logged_employee;
    }





    public void myfunction(Employee employee) {

        logged_employee = employee;
        userinfo.setText(logged_employee.getNom() + " " + logged_employee.getPrenom());
        userJob.setText("ID : " + logged_employee.getEmp_id() + " Poste : " + logged_employee.getPoste());



    }



    public void goto_ratingview(ActionEvent actionEvent) throws IOException {

        // FXMLLoader loader = new FXMLLoader(getClass().getResource("rating-view.fxml"));



        System.out.println(logged_employee.getNom() + "mainmenu");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("rating-view.fxml"));

        Parent parent_layout = (Parent) fxmlLoader.load();

        RatingViewController ratingViewController = fxmlLoader.getController();
        System.out.println("target controller " + ratingViewController);


        borderpane_mainmenu.setCenter(parent_layout);


    }





    public void disconnect(ActionEvent actionEvent) {


        Alert alert =
                new Alert(Alert.AlertType.WARNING,
                        "Voulez vous quitter l'application ?",
                        ButtonType.OK,
                        ButtonType.CANCEL);
        alert.setTitle("Comfirmation !");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            //formatGotIt = true;

            // close admin account and go back to login page


            try {

                //ConnectionUtil.dbDisconnect();

                LoginController.connection.close();

                System.out.println("connection state after disconnect " + LoginController.connection);
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("login-view.fxml"));

                Scene scene = new Scene(fxmlLoader.load(), 600, 400);

                Stage stage = (Stage)borderpane_mainmenu.getScene().getWindow();
                //stage.resizableProperty().setValue(Boolean.FALSE);

                stage.setTitle("Log In");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();


            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }


        else{






        }




    }

    public void goto_dashboard(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("dashboard_view.fxml"));

        Parent parent_layout = (Parent) fxmlLoader.load();


        borderpane_mainmenu.setCenter(parent_layout);




    }

    public void goto_historyview(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("history_view.fxml"));

        Parent parent_layout = (Parent) fxmlLoader.load();


        borderpane_mainmenu.setCenter(parent_layout);



    }

    public void goto_updateemployee(ActionEvent actionEvent) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("update_employee.fxml"));

        Parent parent_layout = (Parent) fxmlLoader.load();


        borderpane_mainmenu.setCenter(parent_layout);




    }
}