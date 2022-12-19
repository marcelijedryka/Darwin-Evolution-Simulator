package backPackage;

import java.util.ArrayList;

public class Animal {

    private Vector2d currentPos;

    ArrayList<Integer> genes;
    private MapDirection currentOrient;

    private int energy;

    private IWorldMap map;

    private final int genotypeLength;

    private ArrayList<IPositionChangeObserver> observerList;

    public Animal(IWorldMap map, int energy, int genotypeLength ){
        this.currentOrient = MapDirection.NORTH;
        this.energy = energy;
        this.map = map;
        setRandomGenotype();
        this.genotypeLength = 4;
//        addObserver(map); Trzeba wprowadzić takie zmiany żeby to działało
    }

//   Trzeba będzie zmienić
    public void addObserver(IPositionChangeObserver observer){
        observerList.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer) {observerList.remove(observer); }
    void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver observer: observerList){
            observer.positionChanged(oldPosition, newPosition);
        }
    }

//    Chyba do genotype dwa poniżej
    public void setRandomGenotype(){
        this.genes = new Genotype().rollGenotype(genotypeLength);
    }

//    Chyba kiepska nazwa
    public void setAncestorGenotype(Animal a1 , Animal a2){
        this.genes = new Genotype().offspringGenotype(a1 , a2);
    }

    public int getEnergy() {
        return energy;
    }

    public String toString() {
        return switch (currentOrient) {
            case WEST -> "W";
            case SOUTH -> "S";
            case EAST -> "E";
            case NORTH -> "N";
            case NORTHWEST -> "NW";
            case NORTHEAST -> "NE";
            case SOUTHEAST -> "SE";
            case SOUTHWEST -> "SW";
        };

    }

    public void energyLoss(){
        energy = energy - 1;
    }


// Dobrze by było przenieść do Genotype, ale to wymaga też innych modyfikacji
    public int getRandomGen() {
        int rand = (int) (Math.random() * (genotypeLength));
        return genes.get(rand);
    }

    public void move() {
        int index = getRandomGen();
        Vector2d new_location = currentPos;
        switch (genes.get(index) ) {
            case 0-> new_location = new_location.add(currentOrient.toUnitVector());
            case 1 -> currentOrient = currentOrient.previous();
            case 2 -> currentOrient = currentOrient.previous().previous();
            case 3 -> currentOrient = currentOrient.previous().previous().previous();
            case 4 -> new_location = new_location.subtract(currentOrient.toUnitVector());
            case 5 -> currentOrient = currentOrient.next().next().next();
            case 6 -> currentOrient = currentOrient.next().next();
            case 7 -> currentOrient = currentOrient.next();
        }
        currentPos = new_location;
    }


}
