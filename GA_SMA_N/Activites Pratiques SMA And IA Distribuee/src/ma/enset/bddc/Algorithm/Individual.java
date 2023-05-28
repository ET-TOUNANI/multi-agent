package ma.enset.bddc.Algorithm;

import java.util.Arrays;

public class Individual implements Comparable {
    GenticAlgorithm genticAlgorithm=new GenticAlgorithm();
    private String[] chromosome = new String[GAUtils.CHROMOSOME_SIZE];
    private int fitness;

    public Individual() {
        fitness = 0;
        for (int j = 0; j < GAUtils.CHROMOSOME_SIZE; j++) {
            chromosome[j] = genticAlgorithm.character();
        }
    }

    public Individual(String[] chromosome) {
        this.chromosome = Arrays.copyOf(chromosome, GAUtils.CHROMOSOME_SIZE);
    }

    public int calculateFitness() {
        int cpt = 0;
        for (String gene : chromosome) {
            if (gene.equals(String.valueOf(GAUtils.ALPHABETS.charAt(cpt)))) {
                fitness++;
            }
            cpt++;
        }
        return fitness;
    }

    public int getFitness() {
        return fitness;
    }

    public String[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(String[] chromosome) {
        this.chromosome = chromosome;
    }

    @Override
    public int compareTo(Object o) {
        Individual individual = (Individual) o;
        if (this.fitness > individual.fitness) {
            return 1;
        } else if (this.fitness < individual.fitness) {
            return -1;
        } else {
            return 0;
        }
    }
}
