package com.ayy.algogenetic;

import java.util.*;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 23/02/2021
 * @ Version 1.0
 */
public class Genetic {
    private List<List<City>> populationBefore;
    private List<List<City>> populationAfter;
    private List<City> parentA;
    private List<City> parentB;
    private List<City> child1;
    private List<City> child2;

    private Random rnd = new Random();
    private double probCrossover;
    private double probMutation;
    private int capacityPop;

    private List<City> currentMin;

    public void generate(){
        while(!isEmpty()){
            selection();
            crossover();
            mutation();
            populationAfter.add(parentA);
            populationAfter.add(parentB);
            populationAfter.add(child1);
            populationAfter.add(child2);
            clearParentsAndChilds();
        }
        naturalSelection();
        clearAll();
    }

    public List<City> getCurrentMinList(){
        return currentMin;
    }

    public List<List<City>> getCurrentPopulation(){
        return populationBefore;
    }

    private void naturalSelection(){
        List<List<City>> temp = populationAfter;
        populationAfter = new ArrayList<>();
        temp.sort(new Comparator<List<City>>() {
            @Override
            public int compare(List<City> o1, List<City> o2) {
                return (int) (100*(AlgoUtils.getDistanceTotalOfAOrderedList(o1)-
                        AlgoUtils.getDistanceTotalOfAOrderedList(o2)));
            }
        });
        currentMin = temp.get(0);
        Set<List<City>> exceptedRepeatedList = new HashSet<>();
        for (int i = 0; i < temp.size(); i++) {
            if(exceptedRepeatedList.size()<capacityPop){
                exceptedRepeatedList.add(temp.get(i));
            }
        }
        populationAfter.addAll(exceptedRepeatedList);
    }

    private void selection(){
        parentA = populationBefore.get(0);
        parentB = populationBefore.get(1);
        populationBefore.remove(0);
        populationBefore.remove(0);
    }

    private void crossover(){
        if(rnd.nextDouble()>probCrossover){
            child1 = parentA;
            child2 = parentB;
            return;
        }
        int pointcut = getRangedIndex(parentA.size()-6)+3;

        for (int i = 0; i < pointcut; i++) {
            child1.add(parentA.get(i));
            child2.add(parentB.get(i));
        }

        List<City> acceptedA = new ArrayList<>();
        List<City> acceptedB = new ArrayList<>();
        List<City> repeatedA = new ArrayList<>();
        List<City> repeatedB = new ArrayList<>();

        for (int i = pointcut; i <parentA.size(); i++) {
            City cityA = parentB.get(i);
            City cityB = parentA.get(i);
            if(child1.contains(cityA)){
                repeatedA.add(cityA);
            }else {
                acceptedA.add(cityA);
            }
            if(child2.contains(cityB)){
                repeatedB.add(cityB);
            }else {
                acceptedB.add(cityB);
            }
        }
        child1.addAll(acceptedA);
        child2.addAll(acceptedB);
        child1.addAll(repeatedB);
        child2.addAll(repeatedA);
    }

    private void mutation(){
        if(rnd.nextDouble()>probMutation){
            return;
        }
        int pointcut1 = getRangedIndex(child1.size());
        int pointcut2;
        do {
            pointcut2 = getRangedIndex(child1.size());
        }while(pointcut1 == pointcut2);
        Collections.swap(child1,pointcut1,pointcut2);
        Collections.swap(child2,pointcut1,pointcut2);
    }

    private int getRangedIndex(int range){
        int res = rnd.nextInt()%range;
        return res<0?res+range:res;
    }

    private boolean isEmpty(){
        return populationBefore.size() == 0;
    }

    private void init(){
        clearParentsAndChilds();
        populationAfter = new ArrayList<>();
        if((capacityPop&1)==1){
            populationAfter.add(
                    populationBefore.remove(populationBefore.size()));
        }
    }

    private void clearAll(){
        clearParentsAndChilds();
        populationBefore = populationAfter;
        populationAfter = new ArrayList<>();
    }

    private void clearParentsAndChilds(){
        parentA = new ArrayList<>();
        parentB = new ArrayList<>();
        child1 = new ArrayList<>();
        child2 = new ArrayList<>();
    }

    public Genetic(Set<List<City>> populationInit, double mutation, double crossover) {
        if(crossover>1 || crossover<=0){
            throw new IllegalArgumentException("Crossover Param Error");
        }
        this.probCrossover = crossover;
        this.capacityPop = populationInit.size();
        this.populationBefore = new ArrayList<>();
        this.probMutation = mutation;
        populationBefore.addAll(populationInit);
        init();
    }
}
