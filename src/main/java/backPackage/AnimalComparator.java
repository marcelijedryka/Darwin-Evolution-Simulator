package backPackage;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal> {
    public int compare(Animal ani1, Animal ani2) {
        if (ani1.getEnergy() < ani2.getEnergy()) return 1;
        else if (ani1.getEnergy() > ani2.getEnergy()) return -1;
        return 0;
    }
}
