package backPackage;

public class AnimalBreed {

    private final IWorldMap map;

    AnimalBreed(IWorldMap map){
        this.map = map;
    }
    public Animal breed(Animal a1, Animal a2 , int energy){
        a1.energyLoss((float) (0.5 * energy));
        a2.energyLoss((float) (0.5 * energy));
        Animal child = new Animal(map , energy);
        child.setAncestorGenotype(a1,a2);
        return child;
    }



}
