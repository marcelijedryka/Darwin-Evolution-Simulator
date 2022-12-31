package backPackage;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Animal {


    private Vector2d currentPos;
    private ArrayList<Integer> genes;
    private MapDirection currentOrient;

    private int geneid;

    private boolean dead = false;

    private int energy;
    private final Field map;
    private final int startingEnergy;
    private final int loss;
    private int lifeTime;

    private int eatenGrass;

    private int offspringAmount;
    private final SimulationEngine engine;

    private ArrayList<IPositionChangeObserver> observerList;

    public Animal(Field map, int energy , int genotypeLength , int energyLoss ,SimulationEngine engine){
        Random random = new Random();
        int startingOrientInt = random.nextInt(8);
        this.currentOrient = MapDirection.values()[startingOrientInt];
        this.energy = energy;
        this.startingEnergy = energy;
        this.map = map;
        this.geneid = 0;
        this.loss = energyLoss;
        this.setRandomGenotype(genotypeLength);
        this.engine = engine;
        this.observerList = new ArrayList<>();
        this.lifeTime = 0;
        this.eatenGrass = 0;
        this.offspringAmount = 0;
        addObserver(map);
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

    public int getLifeTime() {
        return lifeTime;
    }

    public MapDirection getCurrentOrient() {
        return currentOrient;
    }

    public int getGeneid() {
        return geneid;
    }

    public int getStartingEnergy() {
        return startingEnergy;
    }

    public SimulationEngine getEngine() {
        return engine;
    }

    public boolean isDead() {
        return dead;
    }

    public Field getMap() {
        return map;
    }

    public int getLoss() {
        return loss;
    }

    public ArrayList<IPositionChangeObserver> getObserverList() {
        return observerList;
    }

    public ArrayList<Integer> getGenes() {
        return genes;
    }

    public boolean theSameGenes(ArrayList<Integer> otherGenes){
        for (int i = 0; i < genes.size(); i++) {
            if(!Objects.equals(genes.get(i), otherGenes.get(i))){
                return false;
            }
        }
        return true;
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

    public void energyLoss(int energyLoss){
        energy = energy - energyLoss;
        if (energy <= 0){
            this.dead = true;
            this.energy = 0;
            map.updateAvgLifetime(this.lifeTime);
        }
    }

    public void energyGain(int energyGain){
        energy = energy + energyGain;
        eatenGrass += 1;
    }


    public void move() {



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
//        if (map.objectAt(position) instanceof Grass) {
//            this.energy = this.energy + map.eatGrass(position);
//        }


        if(map.getMapVariant() == 1) {
            if (map.canMoveTo(position)) {
                positionChanged(currentPos, position);
                currentPos = position;
            } else {
                energyLoss(map.getBreedEnergyLoss());
                map.removeAnimal(this);
                map.randomPlace(this);
            }
        }

        if (map.getMapVariant() == 0) {
            if (map.canMoveTo(position)) {
                /*
                Zwykły ruch
                 */
                positionChanged(currentPos, position);
                currentPos = position;
            } else if (map.canMoveToOnEarth(position)) {
                /*
                Kulistość ziemi
                 */
                if(position.getX() == -1) {
                    position = new Vector2d(map.getWidth() - 1, position.getY());
                }
                else {
                    position = new Vector2d( 0, position.getY());
                }
                positionChanged(currentPos, position);
                currentPos = position;
            }else {
                /*
                Obrót przy "uderzeniu o barierę"
                 */
                int currentOrient_int = (currentOrient.ordinal() + 4) % 8 ;
                currentOrient = MapDirection.values()[currentOrient_int];
            }
        }

        lifeTime = lifeTime + 1;

    }

    public void standardMoveRotate(){
        int rotate = (currentOrient.ordinal() + genes.get(geneid)) % 8;
        currentOrient = MapDirection.values()[rotate];
        geneid++;
        if (geneid >= genes.size()) {
            geneid = 0;
        }
    }

    public void rollOrient(){
        Random roll = new Random();
        this.currentOrient = MapDirection.values()[roll.nextInt(8)];
    }

    public void rollGeneID(){
        Random roll = new Random();
        this.geneid = roll.nextInt(genes.size());
    }
    public int getEatenGrass() {
        return eatenGrass;
    }

    public void childBorn(){
        this.offspringAmount += 1;
    }

    public int getOffspringAmount(){
        return offspringAmount;
    }

    public int getCurrentGene(){
        return genes.get(geneid);
    }
}
