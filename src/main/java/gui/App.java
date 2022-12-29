package gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.io.FileInputStream;

public class App extends Application {

    private TextField mapHeight;
    private TextField mapWidth;
    private TextField grassAmount;
    private TextField grassEnergyBoost;
    private TextField dailyGrassGrowth;
    private TextField  animalAmount;
    private TextField  startingEnergy;
    private TextField  minBreedEnergy;
    private TextField   breedEnergyLoss;
    private TextField  minMutation;
    private TextField maxMutation;
    private TextField genotypeLength;
    private TextField simulationTime;
    private TextField dailyEnergyCost;

    private TextField speed;
    private ComboBox<String> mapVar;
    private ComboBox<String> behVar;
    private ComboBox<String> plantVar;
    private ComboBox<String> mutationVar;


    @Override
    public void start(Stage primaryStage) throws Exception {

        Text description = new Text("Please insert data required to start simulation");


        Text mHeigth = new Text("Map Heigth:   ");
        mHeigth.setTextAlignment(TextAlignment.CENTER);
        mapHeight = new TextField();

        mapHeight.setMaxWidth(100);
        HBox mapH = new HBox(mHeigth , mapHeight);
        mapH.setAlignment(Pos.CENTER);

        Text mWidth = new Text("Map Width:   ");
        mWidth.setTextAlignment(TextAlignment.CENTER);
        mapWidth = new TextField();
        mapWidth.setMaxWidth(100);
        HBox mapW = new HBox(mWidth , mapWidth);
        mapW.setAlignment(Pos.CENTER);


        Text grassParam = new Text("Initial amount of plants:  ");
        grassParam.setTextAlignment(TextAlignment.CENTER);
        grassAmount = new TextField();
        grassAmount.setMaxWidth(100);
        HBox gSpawn = new HBox(grassParam,grassAmount);
        gSpawn.setAlignment(Pos.CENTER);

        Text grassEnergyParam = new Text("Energy that plants provide:   ");
        grassEnergyParam.setTextAlignment(TextAlignment.CENTER);
        grassEnergyBoost = new TextField();
        grassEnergyBoost.setMaxWidth(100);
        HBox gEnergy = new HBox(grassEnergyParam , grassEnergyBoost);
        gEnergy.setAlignment(Pos.CENTER);

        Text dailyGrassParam = new Text("Daily grass growth:   ");
        dailyGrassParam.setTextAlignment(TextAlignment.CENTER);
        dailyGrassGrowth = new TextField();
        dailyGrassGrowth.setMaxWidth(100);
        HBox gGrowth = new HBox(dailyGrassParam , dailyGrassGrowth);
        gGrowth.setAlignment(Pos.CENTER);

        Text animalParam = new Text("Starting amount of animals:   ");
        animalParam.setTextAlignment(TextAlignment.CENTER);
        animalAmount = new TextField();
        animalAmount.setMaxWidth(100);
        HBox aSpawn = new HBox(animalParam , animalAmount);
        aSpawn.setAlignment(Pos.CENTER);

        Text startingEnergyParam = new Text("Animal starting energy:   ");
        startingEnergyParam.setTextAlignment(TextAlignment.CENTER);
        startingEnergy = new TextField();
        startingEnergy.setMaxWidth(100);
        HBox eStart = new HBox(startingEnergyParam , startingEnergy);
        eStart.setAlignment(Pos.CENTER);

        Text readyToBreedParam = new Text("Energy required to breed:   ");
        readyToBreedParam.setTextAlignment(TextAlignment.CENTER);
        minBreedEnergy = new TextField();
        minBreedEnergy.setMaxWidth(100);
        HBox bEnergy = new HBox(readyToBreedParam , minBreedEnergy);
        bEnergy.setAlignment(Pos.CENTER);

        Text breedCostParam = new Text("Energy cost for breeding:   ");
        breedCostParam.setTextAlignment(TextAlignment.CENTER);
        breedEnergyLoss = new TextField();
        breedEnergyLoss.setMaxWidth(100);
        HBox bCost = new HBox(breedCostParam , breedEnergyLoss);
        bCost.setAlignment(Pos.CENTER);

        Text minoffspringMutationParam = new Text("Minimal amount of possible genotype mutations:   ");
        minoffspringMutationParam.setTextAlignment(TextAlignment.CENTER);
        minMutation = new TextField();
        minMutation.setMaxWidth(100);
        HBox minMut = new HBox(minoffspringMutationParam , minMutation);
        minMut.setAlignment(Pos.CENTER);

        Text maxoffspringMutationParam = new Text("Maximal amount of possible genotype mutations:   ");
        maxoffspringMutationParam.setTextAlignment(TextAlignment.CENTER);
        maxMutation = new TextField();
        maxMutation.setMaxWidth(100);
        HBox maxMut = new HBox(maxoffspringMutationParam , maxMutation);
        maxMut.setAlignment(Pos.CENTER);

       Text genotypeParam = new Text("Genotype length:   ");
       genotypeParam.setTextAlignment(TextAlignment.CENTER);
       genotypeLength = new TextField();
       genotypeLength.setMaxWidth(100);
       HBox genLen = new HBox(genotypeParam , genotypeLength);
       genLen.setAlignment(Pos.CENTER);

       Text timeParam = new Text("Simulation time:   ");
       timeParam.setTextAlignment(TextAlignment.CENTER);
       simulationTime = new TextField();
       simulationTime.setMaxWidth(100);
       HBox sTime = new HBox(timeParam , simulationTime);
       sTime.setAlignment(Pos.CENTER);

        Text dailyEcostParam = new Text("Daily energy cost:  ");
        dailyEcostParam.setTextAlignment(TextAlignment.CENTER);
        dailyEnergyCost = new TextField();
        dailyEnergyCost.setMaxWidth(100);
        HBox eCost = new HBox(dailyEcostParam,dailyEnergyCost);
        eCost.setAlignment(Pos.CENTER);

        Text programmeSpeed = new Text("Set time delay:  ");
        programmeSpeed.setTextAlignment(TextAlignment.CENTER);
        speed = new TextField();
        speed.setMaxWidth(100);
        HBox pSpeed = new HBox(programmeSpeed,speed);
        pSpeed.setAlignment(Pos.CENTER);

        VBox params = new VBox();
        params.getChildren().addAll(description,mapH , mapW ,gSpawn , gEnergy , gGrowth , aSpawn, eStart ,
                bEnergy , bCost, minMut ,maxMut , genLen , sTime , eCost, pSpeed);

        params.setAlignment(Pos.CENTER);

        Text mapVariant = new Text("Select map variant");
        mapVar = new ComboBox<String>();
        mapVar.getItems().addAll("Earth" , "Hell Portal");
        VBox mapV = new VBox(mapVariant , mapVar);
        mapV.setAlignment(Pos.CENTER);


        Text behaviorVariant = new Text("Select behavior variant");
        behVar = new ComboBox<String>();
        behVar.getItems().addAll("Total Predestination" , "A bit of craziness");
        VBox behaviorV = new VBox(behaviorVariant , behVar);
        behaviorV.setAlignment(Pos.CENTER);


        Text plantVariant = new Text("Select plant growth variant");
        plantVar = new ComboBox<String>();
        plantVar.getItems().addAll("Forested Equators" , "Toxic Corpses");
        VBox plantV = new VBox(plantVariant , plantVar);
        plantV.setAlignment(Pos.CENTER);


        Text mutationVariant = new Text("Select mutation variant");
        mutationVar = new ComboBox<String>();
        mutationVar.getItems().addAll("Fully Randomized" , "Slight Correction");
        VBox mutV = new VBox(mutationVariant , mutationVar);
        mutV.setAlignment(Pos.CENTER);

        HBox variants = new HBox(mapV,behaviorV,plantV,mutV);
        variants.setAlignment(Pos.CENTER);

        Button startButton = new Button("Start Simulation");
        startButton.setOnAction(event -> {
            try {
                ParameterHolder x = getParams();
                System.out.println(x);
                VisualizationApp visualizer = new VisualizationApp(x);
                visualizer.start(new Stage());
            }catch(Exception e){
                System.out.println(e);
            }

        });

        VBox buttons = new VBox(startButton);
        buttons.setAlignment(Pos.CENTER);

        VBox root = new VBox(params , variants , buttons);
        Scene ParamScreen = new Scene(root , 600 , 600, Color.LIGHTGREEN);
        primaryStage.setScene(ParamScreen);
        primaryStage.setTitle("Evolution Simulator");
        primaryStage.getIcons().add(new Image(new FileInputStream("src/main/resources/EvolutionIcon.png")));
        primaryStage.show();

    }

    public ParameterHolder getParams(){
        return new ParameterHolder(Integer.parseInt(mapHeight.getText()),
                Integer.parseInt(mapWidth.getText()),
                Integer.parseInt(grassAmount.getText()),
                Integer.parseInt(grassEnergyBoost.getText()),
                Integer.parseInt(dailyGrassGrowth.getText()),
                Integer.parseInt(animalAmount.getText()),
                Integer.parseInt(startingEnergy.getText()),
                Integer.parseInt(minBreedEnergy.getText()),
                Integer.parseInt(breedEnergyLoss.getText()),
                Integer.parseInt(minMutation.getText()),
                Integer.parseInt(maxMutation.getText()),
                Integer.parseInt(genotypeLength.getText()),
                Integer.parseInt(simulationTime.getText()),
                Integer.parseInt(dailyEnergyCost.getText()),
                Integer.parseInt(speed.getText()),
                readComboBox(mapVar),
                readComboBox(behVar),
                readComboBox(plantVar),
                readComboBox(mutationVar));
    }
    public int readComboBox(ComboBox<String> box){
        if (box.getSelectionModel().isSelected(0)){
            return 0;
        }
        return 1;
    }



}
