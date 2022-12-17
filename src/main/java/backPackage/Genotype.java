package backPackage;

import java.util.ArrayList;
import java.util.Random;

public class Genotype {




    public ArrayList<Integer> rollGenotype(int length){
        ArrayList<Integer> genotype = new ArrayList<>();
        Random roll = new Random();
        for (int i = 0; i < length ; i++){
            genotype.add(roll.nextInt(8));
        }
        return genotype;
    }

    public ArrayList<Integer> offspingGenotype(Animal a1 , Animal a2){
        ArrayList<Integer> genotype = new ArrayList<>();
        Random roll = new Random();
        if (a1.getEnergy() > a2.getEnergy()){

        }else{

        }
        return genotype;
    }


}
