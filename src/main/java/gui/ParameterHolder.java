package gui;

public class ParameterHolder {

    private final int mapHeight;
    private final int mapWidth;
    private final int grassAmount;
    private final int grassEnergyBoost;
    private final int dailyGrassGrowth;
    private final int animalAmount;
    private final int startingEnergy;
    private final int minBreedEnergy;
    private final int breedEnergyLoss;
    private final int minMutation;
    private final int maxMutation;
    private final int genotypeLength;
    private final int simulationTime;
    private final int dailyEnergyCost;
    private final int mapVar;
    private final int behVar;
    private final int plantVar;
    private final int mutationVar;
    private final int speed;

    private String errorString;

    private final boolean saveCSV;

    public ParameterHolder(int mapHeight, int mapWidth, int grassAmount, int grassEnergyBoost,
                           int dailyGrassGrowth, int animalAmount, int startingEnergy, int minBreedEnergy,
                           int breedEnergyLoss, int minMutation, int maxMutation, int genotypeLength, int simulationTime,
                           int dailyEnergyCost, int speed, int mapVar, int behVar, int plantVar, int mutationVar, boolean saveCSV) {
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
        this.saveCSV = saveCSV;
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
                ", saveCSV=" + saveCSV +
                '}';
    }

    public boolean isSaveCSV() {
        return saveCSV;
    }

    public int getDailyEnergyCost() {
        return dailyEnergyCost;
    }

    public int getSimulationTime() {
        return simulationTime;
    }

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

    public int getSpeed() {
        return speed;
    }

    public boolean checkParameters() {
        if (mapHeight < 0 || mapWidth < 0 || 0 > grassAmount || 0 > grassEnergyBoost || 0 > dailyGrassGrowth || 0 > animalAmount
                || 0 > startingEnergy || 0 > minBreedEnergy || 0 > breedEnergyLoss || 0 > minMutation || 0 > maxMutation
                || 0 > genotypeLength || 0 > mapVar || 0 > behVar || 0 > plantVar || 0 > mutationVar || 0 > speed) {
            errorString = "Value can't be smaller than 0";
        }

        if (mapHeight == 0 || mapWidth == 0 || 0 == grassEnergyBoost || 0 == animalAmount
                || 0 == startingEnergy || 0 == genotypeLength || 0 == speed) {
            errorString = "Value can't be equal to 0";
        }

        if (grassAmount > mapWidth * mapHeight) {
            errorString = "There can't be more grass than space";
            return false;
        }
        if (dailyGrassGrowth > mapWidth * mapHeight) {
            errorString = "There can't grow more grass than is space";
            return false;
        }
        if (minBreedEnergy < breedEnergyLoss) {
            errorString = "The animals can't die due to birth";
            return false;
        }
        if (minMutation > maxMutation) {
            errorString = "The minimum can't be greater than maximum mutation length";
            return false;
        }
        if (genotypeLength < maxMutation) {
            errorString = "The maximum mutation length can't be greater than genotype length";
            return false;
        }
        if (speed <= 0 ){
            errorString = "Simulation delay must be greater than 0";
            return false;
        }
        if(dailyEnergyCost < 0){
            errorString = "Daily energy cost must be grater than 0";
            return false;
        }
        if (minMutation < 0 ){
            errorString = "Mutation parameters can not be negative";
            return false;
        }
        if(simulationTime <= 0){
            errorString = "Simulation time must be greater than 0";
            return false;
        }
        if(animalAmount < 0){
            errorString = "Animal amount can not be negative";
            return false;
        }
        if (grassAmount < 0){
            errorString = "Initial amount of plants can not be negative";
            return false;
        }
        if (grassEnergyBoost < 0){
            errorString = "Energy gained from grass can not be negative";
            return false;
        }
        if (minBreedEnergy < 0){
            errorString = "Energy required to breed can not be negative";
            return false;
        }
        if (breedEnergyLoss < 0){
            errorString = "Breeding cost can not be negative";
            return false;
        }


        return true;
    }

    public String getErrorString() {
        return errorString;
    }
}
