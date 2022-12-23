package backPackage;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Genotype {

    public ArrayList<Integer> rollGenotype(int length){
        ArrayList<Integer> genotype = new ArrayList<>();
        Random roll = new Random();
        for (int i = 0; i < length ; i++){
            genotype.add(roll.nextInt(8));
        }
        return genotype;
    }

    public ArrayList<Integer> offspingGenotype(Animal a1 , Animal a2 , int variant){
        ArrayList<Integer> genotype = new ArrayList<>();
        float x = calculateStake(a1,a2);
        Random roll = new Random();
        int side = roll.nextInt(2);

        if (a1.getEnergy() > a2.getEnergy()){
            int n = (int) (x * a1.getEnergy());
            //lewa strona
            if (side==0){
                for (int i = 0 ; i < n ; i++){
                    genotype.add(a1.getGenes().get(i));
                for (int j = n ; j < a1.getGenes().size() ; j++){
                    genotype.add(a2.getGenes().get(j));
                }
            }
            }
            else{
                //prawa strona
                int j = a1.getGenes().size()-n;
                for(int i = 0 ; i < j ; i++){
                    genotype.add(a2.getGenes().get(i));
                }
                for (int i = j ; i < a1.getGenes().size() ; i++){
                    genotype.add(a1.getGenes().get(i));
                }
            }

        }else{
            int n = (int) (x * a2.getEnergy());

            //lewa strona

            if (side==0){
                for (int i = 0 ; i < n ; i++) {
                    genotype.add(a2.getGenes().get(i));
                }
                for (int j = n ; j < a2.getGenes().size() ; j++){
                    genotype.add(a1.getGenes().get(j));
                }
            }
            else{
                //prawa strona
                int j = a2.getGenes().size()-n;

                for(int i = 0 ; i < j ; i++){
                    genotype.add(a1.getGenes().get(i));
                }
                for (int i = j ; i < a1.getGenes().size() ; i++){
                    genotype.add(a2.getGenes().get(i));
                }
        }}

        //Warianty mutacji genów

        int maxMutation = a1.getMap().getMaxMutation();
        int minMutation = a1.getMap().getMinMutation();
        int mutation = ThreadLocalRandom.current().nextInt(minMutation, maxMutation + 1);

        //pełna losowość (0)
        if (variant == 0){
            for (int i = 0 ; i < mutation ; i ++){
                int index = roll.nextInt(genotype.size());
                int newGene = roll.nextInt(8);
                genotype.set(index,newGene);
            }
        }
        //lekka korekta (1)
        else{
            for (int i = 0 ; i < mutation ; i ++) {
                int change = roll.nextInt(2);
                int index = roll.nextInt(genotype.size());

                // change == 0 losowy gen maleje o 1
                // change == 1 losowy gen wzrasta o 1

                if (change == 0){
                    if (genotype.get(index) - 1 < 0){
                        genotype.set(index , 7);
                    }else {
                        genotype.set(index , genotype.get(index)-1);
                    }
                }else{
                    if (genotype.get(index) + 1 > 7){
                        genotype.set(index , 0);
                    }else {
                        genotype.set(index , genotype.get(index)+1);
                }
            }
        }
        }

        return genotype;
    }

    public float calculateStake(Animal a1, Animal a2){
        int total =  (int) a1.getEnergy() + (int) a2.getEnergy();
        float p1 = a1.getEnergy() / total;
        if (p1 < 0.50){
            p1 = 1 - p1;
        }
        return p1;
    }


}
