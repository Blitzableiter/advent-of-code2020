package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * TODO Klasse kommentieren
 * 
 * @author rumforma
 * @version $Revision:$<br/>
 *          $Date:$<br/>
 *          $Author:$
 */
public class Day5 {
	public static void main(String[] args) {
		List<Integer> seatIds = part1();
		part2(seatIds);
	}

	private static void part2(List<Integer> seatIds) {

		long start = System.currentTimeMillis();
		System.out.println("Starting at: " + LocalDateTime.now());

		for (int i = 0; i < seatIds.size() - 2; i++) {
			if (seatIds.get(i) + 1 != seatIds.get(i + 1)) {
				System.out.println("found it: before: " + seatIds.get(i) + ", after: " + seatIds.get(i + 1));
				System.out.println("my seat id: " + (seatIds.get(i) + 1));
			}
		}

		long end = System.currentTimeMillis();
		System.out.println("Finishing at: " + LocalDateTime.now());
		System.out.println("Runtime: " + (end - start) + "ms");
	}

	private static List<Integer> part1() {
		long start = System.currentTimeMillis();
		System.out.println("Starting at: " + LocalDateTime.now());

		List<String> seatData = Utils.getListFromFilename("input-day5.txt");
		List<Integer> seatIds = new ArrayList<>();

		for (String seat : seatData) {
			int rowNumber = determineRowNumber(seat);
			int seatNumber = determineSeatNumber(seat);
			Integer seatId = determineSeatId(rowNumber, seatNumber);
			seatIds.add(seatId);
		}

		Collections.sort(seatIds);

		System.out.println("Highest seat id: " + seatIds.get(seatIds.size() - 1));

		long end = System.currentTimeMillis();
		System.out.println("Finishing at: " + LocalDateTime.now());
		System.out.println("Runtime: " + (end - start) + "ms");

		return seatIds;
	}

	private static Integer determineSeatId(int rowNumber, int seatNumber) {
		return rowNumber * 8 + seatNumber;
	}

	private static int determineSeatNumber(String seat) {
		char[] seatCharArray = seat.toCharArray();
		int upperLimit = 8;
		int lowerLimit = 0;

		for (int i = 7; i < 10; i++) {
			if (seatCharArray[i] == 'L') {
				upperLimit -= (upperLimit - lowerLimit) / 2;
			} else {
				lowerLimit += (upperLimit - lowerLimit) / 2;
			}
		}

//		if (seatCharArray[9] == 'L')
		return lowerLimit;
//		return upperLimit;
	}

	private static int determineRowNumber(String seat) {

		char[] seatCharArray = seat.toCharArray();
		int upperLimit = 128;
		int lowerLimit = 0;

		for (int i = 0; i < 7; i++) {
			if (seatCharArray[i] == 'F') {
				upperLimit -= (upperLimit - lowerLimit) / 2;
			} else {
				lowerLimit += (upperLimit - lowerLimit) / 2;
			}
		}
//		if (seatCharArray[6] == 'F')
		return lowerLimit;
//		return upperLimit;
	}

}
