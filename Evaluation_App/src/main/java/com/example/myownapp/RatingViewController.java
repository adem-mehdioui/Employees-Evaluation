package com.example.myownapp;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.controlsfx.control.Rating;

import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RatingViewController implements Initializable {


    private   Employee emp_connecter;

    @FXML
    TableView<Employee> mytableview;

    @FXML TextArea observation;


    @FXML Button  submit_rating;


    @FXML private Rating rate;


    @FXML
    private Label msg;

      ObservableList<Employee> employees;


    @FXML
    HBox filter;


    @FXML ChoiceBox<String> choicebox_category;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        emp_connecter = MainMenuController.logged_employee;


        System.out.println("emp to delete from list " + emp_connecter.getPoste());










        rate.ratingProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {

            msg.setText("Rating Value "+newValue);

        });

        /*rate2.ratingProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {

            msg2.setText("Rating :- "+newValue);

        });*/


        //System.out.println("taile initiale " + employees.size());


        //execute method that brings records from database and add them to the static observable list employees
        table();


        mytableview.getItems().addAll(employees);


        System.out.println("id emp connecté " + Integer.parseInt(emp_connecter.getEmp_id()));
        mytableview.getItems().remove(Integer.parseInt(emp_connecter.getEmp_id()) - 1 );

        mytableview.refresh();





        TableColumn<Employee, String> id_col = new
                TableColumn<>("ID");
        id_col.setCellValueFactory(
                new PropertyValueFactory<>("emp_id"));




        TableColumn<Employee, String> nom_col =
                new TableColumn<>("Nom");
        nom_col.setCellValueFactory(
                new PropertyValueFactory<>("nom"));


        TableColumn<Employee, String> prenom_col =
                new TableColumn<>("Prenom");
        prenom_col.setCellValueFactory(
                new PropertyValueFactory<>("prenom"));






        TableColumn<Employee, String> datenaissance_col =
                new TableColumn<>("Date de naissance");
        datenaissance_col.setCellValueFactory(
                new PropertyValueFactory<>("datenaissance"));




       /* TableColumn<Employee, String> adresse_col =
                new TableColumn<>("Adresse");
        adresse_col.setCellValueFactory(
                new PropertyValueFactory<>("adresse"));*/

        TableColumn<Employee, String> qualification_col =
                new TableColumn<>("Qualification");
        qualification_col.setCellValueFactory(
                new PropertyValueFactory<>("qualification"));

        TableColumn<Employee, String> poste_col =
                new TableColumn<>("Poste occupé");
        poste_col.setCellValueFactory(
                new PropertyValueFactory<>("poste"));

        TableColumn<Employee, String> service_col =
                new TableColumn<>("Service");
        service_col.setCellValueFactory(
                new PropertyValueFactory<>("service"));


        TableColumn<Employee, String> numtel_col =
                new TableColumn<>("N° téléphone");
        numtel_col.setCellValueFactory(
                new PropertyValueFactory<>("numtel"));



        TableColumn<Employee, String> daterecrutement_col =
                new TableColumn<>("Date de Recrutement");
        daterecrutement_col.setCellValueFactory(
                new PropertyValueFactory<>("daterecrutement"));



        id_col.setPrefWidth(40);
        nom_col.setPrefWidth(90);
        prenom_col.setPrefWidth(90);
        datenaissance_col.setPrefWidth(110);
        // adresse_col.setPrefWidth(170);
        qualification_col.setPrefWidth(120);
        poste_col.setPrefWidth(120);
        service_col.setPrefWidth(120);
        numtel_col.setPrefWidth(80);
        daterecrutement_col.setPrefWidth(150);


        mytableview.getColumns().add(id_col);
        mytableview.getColumns().add(nom_col);
        mytableview.getColumns().add(prenom_col);
        mytableview.getColumns().add(datenaissance_col);
        // mytableview.getColumns().add(adresse_col);
        mytableview.getColumns().add(qualification_col);
        mytableview.getColumns().add(poste_col);
        mytableview.getColumns().add(service_col);
        mytableview.getColumns().add(numtel_col);
        mytableview.getColumns().add(daterecrutement_col);





        autoResizeColumns(mytableview);



        FilteredList<Employee> flPerson = new FilteredList(mytableview.getItems(), p -> true);//Pass the data to a filtered list
        mytableview.setItems(flPerson);//Set the table's items using the filtered list


        //Adding ChoiceBox and TextField here!
        ChoiceBox<String> choiceBox = new ChoiceBox();
        choiceBox.getItems().addAll("ID", "Nom", "Prénom","Sexe","Service");
        choiceBox.setValue("Nom");


        TextField textField = new TextField();
        textField.setPromptText("Recherche içi!");
        textField.textProperty().addListener((obs, oldValue, newValue) -> {

            //this is to reset tableview when choicebox has changed
            mytableview.refresh();

            switch (choiceBox.getValue())//Switch on choiceBox value
            {



                case "ID":
                    flPerson.setPredicate(p -> p.getEmp_id().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by emp_id
                    break;
                case "Nom":
                    flPerson.setPredicate(p -> p.getNom().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by nom
                    break;
                case "Prénom":
                    flPerson.setPredicate(p -> p.getPrenom().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by prenom
                    break;
                case "Sexe":
                    flPerson.setPredicate(p -> p.getSexe().toLowerCase().contains(newValue.toLowerCase().trim()));//filter table by sexe
                    break;
                case "Service":
                    flPerson.setPredicate(p -> p.getService().toLowerCase().contains(newValue.toLowerCase().trim()));
                    break;




            }


        });

        choicebox_category.setValue(choicebox_category.getItems().get(0));

        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal)
                -> {//reset table and textfield when new choice is selected
            if (newVal != null) {
                textField.setText("");
            }
        });


        filter.getChildren().addAll(choiceBox, textField);



        mytableview.refresh();




        mytableview.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {




            if (newSelection != null) {

                    //choicebox_category.setValue("Assiduité");

                    rate.setRating(0);
                    observation.clear();
                    choicebox_category.setVisible(true);
                    submit_rating.setVisible(true);
                    rate.setVisible(true);
                    observation.setVisible(true);


                    // String st[] = {""};

                    // add a listener
                    choicebox_category.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

                        // if the item of the list is changed
                        public void changed(ObservableValue ov, Number value, Number new_value) {

                            rate.setRating(0);
                            observation.clear();
                            //choicebox_category.setValue(st[new_value.intValue()]);


                            // set the text for the label to the selected item
                            // l1.setText(st[new_value.intValue()] + " selected");

                        }
                    });


                    //mytableview.getSelectionModel().clearSelection();
                }


        });


        //this for preventing user from switching columns
        mytableview.widthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> source, Number oldWidth, Number newWidth)
            {
                TableHeaderRow header = (TableHeaderRow) mytableview.lookup("TableHeaderRow");
                header.reorderingProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        header.setReordering(false);
                    }
                });
            }
        });





    }



    public static void autoResizeColumns( TableView<?> table )
    {
        //Set the right policy
        table.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().stream().forEach( (column) ->
        {
            //Minimal width = columnheader
            Text t = new Text( column.getText() );
            double max = t.getLayoutBounds().getWidth();
            for ( int i = 0; i < table.getItems().size(); i++ )
            {
                //cell must not be empty
                if ( column.getCellData( i ) != null )
                {
                    t = new Text( column.getCellData( i ).toString() );
                    double calcwidth = t.getLayoutBounds().getWidth();
                    //remember new max-width
                    if ( calcwidth > max )
                    {
                        max = calcwidth;
                    }
                }
            }
            //set the new max-widht with some extra space
            //column.setPrefWidth( max + 10.0d );

            // i have modified the previous instruction
            column.setPrefWidth( max + 11.0d );
            column.setResizable(false);

        } );
    }




    public void table() {

        PreparedStatement pst;
        employees = FXCollections.observableArrayList();
        try {

            pst = LoginController.connection.prepareStatement("select * from employee where demission = 0");
            ResultSet rs = pst.executeQuery();
            {
                while (rs.next()) {
                    Employee employee = new Employee();
                    employee.setEmp_id(rs.getString("emp_id"));
                    employee.setNom(rs.getString("nom"));
                    employee.setPrenom(rs.getString("prenom"));
                    employee.setSexe(rs.getString("sexe"));
                    employee.setDatenaissance(rs.getString("datenaissance"));
                    employee.setQualification(rs.getString("qualification"));
                    employee.setPoste(rs.getString("poste"));
                    employee.setService(rs.getString("service"));
                    employee.setNumtel(rs.getString("numtel"));
                    employee.setDaterecrutement(rs.getString("daterecrutement"));



                    employees.add(employee);


                    //System.out.println(rs.getString(2));


                }

            }






           /* IDColmn.setCellValueFactory(f -> f.getValue().idProperty());
            NameColmn.setCellValueFactory(f -> f.getValue().nameProperty());
            MobileColmn.setCellValueFactory(f -> f.getValue().mobileProperty());
            CourseColmn.setCellValueFactory(f -> f.getValue().courseProperty());*/


        } catch (SQLException ex) {
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }




    }

    public void validate_rating(ActionEvent actionEvent) throws SQLException {

        String my_rating_value="";
        String id_rater="";
        String id_rated="";
        String critere="";
        String obs="";




        //get current date

        //  String date_actuelle = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(Calendar.getInstance().getTime());


       /* java.util.Date current_date = new java.util.Date();

        System.out.println("time is " + current_date.getTime());

        java.sql.Date sqlDate=new java.sql.Date();*/


         SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Timestamp timestamp = new Timestamp(System.currentTimeMillis());


         //System.out.println(sdf3.format(timestamp));         // 2021-03-24 16:48:05





        id_rater = emp_connecter.getEmp_id();
        id_rated = mytableview.getSelectionModel().getSelectedItem().getEmp_id();

        critere = choicebox_category.getValue();

        obs = observation.getText().toString();

        my_rating_value = String.valueOf(rate.getRating());


      /*  LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateObj.format(formatter);
        System.out.println(date);*/

        System.out.println("current date " + getCurrentDate());



        //  statement.setDate(index, new java.sql.Date(time));

        PreparedStatement pst0 = LoginController.connection.prepareStatement("select * from rating where id_rater = ? AND id_rated = ? AND DATE(date_creation) = ?  AND category = ?");
        pst0.setString(1, emp_connecter.getEmp_id());
        pst0.setString(2, mytableview.getSelectionModel().getSelectedItem().getEmp_id());
        pst0.setDate(3,getCurrentDate());
        pst0.setString(4,choicebox_category.getSelectionModel().getSelectedItem());


        ResultSet rs0 = pst0.executeQuery();

        {
            if (!rs0.next()) {



                   try
                    {
                        PreparedStatement pst = LoginController.connection.prepareStatement("insert into rating(id_rater, id_rated, category, valeur, observation, date_creation)values(?,?,?,?,?,?)");
                        pst.setString(1, String.valueOf(id_rater));
                        pst.setString(2, String.valueOf(id_rated));
                        pst.setString(3, critere);
                        pst.setString(4, my_rating_value);
                        pst.setString(5, obs);
                        pst.setTimestamp(6, Timestamp.valueOf(sdf3.format(timestamp)));


                        pst.executeUpdate();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Enregistrement de l'avis!!!");

                        alert.setHeaderText("Avis !!!");
                        alert.setContentText("Votre évaluation a été ajoutée!");

                        alert.showAndWait();






                    }
                    catch (SQLException ex)
                    {
                        Logger.getLogger(RatingViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }




                }else{



                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Notification !!!");

                alert1.setHeaderText("Répétition!!!");
                alert1.setContentText("Vous avez déja evalué ce collègue Aujourd'hui!");
                alert1.showAndWait();




            }



            

            //colorer la ligne du collegue


        }







    }


    private String toDate(long timestamp) {
        Date date = new Date(timestamp * 1000);
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }


    private static Date getCurrentDate() {
        java.util.Date today = new java.util.Date();
        return new Date(today.getTime());
    }





}
