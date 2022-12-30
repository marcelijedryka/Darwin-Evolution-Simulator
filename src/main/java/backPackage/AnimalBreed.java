package backPackage;

public class AnimalBreed {

    public Animal breed(Animal a1, Animal a2 ){
        a1.energyLoss(a1.getMap().getBreedEnergyLoss());
        a2.energyLoss(a2.getMap().getBreedEnergyLoss());
        a1.childBorn();
        a2.childBorn();
        int newbornEnergy = a1.getMap().getBreedEnergyLoss() + a2.getMap().getBreedEnergyLoss();
        Animal child = new Animal(a1.getMap() , newbornEnergy , a1.getGenes().size() , a1.getLoss() , a1.getEngine());
        child.getEngine().addAnimaltoList(child);
        child.setAncestorGenotype(a1,a2);
        child.setCurrentPos(a1.getCurrentPos());
        child.rollOrient();
        child.rollGeneID();
        return child;
    }



}
