package backPackage;
import gui.App;
import javafx.application.Application;

import static java.lang.System.out;
public class World {

    public static void main(String[] args){
        /*
        Mutacja:
            0 - pełna losowość
            1 - lekka korekta
        Zachowanie:
            0 - pełna predestynacja
            1 - nieco szaleństwa
        Trawa:
            0 - zalesione równiki
            1 = !brak!
        Mapa:
            0 - kula ziemska
            1 - piekielny portal
        */

//        Field field = new Field(20, 20 , 100 , 5, 15,1 ,
//                0 , 5, 1 ,60 , 50, 0,
//                0);
//        IEngine engine = new SimulationEngine(field,300,25,50,5,10);
//        engine.run();
        Application.launch(App.class);

    }



}
