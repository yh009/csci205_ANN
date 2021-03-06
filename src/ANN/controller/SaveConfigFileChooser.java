package ANN.controller;

import ANN.model.ANNModel;
import ANN.view.ANNFileView;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/*
 * *****************************************
 *
 * CSCI205 - Software Engineering and Design
 *
 * Spring 2017
 *
 *
 *
 * Name: Zilin Ma, Yuxuan Huang
 *
 * Date: Apr 1, 2017
 *
 * Time: 12:17:54 AM
 *
 *
 *
 * Project: csci205_proj_hw3
 *
 * Package: csci205_proj_hw3.controller
 *
 * File: saveConfigFileChooser
 *
 * Description:
 *
 *
 *
 * ****************************************
 * /
 * package csci205_proj_hw3.controller;
 *
 * /**
 *
 * @author Yuxuan Huang
 */
public class SaveConfigFileChooser implements EventHandler<ActionEvent> {

    ANNFileView fileView;
    ANNModel theModel;

    public SaveConfigFileChooser(ANNFileView fileView, ANNModel theModel) {
        this.fileView = fileView;
        this.theModel = theModel;
        fileView.getSaveConfig().setOnAction(this);

    }

    @Override
    public void handle(ActionEvent event) {
        File workingDirectory = new File(System.getProperty("user.dir"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Config File");
        fileChooser.setInitialDirectory(workingDirectory);

        File dest = fileChooser.showSaveDialog(new Stage());

        if (dest != null) {
            try {
                theModel.saveANN(dest.getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(SaveConfigFileChooser.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incorrect input!");
            alert.setHeaderText("Incorrect input specified!");
            alert.setContentText("Please enter a filename!");

            alert.show();
        }
    }

}
