package gui;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVSaver {

    ArrayList<String> stats;

    public CSVSaver(){
        this.stats = new ArrayList<>();
    }

    public void addToStats(int animalAmount , int grassAmount , int emptyLand , float avgEnergy , float avgLifetime){
        String data = animalAmount + ", " + grassAmount + ", " + emptyLand + ", " + avgEnergy + ", " + avgLifetime;
        stats.add(data);
    }

    public void saveCSVFile(){
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./src/main/resources/CSVData/" + "newSimulation.csv"));
            for (String data : stats) {
                writer.write(data);
            }
            writer.close();
        } catch (IOException e) {
            System.out.print(e);
        }
    }

}
