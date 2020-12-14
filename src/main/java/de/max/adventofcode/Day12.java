package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day12 {

	public static List<String> input;

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println("Starting at: " + LocalDateTime.now());

		input = Utils.getListFromFilename("input-day12.txt");

		part1();
		part2();

		long end = System.currentTimeMillis();
		System.out.println("Finishing at: " + LocalDateTime.now());
		System.out.println("Runtime: " + (end - start) + "ms");
	}

	private static void part1() {
		Integer x = 0;
		Integer y = 0;
		Integer direction = 90; // 0 = n, 90 = e, 180 = s, 270 = w

		for (String instruction : input) {
			Pattern pattern = Pattern.compile("^(\\w)(\\d+)$");
			Matcher matcher = pattern.matcher(instruction);

			if (!matcher.find())
				throw new IllegalArgumentException("input cannot be parsed: " + instruction);

			String action = matcher.group(1);
			Integer value = Integer.parseInt(matcher.group(2));

			switch (action) {
			case "N":
				y += value;
				break;

			case "E":
				x += value;
				break;

			case "S":
				y -= value;
				break;

			case "W":
				x -= value;
				break;

			case "L":
				direction -= value;
				if (direction < 0)
					direction += 360;
				break;

			case "R":
				direction += value;
				if (direction >= 360)
					direction -= 360;
				break;

			case "F":
				switch (direction) {
				case 0:
					y += value;
					break;
				case 90:
					x += value;
					break;

				case 180:
					y -= value;
					break;

				case 270:
					x -= value;
					break;

				default:
					throw new IllegalStateException("direction should not be this value: " + direction);
				}
				break;

			default:
				throw new IllegalArgumentException("Illegal action: " + action);
			}

		}
		System.out.println("Manhattan distance: " + (Math.abs(x) + Math.abs(y)));
	}

	private static void part2() {
		Ship ship = new Ship();

		for (String instruction : input) {
			Pattern pattern = Pattern.compile("^(\\w)(\\d+)$");
			Matcher matcher = pattern.matcher(instruction);

			if (!matcher.find())
				throw new IllegalArgumentException("input cannot be parsed: " + instruction);

			String action = matcher.group(1);
			Integer value = Integer.parseInt(matcher.group(2));

			switch (action) {
			case "N":
				ship.waypoint.toNorth(value);
				break;

			case "E":
				ship.waypoint.toEast(value);
				break;

			case "S":
				ship.waypoint.toSouth(value);
				break;

			case "W":
				ship.waypoint.toWest(value);
				break;

			case "L":
				ship.rotateLeft(value);
				break;

			case "R":
				ship.rotateRight(value);
				break;

			case "F":
				ship.forward(value);
				break;

			default:
				throw new IllegalArgumentException("action not allowed: " + action);
			}

		}

		System.out.println("Manhattan distance: " + ship.getManhattanDistance());

	}

	private static class Ship {
		public Integer x;
		public Integer y;
		public Waypoint waypoint;

		public Ship() {
			x = 0;
			y = 0;
			waypoint = new Waypoint();
		}

		public Integer getManhattanDistance() {
			return Math.abs(x) + Math.abs(y);
		}

		public void forward(Integer times) {
			this.x += waypoint.x;
			this.y += waypoint.y;
			if (times > 1)
				forward(times - 1);
		}

		public void rotateRight(Integer degrees) {
			// 10 x, 4 y -> 4 x, -10 y
			// pos x -> neg y
			// neg x -> pos y
			// pos y -> pos x
			// neg y -> neg x

			Integer temp = waypoint.x.intValue();
			waypoint.x = 0 + waypoint.y.intValue();
			waypoint.y = 0 - temp.intValue();

			if (degrees > 90)
				rotateRight(degrees - 90);
		}

		public void rotateLeft(Integer degrees) {
			// 10 x, 4 y -> -4 x, 10 y
			// pos x -> pos y
			// neg x -> neg y
			// pos y -> neg x
			// neg y -> pos x

			Integer temp = waypoint.x.intValue();
			waypoint.x = 0 - waypoint.y.intValue();
			waypoint.y = 0 + temp.intValue();

			if (degrees > 90)
				rotateLeft(degrees - 90);
		}

		private class Waypoint {

			// position relative to ship
			public Integer x;
			public Integer y;

			public Waypoint() {
				x = 10;
				y = 1;
			}

			public void toNorth(Integer distance) {
				this.y += distance;
			}

			public void toEast(Integer distance) {
				this.x += distance;
			}

			public void toSouth(Integer distance) {
				this.y -= distance;
			}

			public void toWest(Integer distance) {
				this.x -= distance;
			}
		}
	}

}
