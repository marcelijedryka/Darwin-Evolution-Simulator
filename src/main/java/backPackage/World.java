package backPackage;
import static java.lang.System.out;
public class World {

    public static int wide = 10;
    public static int height =10;

    public static void main(String[] args){
        IWorldMap field = new Field(height, wide , 10 , 1 , 5);
        IEngine engine = new SimulationEngine(field,10,2,9,1,4);
        engine.run();
    }



}
