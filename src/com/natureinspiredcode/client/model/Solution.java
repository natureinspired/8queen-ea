package com.natureinspiredcode.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Solution represents the solution to the 8 queen puzzle 
 * that is created from the evolutionary algorithm.
 * 
 * @author james.a.perry
 *
 */
public class Solution implements Serializable, Comparable<Solution> {
	public static final int BOARD_SIZE = 8;
	public static final int DIAGONALS_SIZE = 2 * BOARD_SIZE-1;
	public static final int POPULATION_SIZE = 150;
	public static final int HALF_WAY = BOARD_SIZE / 2;
	
	private List<Integer> board = new ArrayList<Integer>(BOARD_SIZE);
	private Integer fitness;
	private long timeProcessed;

	/**
	 * No argument constructor to satisfy the GWT serialization policy.
	 */
	public Solution() {}

	/**
	 * The constructor that creates a solution randomly.
	 * 
	 * @param r
	 */
	public Solution(Random r) {
		Set<Integer> set = new LinkedHashSet<Integer>(BOARD_SIZE);
		while (set.size() < BOARD_SIZE) {
			set.add(r.nextInt(BOARD_SIZE)+1);
		}
		Iterator<Integer> it = set.iterator();
		while (it.hasNext()) {
			board.add(it.next());
		}
		fitness = calculateFitness();
	}

	/**
	 * Calculates the fitness of the solution to determine
	 *  the quality of the solution.
	 * 
	 * @return the number of collisions in the solution
	 */
	private final int calculateFitness() {
		int[] left = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		right = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		for (int i = 0; i < BOARD_SIZE; i++) {
			left[i+board.get(i) - 1] = left[i + board.get(i) - 1] += 1;
			right[(BOARD_SIZE-(i+1)+board.get(i))-1] = right[(BOARD_SIZE-(i+1)+board.get(i))-1]+=1;
		}
		int sum = 0;
		for (int i = 0; i < DIAGONALS_SIZE; i++) {
			int counter = 0;
			if (left[i] > 1) {
				counter += left[i]-1;
			}
			if (right[i] >1) {
				counter += right[i]-1;
			}
			sum += counter;
		}
		Set<Integer> set = new HashSet<Integer>(board);
		if (set.size() < BOARD_SIZE) {
			sum += BOARD_SIZE - set.size();
		}
		return sum;
	}

	/**
	 * The constructor called for creating offspring and then mutating to simulate evolution. 
	 * 
	 * @param board
	 */
	private Solution(List<Integer> board) {
		this.board.addAll(board);
		mutate();
		fitness = calculateFitness();
	}

	/**
	 * Creates offspring by using a cross-over breeding which creates a children. 
	 * The child consist of the instance's first half of the board and the 
	 * partner's second half.  
	 * 
	 * @param partner the partner to breed with
	 * @param r
	 * @return the offspring
	 */
	public Solution breed(Solution partner, Random r) {
		List<Integer> firstHalf = board.subList(0, HALF_WAY),
		secondHalf = partner.board.subList(HALF_WAY, BOARD_SIZE),
		childBoard = new ArrayList<Integer>(BOARD_SIZE);
		Set<Integer> b = new HashSet<Integer>(BOARD_SIZE);
		for (Integer e : firstHalf) {
			childBoard.add(e);
			b.add(e);
		}
		for (Integer e : secondHalf) {
			e = verifyElement(e, b, r);
			childBoard.add(e);
		}
		return new Solution(childBoard);
	}

	/**
	 * A recursive function to ensure that the random number generator does not 
	 * generate a number that is not already in the set.
	 * 
	 * @param e the number to be verified
	 * @param b the set to verify against
	 * @param r
	 * @return
	 */
	private final Integer verifyElement(Integer e, Set<Integer> b, Random r) {
		if (!b.contains(e)) {
			return e;
		}
		return verifyElement(r.nextInt(BOARD_SIZE)+1, b, r);
	}

	/**
	 * The mutation for the offspring which non deterministically selects two indices 
	 * of its board and swaps them.
	 * 
	 */
	private final void mutate() {
		Random r = new Random();
		int i1 = r.nextInt(BOARD_SIZE), i2 = r.nextInt(BOARD_SIZE);
		while (i1 == i2) {
			i2 = r.nextInt(BOARD_SIZE);
		}
		Integer tmp = board.get(i1);
		board.set(i1, board.get(i2));
		board.set(i2, tmp);
	}

	public int compareTo(Solution o) {
		return fitness.compareTo(o.fitness);
	}

	/**
	 * Computes if the solution is optimum.
	 * 
	 * @return a board that has no collisions
	 */
	public boolean isOptimumSolution() {
		return fitness == 0;
	}

	public int fitness() {
		return fitness;
	}

	public long timeProcessed() {
		return timeProcessed;
	}

	@Override
	public String toString() {
		return board.toString();
	}

	public Solution calculateTimeProcessed(Date start) {
		timeProcessed = new Date().getTime() - start.getTime();
		return this;
	}

	public List<Integer> board() {
		return board;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((board == null) ? 0 : board.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Solution other = (Solution) obj;
		if (board == null) {
			if (other.board != null)
				return false;
		} else if (!board.equals(other.board))
			return false;
		return true;
	}

}
