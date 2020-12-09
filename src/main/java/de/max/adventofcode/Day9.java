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
public class Day9
{

  public static List<String> input;

  public static void main(String[] args)
  {
    long start = System.currentTimeMillis();
    System.out.println("Starting at: " + LocalDateTime.now());

    input = Utils.getListFromFilename("input-day9.txt");

    Long solution = part1(25);
    part2(solution);

    long end = System.currentTimeMillis();
    System.out.println("Finishing at: " + LocalDateTime.now());
    System.out.println("Runtime: " + (end - start) + "ms");
  }

  private static Long part1(final int preambleSize)
  {
    List<Long> numbers = new ArrayList<>();
    for (String inputNumber : input)
      numbers.add(Long.parseLong(inputNumber));

    Long oddOneOut = 0L;

    // Go through List
    for (int i = 0; i < numbers.size() - preambleSize; i++)
    {
      List<Long> sums = new ArrayList<>();
      // Add all combinations in the $preambleSize currently numbers to use
      for (int num1 = i; num1 < i + preambleSize - 1; num1++)
      {
        for (int num2 = num1 + 1; num2 < i + preambleSize; num2++)
        {
          sums.add(numbers.get(num1) + numbers.get(num2));
        }
      }
      if (!sums.contains(numbers.get(i + preambleSize)))
      {
        oddOneOut = numbers.get(i + preambleSize);
        System.out.println("found the odd one out: " + oddOneOut);
        break;
      }
    }
    return oddOneOut;
  }

  private static void part2(Long invalidNumber)
  {
    List<Long> numbers = new ArrayList<>();
    for (String inputNumber : input)
      numbers.add(Long.parseLong(inputNumber));

    Integer indexOfInvalidNumber = numbers.indexOf(invalidNumber);
    // count up from 0
    for (int currentLowest = 0; currentLowest < indexOfInvalidNumber - 1; currentLowest++)
    {
      List<Long> numbersDoingSum = new ArrayList<>();
      numbersDoingSum.add(numbers.get(currentLowest));
      Long sum = numbers.get(currentLowest);

      int currentHighest = currentLowest + 1;
      while (sum.compareTo(invalidNumber) < 0)
      {
        sum += numbers.get(currentHighest);
        numbersDoingSum.add(numbers.get(currentHighest));
        currentHighest++;
      }

      if (sum.equals(invalidNumber))
      {
        System.out.println("found the contiguous set");
        Collections.sort(numbersDoingSum);
        Long lowestNumber = numbersDoingSum.get(0);
        Long highestNumber = numbersDoingSum.get(numbersDoingSum.size() - 1);
        System.out.println("lowest  number: " + lowestNumber);
        System.out.println("highest number: " + highestNumber);
        System.out.println("their sum: " + (lowestNumber + highestNumber));
        break;
      }
    }
  }
}
