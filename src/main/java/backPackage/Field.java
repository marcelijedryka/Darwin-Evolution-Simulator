package backPackage;

import java.util.*;

public class Field implements IWorldMap {

    /*
    *  Trzeba było zrobić dwie osobne hashlisty dla animali i trawy — PAIN
    *  */

    private final Map<Vector2d , TreeSet<Animal>> animalMap = new HashMap<>();
    private final Map<Vector2d , Object> grassMap = new HashMap<>();

    private final Vector2d lowLeft;

    private final Vector2d upRight;

    private final int energyboost;

    // ilość nowej trawy w każdym ruchu
    private final int growth;

    private final int height;
    private final int width;

    public Field(int height , int width , int grassAmount , int growth , int energyBoost){
        this.width = width;
        this.height = height;
        this.lowLeft = new Vector2d(0,0);
        this.upRight = new Vector2d(height-1,width-1);
        this.growth = growth;
        this.energyboost = energyBoost;

        // Generowanie Trawy

        /*
        * !!!! WAŻNA SPRAWA !!!!
        *  Aktualna implementacja zakłada, że trawa nie może pojawić się tam, gdzie jest zwierzak. Czy tak ma być?
         */
        Random roll = new Random();
        while (grassMap.size() < grassAmount){
            Vector2d position = new Vector2d(roll.nextInt(width), roll.nextInt(height));
            if (!isOccupied(position)){
                grassMap.put(position , new Grass(position));
            }
        }

    }

    public void GenerateNewGrass(){
        Random roll = new Random();
        int i = 0;
        while (i < growth){
            Vector2d position = new Vector2d(roll.nextInt(width), roll.nextInt(height));
            if (!isOccupied(position)){
                grassMap.put(position , new Grass(position));
                i++;
        }
        }
    }

    public void removeAnimal(Animal animal){
        Vector2d position = animal.getCurrentPos();
        animalMap.get(position).remove(animal);
    }

    public Map<Vector2d, Object> getGrassMap() {
        return grassMap;
    }

    public Map<Vector2d, TreeSet<Animal>> getAnimalMap() {
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
        return position.getX() < this.width && position.getY() < this.height;
    }

    @Override
    public void randomPlace(Animal animal) {
        Random roll = new Random();
        Vector2d position = new Vector2d(roll.nextInt(width) , roll.nextInt(height));
        animal.setCurrentPos(position);
        /*
        * Jeśli nie ma zwierzaka na danej pozycji, to tworzę TreeSeta dla klucza o tej pozycji
        * Niezależnie od tego if'a, potem dodaje na TreeSecie z klucza position animala/
        */
        if (!isOccupiedByAnimal(position)){
            Comparator<Animal> cmp = new AnimalComparator();
            animalMap.put(position, new TreeSet<Animal>(cmp));
        }
        animalMap.get(position).add(animal);
    }

    public void parentPlace(Animal child ,Animal parent){
        Vector2d childSpawnPostion = parent.getCurrentPos();
        /*
         * Skoro dzieciak rodzi się na miejscu parent'a, to na pewno musi być już ten klucz w animalMap,
         * więc nie trzeba sprawdzać tego, co przy Place'owaniu na starcie
         */
        animalMap.get(childSpawnPostion).add(child);
    }

    public boolean isOccupiedByAnimal(Vector2d position) {
        return animalMap.containsKey(position);
    }
    public boolean isOccupiedByGrass(Vector2d position) {
        return grassMap.containsKey(position);
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
            return animalMap.get(position).first();

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

    public String toString() {
        MapVisualizer draw = new MapVisualizer(this);
        return draw.draw(lowLeft,upRight);
    }

    public Vector2d rollPosition(){
        Random roll = new Random();
        return new Vector2d(roll.nextInt(height-1) , roll.nextInt(width -1));
    }

}
