package csci205_proj_hw3.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import neuralnet.ANN;
import neuralnet.Edge;
import neuralnet.Edges;
import neuralnet.data.LabeledInstances;

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
 * Date: Mar 30, 2017
 *
 * Time: 8:38:20 PM
 *
 *
 *
 * Project: csci205_proj_hw3
 *
 * Package: csci205_proj_hw3
 *
 * File: ANNModel
 *
 * Description:
 *
 *
 *
 * ****************************************
 * /
 * package csci205_proj_hw3;
 *
 * /**
 *
 * @author yh009
 */
public class ANNModel {

    private ANN myANN;
    private LabeledInstances data;
    private int indexPointer = 0;

    public ANNModel(ANN myANN, LabeledInstances data) {
        this.myANN = myANN;
        this.data = data;
    }

    public ANNModel() {

    }

    /**
     * Read in for the new ANNConfig.
     *
     * @param inputNodes Number of input nodes.
     * @param hiddenNodes Number of hidden nodes.
     * @param outputNodes Number of output nodes.
     */
    public void readANNConfig(int inputNodes, int hiddenNodes, int outputNodes) {
        myANN = new ANN(inputNodes, hiddenNodes, outputNodes);
    }

    /**
     * Read in a serilized ANN file.
     *
     * @param file
     */
    public void readSerilizationAnn(File file) {
        try {
            myANN = deserializeANN(file.getAbsolutePath());
        } catch (IOException ex) {
            Logger.getLogger(ANNModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ANNModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Save file in a given dir
     *
     * @param dir The directory where the ANN is stored.
     */
    public void serilizeANN(String dir) {
        try {
            serializeANN(myANN, dir);
        } catch (IOException ex) {
            Logger.getLogger(ANNModel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Opens the trian data and put it into the ANN.
     *
     * @param dir The absolute directory of the file
     * @throws FileNotFoundException
     */
    public void openTrainData(String dir) throws FileNotFoundException {
        data = new LabeledInstances(dir,
                true, 2);

    }

    /**
     * Trains one instance and increment index pointer. Only classify one
     * instance and the index of this instance is the current value of
     * indexPointer. TODO
     */
    public void trainOneInstance() {

        indexPointer++;
    }

    /**
     * Return the dimension of the layer as a list of integers. The first
     * integer is the number of neurons of input layers and so on. The format
     * is: [input][hidden][output]
     *
     * @return Dimension of the array.
     */
    public ArrayList<Integer> getANNInfo() {
        return myANN.getANNLayerDim();
    }

    /**
     * Returns the weights as an array list of ARRAY of weights. The first index
     * represent the first node in the input layer, and this moves down.
     *
     * @return array list of ARRAYs.
     */
    public ArrayList<Edge[]> getWeights() {
        ArrayList<Edge[]> edges = new ArrayList<>();
        for (int i = 0; i < myANN.getNumLayers(); i++) {
            Edges e = myANN.getEdges(i);
            Edge[][] edgeses = e.getEdges();
            for (int j = 0; j < edgeses.length; j++) {
                edges.add(edgeses[j]);
            }
        }

        return edges;
    }

    /**
     * Serializes the ANN object to a file. myANN is the object being
     * serialized, and fileName is the .ser filename.
     *
     * @param myANN
     * @param fileName
     * @throws java.io.FileNotFoundException
     * @see
     * <a href="https://www.tutorialspoint.com/java/java_serialization.htm">https://www.tutorialspoint.com/java/java_serialization.htm</a>
     */
    public static void serializeANN(ANN myANN, String fileName) throws FileNotFoundException, IOException {
        //try {
        fileName += ".ser";
        FileOutputStream fileOut = new FileOutputStream(fileName);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(myANN);
        out.close();
        fileOut.close();
        System.out.printf("Serialized data is saved in " + fileName + "\n");

        //} catch (IOException i) {
        //}
    }

    /**
     * Deserializes a file. fileName is the name of the .ser file. Returns the
     * ANN object that is deserialized.
     *
     * @param fileName
     * @return the deserialized ANN
     * @throws java.io.FileNotFoundException
     * @throws java.lang.ClassNotFoundException
     * @see
     * <a href="https://www.tutorialspoint.com/java/java_serialization.html">https://www.tutorialspoint.com/java/java_serialization.html</a>
     *
     */
    public static ANN deserializeANN(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException {
        ANN myANN = null;

        FileInputStream fileIn;
        fileIn = new FileInputStream(fileName);
        try (ObjectInputStream in = new ObjectInputStream(fileIn)) {
            myANN = (ANN) in.readObject();
        }
        fileIn.close();
        return myANN;

    }

}