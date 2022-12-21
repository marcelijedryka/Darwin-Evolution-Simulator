package backPackage;

import java.util.ArrayList;

//public class SimulationEngine implements IEngine {
//
//    private final MapDirection[] moves;
//    private final IWorldMap map;
//    private final ArrayList<Animal> animals;
//
//    public SimulationEngine(MapDirection[] moves , IWorldMap map , Vector2d[] starting_positions) {
//        this.map=map;
//        this.moves=moves;
//        animals = new ArrayList<Animal>();
//        for(Vector2d pos : starting_positions){
//            Animal possible_animal = new Animal(map,pos);
//            if (map.place(possible_animal)){
//                animals.add(possible_animal);
//
//            }
//        }
//
//    }
//
//    public ArrayList<Animal> getAnimals() {
//        return animals;
//    }
//
//    @Override
//    public void run() {
//        int j= 0;
//        for(int i=0 ; i < moves.length ; i++){
//            if(j == animals.size()){
//                j=0;
//            }
//            animals.get(j).move(moves[i]);
//            System.out.println(map);
//            j++;
//        }
//    }
//}

