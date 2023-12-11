package com.example.myownapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateEmployeeController implements Initializable {


    @FXML
    TextField tf_id;

    @FXML
    TextField tf_nom;


    @FXML
    TextField tf_prenom;


    @FXML
    TextField tf_service;

    @FXML
    TextField tf_poste;

    @FXML
    Button update_btn;


    @FXML
    Button promouvoir_btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }


    Employee emp = new Employee();




    public void handle(KeyEvent ke) {


        if (ke.getCode().equals(KeyCode.ENTER) && tf_id.getText().isEmpty() == false) {


            emp = obtain_employee_by_id(tf_id.getText());


            tf_nom.setEditable(true);
            tf_prenom.setEditable(true);
            tf_service.setEditable(true);
            tf_poste.setEditable(true);


            //fill all textfield with searched employee values

            tf_id.setText(emp.getEmp_id());
            tf_nom.setText(emp.getNom());
            tf_prenom.setText(emp.getPrenom());
            tf_poste.setText(emp.getPoste());
            tf_service.setText(emp.getService());





            promouvoir_btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    System.out.println("promotion key event " + emp.getPromotion());





                        // retrieve employee information
                   /* stid = Integer.parseInt(tf_id.getText());
                    stnom = tf_nom.getText();
                    stprenom = tf_prenom.getText();
                    stdatenaissance = datesTimes.getEditor().getText();
                    stadresse = tf_adresse.getText();
                    stfonction = tf_fonction.getText();
                    stnumtel = tf_numtel.getText();
                    stdaterecrutement = daterecrutement.getEditor().getText();*/


                        try {

                            PreparedStatement pst_update = LoginController.connection.prepareStatement("update employee set poste = ? , service = ?, promotion = ? where emp_id = ?");
                            pst_update.setString(1, tf_poste.getText());
                            pst_update.setString(2, emp.getService());
                            pst_update.setString(3, "1");

                            pst_update.setString(4, emp.getEmp_id());


                            pst_update.executeUpdate();

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Mise à jour infos");

                            alert.setHeaderText("Mise à jour!!!");
                            alert.setContentText("L'employé " + emp.getNom() + " a été promu au poste de " + tf_poste.getText() + " !!");
                            alert.showAndWait();


                            tf_id.clear();
                            tf_nom.clear();
                            tf_prenom.clear();
                            tf_service.clear();
                            tf_poste.clear();

                            //clear textfield value only
                            //datesTimes.getEditor().clear();


        /*
        If you want to clear the value and the textfield, use :
*/


                        } catch (Exception ex) {

                            ex.printStackTrace();


                        }


                    }


            });


        }
    }



    Employee obtain_employee_by_id(String identifier) {


        {

            PreparedStatement pst;
            Employee emp = new Employee();
            try {

                pst = LoginController.connection.prepareStatement("select * from employee where emp_id = " + identifier);
                ResultSet rs = pst.executeQuery();
                {
                    while (rs.next()) {

                        emp.setEmp_id(rs.getString("emp_id"));
                        emp.setNom(rs.getString("nom"));
                        emp.setPrenom(rs.getString("prenom"));
                        emp.setPoste(rs.getString("poste"));
                        emp.setService(rs.getString("service"));
                        emp.setPromotion(rs.getString("promotion"));
                        emp.setDemission(rs.getString("demission"));


                        //System.out.println(rs.getString(2));


                    }
                }


            } catch (SQLException ex) {
                Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
            }


            return emp;


        }


    }



}









