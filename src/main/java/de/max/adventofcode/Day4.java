package de.max.adventofcode;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day4
{
  public static void main(String[] args)
  {
    doPart(1);
    System.out.println();
    doPart(2);
  }

  private static void doPart(int partNumber)
  {
    System.out.println("Doing part " + partNumber);
    long start = System.currentTimeMillis();
    System.out.println("Starting at: " + LocalDateTime.now());

    List<String> passportData = Utils.getListFromFilename("input-day4.txt");
    passportData.add("");

    final String patternString = "(\\w+)\\:(\\#*\\w+)";
    Pattern pattern = Pattern.compile(patternString);

    int validPassports = 0;
    Passport currentPassport = new Passport();

    for (String line : passportData)
    {
      if (line.isEmpty())
      {
        boolean isValid = isValidByPartNumber(currentPassport, partNumber);
        if (isValid)
        {
          validPassports++;
          // System.out.println(currentPassport);
        }
        currentPassport = new Passport();
      }

      Matcher matcher = pattern.matcher(line);
      while (matcher.find())
      {
        switch (matcher.group(1))
        {
          case "byr":
            currentPassport.birthYear = Integer.valueOf(matcher.group(2));
            break;
          case "iyr":
            currentPassport.issueYear = Integer.valueOf(matcher.group(2));
            break;
          case "eyr":
            currentPassport.expirationYear = Integer.valueOf(matcher.group(2));
            break;
          case "hgt":
            currentPassport.height = matcher.group(2);
            break;
          case "hcl":
            currentPassport.hairColor = matcher.group(2);
            break;
          case "ecl":
            currentPassport.eyeColor = matcher.group(2);
            break;
          case "pid":
            currentPassport.passportId = matcher.group(2);
            break;
          case "cid":
            currentPassport.countryId = Integer.valueOf(matcher.group(2));
            break;
          default:
            System.out
                .println("unknown field: " + matcher.group(1) + ", content: " + matcher.group(2));
            break;
        }
      }
    }

    System.out.println("valid passports: " + validPassports);

    long end = System.currentTimeMillis();
    System.out.println("Finishing at: " + LocalDateTime.now());
    System.out.println("Runtime: " + (end - start) + "ms");
  }

  private static boolean isValidByPartNumber(Passport currentPassport, int partNumber)
  {
    if (partNumber == 1)
      return currentPassport.isValidPart1();
    if (partNumber == 2)
      return currentPassport.isValidPart2();
    throw new IllegalArgumentException("Invalid part number: " + partNumber);
  }

  private static class Passport
  {
    public Integer birthYear;
    public Integer issueYear;
    public Integer expirationYear;
    public String height;
    public String hairColor;
    public String eyeColor;
    public String passportId;
    public Integer countryId;

    public boolean isValidPart1()
    {
      if (this.birthYear != null && this.issueYear != null && this.expirationYear != null
          && this.height != null && this.hairColor != null && this.eyeColor != null
          && this.passportId != null)
        return true;
      return false;
    }

    public boolean isValidPart2()
    {
      if (!this.isValidPart1())
      {
        return false;
      }
      else
      {
        if (this.birthYear < 1920 || this.birthYear > 2002)
          return false;

        if (this.issueYear < 2010 || this.issueYear > 2020)
          return false;

        if (this.expirationYear < 2020 || this.expirationYear > 2030)
          return false;

        // matching height
        final String patternString = "(^[0-9]{2,3})(cm$|in$)";
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(this.height);
        if (!matcher.find())
          return false;
        int heightNumber = Integer.parseInt(matcher.group(1));
        String heightUnit = matcher.group(2);

        if (heightUnit.equals("cm") && (heightNumber < 150 || heightNumber > 193))
          return false;
        if (heightUnit.equals("in") && (heightNumber < 59 || heightNumber > 76))
          return false;

        // matching haircolor
        pattern = Pattern.compile("(^\\#[0-9a-f]{6}$)");
        matcher = pattern.matcher(this.hairColor);
        if (!matcher.find())
          return false;

        // matching eyecolor
        pattern = Pattern.compile("^(amb|blu|brn|gry|grn|hzl|oth)$");
        matcher = pattern.matcher(this.eyeColor);
        if (!matcher.find())
          return false;

        // matching passportNumber
        pattern = Pattern.compile("^[0-9]{9}$");
        matcher = pattern.matcher(this.passportId);
        if (!matcher.find())
          return false;

        return true;
      }
    }

    @Override
    public String toString()
    {
      return "Passport [birthYear=" + birthYear + ", issueYear=" + issueYear + ", expirationYear="
          + expirationYear + ", height=" + height + ","
          + (height != null && height.length() == 4 ? " " : "") + " hairColor=" + hairColor
          + ", eyeColor=" + eyeColor + ", passportId=" + passportId + ", countryId=" + countryId
          + "]";
    }

  }
}
