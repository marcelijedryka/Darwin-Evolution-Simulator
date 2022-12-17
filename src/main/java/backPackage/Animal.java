package backPackage;

import java.util.ArrayList;

public class Animal {

    private Vector2d currentPos;

    ArrayList<Integer> genes;
    private MapDirection currentOrient;

    private int energy;

    private IWorldMap map;

    public Animal(IWorldMap map ,int energy ){
        this.currentOrient = MapDirection.NORTH;
        this.energy = energy;
        this.map = map;

    }

    public void setRandomGenotype(){
        this.genes = new Genotype().rollGenotype(4);
    }

    public void setAncestorGenotype(Animal a1 , Animal a2){
        this.genes = new Genotype().offspingGenotype(a1 , a2);
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


}
