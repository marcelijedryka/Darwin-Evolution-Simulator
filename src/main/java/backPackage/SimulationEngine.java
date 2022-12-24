package backPackage;

import java.util.ArrayList;
import java.util.Iterator;

public class SimulationEngine implements IEngine {

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

        for(int i=0 ; i < evolutionTime ; i++){

//            for (Animal currentAnimal : animals) {
//                if (animals.size() > 0) {
//                    currentAnimal.move();
//                    currentAnimal.energyLoss();
//                    System.out.println(currentAnimal.getCurrentPos());
//                    if (currentAnimal.isDead()){ animals.remove(currentAnimal);}
//                }
//                else{
//                    System.out.println("Nie ma już zwierzaków");
//                    System.exit(0);
//                }
//            }
            Iterator<Animal> iterator = animals.iterator();
            while (iterator.hasNext()) {
                Animal currentAnimal = iterator.next();
                currentAnimal.move();
                currentAnimal.energyLoss(currentAnimal.getLoss());
                System.out.println(currentAnimal.getCurrentPos());
                if (currentAnimal.isDead()) {
                    iterator.remove();
                    map.removeAnimal(currentAnimal);
                }
            }
            map.checkPossibleBreed();
            map.generateNewGrass();
            System.out.println(map);
            System.out.println("Number of animals: " + animals.size());

        }
        System.out.println("_________________________________________________________________");
        System.out.println("\n" + animals.size()+ " SURVIVED "+ evolutionTime+ " ITERATIONS\n");
        System.out.println("_________________________________________________________________");
    }

    public float getEnergyLoss() {
        return energyLoss;
    }
}

