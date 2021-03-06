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
 * Time: 12:41:37 AM
 *
 *
 *
 * Project: csci205_proj_hw3
 *
 * Package: csci205_proj_hw3.controller
 *
 * File: TrainCtrl
 *
 * Description:
 *
 *
 *
 **************************************** */
package ANN.controller;

import ANN.model.ANNModel;
import ANN.view.ANNView;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import neuralnet.ANN;
import neuralnet.data.LabeledInstances;

/**
 *
 * @author Zilin Ma
 */
public class TrainCtrl implements EventHandler<ActionEvent> {

    private ANNModel theModel;
    private ANNView theView;
    private RunEpochTask theTask;

    public TrainCtrl(ANNModel theModel, ANNView theView) {
        this.theModel = theModel;
        this.theView = theView;
        theView.getTrainButton().setOnAction(this);

        /*
         * for (int i = 0; i < theModel.getANNInfo().get(0); i++) {
         * theView.getInputLabels().get(i).textProperty().bind(new
         * SimpleDoubleProperty(theModel.getInstance().get(i)).asString()); }
         * for (int j = 0; j < theModel.getANNInfo().get(2); j++) {
         *
         * theView.getOutputLabels().get(j).textProperty().bind(new
         * SimpleDoubleProperty(theModel.getInstance().get(theModel.getANNInfo().get(0)
         * + j)).asString()); }
         */
    }

    @Override
    public void handle(ActionEvent event) {

        int epoch;

        if (theModel.getData() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("CSV unspecified.");
            alert.setHeaderText("CSV unspecified.");
            alert.setContentText("please select your train/classify file.");
            alert.show();
        } else {

            if (theView.getTrainField().getText().trim().isEmpty() == false) {
                try {
                    epoch = Integer.parseInt(theView.getTrainField().getText());

                    if (theView.getInputLR().getText().trim().isEmpty() == false) {
                        theModel.changeLearningRate(Double.parseDouble(theView.getInputLR().getText()));
                    }
                    if (theView.getInputMo().getText().trim().isEmpty() == false) {
                        theModel.changeMomentum(Double.parseDouble(theView.getInputMo().getText()));
                    }

                    theModel.changeActivationFunction(theView.getCombo().getValue());

                    theTask = new RunEpochTask(theModel.getMyANN(), epoch, theModel.getData());
                    theView.getError().textProperty().bind(Bindings.format("Error: %4.3f", theTask.valueProperty()));
                    theView.getNumEpoch().textProperty().bind(theTask.messageProperty());

                } catch (NumberFormatException numberFormatException) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Incorrect input!");
                    alert.setHeaderText("Incorrect input specified!");
                    alert.setContentText("Please type integer in # epoch and doubles in Learning rate and Momentum!");

                    alert.show();

                }
                Thread th = new Thread(theTask);
                th.setDaemon(true);
                th.start();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Number of Epoch unspecified.");
                alert.setHeaderText("Number of Epoch unspecified.");
                alert.setContentText("please Enter an integer value as number of Epoch to be ran!");
                alert.show();

            }
        }

    }

    class RunEpochTask extends Task<Double> {

        private final int epoch;
        private final ANN ann;
        private final LabeledInstances trainData;

        /**
         *
         * @param ann ANN model that produces the computation.
         * @param numIterations Number of iterations needed
         */
        public RunEpochTask(ANN ann, int numIterations, LabeledInstances trainData) {
            this.ann = ann;
            this.epoch = numIterations;
            this.trainData = trainData;
        }

        @Override
        protected Double call() throws Exception {
            double totalError = 0;
            for (int i = 0; i < epoch; i++) {
                ann.learn(trainData, true, 1);
                ArrayList<ArrayList<Double>> output = ann.classifyInstances(trainData);
                if (isCancelled()) {
                    updateMessage("Cancelled");
                    break;

                }
                totalError = ANN.computeOutputError(trainData, output);
                updateValue(totalError);
                //int epoch = Integer.parseInt(theView.getNumEpoch().getText()) + 1;
                updateMessage(String.format("%7d epochs", i + 1));
                updateProgress(i, epoch);

                //for (int j = 0; j < theModel.getANNInfo().get(0); j++) {
                //    theView.getInputLabels().get(i).setText(Double.toString(theModel.getMyANN().getInputLayer().getOutputValueOf(i)));
                //}
                //for (int t = 0; t < theModel.getANNInfo().get(2); t++) {
                //    theView.getOutputLabels().get(t).setText(Double.toString(theModel.getMyANN().getOutputLayer().getOutputValueOf(i)));
                //}
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        // update GUI. TODO
                        //.out.println("test");
                        theView.genGraph();
                    }

                });

                Thread.sleep(1);
            }
            return totalError;
        }

    }

    public RunEpochTask getTheTask() {
        return theTask;
    }

}
