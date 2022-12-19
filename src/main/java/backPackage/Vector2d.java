package backPackage;

import java.util.Objects;
import java.util.Random;

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

    //  ładniej wygląda
    public String toString() { return String.format("(%d,%d)", this.x, this.y); }
    // po co?
    public boolean precedes(Vector2d other) { return this.x <= other.x && this.y <= other.y; }
    // po co?
    public boolean follows(Vector2d other) { return this.x >= other.x && this.y >= other.y; }
    // po co?
    public Vector2d upperRight(Vector2d other) {
        int a = Math.max(this.x, other.x);
        int b = Math.max(this.y, other.y);
        return new Vector2d(a, b);
    }
    // po co?
    public Vector2d lowerLeft(Vector2d other) {
        int a = Math.min(this.x, other.x);
        int b = Math.min(this.y, other.y);
        return new Vector2d(a, b);
    }

    public Vector2d add(Vector2d other) {
        int a = this.x + other.x;
        int b = this.y + other.y;
        return new Vector2d(a, b);
    }

    public Vector2d subtract(Vector2d other) {
        int a = this.x - other.x;
        int b = this.y - other.y;
        return new Vector2d(a, b);
    }

    public boolean equals(Object other) {
        if (!(other instanceof Vector2d that))
            return false;
        return this.x == that.x && this.y == that.y;
    }

    @Override // haszowanie chyba jest konieczne do equals'a
    public int hashCode() {
        return Objects.hash(x, y);
    }


    public Vector2d opposite() {
        int a = -this.x;
        int b = -this.y;
        return new Vector2d(a, b);
    }


}
