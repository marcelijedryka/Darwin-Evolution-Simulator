package backPackage;

public class AnimalBreed {
    private final AbstractWorldMap map;

    AnimalBreed(AbstractWorldMap map){
        this.map = map;
    }
    public Animal breed(Animal a1, Animal a2 , int energy){
        a1.energyLoss();
        a2.energyLoss();
        Animal child = new Animal(map , energy , a1.getGenes().size() , a1.getLoss());
        child.setAncestorGenotype(a1,a2);
        return child;
    }



}
