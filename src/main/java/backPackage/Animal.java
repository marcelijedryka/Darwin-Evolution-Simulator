package backPackage;

import java.util.ArrayList;

public class Animal {


    private Vector2d currentPos;

    private ArrayList<Integer> genes;
    private MapDirection currentOrient;

    private int geneid;

    private boolean dead = false;

    private float energy;
    private final IWorldMap map;

    private final float loss = 1;
    private final int genotypeLength = 4;
    /*
    !!! DO PRZEMYŚLENIA co z length i loss!!!
     */

    private ArrayList<IPositionChangeObserver> observerList;

    public Animal(IWorldMap map, int energy ){
        this.currentOrient = MapDirection.NORTH;
        this.energy = energy;
        this.map = map;
        this.geneid = 0;
        this.setRandomGenotype(genotypeLength);
        /*
        Dzięki AbstractWorldElement nie będzie trzeba rzutować
         */
        addObserver( (IPositionChangeObserver) this.map);
    }

    void addObserver(IPositionChangeObserver observer){
        observerList.add(observer);
    }
    void removeObservers(IPositionChangeObserver observer){
        this.observerList = null;
    }
    void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for(IPositionChangeObserver observer: observerList){
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }

    public ArrayList<Integer> getGenes() {
        return genes;
    }

    public void setRandomGenotype(int length){
        this.genes = new Genotype().rollGenotype(length);
    }

    public void setAncestorGenotype(Animal a1 , Animal a2){
        this.genes = new Genotype().offspingGenotype(a1 , a2);
    }

    public void setCurrentPos(Vector2d position) {
        this.currentPos = position;
    }

    public Vector2d getCurrentPos() {
        return currentPos;
    }

    public float getEnergy() {
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
        energy = energy - loss;
        if (energy <= 0){
            this.dead = true;
        }
    }


    public void move() {

        if (this.dead) {
            map.removeAnimal(this);
        }


        int rotate = (currentOrient.ordinal() + genes.get(geneid)) % 8;
        currentOrient = MapDirection.values()[rotate];
        geneid++;
        if (geneid >= genes.size()) {
            geneid = 0;
        }

        Vector2d position = new Vector2d(currentPos.getX(), currentPos.getY());

        switch (currentOrient) {
            case NORTH -> position.add(new Vector2d(0, 1));
            case NORTHEAST -> position.add(new Vector2d(1, 1));
            case EAST -> position.add(new Vector2d(1, 0));
            case SOUTHEAST -> position.add(new Vector2d(1, -1));
            case SOUTH -> position.add(new Vector2d(0, -1));
            case SOUTHWEST -> position.add(new Vector2d(-1, -1));
            case WEST -> position.add(new Vector2d(-1, 0));
            case NORTHWEST -> position.add(new Vector2d(-1, 1));
        }
        if (map.objectAt(position) instanceof Grass) {
            this.energy = this.energy + map.eatGrass(position);
        }



        if (map.canMoveTo(position)) {

            positionChanged(currentPos, position);
            currentPos = position;
        } else {
            energyLoss();
            map.randomPlace(this);
        }

    }
}
