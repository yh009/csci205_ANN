/* *****************************************
 *
 * CSCI205 - Software Engineering and Design
 *
 * Spring 2017
 *
 *
 *
 * Name: Zilin Ma, Yuxuan Huang
 *
 * Date: Apr 3, 2017
 *
 * Time: 4:11:43 PM
 *
 *
 *
 * Project: csci205_proj_hw3
 *
 * Package: csci205_proj_hw3.controller
 *
 * File: StopCtrl
 *
 * Description:
 *
 *
 *
 **************************************** */
package csci205_proj_hw3.controller;

import csci205_proj_hw3.model.ANNModel;
import csci205_proj_hw3.view.ANNView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author Zilin Ma
 */
public class StopCtrl implements EventHandler<ActionEvent> {

    private ANNView theView;
    private ANNModel theModel;

    public StopCtrl(ANNModel theModel, ANNView theView) {
        this.theView = theView;
        this.theModel = theModel;
        theView.getBtnStop().setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        theView.getTrainCtrl().getTheTask().cancel();

    }

}