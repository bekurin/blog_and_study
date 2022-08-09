package b_observerPattern.subject;

import b_observerPattern.observer.Observer;

public interface Subject {
    void registerObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObservers();
}
