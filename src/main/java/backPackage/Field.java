package backPackage;

import java.util.*;

public class Field extends AbstractWorldMap {


    private final Vector2d lowLeft;

    private final Vector2d upRight;

    private final int energyboost;

    // ilość nowej trawy w każdym ruchu
    private final int growth;

    private final int height;
    private final int width;
    private final int minMutation;
    private final int maxMutation;
    private final int GenotypeVariant;
    private final int AnimalMoveVariant;

    public Field(int height , int width , int grassAmount , int growth , int energyBoost,
                 int GenotypeVariant , int minMutation , int maxMutation , int AnimalMoveVariant){
        this.GenotypeVariant = GenotypeVariant;
        this.width = width;
        this.height = height;
        this.lowLeft = new Vector2d(0,0);
        this.upRight = new Vector2d(height-1,width-1);
        this.growth = growth;
        this.energyboost = energyBoost;
        super.animalMap = new HashMap<>();
        super.grassMap = new HashMap<>();
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;
        this.AnimalMoveVariant = AnimalMoveVariant;
        // Generowanie Trawy

        Random roll = new Random();
        while (grassMap.size() < grassAmount){
            Vector2d position = new Vector2d(roll.nextInt(width), roll.nextInt(height));
            if (!isOccupiedByGrass(position)){
                grassMap.put(position , new Grass(position));
            }
        }

    }

    public void GenerateNewGrass(){
        Random roll = new Random();
        int i = 0;
        while (i < growth){
            Vector2d position = new Vector2d(roll.nextInt(width), roll.nextInt(height));
            if (!isOccupiedByGrass(position)){
                grassMap.put(position , new Grass(position));
                i++;
        }
        }
    }

    public int getAnimalMoveVariant() {
        return AnimalMoveVariant;
    }

    public int getMinMutation() {
        return minMutation;
    }

    public int getMaxMutation() {
        return maxMutation;
    }

    public Map<Vector2d, Object> getGrassMap() {
        return grassMap;
    }

    public Map<Vector2d, PriorityQueue<Animal>> getAnimalMap() {
        return animalMap;
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
        return position.getX() > 0 && position.getX() < this.width - 1 && position.getY() > 0 &&  position.getY() < this.height - 1 ;
    }


    @Override
    public void randomPlace(Animal animal) {
        Random roll = new Random();
        Vector2d position = new Vector2d(roll.nextInt(width ) , roll.nextInt(height ));
        animal.setCurrentPos(position);
        /*
        * Jeśli nie ma zwierzaka na danej pozycji, to tworzę TreeSeta dla klucza o tej pozycji
        * Niezależnie od tego if'a, potem dodaje na TreeSecie z klucza position animala/
        */
       insertAnimal(animal, position);
    }

    public void parentPlace(Animal child ,Animal parent){
        Vector2d childSpawnPosition = parent.getCurrentPos();
        /*
         * Skoro dzieciak rodzi się na miejscu parent'a, to na pewno musi być już ten klucz w animalMap,
         * więc nie trzeba sprawdzać tego, co przy Place'owaniu na starcie
         */
        animalMap.get(childSpawnPosition).add(child);
    }


    @Override
    public boolean isOccupied(Vector2d position){
        /*
         * Pierwszeństwo ma zwierzak - prawdopodobnie nieistotne (?)
         */
        if (isOccupiedByAnimal(position)) {
            return isOccupiedByAnimal(position);
        }
        else if (isOccupiedByGrass(position)) {
            return isOccupiedByGrass(position);
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (isOccupiedByAnimal(position)){

            /*
             * na 95% jest ok, ale jak coś nie zadziała to sprawdzić,
             * czy na pewno największy element, jeśli nie, użyć .last()
             */
            return animalMap.get(position).peek();

        }
        if (isOccupiedByGrass(position)){
            return grassMap.get(position);
        }
        return null;
    }

    public int eatGrass(Vector2d position){
        grassMap.remove(position);
        return energyboost;
    }

    @Override
    public String toString() {
        MapVisualizer draw = new MapVisualizer(this);
        return draw.draw(lowLeft,upRight);
    }

    public Vector2d rollPosition(){
        Random roll = new Random();
        return new Vector2d(roll.nextInt(height-1) , roll.nextInt(width -1));
    }

    public int getGenotypeVariant() {
        return GenotypeVariant;
    }
}
