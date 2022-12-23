package backPackage;

import java.util.ArrayList;

public class SimulationEngine implements IEngine {

    private final int evolutionTime;
    private final float energyLoss;
    private final IWorldMap map;
    private final ArrayList<Animal> animals;


    // Przekazujemy mapę, długość symualcji, początkową ilość zwierząt i ile energii tracą każdego dnia
    public SimulationEngine(Field map , int evolutionTime , int animalAmount , int startEnergy , float energyLoss , int genotypeLength) {
        this.map=map;
        this.energyLoss = energyLoss;
        this.evolutionTime =evolutionTime;
        animals = new ArrayList<Animal>();
        for(int i = 0 ; i < animalAmount ; i++){
            Animal possible_animal = new Animal(map,startEnergy , genotypeLength , energyLoss);
            map.randomPlace(possible_animal);
            animals.add(possible_animal);
        }

    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    @Override
    public void run() {

        for(int i=0 ; i < evolutionTime ; i++){

            for (Animal currentAnimal : animals) {
                currentAnimal.move();
                currentAnimal.energyLoss();
            }

            System.out.println(map);

        }
    }
}

