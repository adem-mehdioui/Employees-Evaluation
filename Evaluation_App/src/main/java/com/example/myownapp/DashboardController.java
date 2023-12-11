package com.example.myownapp;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {



    @FXML Label label_age_moyen;
    @FXML Label label_nombre_employees;

    @FXML Label label_ratio;


    @FXML Label label_taux_anciente;

    @FXML Label taux_promotion;


    int nombre_employes=0;

    int nombre_femmes=0;
    int nombre_hommes=0;

    double pourcentage_hommes=0.0;
    double pourcentage_femmes=0.0;



    ArrayList<Integer> Ages ;



    ArrayList<Integer> Ancientes ;


    int nbr_promu=0;

















    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        count_employees();

        int somme_ages = Ages.stream().mapToInt(Integer::intValue).sum();
        System.out.println("somme des ages = " + somme_ages);
        label_age_moyen.setText(somme_ages/nombre_employes + " Ans");

        int somme_ancientes = Ancientes.stream().mapToInt(Integer::intValue).sum();
        System.out.println("sommes des ancientés = " + somme_ancientes);
        label_taux_anciente.setText(somme_ancientes/nombre_employes + " Ans");




        //SELECT COUNT(*) FROM TableName









    }



    public void count_employees() {


        Ancientes = new ArrayList<>();
        Ages = new ArrayList<>();


        int current_year = Calendar.getInstance().get(Calendar.YEAR);
        String row_birthyear="";

        String row_startjobyear="";

        LocalDate date;

        LocalDate date_recrutement;





        System.out.println("current year is " + current_year);


        String string = "19/07/1997";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);




       /* String str_date = "11/June/07";
        DateFormat formatter = new SimpleDateFormat("dd/MMM/yy");
        Date date = formatter.parse(str_date);*/

        //System.out.println("my birth year is " + date.getYear());




        PreparedStatement pst;
        try {

            pst = LoginController.connection.prepareStatement("select * from employee where demission = 0");
            ResultSet rs = pst.executeQuery();
            {
                while (rs.next()) {


                    if(rs.getString(13).equals("1")){
                        System.out.println(rs.getString(2) + "   column promotion " + rs.getString(13));
                        nbr_promu = nbr_promu + 1;
                    }

                    System.out.println(rs.getRow());

                    nombre_employes = nombre_employes + 1;

                    System.out.println("gender of current row is " + rs.getString("sexe"));
                    //System.out.println("date of birth of current row is " + rs.getString("datenaissance"));


                    row_birthyear = rs.getString("datenaissance");

                    row_startjobyear = rs.getString("daterecrutement");
                    //System.out.println("current employee brith year " + row_birthyear);
                    date = LocalDate.parse(row_birthyear, formatter);

                    date_recrutement = LocalDate.parse(row_startjobyear, formatter);
                    System.out.println("current employee brith year " + date.getYear());
                    System.out.println("current employee start job year " + date_recrutement.getYear());


                    Ages.add(current_year - date.getYear());
                    Ancientes.add(current_year - date_recrutement.getYear());





                    if(rs.getString("sexe").equals("homme"))
                        nombre_hommes = nombre_hommes + 1;
                    else if (rs.getString("sexe").equals( "femme")) {
                        nombre_femmes = nombre_femmes + 1;




                    }



                }

                System.out.print("Array of ages : " + Ages);
                System.out.println("");
                System.out.print("Array of ancientés : " + Ancientes);
                System.out.println("");









            }




        } catch (SQLException ex) {
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
        }




        System.out.println("Nombre Employés = " + nombre_employes);
        System.out.println("Nombre Hommes = " + nombre_hommes);
        System.out.println("Nombre Femmes = " + nombre_femmes);

        pourcentage_hommes = (nombre_hommes * 100) / nombre_employes;
        pourcentage_femmes = (nombre_femmes * 100) / nombre_employes;


        label_nombre_employees.setText(String.valueOf(nombre_employes) + " Employés");
        label_ratio.setText(String.valueOf(pourcentage_hommes) + "% Hommes et " + String.valueOf(pourcentage_femmes) + "% Femmes");

        System.out.println("nombre de promotion = " + nbr_promu);
        taux_promotion.setText(String.valueOf(nbr_promu) + " sur " + nombre_employes + " employées");



    }
}
