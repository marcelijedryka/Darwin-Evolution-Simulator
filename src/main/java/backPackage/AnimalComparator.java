package backPackage;

import java.util.Comparator;

public class AnimalComparator implements Comparator<Animal> {
    public int compare(Animal ani1, Animal ani2) {
        // energy
        if (ani1.getEnergy() < ani2.getEnergy()) return 1;
        else if (ani1.getEnergy() > ani2.getEnergy()) return -1;
        // age
        else if (ani1.getLifeTime() < ani2.getLifeTime())return 1;
        else if (ani1.getLifeTime() > ani2.getLifeTime()) return -1;
        // children
        else if (ani1.getOffspringAmount() < ani2.getOffspringAmount())return 1;
        else if (ani1.getOffspringAmount() > ani2.getOffspringAmount()) return -1;

        return 0;
    }
}
