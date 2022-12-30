package backPackage;

/**
 * The interface responsible for interacting with the field of the world.
 */

public interface IWorldMap {

    /**
     * Returns true if it is possible to move and false if it is impossible.
     */
    boolean canMoveTo(Vector2d position);

    /**
     * Place an animal on the map in random place.
     */
    void randomPlace(Animal animal);

    /**
     * Place an animal on the map in random place.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Return an object at a given position with animal prioritised.
     */
    Object objectAt(Vector2d position);

    /**
     * Returns lowLeft Vector.
     */
    Vector2d getLowLeft();

    /**
     * Returns upRight Vector.
     */
    Vector2d getUpRight();

    /**
     * Removes grass from position and return amount of energy it gets.
     */
    int eatGrass(Vector2d position);

    /**
     * Remove an animal form the field.
     */
    void removeAnimal(Animal animal);

    /**
     * Put an animal into the field.
     */
    void insertAnimal(Animal animal, Vector2d position);

}