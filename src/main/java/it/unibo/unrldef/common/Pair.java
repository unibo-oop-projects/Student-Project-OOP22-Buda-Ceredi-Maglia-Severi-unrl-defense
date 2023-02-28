package it.unibo.unrldef.common;

public class Pair<T,S> {
    private T firstElement;
    private S secondElement;

    public Pair(T firstElement, S secondElement) {
        this.firstElement = firstElement;
        this.secondElement = secondElement;
    }

    public T getFirst() {
        return this.firstElement;
    }

    public S getSecond() {
        return this.secondElement;
    }

    public void setFirstElement(T firstElement) {
        this.firstElement = firstElement;
    }

    public void setSecondElement(S secondElement) {
        this.secondElement = secondElement;
    }

    public Pair<T,S> copy() {
        return new Pair<T,S>(firstElement, secondElement);
    }
    
}
