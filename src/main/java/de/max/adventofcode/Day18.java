package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day18
{

  public static List<String> input;

  public static void main(String[] args)
  {
    long start = System.currentTimeMillis();
    System.out.println("Starting at: " + LocalDateTime.now());

    input = Utils.getListFromFilename("input-day18.txt");

    // part1();
    part2();

    long end = System.currentTimeMillis();
    System.out.println("Finishing at: " + LocalDateTime.now());
    System.out.println("Runtime: " + (end - start) + "ms");
  }

  private static void part1()
  {
    Long sum = 0L;
    // 1 line == 1 expression
    for (String line : input)
    {
      Long result = evaluateExpression(line.replace(" ", ""));
      sum += result;
    }
    System.out.println("sum of results: " + sum);
  }

  private static Long evaluateExpression(String line)
  {
    // replace subterms delimited by ( and ) with their result
    String remainingExpression = replaceParens(line);

    // evaluate remaining expression
    Long result = evaluationExpressionWithoutParens(remainingExpression);

    System.out.println(result);

    return result;
  }

  private static String replaceParens(String expression)
  {
    Pattern innermostParensPattern = Pattern.compile(".*(\\([^\\(^\\).]+\\)).*");
    while (expression.contains("("))
    {
      Matcher innermostParensMatcher = innermostParensPattern.matcher(expression);
      if (!innermostParensMatcher.find())
        throw new IllegalStateException("Should have found something");

      // remove parens from term
      String innermostParensTerm = innermostParensMatcher.group(1)
          .replace("(", "")
          .replace(")", "");
      String replacement = evaluationExpressionWithoutParens(innermostParensTerm).toString();
      // System.out.println("term with parens found: " + innermostParensMatcher.group(1));
      // System.out.println("term with parens removed: " + innermostParensTerm);
      // System.out.println("replacing term with: " + replacement);
      // System.out.println(innermostParensMatcher.group(1)
      // .replace("+", "\\+")
      // .replace("*", "\\*")
      // .replace("(", "\\(")
      // .replace(")", "\\)"));
      // // mask parens in term
      expression = expression.replaceFirst(innermostParensMatcher.group(1)
          .replace("+", "\\+")
          .replace("*", "\\*")
          .replace("(", "\\(")
          .replace(")", "\\)"), replacement);
      // System.out.println("expression after: " + expression);
      // System.out.println();
    }
    return expression;
  }

  private static Long evaluationExpressionWithoutParens(String expression)
  {
    Long result = 0L;
    Pattern nextNumberPattern = Pattern.compile("^(\\d+)");

    Character operator = null;

    while (!expression.isEmpty())
    {
      Matcher nextNumberMatcher = nextNumberPattern.matcher(expression);
      if (!nextNumberMatcher.find())
        throw new IllegalStateException("Should have found something: " + expression);

      Long number = Long.parseLong(nextNumberMatcher.group(1));
      expression = expression.replaceFirst(number.toString(), "");

      if (operator == null)
      {
        // first number has no operator before it
        result += number;
        if (expression.length() > 0)
        {
          operator = expression.charAt(0);
          expression = expression.replaceFirst("\\" + operator.toString(), "");
        }
      }
      else
      {
        // operator != null -> not the first number
        switch (operator)
        {
          case '+':
            result += number;

            break;

          case '*':
            result *= number;
            break;

          default:
            throw new IllegalArgumentException("should not have come here: " + operator);
        }
        if (expression.length() > 0)
        {
          operator = expression.charAt(0);
          expression = expression.replaceFirst("\\" + operator.toString(), "");
        }
      }
    }
    return result;
  }

  private static void part2()
  {
    Long sum = 0L;
    // 1 line == 1 expression
    for (String line : input)
    {
      // System.out.println("expression: " + line);
      Long result = evaluateExpressionPart2(line.replace(" ", ""));
      System.out.println("result: " + result);

      sum += result;
      // System.out.println("current sum: " + sum);
      // System.out.println();

    }
    System.out.println("sum of results: " + sum);
  }

  private static Long evaluateExpressionPart2(String line)
  {
    // replace subterms delimited by ( and ) with their result
    String remainingExpression = replaceParensPart2(line);

    // evaluate remaining expression
    Long result = evaluationExpressionWithoutParensPart2(remainingExpression);

    return result;
  }

  private static String replaceParensPart2(String expression)
  {
    Pattern innermostParensPattern = Pattern.compile(".*(\\([^\\(^\\).]+\\)).*");
    while (expression.contains("("))
    {
      // System.out.println("expression before: " + expression);
      Matcher innermostParensMatcher = innermostParensPattern.matcher(expression);
      if (!innermostParensMatcher.find())
        throw new IllegalStateException("Should have found something");

      // remove parens from term
      String innermostParensTerm = innermostParensMatcher.group(1)
          .replace("(", "")
          .replace(")", "");
      String replacement = evaluationExpressionWithoutParensPart2(innermostParensTerm).toString();
      // System.out.println("term with parens found: " + innermostParensMatcher.group(1));
      // System.out.println("term with parens removed: " + innermostParensTerm);
      // System.out.println("replacing term with: " + replacement);
      // System.out.println(innermostParensMatcher.group(1)
      // .replace("+", "\\+")
      // .replace("*", "\\*")
      // .replace("(", "\\(")
      // .replace(")", "\\)"));
      // mask parens in term
      expression = expression.replaceFirst(innermostParensMatcher.group(1)
          .replace("+", "\\+")
          .replace("*", "\\*")
          .replace("(", "\\(")
          .replace(")", "\\)"), replacement);
      // System.out.println("expression after: " + expression);
      // System.out.println();
    }
    return expression;
  }

  private static Long evaluationExpressionWithoutParensPart2(String expression)
  {
    // System.out.println("expression: " + expression);
    Pattern nextAdditionPattern = Pattern.compile("((\\d+)\\+(\\d+))");
    Pattern nextMultiplicationPattern = Pattern.compile("((\\d+)\\*(\\d+))");

    while (expression.contains("+"))
    {
      Matcher nextAdditionMatcher = nextAdditionPattern.matcher(expression);

      if (nextAdditionMatcher.find())
      {
        // found an addition
        Long sum = Long.parseLong(nextAdditionMatcher.group(2))
            + Long.parseLong((nextAdditionMatcher.group(3)));
        expression = expression.replace(nextAdditionMatcher.group(1), sum.toString());
        evaluateLong(sum);
        // System.out.println("replaced " + nextAdditionMatcher.group(1) + " with " + sum);
      }
    }

    while (expression.contains("*"))
    {
      Matcher nextMultiplicationMatcher = nextMultiplicationPattern.matcher(expression);

      if (nextMultiplicationMatcher.find())
      {
        // found a multiplication
        Long sum = Long.parseLong(nextMultiplicationMatcher.group(2))
            * Long.parseLong((nextMultiplicationMatcher.group(3)));
        expression = expression.replace(nextMultiplicationMatcher.group(1), sum.toString());
        evaluateLong(sum);
        // System.out.println("replaced " + nextMultiplicationMatcher.group(1) + " with " + sum);
      }

    }
    System.out.println(expression);
    System.out.println(Long.parseLong(expression));
    evaluateLong(Long.parseLong(expression));

    return Long.parseLong(expression);
  }

  private static void evaluateLong(Long sum)
  {
    if (sum < 0L)
      throw new IllegalStateException("FLIPOVER");
  }

}
