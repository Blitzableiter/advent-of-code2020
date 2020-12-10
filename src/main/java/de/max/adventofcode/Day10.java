package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day10
{

  public static List<String> input;

  public static void main(String[] args)
  {
    long start = System.currentTimeMillis();
    System.out.println("Starting at: " + LocalDateTime.now());

    input = Utils.getListFromFilename("input-day10.txt");

    part1();
    part2();

    long end = System.currentTimeMillis();
    System.out.println("Finishing at: " + LocalDateTime.now());
    System.out.println("Runtime: " + (end - start) + "ms");
  }

  private static void part1()
  {
    List<Short> numbers = new ArrayList<>();
    for (String inputNumber : input)
      numbers.add(Short.parseShort(inputNumber));

    Collections.sort(numbers);
    System.out.println(numbers);

    Short currentJoltage = 0;
    Short oneJoltJumps = 0;
    Short threeJoltJumps = 0;
    for (Short adapterJoltage : numbers)
    {
      if (adapterJoltage - currentJoltage == 1)
        oneJoltJumps++;
      if (adapterJoltage - currentJoltage == 3)
        threeJoltJumps++;
      currentJoltage = adapterJoltage;
    }
    // The devices built-in adapter is always 3 jolts higher than the highest adapter
    threeJoltJumps++;

    System.out.println("1 jolt jumps: " + oneJoltJumps);
    System.out.println("3 jolt jumps: " + threeJoltJumps);
    Short solution = (short) (oneJoltJumps * threeJoltJumps);
    System.out.println("solution: " + solution);
  }

  private static void part2()
  {
    List<Integer> numbers = new ArrayList<>();
    for (String inputNumber : input)
      numbers.add(Integer.parseInt(inputNumber));

    // add 0 and device-joltage to list
    numbers.add(0);
    numbers.add(Collections.max(numbers) + 3);
    Collections.sort(numbers);

    List<Integer> differences = new ArrayList<>();
    for (int i = 0; i < numbers.size() - 1; i++)
    {
      differences.add(numbers.get(i + 1) - numbers.get(i));
    }
    System.out.println(differences);
    List<List<Integer>> sublists = new ArrayList<>();
    Integer previousIndex = 0;
    for (int i = 0; i < differences.size(); i++)
    {
      if (differences.get(i)
          .equals(3))
      {
        if (previousIndex < i && !differences.subList(previousIndex, i - 1)
            .isEmpty())
          sublists.add(differences.subList(previousIndex, i));
        previousIndex = i + 1;
      }
    }

    System.out.println(sublists);
    Long numberOfPaths = 1L;
    for (List<Integer> sublist : sublists)
    {
      numberOfPaths *= getNumberOfPaths(sublist);
    }
    System.out.println(numberOfPaths);
  }

  /**
   * @param sublist
   */
  private static Integer getNumberOfPaths(List<Integer> sublist)
  {
    // TODO some algorithm to actually determine those numbers..

    switch (sublist.size())
    {
      // eg. 1 2
      case 1:
        return 1;

      // eg. 1 2 3
      case 2:
        return 2;

      // eg. 1 2 3 4
      case 3:
        return 4;

      // eg. 1 2 3 4 5
      case 4:
        return 7;

      default:
        throw new IllegalArgumentException();
    }
  }

}
