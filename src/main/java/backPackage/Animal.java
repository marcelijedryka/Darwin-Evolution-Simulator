package backPackage;

import java.util.ArrayList;
import java.util.Random;

public class Animal {


    private Vector2d currentPos;
    private ArrayList<Integer> genes;
    private MapDirection currentOrient;

    private int geneid;

    private boolean dead = false;

    private float energy;
    private final Field map;

    private final float loss;

    private ArrayList<IPositionChangeObserver> observerList;

    public Animal(Field map, int energy , int genotypeLength , float energyLoss ){
        this.currentOrient = MapDirection.NORTH;
        this.energy = energy;
        this.map = map;
        this.geneid = 0;
        this.loss = energyLoss;
        this.setRandomGenotype(genotypeLength);

        this.observerList = new ArrayList<>();
        addObserver((AbstractWorldMap) map);
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

    public MapDirection getCurrentOrient() {
        return currentOrient;
    }

    public int getGeneid() {
        return geneid;
    }

    public boolean isDead() {
        return dead;
    }

    public Field getMap() {
        return map;
    }

    public float getLoss() {
        return loss;
    }

    public ArrayList<IPositionChangeObserver> getObserverList() {
        return observerList;
    }

    public ArrayList<Integer> getGenes() {
        return genes;
    }

    public void setRandomGenotype(int length){
        this.genes = new Genotype().rollGenotype(length);
    }

    public void setAncestorGenotype(Animal a1 , Animal a2){
        this.genes = new Genotype().offspingGenotype(a1 , a2 ,map.getGenotypeVariant());
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
//            Look for a problem
            map.removeAnimal(this);
            System.out.println("zmarł");
            System.exit(0);
        }

        //Ogranie wariantów ruchów zwierzęcia

        if (map.getAnimalMoveVariant() == 1){
            Random roll = new Random();
            int x = roll.nextInt(11);
            if ( x <= 8){
                this.standardMoveRotate();
            }else{
                geneid = roll.nextInt(genes.size());
                this.standardMoveRotate();
            }
        }else {
           this.standardMoveRotate();
        }

        Vector2d position = new Vector2d(currentPos.getX(), currentPos.getY());

        switch (currentOrient) {
            case NORTH -> position = position.add(new Vector2d(0, 1));
            case NORTHEAST -> position = position.add(new Vector2d(1, 1));
            case EAST -> position =position.add(new Vector2d(1, 0));
            case SOUTHEAST -> position =position.add(new Vector2d(1, -1));
            case SOUTH -> position =position.add(new Vector2d(0, -1));
            case SOUTHWEST -> position =position.add(new Vector2d(-1, -1));
            case WEST -> position =position.add(new Vector2d(-1, 0));
            case NORTHWEST -> position =position.add(new Vector2d(-1, 1));
        }
        if (map.objectAt(position) instanceof Grass) {
            this.energy = this.energy + map.eatGrass(position);
        }



        if (map.canMoveTo(position)) {
            positionChanged(currentPos, position);
            currentPos = position;
        } else {
            energyLoss();
            map.removeAnimal(this);
            map.randomPlace(this);
        }

    }

    public void standardMoveRotate(){
        int rotate = (currentOrient.ordinal() + genes.get(geneid)) % 8;
        currentOrient = MapDirection.values()[rotate];
        geneid++;
        if (geneid >= genes.size()) {
            geneid = 0;
        }
    }
}
