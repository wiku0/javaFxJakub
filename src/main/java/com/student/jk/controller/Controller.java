package com.student.jk.controller;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class Controller {

    @FXML
    Label label1;

    @FXML
    TextField poleTekstowe;



    @FXML
    public void buttonClick(){
        if(poleTekstowe.getText().trim().length()==0){
            label1.setText("Nothing");
        }else{
            label1.setText(poleTekstowe.getText());
        }

    }

}
