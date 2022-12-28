package backPackage;

import java.util.ArrayList;
import java.util.Iterator;

public class SimulationEngine implements IEngine, Runnable {

    private final int evolutionTime;
    private final float energyLoss;
    private final Field map;
    private final ArrayList<Animal> animals;


    // Przekazujemy mapę, długość symualcji, początkową ilość zwierząt i ile energii tracą każdego dnia
    public SimulationEngine(Field map , int evolutionTime , int animalAmount , int startEnergy , int energyLoss , int genotypeLength) {
        this.map=map;
        this.energyLoss = energyLoss;
        this.evolutionTime =evolutionTime;
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
        visualize(0);
        for(int i=0 ; i < evolutionTime ; i++){
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

            visualize(i+1);



        }
        System.out.println("_________________________________________________________________");
        System.out.println("\n" + animals.size()+ " SURVIVED "+ evolutionTime+ " ITERATIONS\n");
        System.out.println("_________________________________________________________________");
    }

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
}

