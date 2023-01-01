package backPackage;

import java.util.*;

public class Field implements IWorldMap, IPositionChangeObserver {
    private final int energyBoost;
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
    private float avgLifetime;
    private int deadAnimals;
    private int yearsLivedSummary;
    private final int grassVariant;
    Map<Vector2d, Integer> deathCountMap;

    protected HashMap<Vector2d, PriorityQueue<Animal>> animalMap;
    protected HashMap<Vector2d, Grass> grassMap;

    IMapObserver observer;

    public Field(int height, int width, int grassAmount, int newGrassAmount, int energyBoost,
                 int GenotypeVariant, int minMutation, int maxMutation, int AnimalMoveVariant,
                 int minBreedEnergy, int breedEnergyLoss, int mapVariant, int grassVariant) {
        this.GenotypeVariant = GenotypeVariant;
        this.width = width;
        this.height = height;
        this.newGrassAmount = newGrassAmount;
        this.energyBoost = energyBoost;
        this.animalMap = new HashMap<>();
        this.grassMap = new HashMap<>();
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;
        this.AnimalMoveVariant = AnimalMoveVariant;
        this.minBreedEnergy = minBreedEnergy;
        this.breedEnergyLoss = breedEnergyLoss;
        this.mapVariant = mapVariant;
        this.grassVariant = grassVariant;
        this.avgLifetime = 0;
        this.deadAnimals = 0;
        this.yearsLivedSummary = 0;
        this.deathCountMap = new HashMap<>();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                deathCountMap.put(new Vector2d(x, y), 0);
            }
        }
        generateNewGrass(grassAmount);
    }

    public void generateNewGrass(int amount) {
        if (grassVariant == 0) {
            Random roll = new Random();
            int beltSize = height / 10;
            if (beltSize <= 0) {
                beltSize = 1;
            }
            int i = 0;
            while (i < amount && grassMap.size() < width * height) {
                double paretoProbability = roll.nextDouble();
                int y;

                // wyjątek
                if (height == 1) {
                    y = roll.nextInt(2);
                }
                else {
                    // Dzielimy field na dwie części
                    int upOrDown = roll.nextInt(2);
                    if (paretoProbability <= 0.8) {

                        if (upOrDown == 1) {
                            // Wstawiamy do górnej części pasa równikowego
                            y = roll.nextInt(beltSize) + (height / 2 - beltSize);
                        } else {
                            // Wstawiamy do dolnej części pasa równikowego
                            y = roll.nextInt(beltSize) + (height / 2);
                        }
                    }

                    else {
                        // Wstawiamy poza pasem równikowym

                        if (height / 2 - beltSize == 0) {
                            y = roll.nextInt(height);
                        } else {
                            if (upOrDown == 1) {
                                // Wstawiamy do górnej części poza pasem równikowym
                                y = roll.nextInt(height / 2 - beltSize);
                            } else {
                                // Wstawiamy do dolnej części poza pasem równikowym
                                y = roll.nextInt(height / 2 - beltSize) + (height / 2 + beltSize);
                            }
                        }

                    }
                }
                int x = roll.nextInt(width);
                Vector2d position = new Vector2d(x, y);
                if (!isOccupiedByGrass(position)) {
                    grassMap.put(position, new Grass(position));
                    i++;
                }
            }

        } else if (grassVariant == 1) {
            // Wariant zatrutych trupów
            List<Vector2d> sortedPositions = deathCountMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey).toList();


            int lowestCount = 0;
            int lowestValue = Integer.MAX_VALUE;
            for (Map.Entry<Vector2d, Integer> entry : deathCountMap.entrySet()) {
                int value = entry.getValue();
                if (value < lowestValue) {
                    lowestValue = value;
                }
            }
            for (Map.Entry<Vector2d, Integer> entry : deathCountMap.entrySet()) {
                int value = entry.getValue();
                if (value == lowestValue) {
                    lowestCount++;
                }
            }


            int i = 0;
            int preferableAmountSize = lowestCount;
            if (preferableAmountSize < (sortedPositions.size() * 2) / 10) {
                preferableAmountSize = (sortedPositions.size() * 2) / 10;
            }
            int unpreferableAmountSize = sortedPositions.size() - preferableAmountSize;

            Random roll = new Random();
            while (i < amount) {
                if (grassMap.size() == width * height) break;
                double rand = roll.nextDouble();
                if (rand <= 0.8) {
                    Vector2d position = sortedPositions.get(roll.nextInt(preferableAmountSize));
                    if (!isOccupiedByGrass(position)) {
                        grassMap.put(position, new Grass(position));
                        i++;
                    }
                } else if (unpreferableAmountSize > 0) {
                    Vector2d position = sortedPositions.get(preferableAmountSize + roll.nextInt(unpreferableAmountSize));
                    if (!isOccupiedByGrass(position)) {
                        grassMap.put(position, new Grass(position));
                        i++;
                    }
                }
            }

        }
    }

    public int getNewGrassAmount() {
        return newGrassAmount;
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

    public Map<Vector2d, Grass> getGrassMap() {
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

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.getX() >= 0 && position.getX() < this.width && position.getY() >= 0 && position.getY() < this.height;
    }

    public boolean canMoveToOnEarth(Vector2d position) {
        return position.getY() >= 0 && position.getY() < this.height;
    }


    @Override
    public void randomPlace(Animal animal) {
        Random roll = new Random();
        Vector2d position = new Vector2d(roll.nextInt(width), roll.nextInt(height));
        animal.setCurrentPos(position);
        insertAnimal(animal, position);
    }

    public void parentPlace(Animal child, Animal parent) {
        animalMap.get(parent.getCurrentPos()).add(child);
    }

    public boolean isOccupiedByAnimal(Vector2d position) {
        return animalMap.containsKey(position);
    }

    public boolean isOccupiedByGrass(Vector2d position) {
        return grassMap.containsKey(position);
    }

    @Override
    public boolean isOccupied(Vector2d position) {

        if (isOccupiedByAnimal(position)) {
            return isOccupiedByAnimal(position);
        } else if (isOccupiedByGrass(position)) {
            return isOccupiedByGrass(position);
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (isOccupiedByAnimal(position)) {
            return animalMap.get(position).peek();
        }
        if (isOccupiedByGrass(position)) {
            return grassMap.get(position);
        }
        return null;
    }

    @Override
    public int eatGrass(Vector2d position) {
        grassMap.remove(position);
        return energyBoost;
    }

    public int getGenotypeVariant() {
        return GenotypeVariant;
    }

    public void checkPossibleBreed() {
        for (Map.Entry<Vector2d, PriorityQueue<Animal>> set : animalMap.entrySet()) {
            PriorityQueue<Animal> currentField = set.getValue();
            if (currentField.size() >= 2) {
                Animal a1 = currentField.poll();
                Animal a2 = currentField.poll();
                if (a1.getEnergy() > minBreedEnergy + breedEnergyLoss && a2.getEnergy() > minBreedEnergy + breedEnergyLoss) {
                    Animal child = new AnimalBreed().breed(a1, a2);
                    parentPlace(child, a1);
                }
                currentField.add(a1);
                currentField.add(a2);
            }
        }
    }

    public int getMapVariant() {
        return mapVariant;
    }

    public void checkPossibleEating() {
        for (Map.Entry<Vector2d, PriorityQueue<Animal>> set : animalMap.entrySet()) {
            PriorityQueue<Animal> currentField = set.getValue();

            if (currentField.size() >= 1) {
                Animal a1 = currentField.poll();
                Vector2d position = a1.getCurrentPos();
                if (isOccupiedByGrass(position)) {
                    a1.energyGain(eatGrass(position));
                }
                currentField.add(a1);
            }

        }

    }

    @Override
    public void insertAnimal(Animal animal, Vector2d position) {
        if (!isOccupiedByAnimal(position)) {
            Comparator<Animal> cmp = new AnimalComparator();
            animalMap.put(position, new PriorityQueue<>(cmp));
        }
        animalMap.get(position).add(animal);
    }


    @Override
    public void removeAnimal(Animal animal) {
        Vector2d position = animal.getCurrentPos();
        animalMap.get(position).remove(animal);
        if (animalMap.get(position).size() == 0) {
            animalMap.remove(position);
        }
        if (deathCountMap.containsKey(position)) {
            int count = deathCountMap.get(position);
            deathCountMap.put(position, count + 1);
        } else {
            deathCountMap.put(position, 1);
        }

    }

    public void addMapObserver(IMapObserver observer) {
        this.observer = observer;
    }

    public void notifyObserver() {
        observer.updateMap();
    }

    public int calculateFreeFields() {
        int freeSpace = 0;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (null == objectAt(new Vector2d(x,y))) {
                    freeSpace += 1;
                }
            }
        }
        return freeSpace;
    }

    public void updateAvgLifetime(int animalLifeTime) {
        deadAnimals = deadAnimals + 1;
        yearsLivedSummary = yearsLivedSummary + animalLifeTime;
        avgLifetime = (float) ((int) (yearsLivedSummary * 10 / (float) deadAnimals)) / 10;
    }

    public float getAvgLifetime() {
        return avgLifetime;
    }

    @Override
    public void positionChanged(Animal ani, Vector2d oldPosition, Vector2d newPosition) {
        removeAnimal(ani);
        insertAnimal(ani, newPosition);
    }
}
