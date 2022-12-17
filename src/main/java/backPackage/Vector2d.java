package backPackage;

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

    public String toString() {
        String a = String.format("%d", this.x);
        String b = String.format("%d", this.y);
        return "(" + a + "," + b + ")";
    }

    public boolean precedes(Vector2d other) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d upperRight(Vector2d other) {
        int a = Math.max(this.x, other.x);
        int b = Math.max(this.y, other.y);
        return new Vector2d(a, b);
    }

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

    public Vector2d substract(Vector2d other) {
        int a = this.x - other.x;
        int b = this.y - other.y;
        return new Vector2d(a, b);
    }

    public boolean equals(Object other) {
        if (!(other instanceof Vector2d that))
            return false;
        return this.x == that.x && this.y == that.y;
    }

    public Vector2d opposite() {
        int a = -this.x;
        int b = -this.y;
        return new Vector2d(a, b);
    }


}
