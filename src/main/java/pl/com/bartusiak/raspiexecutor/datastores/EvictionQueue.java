package pl.com.bartusiak.raspiexecutor.datastores;

import java.util.LinkedList;
import java.util.List;

public class EvictionQueue<T> {
    private LinkedList<T> linkedList;
    private int maxSize;

    public EvictionQueue(int maxSize) {
        this.linkedList = new LinkedList<>();
        this.maxSize = maxSize;
    }

    public void add(T obj) {
        if(maxSize<linkedList.size()+1){
            linkedList.remove(linkedList.size()-1);
        }
        linkedList.push(obj);
    }

    public List<T> get() {
        return linkedList;
    }
}
