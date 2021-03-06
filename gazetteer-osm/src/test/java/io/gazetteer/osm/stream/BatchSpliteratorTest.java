package io.gazetteer.osm.stream;

import static java.util.Spliterator.IMMUTABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BatchSpliteratorTest {

  private int spliteratorSize = 105;
  private int batchSize = 10;

  private BatchSpliterator<Integer> spliterator;

  @BeforeEach
  public void setUp() {
    List<Integer> ints = new ArrayList<>();
    for (int i = 0; i < spliteratorSize; i++) {
      ints.add(i);
    }
    spliterator = new BatchSpliterator<Integer>(batchSize, IMMUTABLE) {
      int i = 0;

      @Override
      public boolean tryAdvance(Consumer<? super Integer> consumer) {
        if (i++ < spliteratorSize) {
          consumer.accept(i);
          return true;
        }
        return false;
      }
    };
  }

  @Test
  public void tryAdvance() throws Exception {
    for (int i = 0; i < spliteratorSize; i++) {
      assertTrue(spliterator.tryAdvance(block -> {
      }));
    }
    assertFalse(spliterator.tryAdvance(block -> {
    }));
  }

  @Test
  public void forEachRemaining() throws Exception {
    AccumulatingConsumer<Integer> accumulator = new AccumulatingConsumer<>();
    spliterator.forEachRemaining(accumulator);
    assertEquals(accumulator.values().size(), spliteratorSize);
  }

  @Test
  public void trySplit() {
    Spliterator<Integer> s;
    for (int i = 0; i < spliteratorSize / batchSize; i++) {
      s = spliterator.trySplit();
      assertNotNull(s);
      assertEquals(s.estimateSize(), batchSize);
    }
    assertNotNull(spliterator);
    assertEquals(spliterator.trySplit().estimateSize(), spliteratorSize % batchSize);
    assertNull(spliterator.trySplit());
  }

  @Test
  public void estimateSize() {
    assertEquals(spliterator.estimateSize(), Long.MAX_VALUE);
  }


}
