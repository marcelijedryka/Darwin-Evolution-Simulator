package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.WindowEvent;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class App extends Application {

    private TextField mapHeight;
    private TextField mapWidth;
    private TextField grassAmount;
    private TextField grassEnergyBoost;
    private TextField dailyGrassGrowth;
    private TextField animalAmount;
    private TextField startingEnergy;
    private TextField minBreedEnergy;
    private TextField breedEnergyLoss;
    private TextField minMutation;
    private TextField maxMutation;
    private TextField genotypeLength;
    private TextField simulationTime;
    private TextField dailyEnergyCost;
    private TextField speed;
    private boolean saveCSV = false;
    private boolean savePreset = false;
    private ComboBox<String> mapVar;
    private ComboBox<String> behVar;
    private ComboBox<String> plantVar;
    private ComboBox<String> mutationVar;
    private ComboBox<String> presets;


    @Override
    public void start(Stage primaryStage) throws Exception {

        //dodatnie tego w takiej formie z jakiegoś powodu psuje symulację nwm dlaczego

        Text description = new Text("Please insert data required to start simulation or select preset");
        presets = loadPresets();

        presets.setOnAction(event -> {
            usePreset(presets.getValue().toString());
        });

        VBox headerBox = new VBox(description,presets);
        headerBox.setSpacing(10);
        headerBox.setAlignment(Pos.CENTER);

        Text mHeigth = new Text("Map Heigth:   ");
        mHeigth.setTextAlignment(TextAlignment.CENTER);
        mapHeight = new TextField();

        mapHeight.setMaxWidth(100);
        HBox mapH = new HBox(mHeigth, mapHeight);
        mapH.setAlignment(Pos.CENTER);

        Text mWidth = new Text("Map Width:   ");
        mWidth.setTextAlignment(TextAlignment.CENTER);
        mapWidth = new TextField();
        mapWidth.setMaxWidth(100);
        HBox mapW = new HBox(mWidth, mapWidth);
        mapW.setAlignment(Pos.CENTER);


        Text grassParam = new Text("Initial amount of plants:  ");
        grassParam.setTextAlignment(TextAlignment.CENTER);
        grassAmount = new TextField();
        grassAmount.setMaxWidth(100);
        HBox gSpawn = new HBox(grassParam, grassAmount);
        gSpawn.setAlignment(Pos.CENTER);

        Text grassEnergyParam = new Text("Energy that plants provide:   ");
        grassEnergyParam.setTextAlignment(TextAlignment.CENTER);
        grassEnergyBoost = new TextField();
        grassEnergyBoost.setMaxWidth(100);
        HBox gEnergy = new HBox(grassEnergyParam, grassEnergyBoost);
        gEnergy.setAlignment(Pos.CENTER);

        Text dailyGrassParam = new Text("Daily grass growth:   ");
        dailyGrassParam.setTextAlignment(TextAlignment.CENTER);
        dailyGrassGrowth = new TextField();
        dailyGrassGrowth.setMaxWidth(100);
        HBox gGrowth = new HBox(dailyGrassParam, dailyGrassGrowth);
        gGrowth.setAlignment(Pos.CENTER);

        Text animalParam = new Text("Starting amount of animals:   ");
        animalParam.setTextAlignment(TextAlignment.CENTER);
        animalAmount = new TextField();
        animalAmount.setMaxWidth(100);
        HBox aSpawn = new HBox(animalParam, animalAmount);
        aSpawn.setAlignment(Pos.CENTER);

        Text startingEnergyParam = new Text("Animal starting energy:   ");
        startingEnergyParam.setTextAlignment(TextAlignment.CENTER);
        startingEnergy = new TextField();
        startingEnergy.setMaxWidth(100);
        HBox eStart = new HBox(startingEnergyParam, startingEnergy);
        eStart.setAlignment(Pos.CENTER);

        Text readyToBreedParam = new Text("Energy required to breed:   ");
        readyToBreedParam.setTextAlignment(TextAlignment.CENTER);
        minBreedEnergy = new TextField();
        minBreedEnergy.setMaxWidth(100);
        HBox bEnergy = new HBox(readyToBreedParam, minBreedEnergy);
        bEnergy.setAlignment(Pos.CENTER);

        Text breedCostParam = new Text("Energy cost for breeding:   ");
        breedCostParam.setTextAlignment(TextAlignment.CENTER);
        breedEnergyLoss = new TextField();
        breedEnergyLoss.setMaxWidth(100);
        HBox bCost = new HBox(breedCostParam, breedEnergyLoss);
        bCost.setAlignment(Pos.CENTER);

        Text minoffspringMutationParam = new Text("Minimal amount of possible genotype mutations:   ");
        minoffspringMutationParam.setTextAlignment(TextAlignment.CENTER);
        minMutation = new TextField();
        minMutation.setMaxWidth(100);
        HBox minMut = new HBox(minoffspringMutationParam, minMutation);
        minMut.setAlignment(Pos.CENTER);

        Text maxoffspringMutationParam = new Text("Maximal amount of possible genotype mutations:   ");
        maxoffspringMutationParam.setTextAlignment(TextAlignment.CENTER);
        maxMutation = new TextField();
        maxMutation.setMaxWidth(100);
        HBox maxMut = new HBox(maxoffspringMutationParam, maxMutation);
        maxMut.setAlignment(Pos.CENTER);

        Text genotypeParam = new Text("Genotype length:   ");
        genotypeParam.setTextAlignment(TextAlignment.CENTER);
        genotypeLength = new TextField();
        genotypeLength.setMaxWidth(100);
        HBox genLen = new HBox(genotypeParam, genotypeLength);
        genLen.setAlignment(Pos.CENTER);

        Text timeParam = new Text("Simulation time:   ");
        timeParam.setTextAlignment(TextAlignment.CENTER);
        simulationTime = new TextField();
        simulationTime.setMaxWidth(100);
        HBox sTime = new HBox(timeParam, simulationTime);
        sTime.setAlignment(Pos.CENTER);

        Text dailyEcostParam = new Text("Daily energy cost:  ");
        dailyEcostParam.setTextAlignment(TextAlignment.CENTER);
        dailyEnergyCost = new TextField();
        dailyEnergyCost.setMaxWidth(100);
        HBox eCost = new HBox(dailyEcostParam, dailyEnergyCost);
        eCost.setAlignment(Pos.CENTER);

        Text programmeSpeed = new Text("Set time delay (ms) :  ");
        programmeSpeed.setTextAlignment(TextAlignment.CENTER);
        speed = new TextField();
        speed.setMaxWidth(100);
        HBox pSpeed = new HBox(programmeSpeed, speed);
        pSpeed.setAlignment(Pos.CENTER);

        CheckBox CSVtick = new CheckBox("Save data to CSV file");
        CSVtick.setOnAction(event -> {
            saveCSV = !saveCSV;
        });

        CheckBox presetSaver = new CheckBox("Save preset");
        presetSaver.setOnAction(event -> {
            savePreset = !savePreset;
        });

        HBox tickBox = new HBox(CSVtick , presetSaver);
        tickBox.setSpacing(10);
        tickBox.setAlignment(Pos.CENTER);

        VBox params = new VBox();
        params.getChildren().addAll(mapH, mapW, gSpawn, gEnergy, gGrowth, aSpawn, eStart,
                bEnergy, bCost, minMut, maxMut, genLen, sTime, eCost, pSpeed, tickBox);

        params.setAlignment(Pos.CENTER);
        params.setSpacing(5);

        Text mapVariant = new Text("Select map variant");
        mapVar = new ComboBox<String>();
        mapVar.getItems().addAll("Earth", "Hell Portal");
        VBox mapV = new VBox(mapVariant, mapVar);
        mapV.setAlignment(Pos.CENTER);


        Text behaviorVariant = new Text("Select behavior variant");
        behVar = new ComboBox<String>();
        behVar.getItems().addAll("Total Predestination", "A bit of craziness");
        VBox behaviorV = new VBox(behaviorVariant, behVar);
        behaviorV.setAlignment(Pos.CENTER);


        Text plantVariant = new Text("Select plant growth variant");
        plantVar = new ComboBox<String>();
        plantVar.getItems().addAll("Forested Equators", "Toxic Corpses");
        VBox plantV = new VBox(plantVariant, plantVar);
        plantV.setAlignment(Pos.CENTER);


        Text mutationVariant = new Text("Select mutation variant");
        mutationVar = new ComboBox<String>();
        mutationVar.getItems().addAll("Fully Randomized", "Slight Correction");
        VBox mutV = new VBox(mutationVariant, mutationVar);
        mutV.setAlignment(Pos.CENTER);

        HBox variants = new HBox(mapV, behaviorV, plantV, mutV);
        variants.setAlignment(Pos.CENTER);
        variants.setSpacing(5);

        Button startButton = new Button("Start Simulation");
        startButton.setOnAction(event -> {
            try {
                ParameterHolder x = getParams();
                if(!x.checkParameters()){
                    Stage errorDataStage = new Stage();
                    errorDataStage.setTitle("INCORRECT DATA ERROR");
                    Text errorText = new Text("You have inserted incorrect data:");
                    Text error = new Text(x.getErrorString());
                    errorText.setTextAlignment(TextAlignment.CENTER);
                    error.setTextAlignment(TextAlignment.CENTER);
                    errorText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
                    error.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

                    VBox errorCommunicate = new VBox(errorText, error);
                    errorCommunicate.setAlignment(Pos.CENTER);
                    Scene scene = new Scene(errorCommunicate, 900, 100);
                    errorDataStage.setScene(scene);
                    errorDataStage.show();
                }
                else {
                    if (savePreset){
                        createNewPreset(x);
                    }
                    VisualizationApp visualizer = new VisualizationApp(x);
                    visualizer.start(new Stage());
                }

            }catch (NumberFormatException  e){
                Stage errorStage = new Stage();

                errorStage.setTitle("INCORRECT TYPE ERROR");
                Text errorText = new Text("IT IS ONLY ALLOWED TO USE INT\nCHECK IF ALL WINDOWS HAVE CORRECT VALUES\nPROGRAM WON'T WORK UNLESS YOU INSERT CORRECT DATA");
                errorText.setTextAlignment(TextAlignment.CENTER);
                errorText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));

                VBox errorCommunicate = new VBox(errorText);
                errorCommunicate.setAlignment(Pos.CENTER);
                Scene scene = new Scene(errorCommunicate, 700, 100);
                errorStage.setScene(scene);
                errorStage.show();
            }


        });

        VBox buttons = new VBox(startButton);
        buttons.setAlignment(Pos.CENTER);


        VBox root = new VBox(headerBox, params, variants, buttons);
        root.setSpacing(15);
        Scene ParamScreen = new Scene(root, 600, 650, Color.LIGHTGREEN);
        primaryStage.setScene(ParamScreen);
        primaryStage.setTitle("Evolution Simulator");
        primaryStage.getIcons().add(new Image(new FileInputStream("src/main/resources/EvolutionIcon.png")));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });
        primaryStage.show();

    }

    public ParameterHolder getParams() {
        return new ParameterHolder(
                Integer.parseInt(mapHeight.getText()),
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
                readVariantBox(mapVar),
                readVariantBox(behVar),
                readVariantBox(plantVar),
                readVariantBox(mutationVar), saveCSV);
    }

    public int readVariantBox(ComboBox<String> box) {
        if (box.getSelectionModel().isSelected(0)) {
            return 0;
        }
        return 1;
    }

    public void setComboBoxValue(ComboBox<String> box, int index) {
        box.getSelectionModel().select(index);
    }

    public ComboBox<String> loadPresets() {
        ComboBox<String> loaded = new ComboBox<String>();
        List<String> psets = Stream.of(Objects.requireNonNull(new File("./src/main/resources/SimulationPresets").listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName).toList();
        for (String preset : psets) {
            loaded.getItems().add(preset);
        }
        return loaded;
    }

    public void createNewPreset(ParameterHolder params){

        try {

            int id =  (Objects.requireNonNull(new File("./src/main/resources/SimulationPresets/").list()).length + 1);

            BufferedWriter writer = new BufferedWriter(new FileWriter("./src/main/resources/SimulationPresets/" + "Preset"+ Integer.toString(id) + ".txt"));

            writer.write(params.getMapHeight()+ "\n");
            writer.write(params.getMapWidth()+ "\n");
            writer.write(params.getGrassAmount()+ "\n");
            writer.write(params.getGrassEnergyBoost()+ "\n");
            writer.write(params.getDailyGrassGrowth()+ "\n");
            writer.write(params.getAnimalAmount()+ "\n");
            writer.write(params.getStartingEnergy()+ "\n");
            writer.write(params.getMinBreedEnergy()+ "\n");
            writer.write(params.getBreedEnergyLoss()+ "\n");
            writer.write(params.getMinMutation()+ "\n");
            writer.write(params.getMaxMutation()+ "\n");
            writer.write(params.getGenotypeLength()+ "\n");
            writer.write(params.getSimulationTime()+ "\n");
            writer.write(params.getDailyEnergyCost()+ "\n");
            writer.write(params.getSpeed()+ "\n");
            writer.write(params.getMapVar()+ "\n");
            writer.write(params.getBehVar()+ "\n");
            writer.write(params.getPlantVar()+ "\n");
            writer.write(params.getMutationVar()+ "\n");
            writer.close();

            updatePresets();

        }catch (IOException e) {
            System.out.print(e);
        }


    }

    public void updatePresets(){
      presets.getItems().clear();
      List<String> psets = Stream.of(Objects.requireNonNull(new File("./src/main/resources/SimulationPresets").listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName).toList();
      for (String preset : psets) {
            presets.getItems().add(preset);
        }
    }

    public void usePreset(String file) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/SimulationPresets/" + file));

            mapHeight.setText(reader.readLine());
            mapWidth.setText(reader.readLine());
            grassAmount.setText(reader.readLine());
            grassEnergyBoost.setText(reader.readLine());
            dailyGrassGrowth.setText(reader.readLine());
            animalAmount.setText(reader.readLine());
            startingEnergy.setText(reader.readLine());
            minBreedEnergy.setText(reader.readLine());
            breedEnergyLoss.setText(reader.readLine());
            minMutation.setText(reader.readLine());
            maxMutation.setText(reader.readLine());
            genotypeLength.setText(reader.readLine());
            simulationTime.setText(reader.readLine());
            dailyEnergyCost.setText(reader.readLine());
            speed.setText(reader.readLine());

            int mVar = Integer.parseInt(reader.readLine());
            setComboBoxValue(mapVar, mVar);

            int bVar = Integer.parseInt(reader.readLine());
            setComboBoxValue(behVar, bVar);

            int pVar = Integer.parseInt(reader.readLine());
            setComboBoxValue(plantVar, pVar);

            int mutVar = Integer.parseInt(reader.readLine());
            setComboBoxValue(mutationVar, mutVar);

        } catch (IOException e) {
            System.out.println(e);
        }
    }



}
