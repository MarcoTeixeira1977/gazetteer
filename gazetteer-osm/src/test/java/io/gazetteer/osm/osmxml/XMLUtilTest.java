package io.gazetteer.osm.osmxml;

import static io.gazetteer.osm.OSMTestUtil.osmXmlData;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.gazetteer.osm.model.Member;
import io.gazetteer.osm.model.Node;
import io.gazetteer.osm.model.Relation;
import io.gazetteer.osm.model.Way;
import java.time.ZoneOffset;
import java.util.Arrays;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.events.XMLEvent;
import org.junit.jupiter.api.Test;

public class XMLUtilTest {

  @Test
  public void readNode() throws Exception {
    XMLEventReader reader = XMLUtil.xmlEventReader(osmXmlData());
    while (reader.hasNext()) {
      XMLEvent event = reader.nextEvent();
      if (event.isStartElement()
          && event.asStartElement().getName().getLocalPart().equals("node")) {
        Node node = XMLUtil.readNode(event.asStartElement(), reader);
        assertEquals(1, node.getInfo().getId());
        assertEquals(10, node.getInfo().getVersion());
        assertEquals(1199243045l, node.getInfo().getTimestamp().toEpochSecond(ZoneOffset.UTC));
        assertEquals(10, node.getInfo().getUserId());
        assertEquals(11, node.getInfo().getChangeset());
        assertEquals(-1, node.getLat());
        assertEquals(-2, node.getLon());
        assertEquals("Me1", node.getInfo().getTags().get("created_by"));
        break;
      }
    }
  }

  @Test
  public void readWay() throws Exception {
    XMLEventReader reader = XMLUtil.xmlEventReader(osmXmlData());
    while (reader.hasNext()) {
      XMLEvent event = reader.nextEvent();
      if (event.isStartElement() && event.asStartElement().getName().getLocalPart().equals("way")) {
        Way way = XMLUtil.readWay(event.asStartElement(), reader);
        assertEquals(1, way.getInfo().getId());
        assertEquals(10, way.getInfo().getVersion());
        assertEquals(1199243045l, way.getInfo().getTimestamp().toEpochSecond(ZoneOffset.UTC));
        assertEquals(10, way.getInfo().getUserId());
        assertEquals(11, way.getInfo().getChangeset());
        assertEquals(Arrays.asList(1l, 2l, 3l), way.getNodes());
        assertEquals("Me1", way.getInfo().getTags().get("created_by"));
        break;
      }
    }
  }

  @Test
  public void readRelation() throws Exception {
    XMLEventReader reader = XMLUtil.xmlEventReader(osmXmlData());
    while (reader.hasNext()) {
      XMLEvent event = reader.nextEvent();
      if (event.isStartElement()
          && event.asStartElement().getName().getLocalPart().equals("relation")) {
        Relation relation = XMLUtil.readRelation(event.asStartElement(), reader);
        assertEquals(1, relation.getInfo().getId());
        assertEquals(10, relation.getInfo().getVersion());
        assertEquals(1199243045l, relation.getInfo().getTimestamp().toEpochSecond(ZoneOffset.UTC));
        assertEquals(10, relation.getInfo().getUserId());
        assertEquals(11, relation.getInfo().getChangeset());
        assertEquals(6, relation.getMembers().get(0).getRef());
        assertEquals(Member.Type.node, relation.getMembers().get(0).getType());
        assertEquals("noderole", relation.getMembers().get(0).getRole());
        break;
      }
    }
  }
}
