package edu.phystech.ant_colony;

import edu.phystech.ant_colony.utils.OpenStreetMapParser;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;

/**
 * Created by andrew on 18.05.15.
 */
public class RoadDisplay implements Runnable {
    public static void main(String[] args) {
        new RoadDisplay().run();
    }

    @Override
    public void run() {
        File streetMapsDir = new File("street_maps");
        Viewer v = new OpenStreetMapParser(new File(streetMapsDir, "dolgopa_small.osm")).parse().toMultiGraph().display(false);
        View view = v.getDefaultView();
        //view.resizeFrame(800, 600);
        //view.getCamera().setViewCenter(40.70820, -74.01113, 0d);
        //view.getCamera().setViewPercent(0.25);

    }
}
