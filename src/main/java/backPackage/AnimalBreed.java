package backPackage;

public class AnimalBreed {

    public Animal breed(Animal a1, Animal a2 , int energy){
        a1.energyLoss(a1.getMap().getBreedEnergyLoss());
        a2.energyLoss(a2.getMap().getBreedEnergyLoss());
        Animal child = new Animal(a1.getMap() , energy , a1.getGenes().size() , a1.getLoss() , a1.getEngine());
        child.getEngine().addAnimaltoList(child);
        child.setAncestorGenotype(a1,a2);
        return child;
    }



}
