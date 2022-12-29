package gui;

import backPackage.*;

import javafx.event.ActionEvent;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.Scene;

import java.util.Map;
import java.util.PriorityQueue;


public class VisualizationApp implements IMapObserver {

    private Thread engineThread;
    private Field field;
    private ParameterHolder params;
    private int squareSize;
    private SimulationEngine engine;
    private Canvas mapCanvas;
    private GraphicsContext WorldMap;
    private VBox leftSide;
    private Text currentYear;
    private Text animalAmount;
    private Text grassAmount;
    private Text emptyLand;
    private Text avgEnergy;
    private Text avgLifeTime;
    private int buttonType;



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
        VBox stats = createStatBox();

        this.buttonType = 1;
        Button Stopbutton = new Button("Pause");
        Stopbutton.setLayoutX(100);
        Stopbutton.setLayoutY(50);
        Stopbutton.setOnAction((ActionEvent event) -> {
            //nie działa idk dlaczego
            if (buttonType == 1){
                engine.setPause(false);
                Stopbutton.setText("Resume");
                buttonType = 0;
            }else{
                engine.setPause(true);
                Stopbutton.setText("Pause");
                buttonType = 1;
            }});

        this.leftSide = new VBox(stats,Stopbutton);

    }

    private void run(Stage primaryStage) {
        primaryStage.setTitle("Simulation");
        VBox mapBox = new VBox();
        mapBox.getChildren().add(mapCanvas);
        mapBox.setLayoutX(1400 - mapCanvas.getWidth());
        mapBox.setAlignment(Pos.CENTER);

        Group root = new Group(leftSide,mapBox);
        Scene scene = new Scene(root, 1400, 750);
        engineThread.start();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void updateMap() {
        Platform.runLater(() -> {
            showMapCanvas();
            showStats();
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

    public VBox createStatBox(){

        Text year = new Text("Year : ");
        year.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        year.setTextAlignment(TextAlignment.CENTER);
        currentYear = new Text("-");
        currentYear.setTextAlignment(TextAlignment.CENTER);
        HBox yearBox = new HBox(year,currentYear);
        yearBox.setAlignment(Pos.CENTER);

        Text aAmount = new Text("Animals on the map : ");
        aAmount.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        aAmount.setTextAlignment(TextAlignment.CENTER);
        animalAmount = new Text("-");
        animalAmount.setTextAlignment(TextAlignment.CENTER);
        HBox animalBox = new HBox(aAmount,animalAmount);
        animalBox.setAlignment(Pos.CENTER);

        Text gAmount = new Text("Amount of grass on the map : ");
        gAmount.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        gAmount.setTextAlignment(TextAlignment.CENTER);
        grassAmount = new Text("-");
        grassAmount.setTextAlignment(TextAlignment.CENTER);
        HBox grassBox = new HBox(gAmount,grassAmount);
        grassBox.setAlignment(Pos.CENTER);

        Text freeSpace = new Text("Amount of empty fields : ");
        freeSpace.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        freeSpace.setTextAlignment(TextAlignment.CENTER);
        emptyLand = new Text("-");
        emptyLand.setTextAlignment(TextAlignment.CENTER);
        HBox landBox = new HBox(freeSpace,emptyLand);
        landBox.setAlignment(Pos.CENTER);

        Text avEnergy = new Text("Current average energy : ");
        avEnergy.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        avEnergy.setTextAlignment(TextAlignment.CENTER);
        avgEnergy = new Text("-");
        avgEnergy.setTextAlignment(TextAlignment.CENTER);
        HBox energyBox = new HBox(avEnergy,avgEnergy);
        energyBox.setAlignment(Pos.CENTER);

        Text avLifeTime = new Text("Current Average lifetime based on age of death : ");
        avLifeTime.setTextAlignment(TextAlignment.CENTER);
        avLifeTime.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        avgLifeTime = new Text("-");
        avgLifeTime.setTextAlignment(TextAlignment.CENTER);
        HBox lifeBox = new HBox(avLifeTime,avgLifeTime);
        lifeBox.setAlignment(Pos.CENTER);

        VBox stats = new VBox(yearBox,animalBox,grassBox,landBox,energyBox,lifeBox);
        stats.setLayoutX(80);

        return stats;

    }

//    public HBox createButtonBox(){
//
//    }

    private void showMapCanvas() {
        WorldMap.setFill(Color.LIGHTGREEN);
        WorldMap.fillRect(0, 0, field.getWidth() * squareSize, field.getHeight() * squareSize);

        for (Grass grass : field.getGrassMap().values()) {
            Vector2d position = grass.getPosition();
            WorldMap.setFill(Color.DARKGREEN);
            WorldMap.fillOval(position.getX() * squareSize, position.getY() * squareSize, squareSize, squareSize);
        }

        //Pokazywanie ile energi ma dane zwierzę

        for(Map.Entry<Vector2d, PriorityQueue<Animal>> entry : field.getAnimalMap().entrySet()){
            Vector2d position = entry.getKey();
            Animal animal = entry.getValue().peek();

            if (animal.getEnergy() / params.getDailyEnergyCost() >= 10){
                WorldMap.setFill(Color.WHITE);
                WorldMap.fillOval(position.getX() * squareSize, position.getY() * squareSize, squareSize, squareSize);
            }else if(animal.getEnergy() / params.getDailyEnergyCost() >= 7){
                WorldMap.setFill(Color.WHEAT);
                WorldMap.fillOval(position.getX() * squareSize, position.getY() * squareSize, squareSize, squareSize);
            }else if(animal.getEnergy() / params.getDailyEnergyCost() >= 5){
                WorldMap.setFill(Color.PERU);
                WorldMap.fillOval(position.getX() * squareSize, position.getY() * squareSize, squareSize, squareSize);
            }else if(animal.getEnergy() / params.getDailyEnergyCost() >= 2) {
                WorldMap.setFill(Color.SADDLEBROWN);
                WorldMap.fillOval(position.getX() * squareSize, position.getY() * squareSize, squareSize, squareSize);
            }else{
                WorldMap.setFill(Color.BLACK);
                WorldMap.fillOval(position.getX() * squareSize, position.getY() * squareSize, squareSize, squareSize);
            }

        }
    }

    public void showStats(){
        currentYear.setText(String.valueOf(engine.getCurrentYear()));
        currentYear.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));

        animalAmount.setText(String.valueOf(field.getAnimalMap().size()));
        animalAmount.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));

        grassAmount.setText(String.valueOf(field.getGrassMap().size()));
        grassAmount.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));

        emptyLand.setText(String.valueOf(engine.getFreeFields()));
        emptyLand.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));

        avgEnergy.setText(String.valueOf(engine.getAvgEnergy()));
        avgEnergy.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));

        avgLifeTime.setText(String.valueOf(field.getAvgLifetime()));
        avgLifeTime.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));

        //zostały najpopulariejsze genotypy do dodania


    }




}