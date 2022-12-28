package gui;

import backPackage.*;

import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.Scene;


public class VisualizationApp implements IMapObserver {

    private Thread engineThread;
    private Field field;
    private ParameterHolder params;
    private int squareSize;
    private SimulationEngine engine;
    private Canvas mapCanvas;
    private GraphicsContext WorldMap;



    public void start (Stage primaryStage, ParameterHolder params){
        this.params = params;
        initialize(this.params);
        run(primaryStage);
    }

    private void initialize(ParameterHolder params){
        field = new Field(params.getMapHeight(), params.getMapWidth(), params.getGrassAmount(), params.getDailyGrassGrowth(),
                params.getGrassEnergyBoost() , params.getMutationVar() , params.getMinMutation(), params.getMaxMutation(),
                params.getBehVar(), params.getMinBreedEnergy(), params.getBreedEnergyLoss(), params.getMapVar(), params.getPlantVar());
        field.addMapObserver(this);
        engine = new SimulationEngine(field,params.getSimulationTime() , params.getAnimalAmount(), params.getStartingEnergy(),
                params.getDailyEnergyCost() , params.getGenotypeLength(), params.getSpeed());
        engineThread = new Thread(engine);
        this.squareSize = calculateSquareSize(field);
        mapCanvas = new Canvas();
        mapCanvas.setWidth(field.getWidth() * squareSize);
        mapCanvas.setHeight(field.getHeight() * squareSize);
        WorldMap = mapCanvas.getGraphicsContext2D();
    }

    private void run(Stage primaryStage) {
        primaryStage.setTitle("Simulation");
        VBox mapBox = new VBox();
        mapBox.getChildren().add(mapCanvas);
        mapBox.setLayoutX(1400 - mapCanvas.getWidth());
        mapBox.setAlignment(Pos.CENTER);

        Group root = new Group(mapBox);
        Scene scene = new Scene(root, 1400, 750);
        engineThread.start();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void updateMap() {
        Platform.runLater(() -> {
            drawMapCanvas();
            drawStats();
        });
    }

    private int calculateSquareSize(Field field) {
        int height = field.getHeight();
        int width = field.getWidth();
        if (width > height) {
            squareSize = 900 / width;
        } else {
            squareSize = 750 / height;
        }
        return squareSize;
    }

    private void drawMapCanvas() {
        WorldMap.setFill(Color.LIGHTGREEN);
        WorldMap.fillRect(0, 0, field.getWidth() * squareSize, field.getHeight() * squareSize);

        for (Grass grass : field.getGrassMap().values()) {
            Vector2d position = grass.getPosition();
            WorldMap.setFill(Color.DARKGREEN);
            WorldMap.fillOval(position.getX() * squareSize, position.getY() * squareSize, squareSize, squareSize);
        }

        for (Vector2d position : field.getAnimalMap().keySet()) {
            WorldMap.setFill(Color.BROWN);
            WorldMap.fillOval(position.getX() * squareSize, position.getY() * squareSize, squareSize, squareSize);
        }
    }

    public void drawStats(){
    }



}