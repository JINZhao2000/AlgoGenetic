package com.ayy.test;

import com.ayy.algogenetic.AlgoUtils;
import com.ayy.algogenetic.City;
import com.ayy.algogenetic.Genetic;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 23/02/2021
 * @ Version 1.0
 */
public class Application {
    public static final int POPULATION = 50;
    public static final int NB_GENERATION = 200;
    public static final int CITY_CAPACITY = 10;
    public static final double MUTATION = 0.5;
    public static final double CROSSOVER = 1;

    public static void main(String[] args) {
        test12CitiesEnCircle();
    }

    public static void test10Cities(){
        Genetic genetic = new Genetic(AlgoUtils.getNbPopList(AlgoUtils.getNbCities(CITY_CAPACITY),POPULATION),MUTATION,CROSSOVER);
        for (int i = 0; i < NB_GENERATION; i++) {
            genetic.generate();
        }
        List<City> cities = genetic.getCurrentMinList();
        AlgoUtils.createAndShowGUI(cities);
        System.out.println(AlgoUtils.getDistanceTotalOfAOrderedList(cities));
    }

    public static void test12CitiesEnCircle(){
        Genetic genetic = new Genetic(AlgoUtils.getNbPopList(create12CitiesEnCircle(),12),MUTATION,CROSSOVER);
        for (int i = 0; i < NB_GENERATION; i++) {
            genetic.generate();
        }
        List<City> cities = genetic.getCurrentMinList();
        AlgoUtils.createAndShowGUI(cities);
        System.out.println(AlgoUtils.getDistanceTotalOfAOrderedList(cities));
    }

    private static List<City> create12CitiesEnCircle(){
        double rad = 0;
        double centerX = 0.5;
        double centerY = 0.5;
        List<City> cities = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            cities.add(new City(
                    (i+1),centerX+0.5*Math.sin(rad),centerY+0.5*Math.cos(rad)
            ));
            rad+=Math.PI/6;
        }
        return cities;
    }
}
