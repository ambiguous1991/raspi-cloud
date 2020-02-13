package pl.com.bartusiak.raspiexecutor.datastores;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EvictionQueueTest {

    @Test
    void add() {
        EvictionQueue<Integer> integerEvictionQueue = new EvictionQueue<>(5);

        for (int i=0; i<5; i++){
            integerEvictionQueue.add(i);
        }

        assertEquals(5, integerEvictionQueue.get().size());
        integerEvictionQueue.add(5);
        assertEquals(5, integerEvictionQueue.get().size());
        integerEvictionQueue.add(6);
        integerEvictionQueue.add(7);
        assertEquals(5, integerEvictionQueue.get().size());

        for (int i=0; i< integerEvictionQueue.get().size(); i++){
            System.out.println(integerEvictionQueue.get().get(i));
        }
    }
}