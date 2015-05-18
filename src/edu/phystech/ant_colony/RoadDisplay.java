package edu.phystech.ant_colony;

import edu.phystech.ant_colony.utils.OpenStreetMapParser;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import java.awt.event.*;
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
        view.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                Point3 viewCenter = view.getCamera().getViewCenter();
                double viewPercent = view.getCamera().getViewPercent();
                if (e.getKeyChar() == '+') {
                    view.getCamera().setViewPercent(viewPercent * 0.9);
                } if (e.getKeyChar() == '-') {
                    view.getCamera().setViewPercent(viewPercent / 0.9);
                } if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    viewCenter.moveX(0.1);
                } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    viewCenter.moveX(-0.1);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    viewCenter.moveY(-0.1);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    viewCenter.moveY(0.1);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        //view.resizeFrame(800, 600);
        //view.getCamera().setViewCenter(40.70820, -74.01113, 0d);
        //view.getCamera().setViewPercent(0.25);

    }
}
