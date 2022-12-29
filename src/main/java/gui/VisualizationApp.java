package gui;

import backPackage.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
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
    private final Field field;
    private final ParameterHolder params;
    private int squareSize;
    private final SimulationEngine engine;
    private Canvas space;
    private GraphicsContext spaceVisualisation;
    private VBox leftSide;
    private Text currentYear;
    private Text animalAmount;
    private Text grassAmount;
    private Text emptyLand;
    private Text avgEnergy;
    private Text avgLifeTime;
    private Text mostPopularGenotype;
    private int buttonType;

    private int windowWidth = 1400;
    private int windowHeight = 750;

    private Text genome;
    private Text energyLevel;
    private Text consumptionAmount;
    private Text offspringAmount;
    private Text age;

    private Text trakcedHead;
    private Text genomeText;
    private Text energyLevelText;
    private Text consumedText;
    private Text chlidrenText;
    private Text ageText;

    private Animal selected = null;

    public VisualizationApp(ParameterHolder params){
        this.params = params;
        field = new Field(params.getMapHeight(), params.getMapWidth(), params.getGrassAmount(), params.getDailyGrassGrowth(),
                params.getGrassEnergyBoost() , params.getMutationVar() , params.getMinMutation(), params.getMaxMutation(),
                params.getBehVar(), params.getMinBreedEnergy(), params.getBreedEnergyLoss(), params.getMapVar(), params.getPlantVar());
        field.addMapObserver(this);
        engine = new SimulationEngine(field,params.getSimulationTime() , params.getAnimalAmount(), params.getStartingEnergy(),
                params.getDailyEnergyCost() , params.getGenotypeLength(), params.getSpeed());
    }


    public void start (Stage primaryStage){
        initialize();
        run(primaryStage);
    }

    private void initialize(){
        engineThread = new Thread(engine);
        this.squareSize = calculateSquareSize(field);
        space = new Canvas();
        space.setWidth(field.getWidth() * squareSize);
        space.setHeight(field.getHeight() * squareSize);
        spaceVisualisation = space.getGraphicsContext2D();
        VBox stats = createStatBox();
        VBox selectedAnimalBox = initBoxForSelectedAnimal();

        this.buttonType = 1;
        Button Stopbutton = new Button("Pause");
        Stopbutton.setLayoutX(400);
        Stopbutton.setLayoutY(100);
        Stopbutton.setOnAction((ActionEvent event) -> {
            if (buttonType == 1){
                engine.setPause(false);
                Stopbutton.setText("Resume");
                buttonType = 0;
            }else{
                engine.setPause(true);
                Stopbutton.setText("Pause");
                buttonType = 1;
            }});

        this.leftSide = new VBox(stats,Stopbutton, selectedAnimalBox);


    }

    private void run(Stage primaryStage) {
        primaryStage.setTitle("Simulation");
        VBox canvasBox = new VBox();
        canvasBox.getChildren().add(space);
        canvasBox.setLayoutX(windowWidth - space.getWidth());
        canvasBox.setAlignment(Pos.CENTER);

        EventHandler<MouseEvent> mouseHit = event -> {
            Vector2d position = new Vector2d((int)(event.getX()/squareSize), (int) event.getY()/squareSize);
            Object thing =  field.objectAt(position);
            if (thing instanceof Animal) {
                selected = (Animal) thing;
                updateBoxForSelectedAnimal((Animal) thing);
                spaceVisualisation.setFill(Color.RED);
                spaceVisualisation.fillOval(position.getX() * squareSize, position.getY() * squareSize, squareSize, squareSize);
            }
        };
        space.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseHit);

        Group root = new Group(leftSide, canvasBox);
        Scene scene = new Scene(root, windowWidth, windowHeight);
        engineThread.start();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void updateMap() {
        Platform.runLater(() -> {
            showMapCanvas();
            showStats();
            if (selected != null) {updateBoxForSelectedAnimal(selected);}
        });
    }

    private int calculateSquareSize(Field field) {
        int height = field.getHeight();
        int width = field.getWidth();
        if (width > height) {
            squareSize = (int) (0.7 * windowWidth) / width;
        } else {
            squareSize = windowHeight / height;
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

        Text avLifeTime = new Text("Current Average lifetime  \nbased on age of death:  ");
        avLifeTime.setTextAlignment(TextAlignment.CENTER);
        avLifeTime.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        avgLifeTime = new Text("-");
        avgLifeTime.setTextAlignment(TextAlignment.CENTER);
        HBox lifeBox = new HBox(avLifeTime,avgLifeTime);
        lifeBox.setAlignment(Pos.CENTER);

        Text mostPopGenome = new Text("Most popular genome: ");
        mostPopGenome.setTextAlignment(TextAlignment.CENTER);
        mostPopGenome.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        mostPopularGenotype = new Text("-");
        mostPopularGenotype.setTextAlignment(TextAlignment.CENTER);
        HBox genomeTextBox = new HBox(mostPopGenome);
        genomeTextBox.setAlignment(Pos.CENTER);
        HBox genomeBox = new HBox(mostPopularGenotype);
        genomeBox.setAlignment(Pos.CENTER);

        VBox stats = new VBox(yearBox,animalBox,grassBox,landBox,energyBox,lifeBox, genomeTextBox ,genomeBox);
        stats.setLayoutX(80);

        return stats;

    }


    private void showMapCanvas() {
        spaceVisualisation.setFill(Color.LIGHTGREEN);
        spaceVisualisation.fillRect(0, 0, field.getWidth() * squareSize, field.getHeight() * squareSize);

        for (Grass grass : field.getGrassMap().values()) {
            Vector2d position = grass.getPosition();
            spaceVisualisation.setFill(Color.DARKGREEN);
            spaceVisualisation.fillOval(position.getX() * squareSize, position.getY() * squareSize, squareSize, squareSize);
        }

        //Pokazywanie ile energi ma dane zwierzę

        for(Map.Entry<Vector2d, PriorityQueue<Animal>> entry : field.getAnimalMap().entrySet()){
            Vector2d position = entry.getKey();
            Animal animal = entry.getValue().peek();


            if(animal != null && animal.equals(selected)){
                spaceVisualisation.setFill(Color.RED);
                spaceVisualisation.fillOval(position.getX() * squareSize, position.getY() * squareSize, squareSize, squareSize);
            }

            else if (animal.getEnergy() / params.getDailyEnergyCost() >= 10){
                spaceVisualisation.setFill(Color.WHITE);
                spaceVisualisation.fillOval(position.getX() * squareSize, position.getY() * squareSize, squareSize, squareSize);
            }else if(animal.getEnergy() / params.getDailyEnergyCost() >= 7){
                spaceVisualisation.setFill(Color.WHEAT);
                spaceVisualisation.fillOval(position.getX() * squareSize, position.getY() * squareSize, squareSize, squareSize);
            }else if(animal.getEnergy() / params.getDailyEnergyCost() >= 5){
                spaceVisualisation.setFill(Color.PERU);
                spaceVisualisation.fillOval(position.getX() * squareSize, position.getY() * squareSize, squareSize, squareSize);
            }else if(animal.getEnergy() / params.getDailyEnergyCost() >= 2) {
                spaceVisualisation.setFill(Color.SADDLEBROWN);
                spaceVisualisation.fillOval(position.getX() * squareSize, position.getY() * squareSize, squareSize, squareSize);
            }else{
                spaceVisualisation.setFill(Color.BLACK);
                spaceVisualisation.fillOval(position.getX() * squareSize, position.getY() * squareSize, squareSize, squareSize);
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

        mostPopularGenotype.setText(String.valueOf(engine.getBestGenes()));
        mostPopularGenotype.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));


    }

    private VBox initBoxForSelectedAnimal(){
        trakcedHead = new Text("Tracked animal parameters: ");
        trakcedHead.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        genomeText = new Text("Genome:");
        genomeText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        genome = new Text("None");
        genome.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        HBox genomeTextBox = new HBox(genomeText, genome);
        energyLevelText = new Text("Energy level: ");
        energyLevelText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        energyLevel = new Text("None");
        energyLevel.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        HBox energyLevelBox = new HBox(energyLevelText, energyLevel);
        consumedText = new Text("Amount of consumed grass: ");
        consumedText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        consumptionAmount = new Text("None");
        consumptionAmount.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        HBox consumptionBox = new HBox(consumedText, consumptionAmount);
        chlidrenText = new Text("Amount of children: ");
        chlidrenText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        offspringAmount = new Text("None");
        offspringAmount.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        HBox childrenBox = new HBox(chlidrenText, offspringAmount);
        ageText = new Text("Age of animal: ");
        ageText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        age = new Text("None");
        age.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 20));
        HBox ageBox = new HBox(ageText, age);


        return new VBox(genomeTextBox, energyLevelBox, consumptionBox, childrenBox, ageBox);
    }

    private void updateBoxForSelectedAnimal(Animal animal) {
        genome.setText(String.valueOf(animal.getGenes()));
        energyLevel.setText(String.valueOf(animal.getEnergy()));
        consumptionAmount.setText(String.valueOf(animal.getEatenGrass()));
        offspringAmount.setText(String.valueOf(animal.getOffspringAmount()));
        age.setText(String.valueOf(animal.getLifeTime()));

    }




}