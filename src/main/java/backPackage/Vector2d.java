package backPackage;

import java.util.Objects;

public class Vector2d {

    private final int x;

    private final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y  = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() { return String.format("(%d,%d)", this.x, this.y); }

    public Vector2d add(Vector2d other) {
        int a = this.x + other.x;
        int b = this.y + other.y;
        return new Vector2d(a, b);
    }

    public boolean equals(Object other) {
        if (!(other instanceof Vector2d that))
            return false;
        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.x,this.y);
    }
}
