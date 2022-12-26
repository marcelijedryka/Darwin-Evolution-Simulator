package backPackage;

import java.util.*;

public class Field extends AbstractWorldMap {


    private final Vector2d lowLeft;

    private final Vector2d upRight;

    private final int energyboost;

    // ilość nowej trawy w każdym ruchu
    private final int newGrassAmount;

    private final int height;
    private final int width;
    private final int minMutation;
    private final int maxMutation;
    private final int GenotypeVariant;
    private final int AnimalMoveVariant;
    private final int minBreedEnergy;
    private final int breedEnergyLoss;
    private final int mapVariant;

    private final int grassVariant;

    public Field(int height , int width , int grassAmount , int newGrassAmount , int energyBoost,
                 int GenotypeVariant , int minMutation , int maxMutation , int AnimalMoveVariant,
                 int minBreedEnergy , int breedEnergyLoss, int mapVariant, int grassVariant){
        this.GenotypeVariant = GenotypeVariant;
        this.width = width;
        this.height = height;
        this.lowLeft = new Vector2d(0,0);
        this.upRight = new Vector2d(height-1,width-1);
        this.newGrassAmount = newGrassAmount;
        this.energyboost = energyBoost;
        super.animalMap = new HashMap<>();
        super.grassMap = new HashMap<>();
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;
        this.AnimalMoveVariant = AnimalMoveVariant;
        this.minBreedEnergy = minBreedEnergy;
        this.breedEnergyLoss = breedEnergyLoss;
        this.mapVariant = mapVariant;
        this.grassVariant = grassVariant;

        generateNewGrass(grassAmount);
    }

//    public void generateNewGrass(){
//        Random roll = new Random();
//        int i = 0;
//
//        int beltGrassAmount = (int) (0.8 * newGrassAmount);
//        int notBeltGrassAmount = newGrassAmount - beltGrassAmount;
//        int beltSize = (int) (0.2 * height);
//
//        while (i < newGrassAmount) {
//            if (grassMap.size() == width * height) break;
//            Vector2d position = new Vector2d(roll.nextInt(width), roll.nextInt(height));
//            if (!isOccupiedByGrass(position)) {
//                grassMap.put(position, new Grass(position));
//                i++;
//            }
//        }
//    }
    public void generateNewGrass(int amount){
        if (grassVariant == 0) {
            Random roll = new Random();
            int beltSize = (int) (0.1 * height);

            int i = 0;
            while (i < amount) {
                if (grassMap.size() == width * height) break;
                Vector2d position;
                double rand = roll.nextDouble();
                if (rand <= 0.8) {
                    // Pas równikowy
                    int up_down = roll.nextInt(2);
                    int y;

                    if (up_down == 1) {
                        y = roll.nextInt(beltSize) + (width / 2 - beltSize);
                    } else {
                        y = roll.nextInt(beltSize) + (width / 2);
                    }
                    int x = roll.nextInt(height);
                    position = new Vector2d(x, y);
                } else {
                    // Poza pasem równikowym
                    int up_down = roll.nextInt(2);
                    int y;

                    if (up_down == 1) {
                        y = roll.nextInt(width / 2 - beltSize);
                    } else {
                        y = roll.nextInt(width / 2 - beltSize) + (width / 2 + beltSize);
                    }
                    int x = roll.nextInt(height);
                    position = new Vector2d(x, y);
                }
                if (!isOccupiedByGrass(position)) {
                    grassMap.put(position, new Grass(position));
                    i++;
                }
            }
        }
        else{
            System.out.println("\n\n\n\n\n\n\n!!! JESZCZE BRAK ROZWIĄZANIA !!!!\n\n\n\n\n\n\n");
            System.exit(0);
        }
    }

    public int getEnergyboost() {
        return energyboost;
    }

    public int getNewGrassAmount() {
        return newGrassAmount;
    }

    public int getMinBreedEnergy() {
        return minBreedEnergy;
    }

    public int getBreedEnergyLoss() {
        return breedEnergyLoss;
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
        return position.getX() >= 0 && position.getX() < this.width && position.getY() >= 0 &&  position.getY() < this.height ;
    }

    public boolean canMoveToOnEarth(Vector2d position) {
        return position.getY() >= 0 && position.getY() < this.height ;
    }


    @Override
    public void randomPlace(Animal animal) {
        Random roll = new Random();
        Vector2d position = new Vector2d(roll.nextInt(width - 1) , roll.nextInt(height - 1));
        animal.setCurrentPos(position);
        /*
        * Jeśli nie ma zwierzaka na danej pozycji, to tworzę TreeSeta dla klucza o tej pozycji
        * Niezależnie od tego if'a, potem dodaje na TreeSecie z klucza position animala/
        */
       insertAnimal(animal, position);
    }

    public void parentPlace(Animal child ,Animal parent){
        /*
         * Skoro dzieciak rodzi się na miejscu parent'a, to na pewno musi być już ten klucz w animalMap,
         * więc nie trzeba sprawdzać tego, co przy Place'owaniu na starcie
         */
        animalMap.get(parent.getCurrentPos()).add(child);
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
        return new Vector2d(roll.nextInt(height) , roll.nextInt(width));
    }

    public int getGenotypeVariant() {
        return GenotypeVariant;
    }

    //będzie działać przy założeniu że usuwamy z mapy pola na których nie stoją zwierzęta
    public void checkPossibleBreed(){
        for (Map.Entry<Vector2d , PriorityQueue<Animal>> set : animalMap.entrySet()){
            PriorityQueue<Animal> currentField = set.getValue();
            if(currentField.size() >= 2){
                Animal a1 = currentField.poll();
                Animal a2 = currentField.poll();
                if (a1.getEnergy() > minBreedEnergy+breedEnergyLoss && a2.getEnergy() > minBreedEnergy+breedEnergyLoss){
                    Animal child = new AnimalBreed().breed(a1 ,a2 , a1.getStartingEnergy());
                    parentPlace(child,a1);
                }
                currentField.add(a1);
                currentField.add(a2);
            }
        }
    }

    public int getMapVariant() {
        return mapVariant;
    }

    public int getGrassVariant(){
        return grassVariant;
    }

}
