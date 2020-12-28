package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14
{

  public static List<String> input;

  public static void main(String[] args)
  {
    long start = System.currentTimeMillis();
    System.out.println("Starting at: " + LocalDateTime.now());

    input = Utils.getListFromFilename("input-day14.txt");

    // part1();
    part2();

    long end = System.currentTimeMillis();
    System.out.println("Finishing at: " + LocalDateTime.now());
    System.out.println("Runtime: " + (end - start) + "ms");
  }

  private static void part1()
  {
    Pattern patternMask = Pattern.compile("mask\\s\\=\\s(\\w+)");
    Pattern patternMem = Pattern.compile("mem\\[(\\d+).+\\s(\\d+)");
    Map<Long, Long> memory = new HashMap<>();
    List<Character> currentMask = new ArrayList<>();
    for (String command : input)
    {
      Matcher matcher = patternMask.matcher(command);
      if (matcher.find())
      {
        currentMask = new ArrayList<>();
        for (char maskCharacter : matcher.group(1)
            .toCharArray())
          currentMask.add(maskCharacter);

        System.out.println(currentMask);
      }

      matcher = patternMem.matcher(command);
      if (matcher.find())
      {
        System.out.println(
            "found value " + matcher.group(2) + " for memory position " + matcher.group(1));
        Long value = Long.parseLong(matcher.group(2));
        String valueAsBytes = Long.toBinaryString(value);
        String maskedValueAsString = maskValue(valueAsBytes, currentMask);

        Long maskedValue = Long.parseLong(maskedValueAsString, 2);

        memory.put(Long.parseLong(matcher.group(1)), maskedValue);
      }

    }
    System.out.println(memory);
    Long sum = 0L;
    for (Long value : memory.values())
      sum += value;
    System.out.println("sum: " + sum);

  }

  private static String maskValue(String valueAsBytes, List<Character> currentMask)
  {
    List<Character> valueBytes = new ArrayList<>();
    Integer zeroesToFill = currentMask.size() - valueAsBytes.length();

    for (int i = 0; i < zeroesToFill; i++)
      valueBytes.add('0');

    for (Character actualByte : valueAsBytes.toCharArray())
      valueBytes.add(actualByte);

    for (int i = 0; i < currentMask.size(); i++)
    {
      if (currentMask.get(i)
          .equals('X'))
        continue;

      valueBytes.set(i, currentMask.get(i));
    }

    StringBuilder sb = new StringBuilder();
    for (Character changedByte : valueBytes)
      sb.append(changedByte);

    return sb.toString();
  }

  private static String maskValuePart2(String valueAsBytes, List<Character> currentMask)
  {
    List<Character> valueBytes = new ArrayList<>();
    Integer zeroesToFill = currentMask.size() - valueAsBytes.length();

    for (int i = 0; i < zeroesToFill; i++)
      valueBytes.add('0');

    for (Character actualByte : valueAsBytes.toCharArray())
      valueBytes.add(actualByte);

    for (int i = 0; i < currentMask.size(); i++)
    {
      switch (currentMask.get(i))
      {
        case '0':
          continue;

        case '1':
        case 'X':
          valueBytes.set(i, currentMask.get(i));
          break;

        default:
          throw new IllegalArgumentException(
              "Illegal value " + currentMask.get(i) + " in value " + valueAsBytes);
      }
    }

    StringBuilder sb = new StringBuilder();
    for (Character changedByte : valueBytes)
      sb.append(changedByte);

    return sb.toString();
  }

  private static void part2()
  {
    Pattern patternMask = Pattern.compile("mask\\s\\=\\s(\\w+)");
    Pattern patternMem = Pattern.compile("mem\\[(\\d+).+\\s(\\d+)");
    Map<String, Long> memory = new HashMap<>();
    List<Character> currentMask = new ArrayList<>();
    for (String command : input)
    {
      Matcher matcher = patternMask.matcher(command);
      if (matcher.find())
      {
        currentMask = new ArrayList<>();
        for (char maskCharacter : matcher.group(1)
            .toCharArray())
          currentMask.add(maskCharacter);

        System.out.println(currentMask);
      }

      matcher = patternMem.matcher(command);
      if (matcher.find())
      {
        System.out.println(
            "found value " + matcher.group(2) + " for memory position " + matcher.group(1));
        Long initialAddress = Long.parseLong(matcher.group(1));
        Long value = Long.parseLong(matcher.group(2));
        modifyAndPutToMemory(memory, initialAddress, value, currentMask);
      }

    }
    for (String key : memory.keySet())
      System.out.println(key + ": " + memory.get(key));
    Long sum = 0L;
    for (Long value : memory.values())
      sum += value;
    System.out.println("sum: " + sum);
  }

  private static void modifyAndPutToMemory(Map<String, Long> memory, Long initialAddress,
      Long value, List<Character> currentMask)
  {
    // do the changes for the 1s and 0s
    String maskedAddressStep1 = maskValuePart2(Long.toBinaryString(initialAddress), currentMask);
    // System.out.println("maskedAddressStep1: " + maskedAddressStep1);
    List<String> addressesToWriteTo = getAllAddressesForMaskedAddress(maskedAddressStep1);
    for (String address : addressesToWriteTo)
    {
      memory.put(address, value);
    }
  }

  /**
   * @param maskedAddressStep1
   * @return
   */
  private static List<String> getAllAddressesForMaskedAddress(final String maskedAddressStep1)
  {
    List<String> listOfAddresses = new ArrayList<>();
    if (!maskedAddressStep1.contains("X"))
    {
      // No more Xs to float
      listOfAddresses.add(maskedAddressStep1);
      return listOfAddresses;
    }

    // Given address contains at least 1 X
    String addressAlternative0 = maskedAddressStep1.replaceFirst("X", "0");
    String addressAlternative1 = maskedAddressStep1.replaceFirst("X", "1");

    listOfAddresses.addAll(getAllAddressesForMaskedAddress(addressAlternative0));
    listOfAddresses.addAll(getAllAddressesForMaskedAddress(addressAlternative1));

    return listOfAddresses;
  }

}
