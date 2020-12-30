package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16
{

  public static List<String> input;
  private static List<String> validTickets;

  public static void main(String[] args)
  {
    long start = System.currentTimeMillis();
    System.out.println("Starting at: " + LocalDateTime.now());

    input = Utils.getListFromFilename("input-day16.txt");

    part1();
    part2();

    long end = System.currentTimeMillis();
    System.out.println("Finishing at: " + LocalDateTime.now());
    System.out.println("Runtime: " + (end - start) + "ms");
  }

  private static void part1()
  {
    // all valid numbers
    Set<Integer> validNumbers = new HashSet<>();

    Pattern rangesPattern = Pattern.compile(".+\\:\\s(\\d+)\\-(\\d+)\\sor\\s(\\d+)\\-(\\d+)");
    for (String line : input)
    {
      Matcher rangesMatcher = rangesPattern.matcher(line);
      if (!rangesMatcher.find())
      {
        continue;
      }
      Integer min1 = Integer.parseInt(rangesMatcher.group(1));
      Integer max1 = Integer.parseInt(rangesMatcher.group(2));
      Integer min2 = Integer.parseInt(rangesMatcher.group(3));
      Integer max2 = Integer.parseInt(rangesMatcher.group(4));

      for (Integer i = min1; i <= max1; i++)
      {
        validNumbers.add(i);
      }
      for (Integer i = min2; i <= max2; i++)
      {
        validNumbers.add(i);
      }
    }

    System.out.println(validNumbers);
    List<Integer> invalidNumbers = new ArrayList<>();
    validTickets = new ArrayList<>();
    // go backwards through input to get nearby tickets
    for (int i = input.size() - 1; i >= 0; i--)
    {
      String line = input.get(i);

      // break, wenn title "nearby tickets" is found
      if (line.matches("nearby\\stickets.+"))
        break;

      Pattern ticketPattern = Pattern.compile("([\\d+\\,*]+)");
      Matcher ticketMatcher = ticketPattern.matcher(line);

      if (!ticketMatcher.find())
        throw new IllegalArgumentException(
            "severe problem: Input file is not as expected: " + line);

      boolean containsInvalidNumbers = false;
      for (String number : line.split(","))
        if (!validNumbers.contains(Integer.parseInt(number)))
        {
          // number on ticket is not in set of valid numbers
          invalidNumbers.add(Integer.parseInt(number));
          containsInvalidNumbers = true;
        }

      if (!containsInvalidNumbers)
        validTickets.add(line);
    }

    System.out.println(invalidNumbers);
    Integer ticketErrorRate = 0;
    for (Integer number : invalidNumbers)
      ticketErrorRate += number;

    System.out.println("Ticket scanning error rate: " + ticketErrorRate);
    // System.out.println();
    // System.out.println("valid tickets:");
    // for (String ticket : validTickets)
    // System.out.println(ticket);
  }

  private static void part2()
  {
    // validTickets
    Pattern rulePattern = Pattern.compile("(.+)\\:\\s(\\d+)\\-(\\d+)\\sor\\s(\\d+)\\-(\\d+)");

    List<List<Boolean>> listOfPossibleFields = new ArrayList<>();
    for (String line : input)
    {
      Matcher ruleMatcher = rulePattern.matcher(line);

      if (!ruleMatcher.find())
        // all rules have been worked through
        break;

      // extracting all valid numbers for current rule
      Set<Integer> validNumbers = new HashSet<>();
      Integer min1 = Integer.parseInt(ruleMatcher.group(2));
      Integer max1 = Integer.parseInt(ruleMatcher.group(3));
      Integer min2 = Integer.parseInt(ruleMatcher.group(4));
      Integer max2 = Integer.parseInt(ruleMatcher.group(5));
      for (Integer i = min1; i <= max1; i++)
        validNumbers.add(i);
      for (Integer i = min2; i <= max2; i++)
        validNumbers.add(i);

      System.out.println("valid numbers for " + ruleMatcher.group(1));
      System.out.println(validNumbers);

      List<Boolean> possibleFields = null;
      for (String ticket : validTickets)
      {
        List<Integer> fieldsInTicket = new ArrayList<>();
        String[] StringsOnTicket = ticket.split(",");
        for (String string : StringsOnTicket)
          fieldsInTicket.add(Integer.parseInt(string));

        if (possibleFields == null)
        {
          possibleFields = new ArrayList<>();
          for (Integer field : fieldsInTicket)
            possibleFields.add(true);
        }

        for (int i = 0; i < fieldsInTicket.size(); i++)
        {
          if (!validNumbers.contains(fieldsInTicket.get(i)))
            possibleFields.set(i, false);
        }

      }
      System.out.println(possibleFields);
      listOfPossibleFields.add(possibleFields);
    }
    System.out.println();
    System.out.println("lists:");
    for (List<Boolean> list : listOfPossibleFields)
      System.out.println(list);
    System.out.println();

    List<Integer> orderOfRules = new ArrayList<>();
    for (List<Boolean> list : listOfPossibleFields)
    {
      Integer countOfTrues = 0;
      for (Boolean bool : list)
      {
        if (bool)
          countOfTrues++;
      }
      orderOfRules.add(countOfTrues);
    }
    System.out.println("order of rules");
    System.out.println(orderOfRules);

    List<Integer> indexes = new ArrayList<>();
    // there is exactly 1 rule with 1 true, 1 rule with 2 true, 1 rule with 3 true etc...
    for (int i = 1; i <= orderOfRules.size(); i++)
    {
      indexes.add(orderOfRules.indexOf(i));
    }
    System.out.println("indexes of rules where this.index == index of rule.value");
    System.out.println(indexes);

    System.out.println();
    System.out.println("listOfPossibleFields sorted:");

    // key: index of rule, value: index of field
    Map<Integer, Integer> ruleToField = new HashMap<>();
    List<Boolean> lastBools = null;
    for (int i = 0; i < indexes.size(); i++)
    {
      Integer index = indexes.get(i);
      List<Boolean> bools = listOfPossibleFields.get(index);
      // System.out.println(bools);

      // iterate over entries in possibleFieldsList
      // for (int i = 0; i < bools.size(); i++)
      // {
      // if (bools.get(i))
      // System.out.println(true);
      // }

      Integer diffIndex = findDifferencesOfTrue(lastBools, bools);
      System.out.println("Rule no. " + index + " is for field no. " + diffIndex);
      ruleToField.put(index, diffIndex);

      lastBools = listOfPossibleFields.get(index);

    }

    Map<String, Integer> ruleNameToField = new HashMap<>();
    Set<String> ruleStartsWithDeparture = new HashSet<>();
    Integer currentRuleIndex = 0;

    Map<Integer, Integer> fieldIndexToValue = new HashMap<>();

    // this sucks because RegEx sucks
    boolean lineIsYourTicketNumbers = false;
    for (String line : input)
    {
      if (lineIsYourTicketNumbers)
      {
        lineIsYourTicketNumbers = false;
        String[] fields = line.split(",");
        for (int i = 0; i < fields.length; i++)
        {
          fieldIndexToValue.put(i, Integer.parseInt(fields[i]));
        }
        System.out.println(fieldIndexToValue);

        // "your ticket:... is the last piece of text we need
        break;
      }

      Pattern ruleNamePattern = Pattern.compile("(.+)\\:\\s\\d+\\-\\d+\\sor\\s\\d+\\-\\d+");
      Matcher ruleNameMatcher = ruleNamePattern.matcher(line);
      if (ruleNameMatcher.find())
      {
        String ruleName = ruleNameMatcher.group(1);
        ruleNameToField.put(ruleName, ruleToField.get(currentRuleIndex));
        if (ruleName.startsWith("departure"))
          ruleStartsWithDeparture.add(ruleName);
        currentRuleIndex++;
      }

      Pattern yourTicketPattern = Pattern.compile("your\\sticket");
      Matcher yourTicketMatcher = yourTicketPattern.matcher(line);
      if (yourTicketMatcher.find())
      {
        lineIsYourTicketNumbers = true;
      }
    }

    Long productOfDepartureFields = 1L;
    for (String ruleName : ruleStartsWithDeparture)
    {
      Integer fieldIndex = ruleNameToField.get(ruleName);
      // System.out.println(fieldIndex);
      // System.out.println(fieldIndexToValue);
      Integer value = fieldIndexToValue.get(fieldIndex);

      System.out.println(ruleName + " on my ticket is " + value);
      productOfDepartureFields *= value;
    }
    System.out.println("product of these fields: " + productOfDepartureFields);
    System.out.println(productOfDepartureFields);
    System.out.println(Long.MAX_VALUE);

  }

  /**
   * @param lastBools
   * @param bools
   * @return
   */
  private static Integer findDifferencesOfTrue(List<Boolean> lastBools, List<Boolean> bools)
  {
    if (lastBools == null)
    {
      return bools.indexOf(true);
    }

    if (lastBools.size() != bools.size())
      throw new IllegalArgumentException("lists have different sizes.");

    // both lists not null
    for (int i = 0; i < lastBools.size(); i++)
    {
      if (!lastBools.get(i)
          .equals(bools.get(i)))
        return i;
    }
    throw new IllegalStateException("Should not have gotten here");
  }

}
