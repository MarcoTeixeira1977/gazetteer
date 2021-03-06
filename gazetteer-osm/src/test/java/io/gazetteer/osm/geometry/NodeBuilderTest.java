package io.gazetteer.osm.geometry;

import static io.gazetteer.osm.TestUtils.COORDINATE_TRANSFORM;
import static io.gazetteer.osm.TestUtils.GEOMETRY_FACTORY;
import static io.gazetteer.osm.TestUtils.NODE_0;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Point;

public class NodeBuilderTest {

  @Test
  public void create() {
    NodeBuilder nodeBuilder = new NodeBuilder(COORDINATE_TRANSFORM, GEOMETRY_FACTORY);
    Point point = nodeBuilder.build(NODE_0);
    assertEquals(point.getX(), NODE_0.getLon());
    assertEquals(point.getY(), NODE_0.getLat());
  }
}
