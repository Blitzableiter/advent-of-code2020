package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Day13 {

	public static List<String> input;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println("Starting at: " + LocalDateTime.now());

		input = Utils.getListFromFilename("input-day13.txt");

		part1();
		part2();

		long end = System.currentTimeMillis();
		System.out.println("Finishing at: " + LocalDateTime.now());
		System.out.println("Runtime: " + (end - start) + "ms");
	}

	private static void part1() {
		Integer earliestDepartureTime = Integer.parseInt(input.get(0));
		Map<Integer, Integer> lines = new HashMap<>();
		for (String bus : input.get(1).split(",")) { // get lines from input
			if (bus.equals("x")) // not in service
				continue;

			Integer nextDeparture = 0;
			Integer busSchedule = Integer.parseInt(bus);
			while (nextDeparture < earliestDepartureTime) {
				nextDeparture += busSchedule;
			}

			lines.put(busSchedule, nextDeparture);
		}
		Integer nextDeparture = Integer.MAX_VALUE;
		for (Integer time : lines.values())
			if (time < nextDeparture)
				nextDeparture = time;

		Integer busId = 0;
		for (Entry<Integer, Integer> entry : lines.entrySet()) {
			if (entry.getValue().equals(nextDeparture)) {
				busId = entry.getKey();
			}
		}

		Integer waitingTime = nextDeparture - earliestDepartureTime;
		System.out.println("busId: " + busId + ", next departure: " + nextDeparture + ", waiting time: " + waitingTime
				+ ", factor: " + (busId * waitingTime));

	}

	private static void part2() {
		List<Long> busIds = new ArrayList<>();
		for (String bus : input.get(1).split(",")) {
			if (bus.equals("x")) {
				busIds.add(0L);
				continue;
			}
			busIds.add(Long.parseLong(bus));
		}
		System.out.println(busIds);

		Integer t = 0;
		while (true) {

		}
	}

}
