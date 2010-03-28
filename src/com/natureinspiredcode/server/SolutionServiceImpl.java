package com.natureinspiredcode.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.natureinspiredcode.client.SolutionService;
import com.natureinspiredcode.client.model.Solution;

public class SolutionServiceImpl extends RemoteServiceServlet implements
		SolutionService {
	
	@Override
	public Solution findSolution() {
		Date start = new Date();
		List<Solution> population = new ArrayList<Solution>(Solution.POPULATION_SIZE);
		Solution s = populate(population, start);
		if (s!=null && s.isOptimumSolution()) {
			return s.calculateTimeProcessed(start);
		}
		Random r = new Random();
		for (int i = 0; i < 300; i++) {
			Set<Solution> selection = new TreeSet<Solution>();
			while (selection.size() < 3) {
				s = population.get(r.nextInt(Solution.POPULATION_SIZE));
				selection.add(s);
			}

			List<Solution> breedList = new ArrayList<Solution>(selection);
			Collections.sort(breedList);
			Solution male = breedList.get(0), female = breedList.get(1);
			Solution son = male.breed(female, r),
			daughter = female.breed(male, r);

			if (son.isOptimumSolution()) {
				return son.calculateTimeProcessed(start);
			}
			if (daughter.isOptimumSolution()) {
				return daughter.calculateTimeProcessed(start);
			}
			population.add(son);
			population.add(daughter);
		}
		return null;    

	}
	
    private final Solution populate(List<Solution> population, Date start) {
		Random r = new Random();
		while (population.size() < Solution.POPULATION_SIZE) {
			Solution s = new Solution(r);
			if (s.isOptimumSolution()) {
				return s.calculateTimeProcessed(start);
			}
			population.add(s);
		}
		return null;
	}	

}
