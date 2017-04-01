package csci205_proj_hw3.controller;

import csci205_proj_hw3.model.ANNModel;
import csci205_proj_hw3.view.ANNFileView;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
 * Time: 12:27:36 AM
 *
 *
 *
 * Project: csci205_proj_hw3
 *
 * Package: csci205_proj_hw3.controller
 *
 * File: selectTestFileChooser
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
 * @author mac
 */
public class selectTestFileChooser implements EventHandler<ActionEvent> {

    ANNFileView fileView;
    ANNModel theModel;

    public selectTestFileChooser(ANNFileView fileView, ANNModel theModel) {
        this.fileView = fileView;
        this.theModel = theModel;
        fileView.getSelectData().setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        File workingDirectory = new File(System.getProperty("user.dir"));
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Config File");
        fileChooser.setInitialDirectory(workingDirectory);

        File configFile = fileChooser.showOpenDialog(new Stage());
        try {
            theModel.openTrainData(configFile.getAbsolutePath());

            //fileView.fileSelected(configFile);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(selectTestFileChooser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
