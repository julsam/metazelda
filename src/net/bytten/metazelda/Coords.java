package net.bytten.metazelda;

public class Coords implements Comparable<Coords> {

    public final int x, y;
    
    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public Coords nextInDirection(Direction d) {
        // Return the coordinates of the next room/space in the given direction
        return new Coords(x + d.x, y + d.y);
    }
    
    @Override
    public boolean equals(Object other) {
         if (other instanceof Coords) {
             Coords o = (Coords)other;
             return this.x == o.x && this.y == o.y;
         } else {
             return super.equals(other);
         }
    }

    @Override
    public int compareTo(Coords other) {
        // For Dungeon's TreeMap
        int d = this.x - other.x;
        if (d == 0) {
            d = this.y - other.y;
        }
        return d;
    }
    
    public boolean isAdjacent(Coords other) {
        int dx = Math.abs(x - other.x),
            dy = Math.abs(y - other.y);
        return (dx == 1 && dy == 0) || (dx == 0 && dy == 1);
    }

    public Direction getDirectionTo(Coords other) {
        int dx = x - other.x,
            dy = y - other.y;
        assert dx == 0 || dy == 0;
        if (dx < 0) return Direction.E;
        if (dx > 0) return Direction.W;
        if (dy < 0) return Direction.S;
        if (dy > 0) return Direction.N;
        throw new RuntimeException("Coords do not align in one dimension, or are equal");
    }
    
    public String toString() {
        return x+","+y;
    }
}
