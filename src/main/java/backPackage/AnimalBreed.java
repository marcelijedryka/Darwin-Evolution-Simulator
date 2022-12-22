package backPackage;

public class AnimalBreed {

    private final IWorldMap map;

    AnimalBreed(IWorldMap map){
        this.map = map;
    }
    public Animal breed(Animal a1, Animal a2 , int energy){
        /*
        Chyba aphollo chodziło że jest zadana strata energii, zmieniłem w Animal'u
         */
        a1.energyLoss();
        a2.energyLoss();
        Animal child = new Animal(map , energy);
        child.setAncestorGenotype(a1,a2);
        return child;
    }



}
