package backPackage;
import static java.lang.System.out;
public class World {

    public static int wide = 5;
    public static int height = 5;

    public static void main(String[] args){
        IWorldMap field = new Field(height, wide);
        MapVisualizer zmienna = new MapVisualizer(field);
        out.println(field);
    }



}
