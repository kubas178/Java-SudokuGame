package model;

public interface Observable {

    void notifyObserver();

    void attach(Observer observer);

    void detach(Observer observer);
}
