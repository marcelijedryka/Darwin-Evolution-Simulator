package gui;

import backPackage.Field;
import backPackage.IEngine;
import backPackage.SimulationEngine;
import backPackage.Vector2d;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class VisualizationApp{

    private Thread engineThread;
    private Field map;
    private ParameterHolder params;
    private Stage primaryStage;

    public VisualizationApp(Stage primaryStage , ParameterHolder params){
        this.params = params;

        this.primaryStage = primaryStage;

        map = new Field(params.getMapHeight(), params.getMapWidth(), params.getGrassAmount(), params.getDailyGrassGrowth(),
                params.getGrassEnergyBoost() , params.getMutationVar() , params.getMinMutation(), params.getMaxMutation(),
                params.getBehVar(), params.getMinBreedEnergy(), params.getBreedEnergyLoss(), params.getMapVar(), params.getPlantVar());

        SimulationEngine engine = new SimulationEngine(map,params.getSimulationTime() , params.getAnimalAmount(), params.getStartingEnergy(),
                params.getDailyEnergyCost() , params.getGenotypeLength());

        engineThread = new Thread(engine);
    }

    public void start (){
        VBox data = createDataBox();

    }

    public VBox createDataBox(){
        VBox box = new VBox();
        return box;
    }
}