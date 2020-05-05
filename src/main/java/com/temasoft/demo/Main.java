package com.temasoft.demo;

public class Main {

  public static void main(String[] args) {
    start(args[0]);
  }

  private static void start(String path) {
    new CountingLines().process(path);
  }

}
