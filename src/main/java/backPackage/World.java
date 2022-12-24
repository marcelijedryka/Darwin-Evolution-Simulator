package backPackage;
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

        Krańce mapy i wzrost roślinności do przedyskutowania jak rozwiązać
        */

        Field field = new Field(4,4 , 4 , 2 , 15,1 , 0 , 5,
                1 ,2 , 1);
        IEngine engine = new SimulationEngine(field,100,4,10,1,10 );
        engine.run();
    }



}
