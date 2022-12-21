package backPackage;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Field implements IWorldMap {

    private final Map<Vector2d , Object> map = new HashMap<>();

    private final Vector2d lowLeft;

    private final Vector2d upRight;

    private final int width;

    private final int height;

    public Field(int height , int width){
        this.width = width;
        this.height = height;
        this.lowLeft = new Vector2d(0,0);
        this.upRight = new Vector2d(height-1,width-1);

    }

    public Map<Vector2d, Object> getMap() {
        return map;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Vector2d getLowLeft() {
        return lowLeft;
    }

    public Vector2d getUpRight() {
        return upRight;
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
//        if (map.containsKey(position)) {
//            if (map.get(position) instanceof Grass) {
//                generateNewGrass((Grass) map.get(position));
//                return true;
//            }
//            //po
//            return false;
//        }
        return true;
    }

    @Override
    public void Randomplace(Animal animal) {
        Random roll = new Random();
        Vector2d position = new Vector2d(roll.nextInt(width) , roll.nextInt(height));
        animal.setCurrentPos(position);
        map.put(position , animal);
    }

    public void ParentPlace(Animal child ,Animal parent){
        child.setCurrentPos(parent.getCurrentPos());
        map.put(child.getCurrentPos() , child);
    }

    public boolean isOccupied(Vector2d position) {
        return map.containsKey(position);
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (isOccupied(position)){
            return map.get(position);
        }
        return null;
    }

    public String toString() {
        MapVisualizer draw = new MapVisualizer(this);
        return draw.draw(lowLeft,upRight);
    }

    public Vector2d rollPosition(){
        Random roll = new Random();
        return new Vector2d(roll.nextInt(height-1) , roll.nextInt(width -1));
    }

}
