package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Day15
{

  public static List<String> input;

  public static void main(String[] args)
  {
    long start = System.currentTimeMillis();
    System.out.println("Starting at: " + LocalDateTime.now());

    input = Utils.getListFromFilename("input-day15.txt");

    doManyTurns(2020);
    doManyTurns(30_000_000);

    long end = System.currentTimeMillis();
    System.out.println("Finishing at: " + LocalDateTime.now());
    System.out.println("Runtime: " + (end - start) + "ms");
  }

  private static void doManyTurns(Integer maxNumberOfTurns)
  {
    // parsing the starting numbers
    String[] startingNumbers = input.get(0)
        .split(",");
    HashMap<Integer, Integer> turns = new HashMap<>();

    // load all but the last starting numbers in the turns-Map
    String[] allNumbersButLast = Arrays.copyOfRange(startingNumbers, 0, startingNumbers.length - 1);
    for (int i = 0; i < allNumbersButLast.length; i++)
      turns.put(Integer.parseInt(allNumbersButLast[i]), i + 1);

    // the first nextNumber is the last startingNumber
    Integer nextNumber = Integer.parseInt(
        Arrays.copyOfRange(startingNumbers, startingNumbers.length - 1, startingNumbers.length)[0]);

    Integer currentTurn = turns.size();

    while (currentTurn < maxNumberOfTurns - 1)
    {
      currentTurn++;
      // System.out.println("current turn: " + currentTurn + ", number: " + nextNumber);
      Integer roundForNumber = turns.get(nextNumber);

      // nextNumber is new
      if (roundForNumber == null)
      {
        turns.put(nextNumber, currentTurn);
        nextNumber = 0;
        continue;
      }

      // nextNumber is not new
      Integer age = currentTurn - roundForNumber;
      turns.put(nextNumber, currentTurn);
      nextNumber = age;
    }

    System.out.println(maxNumberOfTurns + "th number spoken: " + nextNumber);
  }

}
