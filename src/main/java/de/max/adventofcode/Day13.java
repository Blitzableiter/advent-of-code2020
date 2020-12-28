package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Day13
{

  public static List<String> input;

  public static void main(String[] args)
  {
    long start = System.currentTimeMillis();
    System.out.println("Starting at: " + LocalDateTime.now());

    input = Utils.getListFromFilename("input-day13.txt");

    part1();
    part2();

    long end = System.currentTimeMillis();
    System.out.println("Finishing at: " + LocalDateTime.now());
    System.out.println("Runtime: " + (end - start) + "ms");
  }

  private static void part1()
  {
    Integer earliestDepartureTime = Integer.parseInt(input.get(0));
    Map<Integer, Integer> lines = new HashMap<>();
    for (String bus : input.get(1)
        .split(","))
    { // get lines from input
      if (bus.equals("x")) // not in service
        continue;

      Integer nextDeparture = 0;
      Integer busSchedule = Integer.parseInt(bus);
      while (nextDeparture < earliestDepartureTime)
      {
        nextDeparture += busSchedule;
      }

      lines.put(busSchedule, nextDeparture);
    }
    Integer nextDeparture = Integer.MAX_VALUE;
    for (Integer time : lines.values())
      if (time < nextDeparture)
        nextDeparture = time;

    Integer busId = 0;
    for (Entry<Integer, Integer> entry : lines.entrySet())
    {
      if (entry.getValue()
          .equals(nextDeparture))
      {
        busId = entry.getKey();
      }
    }

    Integer waitingTime = nextDeparture - earliestDepartureTime;
    System.out.println("busId: " + busId + ", next departure: " + nextDeparture + ", waiting time: "
        + waitingTime + ", factor: " + (busId * waitingTime));

  }

  private static void part2()
  {
    List<Long> busIds = new ArrayList<>();
    List<Long> offsets = new ArrayList<>(); // index 0 = offset between bus index 0 and 1
    String[] busses = input.get(1)
        .split(",");
    Long currentOffset = 1L;
    int lastIndexFilled = Integer.MIN_VALUE;

    // filling the lists of busses and offsets
    for (int i = 0; i < busses.length; i++)
    {
      if (busses[i].equals("x"))
      {
        currentOffset++;
      }
      else
      {
        if (lastIndexFilled != Integer.MIN_VALUE)
          offsets.add(currentOffset);
        busIds.add(Long.parseLong(busses[i]));
        lastIndexFilled = i;
        currentOffset = 1L;
      }
    }

    List<Long> multiples = new ArrayList<>();
    for (Long busId : busIds)
      multiples.add(busId);

    boolean justChanged = false;

    for (int i = 0; i < busIds.size(); i++)
    {
      if (justChanged)
        multiples.set(i, multiples.get(i) + busIds.get(i));

      // calculate multiples until multiple with offset is evenly divisible by next busId
      while ((multiples.get(i) + offsets.get(i)) % busIds.get(i + 1) != 0L)
      {
        if (multiples.get(i) % 1000 == 0)
          System.out.println(multiples.get(0));
        if (i != 0)
        {
          i -= 2;
          justChanged = true;
          break;
        }

        multiples.set(i, multiples.get(i) + busIds.get(i));
      }
    }

    System.out.println(multiples.get(0));
  }

  public static List<Long> primeFactors(Long number)
  {
    List<Long> primeFactors = new ArrayList<>();
    for (long i = 2; i < number; i++)
    {
      while (number % i == 0)
      {
        primeFactors.add(i);
        number = number / i;
      }
    }
    if (number > 2)
      primeFactors.add(number);

    return primeFactors;
  }

  public static Long lowestCommonMultiple(Long smallerNumber, Long largerNumber)
  {
    if (smallerNumber >= largerNumber)
      throw new IllegalArgumentException("First argument must be smaller than second argument");

    Long lowestCommonMultiple = 1L;
    List<Long> largerPrimeFactors = primeFactors(largerNumber);
    for (Long factor : largerPrimeFactors)
      lowestCommonMultiple *= factor;

    for (Long factor : primeFactors(smallerNumber))
      if (!largerPrimeFactors.contains(factor))
        lowestCommonMultiple *= factor;

    return lowestCommonMultiple;
  }

}
