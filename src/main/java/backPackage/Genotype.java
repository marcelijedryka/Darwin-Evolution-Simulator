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
