package com.example.myownapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.*;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Scale;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HistoryViewController implements Initializable {

    @FXML
    DatePicker date_rating;
    String date_actuelle;

    @FXML VBox vbox_rating;


    @FXML Rating evaluation_globale;


    private   Employee connected_user;

    @FXML Label note_evaluation;


    String selected_date ="";


    LocalDate date_first_day=null;


    String sql_average="";

    LocalDate parsedDate=null;

    DateTimeFormatter formatters = null;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //set current date for rating datepicker

        date_actuelle = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
       /* date_rating = new DatePicker(LocalDate.now());
        date_rating.setValue(LocalDate.now());*/


        //disable futur dates for rating datepicker

        date_rating.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
            }
        });





        /*String cssLayout = "-fx-border-color: #049bc9;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 3;\n" +
                "-fx-border-style: dashed;\n";*/


        String cssLayout = "-fx-border-color: #049bc9;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 3;";

        vbox_rating.setStyle(cssLayout);




        connected_user = LoginController.emp;


        System.out.println("display name from history controller " + connected_user.getNom());




        // Listener for updating the checkout date w.r.t check in date


        /*date_rating.valueProperty().addListener((ov, oldValue, newValue) -> {



        });*/








    }

    public void actualiser (ActionEvent actionEvent) {



        //date_changed = new SimpleDateFormat("dd/MM/yyyy").format(newValue);


        selected_date = date_rating.getEditor().getText();


        System.out.println("selected date from datepicker is " + selected_date);


        date_first_day = date_rating.getValue().minusDays(date_rating.getValue().getDayOfMonth()-1);



        System.out.println("start date of the month is " + date_first_day.toString());




        sql_average = "SELECT AVG(valeur),id_rated,date_creation FROM rating WHERE id_rated = ? AND date_creation >= ? and date_creation <= ? ;";



        PreparedStatement preparedStatement;

        try {



            // here i'm getting the date that comes after selected date to be able to retain only [1 - end of month]
            LocalDate limit =  date_rating.getValue().plusDays(1);
            System.out.println("limit date is " + limit.toString());




            preparedStatement = LoginController.connection.prepareStatement(sql_average);
            preparedStatement.setString(1, connected_user.getEmp_id());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(date_first_day.atStartOfDay()) );
            preparedStatement.setTimestamp(3, Timestamp.valueOf(limit.atStartOfDay()));




            ResultSet rs = preparedStatement.executeQuery();
            String rate_with_two_digits;
            {
                while (rs.next()) {




                    if(rs.getString(1) != null) {


                        System.out.println("la moyenne  de l'evaluation pour la date " + selected_date.toString() + " de l'employee connecté " + connected_user.getNom() + " = " + rs.getString(1));
                        evaluation_globale.setStyle("-fx-fill: orange");
                        evaluation_globale.setRating(Double.parseDouble(rs.getString(1)));

                        //show only two digits after decimal
                        // new DecimalFormat("##.##").format(rs.getString(1))


                        rate_with_two_digits = String.format("%.2f", Double.parseDouble(rs.getString(1)));
                        note_evaluation.setText(rate_with_two_digits + " sur 5");

                        //evaluation_globale.setDisable(true);

                    }
                    else{
                       /* Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Introuvable !!!");

                        alert.setHeaderText("Introuvable");
                        alert.setContentText("Aucun résultat trouvé!");

                        alert.showAndWait(); */

                        evaluation_globale.setRating(0.0);
                        note_evaluation.setText("pas de résultat");



                    }


                }



            }



        } catch (SQLException ex) {
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }




    }



    public void print_result(){


        // this is to print rating result on a paper A4

        PrinterJob job = PrinterJob.createPrinterJob();



        if(job != null && job.showPrintDialog(vbox_rating.getScene().getWindow())){
            Printer printer = job.getPrinter();
            PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.HARDWARE_MINIMUM);



            double width = vbox_rating.getWidth();
            double height = vbox_rating.getHeight();

            PrintResolution resolution = job.getJobSettings().getPrintResolution();

            width /= resolution.getFeedResolution();

            height /= resolution.getCrossFeedResolution();

            double scaleX = pageLayout.getPrintableWidth()/width/1100;
            double scaleY = pageLayout.getPrintableHeight()/height/600;

            Scale scale = new Scale(scaleX, scaleY);

            vbox_rating.getTransforms().add(scale);

            boolean success = job.printPage(pageLayout, vbox_rating);
            if(success){
                job.endJob();
            }
            vbox_rating.getTransforms().remove(scale);
        }


    }
}
