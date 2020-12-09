package de.max.adventofcode;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2
{
  public static void main(String[] args)
  {
    // part1();
    part2();
  }

  private static void part1()
  {
    List<String> list = Utils.getListFromFilename("input-day2.txt");
    final String patternString = "(\\d+)\\-(\\d+)\\s(\\w)\\:\\s(\\w+)";
    Pattern pattern = Pattern.compile(patternString);

    int validPasswords = 0;
    for (String line : list)
    {
      Matcher matcher = pattern.matcher(line);
      if (matcher.find())
      {
        Integer minTimes = Integer.valueOf(matcher.group(1));
        Integer maxTimes = Integer.valueOf(matcher.group(2));
        char letterToMatch = matcher.group(3)
            .charAt(0);
        char[] password = matcher.group(4)
            .toCharArray();
        int count = 0;
        for (char letter : password)
          if (letter == letterToMatch)
            count++;

        if (minTimes <= count && count <= maxTimes)
          validPasswords++;
      }

    }
    System.out.println("valid passwords: " + validPasswords);
  }

  private static void part2()
  {
    List<String> list = Utils.getListFromFilename("input-day2.txt");
    final String patternString = "(\\d+)\\-(\\d+)\\s(\\w)\\:\\s(\\w+)";
    Pattern pattern = Pattern.compile(patternString);

    int validPasswords = 0;
    for (String line : list)
    {
      Matcher matcher = pattern.matcher(line);
      if (matcher.find())
      {
        Integer firstPosition = Integer.valueOf(matcher.group(1)) - 1;
        Integer secondPosition = Integer.valueOf(matcher.group(2)) - 1;
        char letterToMatch = matcher.group(3)
            .charAt(0);
        char[] password = matcher.group(4)
            .toCharArray();

        char firstLetter = password[firstPosition];
        char secondLetter = password[secondPosition];

        if (firstLetter != secondLetter
            && (firstLetter == letterToMatch || secondLetter == letterToMatch))
        {
          validPasswords++;
        }
      }
    }
    System.out.println("valid passwords: " + validPasswords);
  }

}
