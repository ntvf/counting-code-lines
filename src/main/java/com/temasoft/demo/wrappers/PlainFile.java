package com.temasoft.demo.wrappers;

public class PlainFile {

  private final String name;
  private final int linesCount;

  public PlainFile(String name, int linesCount) {
    this.name = name;
    this.linesCount = linesCount;
  }

  public String getName() {
    return name;
  }

  public int getLinesCount() {
    return linesCount;
  }
}
