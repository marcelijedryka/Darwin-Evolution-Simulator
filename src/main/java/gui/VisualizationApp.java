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
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class VisualizationApp extends Application{






    private Field field;

    @Override
    public void init(){
        field = new Field(20, 20 , 100 , 5, 15,1 ,
        0 , 5, 1 ,60 , 50, 0,
        0);
        IEngine engine = new SimulationEngine(field,300,25,50,5,10);
        engine.run();
    }

    public void start(Stage primaryStage){
        primaryStage.setTitle("New visualisation");
        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);
        Scene scene = new Scene(gridPane, 600, 600);
        drawMap(gridPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void drawMap(GridPane gridPane){

        Text title = new Text("y/x");
        gridPane.add(title, 0, 0, 1, 1);
        GridPane.setHalignment(title, HPos.CENTER);
        gridPane.getColumnConstraints().add(new ColumnConstraints(30));
        gridPane.getRowConstraints().add(new RowConstraints(30));

        Vector2d lowL= new Vector2d(0,0);
        Vector2d upR = new Vector2d(field.getWidth(), field.getHeight());


//      Cols
        for (int i = 1; i <= upR.getX() - lowL.getX() +1; i++){
            Label label = new Label(Integer.toString(lowL.getX() + i -1));
            gridPane.add(label, i, 0, 1, 1);
            gridPane.getColumnConstraints().add(new ColumnConstraints(40));
            GridPane.setHalignment(label, HPos.CENTER);
        }
//      Rows
        for (int i = 1; i <= upR.getY() - lowL.getY() +1; i++){
            Label label = new Label(Integer.toString(lowL.getY() + i -1));
            gridPane.add(label, 0, upR.getY() - lowL.getY() +2 - i, 1, 1);
            gridPane.getRowConstraints().add(new RowConstraints(40));
            GridPane.setHalignment(label, HPos.CENTER);
        }

        for (int i = 1; i <= upR.getX() - lowL.getX() +1; i++){
            for(int j = 1; j <= upR.getY() - lowL.getY() +1; j++){
                Label label = new Label(getThing(new Vector2d(lowL.getX() + i - 1, upR.getY() -j + 1)));
                gridPane.add(label, i, j, 1, 1);
                GridPane.setHalignment(label, HPos.CENTER);
            }
        }
    }

    private String getThing(Vector2d currentPosition) {
        String result = null;
        if (this.field.isOccupied(currentPosition)) {
            Object object = this.field.objectAt(currentPosition);
            if (object != null) {
                result = object.toString();
            } else {
                result = "";
            }
        } else {
            result = "";
        }
        return result;

    }


}