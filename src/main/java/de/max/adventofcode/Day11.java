package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Day11 {

	public static List<List<Character>> input;

	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();
		System.out.println("Starting at: " + LocalDateTime.now());

		input = Utils.get2dCharListFromFilename("input-day11.txt");

		part1();
		part2();

		long end = System.currentTimeMillis();
		System.out.println("Finishing at: " + LocalDateTime.now());
		System.out.println("Runtime: " + (end - start) + "ms");
	}

	private static void part1() throws InterruptedException {
		List<List<Character>> gridBefore = new ArrayList<>();
		List<List<Character>> gridAfter = new ArrayList<>();

		// new ArrayList<>(List<>) only shallowly copies and keeps references to inner
		// Lists
		for (List<Character> row : input) {
			gridBefore.add(new ArrayList<>(row));
			gridAfter.add(new ArrayList<>(row));
		}

		Integer changedSeats;
		Integer occupiedSeats;
		Integer epochs = 0;
		do {
			epochs++;
			occupiedSeats = 0;
			changedSeats = 0;
			for (int rowNumber = 0; rowNumber < gridAfter.size(); rowNumber++) {
				for (int seatNumber = 0; seatNumber < gridAfter.get(rowNumber).size(); seatNumber++) {
					boolean spotChanged = evolveSpot(rowNumber, seatNumber, gridBefore, gridAfter);
					if (spotChanged) {
						changedSeats++;
					}
					if (isOccupiedSeat(rowNumber, seatNumber, gridAfter))
						occupiedSeats++;
				}
			}

			// make gridBefore to be gridAfter
			gridBefore = new ArrayList<>();
			for (List<Character> row : gridAfter) {
				gridBefore.add(new ArrayList<>(row));
			}

		} while (changedSeats != 0);
		printGrid(gridAfter);

		System.out.println("occupied seats after " + epochs + " epochs: " + occupiedSeats);

	}

	private static void part2() {
		List<List<Character>> gridBefore = new ArrayList<>();
		List<List<Character>> gridAfter = new ArrayList<>();

		// new ArrayList<>(List<>) only shallowly copies and keeps references to inner
		// Lists
		for (List<Character> row : input) {
			gridBefore.add(new ArrayList<>(row));
			gridAfter.add(new ArrayList<>(row));
		}

		Integer changedSeats;
		Integer occupiedSeats;
		Integer epochs = 0;
		do {
			epochs++;
			occupiedSeats = 0;
			changedSeats = 0;
			for (int rowNumber = 0; rowNumber < gridAfter.size(); rowNumber++) {
				for (int seatNumber = 0; seatNumber < gridAfter.get(rowNumber).size(); seatNumber++) {
					boolean spotChanged = evolveSpotPart2(rowNumber, seatNumber, gridBefore, gridAfter);
					if (spotChanged) {
						changedSeats++;
					}
					if (isOccupiedSeat(rowNumber, seatNumber, gridAfter))
						occupiedSeats++;
				}
			}

			// make gridBefore to be gridAfter
			gridBefore = new ArrayList<>();
			for (List<Character> row : gridAfter) {
				gridBefore.add(new ArrayList<>(row));
			}

		} while (changedSeats != 0);
		printGrid(gridAfter);

		System.out.println("occupied seats after " + epochs + " epochs: " + occupiedSeats);
	}

	private static void printGrid(List<List<Character>> grid) {
		for (List<Character> row : grid) {
			for (Character seat : row)
				System.out.print(seat);
			System.out.println();
		}
	}

	private static boolean evolveSpot(int rowNumber, int seatNumber, List<List<Character>> gridBefore,
			List<List<Character>> gridAfter) {
		// . == floor
		if (gridBefore.get(rowNumber).get(seatNumber).equals('.'))
			return false;

		// spare seat (L)
		if (isSpareSeat(rowNumber, seatNumber, gridBefore) //
				&& howManyOccupiedAroundPiece(rowNumber, seatNumber, gridBefore) == 0) {
			// change when no seats around are occupied
			occupySeat(rowNumber, seatNumber, gridAfter);
			return true;
		}

		// occupied seat (#)
		if (isOccupiedSeat(rowNumber, seatNumber, gridBefore) && //
				howManyOccupiedAroundPiece(rowNumber, seatNumber, gridBefore) >= 4) {
			// change when 4 or more seats around are occupied
			vacateSeat(rowNumber, seatNumber, gridAfter);
			return true;
		}

		// nothing has changed
		return false;
	}

	private static boolean evolveSpotPart2(int rowNumber, int seatNumber, List<List<Character>> gridBefore,
			List<List<Character>> gridAfter) {
		// . == floor
		if (gridBefore.get(rowNumber).get(seatNumber).equals('.'))
			return false;

		// spare seat (L)
		if (isSpareSeat(rowNumber, seatNumber, gridBefore) //
				&& howManyOccupiedAroundPiecePart2(rowNumber, seatNumber, gridBefore) == 0) {
			// change when no seats around are occupied
			occupySeat(rowNumber, seatNumber, gridAfter);
			return true;
		}

		// occupied seat (#)
		if (isOccupiedSeat(rowNumber, seatNumber, gridBefore) && //
				howManyOccupiedAroundPiecePart2(rowNumber, seatNumber, gridBefore) >= 5) {
			// change when 4 or more seats around are occupied
			vacateSeat(rowNumber, seatNumber, gridAfter);
			return true;
		}

		// nothing has changed
		return false;
	}

	private static void occupySeat(int i, int j, List<List<Character>> gridAfter) {
		gridAfter.get(i).set(j, '#');
	}

	private static void vacateSeat(int i, int j, List<List<Character>> gridAfter) {
		gridAfter.get(i).set(j, 'L');
	}

	private static Integer howManyOccupiedAroundPiece(int i, int j, List<List<Character>> gridBefore) {
		Integer occupiedSeats = 0;
		for (int iInner = i - 1; iInner <= i + 1; iInner++)
			for (int jInner = j - 1; jInner <= j + 1; jInner++) {
				// do not check middle seat
				if (iInner == i && jInner == j)
					continue;

				// do not check seat if outside grid
				if (iInner < 0 || iInner > gridBefore.size() - 1 || jInner < 0 || jInner > gridBefore.get(i).size() - 1)
					continue;

				if (isOccupiedSeat(iInner, jInner, gridBefore))
					occupiedSeats++;
			}

		return occupiedSeats;
	}

	private static Integer howManyOccupiedAroundPiecePart2(int rowNumber, int seatNumber,
			List<List<Character>> gridBefore) {
		Integer occupiedSeats = 0;
		Integer seatsFound = 0;
		Character n = null, ne = null, e = null, se = null, s = null, sw = null, w = null, nw = null;
		Integer iterator = 1;

		while (seatsFound != 8) {

			// seat to the north
			if (n == null) {
				if (rowNumber - iterator >= 0) {
					if (isSeat(rowNumber - iterator, seatNumber, gridBefore)) {
						n = gridBefore.get(rowNumber - iterator).get(seatNumber);
						seatsFound++;
						if (isOccupiedSeat(rowNumber - iterator, seatNumber, gridBefore))
							occupiedSeats++;
					}
				} else {
					n = '.';
					seatsFound++;
				}
			}

			// seat to the north east
			if (ne == null) {
				if (rowNumber - iterator >= 0 && seatNumber + iterator < gridBefore.get(rowNumber - iterator).size()) {
					if (isSeat(rowNumber - iterator, seatNumber + iterator, gridBefore)) {
						ne = gridBefore.get(rowNumber - iterator).get(seatNumber + iterator);
						seatsFound++;
						if (isOccupiedSeat(rowNumber - iterator, seatNumber + iterator, gridBefore))
							occupiedSeats++;
					}
				} else {
					ne = '.';
					seatsFound++;
				}
			}

			// seat to the east
			if (e == null) {
				if (seatNumber + iterator < gridBefore.get(rowNumber).size()) {
					if (isSeat(rowNumber, seatNumber + iterator, gridBefore)) {
						e = gridBefore.get(rowNumber).get(seatNumber + iterator);
						seatsFound++;
						if (isOccupiedSeat(rowNumber, seatNumber + iterator, gridBefore))
							occupiedSeats++;
					}
				} else {
					e = '.';
					seatsFound++;
				}
			}

			// seat to the south east
			if (se == null) {
				if (rowNumber + iterator < gridBefore.size()
						&& seatNumber + iterator < gridBefore.get(rowNumber + iterator).size()) {
					if (isSeat(rowNumber + iterator, seatNumber + iterator, gridBefore)) {
						se = gridBefore.get(rowNumber + iterator).get(seatNumber + iterator);
						seatsFound++;
						if (isOccupiedSeat(rowNumber + iterator, seatNumber + iterator, gridBefore))
							occupiedSeats++;
					}
				} else {
					se = '.';
					seatsFound++;
				}
			}

			// seat to the south
			if (s == null) {
				if (rowNumber + iterator < gridBefore.size()) {
					if (isSeat(rowNumber + iterator, seatNumber, gridBefore)) {
						s = gridBefore.get(rowNumber + iterator).get(seatNumber);
						seatsFound++;
						if (isOccupiedSeat(rowNumber + iterator, seatNumber, gridBefore))
							occupiedSeats++;
					}
				} else {
					s = '.';
					seatsFound++;
				}
			}

			// seat to the south west
			if (sw == null) {
				if (rowNumber + iterator < gridBefore.size() && seatNumber - iterator >= 0) {
					if (isSeat(rowNumber + iterator, seatNumber - iterator, gridBefore)) {
						sw = gridBefore.get(rowNumber + iterator).get(seatNumber - iterator);
						seatsFound++;
						if (isOccupiedSeat(rowNumber + iterator, seatNumber - iterator, gridBefore))
							occupiedSeats++;
					}
				} else {
					sw = '.';
					seatsFound++;
				}
			}

			// seat to the west
			if (w == null) {
				if (seatNumber - iterator >= 0) {
					if (isSeat(rowNumber, seatNumber - iterator, gridBefore)) {
						w = gridBefore.get(rowNumber).get(seatNumber - iterator);
						seatsFound++;
						if (isOccupiedSeat(rowNumber, seatNumber - iterator, gridBefore))
							occupiedSeats++;
					}
				} else {
					w = '.';
					seatsFound++;
				}
			}

			// seat to the north west
			if (nw == null) {
				if (rowNumber - iterator >= 0 && seatNumber - iterator >= 0) {
					if (isSeat(rowNumber - iterator, seatNumber - iterator, gridBefore)) {
						nw = gridBefore.get(rowNumber - iterator).get(seatNumber - iterator);
						seatsFound++;
						if (isOccupiedSeat(rowNumber - iterator, seatNumber - iterator, gridBefore))
							occupiedSeats++;
					}
				} else {
					nw = '.';
					seatsFound++;
				}
			}

			iterator++;

		} // end of while
		return occupiedSeats;
	}

	private static boolean isSeat(int i, int j, List<List<Character>> gridBefore) {
		return (isOccupiedSeat(i, j, gridBefore) || isSpareSeat(i, j, gridBefore));
	}

	private static boolean isOccupiedSeat(int i, int j, List<List<Character>> gridBefore) {
		return gridBefore.get(i).get(j).equals('#');
	}

	private static boolean isSpareSeat(int i, int j, List<List<Character>> gridBefore) {
		return gridBefore.get(i).get(j).equals('L');
	}

}
