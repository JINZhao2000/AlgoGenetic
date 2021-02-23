package com.ayy.algogenetic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 23/02/2021
 * @ Version 1.0
 */
public class Drawer extends JPanel {
    private List<List<Point>> lines = new ArrayList<>();
    private List<Point> curreLine;

    public Drawer(List<City> cities) {
        for (int i = 0; i < cities.size(); i++) {
            curreLine = new ArrayList<Point>();
            lines.add(curreLine);
            City ville = cities.get((i)%cities.size());
            City villeSuivant = cities.get((i+1)%cities.size());
            curreLine.add(new Point((int)(ville.getX()*500),(int)(ville.getY()*500)));
            curreLine.add(new Point((int)(villeSuivant.getX()*500),(int)(villeSuivant.getY()*500)));
            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Color[] c = {Color.BLACK,Color.RED};
        int compte = 0;
        for(List<Point> points : lines) {
            int x1 = -1, y1 = -1, x2 = -1, y2 = -1;
            for (Point point:points) {
                x2 = (int)point.getX();
                y2 = (int)point.getY();
                if(x1!=-1) {
                    g.drawOval(x1-5, y1-5, 10, 10);
                    g.setColor(c[compte%2]);
                    g.drawLine(x1, y1, x2, y2);
                    compte++;
                }
                x1 = x2;
                y1 = y2;
            }
        }
    }
}
