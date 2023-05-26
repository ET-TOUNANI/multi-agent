# Genetic Algorithm with SMA for finding the sentence "Bonjour BDCC"

## Table of Contents
- [Introduction](#introduction)
- [Installation](#installation)
- [Usage](#usage)
- [Code](#code)
- [WHO](#who)
## Introduction

This project is a simple implementation of a genetic algorithm that finds the sentence "Bonjour BDCC" using
an agent-based model. The project is written in Java.

## Installation
> Requirements
- Java 8 or higher
- An IDE (IntelliJ IDEA, Eclipse, Netbeans, etc...)
- Jade platform (included in the project)
- Git
- A cup of coffee ☕

> Steps

- Clone the repository
```bash
git clone
```
- Open the project with your favorite IDE
- Add the jade.jar file to the project's dependencies
- Run the project
- Enjoy

## Usage


## Code

> GAApplication 
```Java
public class GAApplication {


  public static void main(String[] args) {
    GenticAlgorithm ga=new GenticAlgorithm();
    ga.initialize();
    ga.sortPopulation();
    ga.showPopulation();
    int cpt=0;
    while (GAUtils.MAX_ITERATIONS>cpt &&  ga.getBestFintness()<GAUtils.CHROMOSOME_SIZE){
      System.out.println("Iteration : "+cpt);
      ga.crossover();
      ga.mutation();
      ga.sortPopulation();
      ga.showPopulation();
      cpt++;
    }

  }
}
```
> GAUTILS
```Java
public class GAUtils {
  public static final int CHROMOSOME_SIZE=12;
  public static final String solution="Bonjour BDCC";
  public static final int POPULATION_SIZE=20;
  public static final double MUTATION_PROP=0.5;
  public static final int MAX_ITERATIONS=1000;

  public static final String ALPHAS="abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ";
}
```
> Individual
```Java
package ma.enset.bddc;

import java.util.Arrays;
import java.util.Random;

public class Individual implements Comparable{
    private char []chromosome=new char[GAUtils.CHROMOSOME_SIZE];
    private int fitness;


    public Individual() {
        Random random = new Random();
        for (int i = 0; i < GAUtils.CHROMOSOME_SIZE; i++) {
            chromosome[i] = GAUtils.ALPHAS.charAt(random.nextInt(GAUtils.ALPHAS.length()));
        }
    }


    public Individual(char[] chromosome) {
        this.chromosome = Arrays.copyOf(chromosome,GAUtils.CHROMOSOME_SIZE);
    }

    public void calculateFintess(){

        for(int i=0;i<GAUtils.CHROMOSOME_SIZE;i++){
            if(GAUtils.solution.charAt(i)==chromosome[i])
                fitness++;
        }
    }

    public int getFitness() {
        return fitness;
    }

    public char[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(char[] chromosome) {
        this.chromosome = chromosome;
    }

    @Override
    public int compareTo(Object o) {
        Individual individual=(Individual) o;
        if (this.fitness>individual.fitness){
            return  1;
        }else if(this.fitness< individual.fitness){
            return -1;
        }else{
            return 0;
        }
    }
}
```
> GenticAlgorithm
```Java
public class GenticAlgorithm {
    private Individual[] population=new Individual[GAUtils.POPULATION_SIZE];
    private Individual individual1;
    public Individual individual2;
    public void initialize(){
        for (int i=0;i<GAUtils.POPULATION_SIZE;i++) {
            population[i]=new Individual();
            population[i].calculateFintess();
        }
    }

    public void crossover(){
        individual1=new Individual(population[0].getChromosome());
        individual2=new Individual(population[1].getChromosome());

        Random random=new Random();
        int crossPoint=random.nextInt(GAUtils.CHROMOSOME_SIZE-1);
        crossPoint++;
        for (int i = 0; i <crossPoint ; i++) {
         individual1.getChromosome()[i]=population[1].getChromosome()[i];
         individual2.getChromosome()[i]=population[0].getChromosome()[i];
        }


     //   System.out.println("crossover point "+crossPoint);
      //  System.out.println("Individual 1 "+Arrays.toString(individual1.getChromosome()));
       // System.out.println("Individual 2 "+Arrays.toString(individual2.getChromosome()));
    }
    public void showPopulation(){
        for (Individual individual:population) {
            System.out.println(Arrays.toString(individual.getChromosome())+" = "+individual.getFitness());
        }
    }
    public void sortPopulation(){
        Arrays.sort(population, Comparator.reverseOrder());
    }
    public void mutation(){
        Random random=new Random();
        // individual1
        if(random.nextDouble()>GAUtils.MUTATION_PROP){
            int index = random.nextInt(GAUtils.CHROMOSOME_SIZE);
            individual1.getChromosome()[index]=GAUtils.ALPHAS.charAt(random.nextInt(GAUtils.ALPHAS.length()));
        }
        // individual2
        if(random.nextDouble()>GAUtils.MUTATION_PROP){
            int index = random.nextInt(GAUtils.CHROMOSOME_SIZE);
            individual2.getChromosome()[index]=GAUtils.ALPHAS.charAt(random.nextInt(GAUtils.ALPHAS.length()));
        }
       // System.out.println("Après mutation ");
       // System.out.println("Individual 1 "+Arrays.toString(individual1.getChromosome()));
        //System.out.println("Individual 2 "+Arrays.toString(individual2.getChromosome()));
        individual1.calculateFintess();
        individual2.calculateFintess();
        population[GAUtils.POPULATION_SIZE-2]=individual1;
        population[GAUtils.POPULATION_SIZE-1]=individual2;


    }
    public int getBestFintness(){
        return population[0].getFitness();
    }
}
```

## WHO
This project was created by Abderrahmane Ettounani.