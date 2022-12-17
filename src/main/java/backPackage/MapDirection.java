package backPackage;

public enum MapDirection {

    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public String toString() {
        return switch (this){
            case NORTH -> "0";
            case NORTHEAST -> "1";
            case EAST -> "2";
            case SOUTHEAST -> "3";
            case SOUTH -> "4";
            case SOUTHWEST -> "5";
            case WEST -> "6";
            case NORTHWEST -> "7";
        };
    }


}
