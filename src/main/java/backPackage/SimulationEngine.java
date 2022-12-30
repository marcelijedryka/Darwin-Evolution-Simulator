package backPackage;

import gui.CSVSaver;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SimulationEngine implements IEngine, Runnable {

    private final int evolutionTime;
    private final float energyLoss;
    private final Field map;
    private final ArrayList<Animal> animals;
    private final int sleepTime;
    private int currentYear;
    private int freeFields;
    private float avgEnergy;
    private boolean pause = true;
    private boolean saveToCSV;
    private CSVSaver saver;



    // Przekazujemy mapę, długość symualcji, początkową ilość zwierząt i ile energii tracą każdego dnia
    public SimulationEngine(Field map , int evolutionTime , int animalAmount , int startEnergy , int energyLoss , int genotypeLength,
                            int speed , boolean saveToCSV) {
        this.map=map;
        this.freeFields = map.getHeight() * map.getWidth();
        this.energyLoss = energyLoss;
        this.evolutionTime =evolutionTime;
        this.sleepTime = speed;
        this.currentYear = 0;
        this.saveToCSV = saveToCSV;
        saver = new CSVSaver();
        animals = new ArrayList<Animal>();
        for(int i = 0 ; i < animalAmount ; i++){
            Animal possible_animal = new Animal(map,startEnergy , genotypeLength , energyLoss , this);
            map.randomPlace(possible_animal);
            animals.add(possible_animal);
        }


    }

    public void addAnimaltoList(Animal animal){
        animals.add(animal);
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    @Override
    public void run() {
//        visualize(0);
        int i = 0;

        while (i < evolutionTime && animals.size() > 0){

            if(pause){
                if (saveToCSV){
                    saver.addToStats(map.getAnimalMap().size() , map.getGrassMap().size(),map.calculateFreeFields(), avgEnergy , map.getAvgLifetime());
                }
                runYear(i);
                i++;
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        if(saveToCSV){
            saver.saveCSVFile();
        }
        }

        public void runYear(int i){

                currentYear = i + 1;
                Iterator<Animal> iterator = animals.iterator();
                while (iterator.hasNext()) {
                    Animal currentAnimal = iterator.next();
                    currentAnimal.move();
                    currentAnimal.energyLoss(currentAnimal.getLoss());
//                System.out.println(currentAnimal.getCurrentPos());
                    if (currentAnimal.isDead()) {
                        iterator.remove();
                        map.removeAnimal(currentAnimal);
                    }

                }
                map.generateNewGrass(map.getNewGrassAmount());
                map.checkPossibleEating();
                map.checkPossibleBreed();
                freeFields = map.calculateFreeFields();
                avgEnergy = calculateAvgEnergy();
//            Tylko w aplikacji
                map.notifyObserver();




//            visualize(i+1);

        }
//        System.out.println("_________________________________________________________________");
//        System.out.println("\n" + animals.size()+ " SURVIVED "+ evolutionTime+ " ITERATIONS\n");
//        System.out.println("_________________________________________________________________");

    public void visualize(int time) {
        System.out.println("Number of animals: " + animals.size());
        System.out.println("Number of weed: " + map.grassMap.size());
        System.out.println("Number of free space: " + (map.getWidth()*map.getHeight() - map.grassMap.size() - map.getAnimalMap().size()));
        System.out.println("Time: " + time);
        System.out.println(map);
    }

    public float getEnergyLoss() {
        return energyLoss;
    }

    public int getCurrentYear() {
        return currentYear;
    }

    public int getFreeFields() {
        return freeFields;
    }

    public float getAvgEnergy() {
        return avgEnergy;
    }

    public float calculateAvgEnergy(){
        int energy = 0;
        for(Animal animal : animals){
            energy = energy + animal.getEnergy();
        }
        return energy / animals.size();
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public ArrayList<Integer> getBestGenes(){
        HashMap<ArrayList<Integer>, Integer> counts = new HashMap<>();

        for(Animal ani : animals){
            ArrayList<Integer> currentGenes = ani.getGenes();
            if(counts.containsKey(currentGenes)){
                counts.put(currentGenes, counts.get(currentGenes) + 1);
            }else{
                counts.put(currentGenes, 1);
            }
        }

        ArrayList<Integer> mostPopular = null;
        int maxCount = 0;
        for (ArrayList<Integer> gene : counts.keySet()) {
            if (counts.get(gene) > maxCount) {
                mostPopular = gene;
                maxCount = counts.get(gene);
            }
        }
        return mostPopular;

    }


}



