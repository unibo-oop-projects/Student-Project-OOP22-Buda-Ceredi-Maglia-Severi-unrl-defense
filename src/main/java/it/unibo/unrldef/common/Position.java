package it.unibo.unrldef.common;

/**
 * the coordinates of a point in a 2d world.
 * 
 * @author francesco.buda3@studio.unibo.it
 */
public final class Position {

    private double x, y;

    /**
     * 
     * @param x the abscissa
     * @param y the ordinate
     */
    public Position(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * 
     * @return the value of the abscissa
     */
    public double getX() {
        return this.x;
    }

    /**
     * 
     * @return the value of the ordinate
     */
    public double getY() {
        return this.y;
    }

    /**
     * 
     * @param x the new x value
     */
    public void setX(final double x) {
        this.x = x;
    }

    /**
     * 
     * @param y the new y value
     */
    public void setY(final double y) {
        this.y = y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) { // NOPMD
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    /**
     * 
     * @return a copy of the position
     */
    public Position copy() {
        return new Position(x, y);
    }
}
