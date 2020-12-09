package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day8
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

    List<String> bootCodeStatements = Utils.getListFromFilename("input-day8.txt");
    // (%s) (+|-)(%d)
    Pattern pattern = Pattern.compile("(\\w+)\\s([+-])(\\d+)");
    Integer i = 0;
    Integer acc = 0;

    while (!bootCodeStatements.get(i)
        .contains("X"))
    {
      Matcher matcher = pattern.matcher(bootCodeStatements.get(i));
      // "X" an die Befehle hängen
      bootCodeStatements.set(i, bootCodeStatements.get(i) + "X");

      if (matcher.find())
      {
        // matcher.group(1) contains the instruction keyword
        switch (matcher.group(1))
        {
          case "nop":
            i++;
            continue;

          case "acc":
            i++;
            acc += Integer.parseInt(matcher.group(2) + matcher.group(3));
            continue;

          case "jmp":
            i += Integer.parseInt(matcher.group(2) + matcher.group(3));
            continue;

          default:
            throw new IllegalArgumentException("Wrong keyword");
        }
      }
    }

    System.out.println("acc: " + acc);

    long end = System.currentTimeMillis();
    System.out.println("Finishing at: " + LocalDateTime.now());
    System.out.println("Runtime: " + (end - start) + "ms");
  }

  private static void part2()
  {
    long start = System.currentTimeMillis();
    System.out.println("Starting at: " + LocalDateTime.now());

    List<String> bootCodeStatementsForCounter = Utils.getListFromFilename("input-day8.txt");
    // (%s) (+|-)(%d)
    Pattern pattern = Pattern.compile("(\\w+)\\s([+-])(\\d+)");
    Integer i = 0;
    Integer acc = 0;

    // Change the statemtens one after the other
    for (int changeStatementNo = 0; changeStatementNo < bootCodeStatementsForCounter
        .size(); changeStatementNo++)
    {
      i = 0;
      acc = 0;

      List<String> bootCodeStatements = Utils.getListFromFilename("input-day8.txt");
      if (bootCodeStatements.get(changeStatementNo)
          .contains("nop"))
        // Replace "nop" by "jmp"
        bootCodeStatements.set(changeStatementNo, bootCodeStatements.get(changeStatementNo)
            .replace("nop", "jmp"));
      else if (bootCodeStatements.get(changeStatementNo)
          .contains("jmp"))
        // Replace "jmp" by "nop"
        bootCodeStatements.set(changeStatementNo, bootCodeStatements.get(changeStatementNo)
            .replace("jmp", "nop"));

      // Currently altered statement
      System.out.println(changeStatementNo + " " + bootCodeStatements.get(changeStatementNo));

      try
      {
        while (!bootCodeStatements.get(i)
            .contains("X"))
        {
          // If the bootCodeStatement does not contain "X" it has not yet been executed

          Matcher matcher = pattern.matcher(bootCodeStatements.get(i));
          // append "X" to the currently executing statement
          bootCodeStatements.set(i, bootCodeStatements.get(i) + "X");

          if (matcher.find())
          {
            // matcher.group(1) contains the instruction keyword
            switch (matcher.group(1))
            {
              case "nop":
                i++;
                continue;

              case "acc":
                i++;
                // Increase the accumulator by the given value
                acc += Integer.parseInt(matcher.group(2) + matcher.group(3));
                continue;

              case "jmp":
                // Increase the index by the given value
                i += Integer.parseInt(matcher.group(2) + matcher.group(3));
                continue;

              default:
                throw new IllegalArgumentException("Wrong keyword");
            }
          }
        }
      }
      catch (IndexOutOfBoundsException e)
      {
        // Catch, if we venture out of bounds with our index on the boot statements which is to be
        // treated as a success.
        break;
      }
    }

    if (i >= bootCodeStatementsForCounter.size())
      System.out.println("success");

    System.out.println("acc: " + acc);

    long end = System.currentTimeMillis();
    System.out.println("Finishing at: " + LocalDateTime.now());
    System.out.println("Runtime: " + (end - start) + "ms");
  }

}
