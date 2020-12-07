package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO Klasse kommentieren
 * 
 * @author rumforma
 * @version $Revision:$<br/>
 *          $Date:$<br/>
 *          $Author:$
 */
public class Day7
{
  static List<String> allRules;

  public static void main(String[] args)
  {
    allRules = Utils.getListFromFilename("input-day7.txt");

    part1();
    part2();
  }

  private static void part1()
  {
    long start = System.currentTimeMillis();
    System.out.println("Starting at: " + LocalDateTime.now());

    // (%s %s) ... contain (...).
    Pattern pattern = Pattern.compile("(\\w+\\s\\w+)\\sbags\\scontain\\s(.+)\\.");
    Set<String> outerBags = new HashSet<>();
    outerBags.add("shiny gold");

    int numberInLastRun = 0;
    while (numberInLastRun != outerBags.size())
    {
      numberInLastRun = outerBags.size();
      for (String rule : allRules)
      {
        Matcher matcher = pattern.matcher(rule);
        if (matcher.find())
        {
          String container = matcher.group(1);
          String[] contains = matcher.group(2)
              .split(", ");
          for (String inBag : contains)
          {
            Pattern innerPattern = Pattern.compile("\\d+\\s(\\w+\\s\\w+)");
            Matcher innerMatcher = innerPattern.matcher(inBag);
            if (innerMatcher.find())
            {
              String toAdd = innerMatcher.group(1);
              if (outerBags.contains(toAdd))
                outerBags.add(container);
            }
          }
        }
      }
    }
    System.out.println(outerBags.size() - 1);

    long end = System.currentTimeMillis();
    System.out.println("Finishing at: " + LocalDateTime.now());
    System.out.println("Runtime: " + (end - start) + "ms");
  }

  private static void part2()
  {
    long start = System.currentTimeMillis();
    System.out.println("Starting at: " + LocalDateTime.now());

    Integer subBags = findBagsInBag("shiny gold");

    System.out.println(subBags - 1);

    long end = System.currentTimeMillis();
    System.out.println("Finishing at: " + LocalDateTime.now());
    System.out.println("Runtime: " + (end - start) + "ms");
  }

  private static String findRuleAllRules(String ruleToFind)
  {
    for (String rule : allRules)
      if (rule.startsWith(ruleToFind))
        return rule;
    return null;
  }

  /**
   * @param colorToFind
   * @return
   */
  private static Integer findBagsInBag(String colorToFind)
  {
    Pattern pattern = Pattern.compile("(.+)contain(.+)\\.$");
    Matcher matcher = pattern.matcher(findRuleAllRules(colorToFind));

    Integer allBags = 1;
    if (matcher.find())
    {
      String[] subBags = matcher.group(2)
          .trim()
          .split(", ");
      for (String subBag : subBags)
      {
        Pattern innerPattern = Pattern.compile("(\\d)\\s(\\w+\\s\\w+)");
        Matcher innerMatcher = innerPattern.matcher(subBag);
        if (innerMatcher.find())
        {
          String colorOfSubBag = innerMatcher.group(2);
          Integer numberOfSubBags = Integer.parseInt(innerMatcher.group(1));
          allBags += numberOfSubBags * findBagsInBag(colorOfSubBag);
        }
      }
    }
    return allBags;
  }

}
