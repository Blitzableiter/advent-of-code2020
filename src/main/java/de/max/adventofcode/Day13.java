package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
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
    List<Long> offsets = new ArrayList<>(); // offsets index 0 = offset between busIds index 0 and 1
    Long currentOffset = -1L;
    for (String bus : input.get(1)
        .split(","))
    {
      currentOffset++;
      if (bus.equals("x"))
        continue;

      if (!busIds.isEmpty())
        offsets.add(currentOffset);

      busIds.add(Long.parseLong(bus));
    }

    Integer maxBusIdIndex = findIndexOfMaximumBusId(busIds);

    Long currentDepartureTime = busIds.get(maxBusIdIndex);
    while (!departureTimesWithOffsets(currentDepartureTime, busIds, offsets, maxBusIdIndex))
      currentDepartureTime += busIds.get(maxBusIdIndex);

    System.out.println("busIds: " + busIds);
    System.out.println("offsets: " + offsets);
    System.out.println("currentDepartureTime for maxBusId: " + currentDepartureTime);
    System.out.println(currentDepartureTime);
    System.out.println("departure time for busId == 0: "
        + (currentDepartureTime - offsets.get(maxBusIdIndex - 1)));
  }

  /**
   * @param busIds
   * @return
   */
  private static Integer findIndexOfMaximumBusId(List<Long> busIds)
  {
    List<Long> ids = new ArrayList<>(busIds);
    Collections.sort(ids);
    Long maxBusId = ids.get(ids.size() - 1);
    for (Integer i = 0; i < busIds.size(); i++)
      if (busIds.get(i)
          .equals(maxBusId))
        return i;
    return null;
  }

  public static boolean departureTimesWithOffsets(Long currentDepartureTime, List<Long> busIds,
      List<Long> offsets, Integer maxBusIdIndex)
  {
    if (currentDepartureTime < 0)
      throw new IllegalStateException("currentDepartureTime < 0: " + currentDepartureTime);

    for (Integer i = 0; i < busIds.size(); i++)
    {
      if (i.equals(maxBusIdIndex))
        continue;

      Long maxBusIdOffset = offsets.get(maxBusIdIndex - 1);
      Long currentBusId = busIds.get(i);
      Long currentBusIdOffset = (i != 0) ? offsets.get(i - 1) : 0L;

      Long busDepartureTime = currentDepartureTime - maxBusIdOffset + currentBusIdOffset;

      if (busDepartureTime % currentBusId != 0)
        return false;

    }

    return true;
  }

}
