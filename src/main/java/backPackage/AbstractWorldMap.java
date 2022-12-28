package backPackage;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{

    protected HashMap<Vector2d , PriorityQueue<Animal>> animalMap;
    protected HashMap<Vector2d , Grass> grassMap;


    @Override
    public void positionChanged(Animal ani, Vector2d oldPosition, Vector2d newPosition){
        removeAnimal(ani);
        insertAnimal(ani, newPosition);
    }
    @Override
    abstract public boolean canMoveTo(Vector2d position);
    @Override
    abstract public void randomPlace(Animal animal);

    @Override
    abstract public boolean isOccupied(Vector2d position);

    @Override
    abstract public Object objectAt(Vector2d position);

    @Override
    abstract public Vector2d getLowLeft();

    @Override
    abstract public Vector2d getUpRight();

    @Override
    abstract public int eatGrass(Vector2d position);

    @Override
    abstract public void removeAnimal(Animal animal);

    @Override
    public void insertAnimal(Animal animal, Vector2d position){
        if (!isOccupiedByAnimal(position)){
            Comparator<Animal> cmp = new AnimalComparator();
            animalMap.put(position, new PriorityQueue<>(cmp));
        }
        animalMap.get(position).add(animal);
    }

    public boolean isOccupiedByAnimal(Vector2d position) {
        return animalMap.containsKey(position);
    }
    public boolean isOccupiedByGrass(Vector2d position) {
        return grassMap.containsKey(position);
    }



    abstract public String toString();

}
