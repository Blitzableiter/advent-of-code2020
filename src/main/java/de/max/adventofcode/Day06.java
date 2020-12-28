package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day06
{
  public static void main(String[] args)
  {
    part1();
    part2();
  }

  private static void part1()
  {
    long start = System.currentTimeMillis();
    System.out.println("Starting at: " + LocalDateTime.now());

    List<String> questionData = Utils.getListFromFilename("input-day06.txt");
    questionData.add("");
    Set<Character> groupQuestions = new HashSet<>();

    int sum = 0;

    for (String person : questionData)
    {
      // Ende einer Gruppe
      if (person.isEmpty())
      {
        sum += groupQuestions.size();
        groupQuestions = new HashSet<>();

        continue;
      }

      char[] answers = person.toCharArray();
      for (char answer : answers)
        groupQuestions.add(answer);
    }

    long end = System.currentTimeMillis();
    System.out.println("Finishing at: " + LocalDateTime.now());
    System.out.println("Runtime: " + (end - start) + "ms");
  }

  private static void part2()
  {

    long start = System.currentTimeMillis();
    System.out.println("Starting at: " + LocalDateTime.now());

    List<String> questionData = Utils.getListFromFilename("input-day06.txt");
    questionData.add("");
    Set<Character> groupQuestions = new HashSet<>();

    int sum = 0;
    boolean nextPersonIsFirst = true;

    for (String person : questionData)
    {
      // Ende einer Gruppe
      if (person.isEmpty())
      {
        System.out.println(groupQuestions);
        sum += groupQuestions.size();
        System.out.println(sum);
        groupQuestions = new HashSet<>();
        nextPersonIsFirst = true;

        continue;
      }

      System.out.println();
      List<Character> personsAnswers = new ArrayList<>();
      for (char answer : person.toCharArray())
        personsAnswers.add(answer);
      System.out.println("person answered: " + personsAnswers);

      // Erste Person in dieser Gruppe
      if (nextPersonIsFirst)
      {
        nextPersonIsFirst = false;
        for (Character answer : personsAnswers)
          groupQuestions.add(answer);

        continue;
      }

      /*
       * Cannot remove while accessing element, will throw java.util.ConcurrentModificationException
       * 
       * Should be rewritten for cleanliness
       */
      List<Character> toRemove = new ArrayList<>();
      for (Character groupAnswer : groupQuestions)
      {
        if (!personsAnswers.contains(groupAnswer))
          toRemove.add(groupAnswer);
      }
      for (Character rem : toRemove)
        groupQuestions.remove(rem);

      System.out.println("groupQuestions: " + groupQuestions);

    }

    long end = System.currentTimeMillis();
    System.out.println("Finishing at: " + LocalDateTime.now());
    System.out.println("Runtime: " + (end - start) + "ms");
  }

}
