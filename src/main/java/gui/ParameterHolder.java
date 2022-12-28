package gui;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ParameterHolder {

    private int mapHeight;
    private int mapWidth;
    private int grassAmount;
    private int grassEnergyBoost;
    private int dailyGrassGrowth;
    private int animalAmount;
    private int startingEnergy;
    private int minBreedEnergy;
    private int breedEnergyLoss;
    private int minMutation;
    private int maxMutation;
    private int genotypeLength;
    private int simulationTime;
    private int dailyEnergyCost;
    private int mapVar;
    private int behVar;
    private int plantVar;
    private int mutationVar;

    private int speed;

    public ParameterHolder(int mapHeight,int mapWidth,int grassAmount,int grassEnergyBoost,
                           int dailyGrassGrowth,int animalAmount,int startingEnergy,int minBreedEnergy,
                           int breedEnergyLoss, int minMutation, int maxMutation, int genotypeLength, int simulationTime,
                           int dailyEnergyCost, int speed ,int mapVar, int behVar, int plantVar, int mutationVar){
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.grassAmount = grassAmount;
        this.grassEnergyBoost = grassEnergyBoost;
        this.dailyGrassGrowth = dailyGrassGrowth;
        this.animalAmount = animalAmount;
        this.startingEnergy = startingEnergy;
        this.minBreedEnergy = minBreedEnergy;
        this.breedEnergyLoss = breedEnergyLoss;
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;
        this.genotypeLength = genotypeLength;
        this.simulationTime = simulationTime;
        this.dailyEnergyCost = dailyEnergyCost;
        this.speed = speed;
        this.mapVar = mapVar;
        this.behVar = behVar;
        this.plantVar = plantVar;
        this.mutationVar = mutationVar;
    }

    @Override
    public String toString() {
        return "ParameterHolder{" +
                "mapHeight=" + mapHeight +
                ", mapWidth=" + mapWidth +
                ", grassAmount=" + grassAmount +
                ", grassEnergyBoost=" + grassEnergyBoost +
                ", dailyGrassGrowth=" + dailyGrassGrowth +
                ", animalAmount=" + animalAmount +
                ", startingEnergy=" + startingEnergy +
                ", minBreedEnergy=" + minBreedEnergy +
                ", breedEnergyLoss=" + breedEnergyLoss +
                ", minMutation=" + minMutation +
                ", maxMutation=" + maxMutation +
                ", genotypeLength=" + genotypeLength +
                ", mapVar=" + mapVar +
                ", behVar=" + behVar +
                ", plantVar=" + plantVar +
                ", mutationVar=" + mutationVar +
                ", FPS =" + speed +
                '}';
    }

    public int getDailyEnergyCost() {return dailyEnergyCost;}

    public int getSimulationTime() {return simulationTime;}

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getGrassAmount() {
        return grassAmount;
    }

    public int getGrassEnergyBoost() {
        return grassEnergyBoost;
    }

    public int getDailyGrassGrowth() {
        return dailyGrassGrowth;
    }

    public int getAnimalAmount() {
        return animalAmount;
    }

    public int getStartingEnergy() {
        return startingEnergy;
    }

    public int getMinBreedEnergy() {
        return minBreedEnergy;
    }

    public int getBreedEnergyLoss() {
        return breedEnergyLoss;
    }

    public int getMinMutation() {
        return minMutation;
    }

    public int getMaxMutation() {
        return maxMutation;
    }

    public int getGenotypeLength() {
        return genotypeLength;
    }

    public int getMapVar() {
        return mapVar;
    }

    public int getBehVar() {
        return behVar;
    }

    public int getPlantVar() {
        return plantVar;
    }

    public int getMutationVar() {
        return mutationVar;
    }

    public int getSpeed() { return speed;}
}
