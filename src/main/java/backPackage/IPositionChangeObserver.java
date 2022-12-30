package backPackage;
public interface IPositionChangeObserver {
    /**
     * It updates change of position for Animal ani
     */
    void positionChanged(Animal ani, Vector2d oldPosition, Vector2d newPosition);
}
