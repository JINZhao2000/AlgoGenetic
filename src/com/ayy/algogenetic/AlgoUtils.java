package com.ayy.algogenetic;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 23/02/2021
 * @ Version 1.0
 */

import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 *
 */
public class AlgoUtils {
    private static String filename = "defi250.csv";
    private static List<City> cityList;

    static {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File("./src/com/ayy/algogenetic/"+filename)));
            cityList = new ArrayList<>();
            String location = null;
            City currentCity = null;
            int currentCityNum = 1;

            while((location = reader.readLine())!=null){
                String x = location.substring(0,location.indexOf(";"));
                String y = location.substring(location.indexOf(";")+1);
                Double dx = Double.valueOf(x);
                Double dy = Double.valueOf(y);
                currentCity = new City(currentCityNum, dx, dy);
                cityList.add(currentCity);
                currentCityNum++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File "+filename+" is not exist");
        } catch (IOException e) {
            System.out.println("File "+filename+" is not readable");
        }
    }

    public static List<City> getCityList() {
        return cityList;
    }

    public static double getDistanceOfTwoCities(@NotNull City c1,@NotNull City c2) {
        return Math.sqrt(Math.pow((c1.getX()-c2.getX()),2)+Math.pow((c1.getY()-c2.getY()), 2));
    }

    public static double getDistanceTotalOfAOrderedList(List<City> cities) {
        double distanceTotal = 0;
        int cap = cities.size();
        for (int i=0; i<cap; i++){
            distanceTotal += getDistanceOfTwoCities(
                    cities.get(i),
                    cities.get(
                            (i+1) == cap?0:i+1));
        }
        return distanceTotal;
    }

    public static List<City> getNbCities(int nbCities){
        if (nbCities<0 || nbCities>cityList.size()){
            throw new IllegalArgumentException("Number Error");
        }
        List<City> nbCitiesList = new ArrayList<>();
        for (int i = 0; i < nbCities; i++) {
            nbCitiesList.add(cityList.get(i));
        }
        return nbCitiesList;
    }

    public static Set<List<City>> getNbPopList(List<City> seed, int population){
        Set<List<City>> popList = new HashSet<>();
        while (popList.size()<population){
            List<City> temp = new ArrayList<>();
            temp.addAll(seed);
            Collections.shuffle(temp);
            popList.add(temp);
        }
        return popList;
    }

    public static void createAndShowGUI(List<City> cities) {
        JPanel panel = new Drawer(cities);
        JFrame frame = new JFrame("Line of cities");
        frame.setContentPane(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setLocation(500, 200);
        frame.setVisible(true);
    }
}
