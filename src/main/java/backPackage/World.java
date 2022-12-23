package backPackage;
import static java.lang.System.out;
public class World {

    public static int wide = 10;
    public static int height = 10;

    public static void main(String[] args){
        /*
        Mutacja:
            0 - pełna losowość
            1 - lekka korekta
        Zachowanie:
            0 - pełna predestynacja
            1 - nieco szaleństwa

        Krańce mapy i wzrost roślinności do przedyskutowania jak rozwiązać
        */

        Field field = new Field(height, wide , 100 , 1 , 5,0 , 0 , 5,
                1);
        IEngine engine = new SimulationEngine(field,100,2,9,0,4 );
        engine.run();
    }



}
