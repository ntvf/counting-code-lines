package com.temasoft.demo;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    String path = args[0];
    start(path);
  }

  private static void start(String path) throws IOException {
    new CountingLines().process(path);
  }


}
