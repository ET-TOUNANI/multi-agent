package ma.enset.bddc;

import java.util.ArrayList;
import java.util.List;

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
