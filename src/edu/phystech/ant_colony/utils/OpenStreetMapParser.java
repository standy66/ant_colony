package edu.phystech.ant_colony.utils;

import org.openstreetmap.osmosis.core.container.v0_6.*;
import org.openstreetmap.osmosis.core.domain.v0_6.Entity;
import org.openstreetmap.osmosis.core.task.v0_6.Sink;
import org.openstreetmap.osmosis.xml.common.CompressionMethod;
import org.openstreetmap.osmosis.xml.v0_6.XmlReader;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by andrew on 18.05.15.
 */
public class OpenStreetMapParser {
    private File osmFile;

    public OpenStreetMapParser(File osmFile) {
        this.osmFile = osmFile;
    }

    public RoadGraph parse() {
        RoadGraph graph = new RoadGraph();
        XmlReader osmXmlReader = new XmlReader(osmFile, false, CompressionMethod.None);
        osmXmlReader.setSink(new Sink() {
            @Override
            public void process(EntityContainer entityContainer) {
                entityContainer.process(graph.getAddingEntityProcessor());
            }

            @Override
            public void initialize(Map<String, Object> map) {

            }

            @Override
            public void complete() {

            }

            @Override
            public void release() {

            }
        });
        osmXmlReader.run();
        return graph;
    }
}
