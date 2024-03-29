/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package org.prashp.lib19;

import org.junit.jupiter.api.Test;
import org.prashp.lib19.EventStream.Event;

import com.google.common.collect.Ordering;
import com.google.common.util.concurrent.Uninterruptibles;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

class EventStreamTest {
    @Test void someLibraryMethodReturnsTrue() {
        EventStream classUnderTest = new EventStream(null, null, 10L);
        assertNotNull(classUnderTest);
        Thread.dumpStack();
    }

    @Test void testEventStream() throws Exception {
        SourceAndSink ss = new SourceAndSink();
        EventStream eventStream = new EventStream(ss, ss, 100);
        eventStream.process();

        // Send 100 events
        IntStream.range(1, 100)
            .forEach(n -> ss.getSourceQueue().offer(new Event(100-n, System.currentTimeMillis() + n, null)));

        // await 150ms & drain events, they should be sorted
        Uninterruptibles.sleepUninterruptibly(150, TimeUnit.MILLISECONDS);
        long[] processedEvents = IntStream.range(1, 100)
            .mapToLong(n -> {
                try {
                    return ss.getSinkQueue().take().getVersion();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            })
            .toArray();

        // asser that the array is sorted
        assertTrue(isSorted(processedEvents));

        eventStream.stop();

    }

    private static boolean isSorted(long[] data) {
        for(int i=0; i<data.length-1; i++) {
            if (data[i] > data[i+1]) return false;
        }
        return true;
    }

    public static class SourceAndSink implements SourceStream, SinkStream {
        private LinkedBlockingQueue<Event> sourceQueue = new LinkedBlockingQueue<>();
        private LinkedBlockingQueue<Event> sinkQueue = new LinkedBlockingQueue<>();

        public LinkedBlockingQueue<Event> getSinkQueue() {
            return sinkQueue;
        }

        public LinkedBlockingQueue<Event> getSourceQueue() {
            return sourceQueue;
        }

        @Override
        public Event getEvent() {
            try {
                return sourceQueue.take();
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
        }

        @Override
        public void writeEvent(Event e) {
            try {
                sinkQueue.put(e);
            } catch (InterruptedException e1) {
                throw new IllegalStateException(e1);
            }
        }

    }
}
