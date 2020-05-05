package com.temasoft.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.temasoft.demo.counter.SimplePassExcludeCommentsLineCounter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimplePassExcludeCommentsLineCounterTest {

  private SimplePassExcludeCommentsLineCounter lineCounter;

  @BeforeEach
  void init() {
    lineCounter = new SimplePassExcludeCommentsLineCounter();
  }

  @Test
  void shouldCountDaveJavaFile() throws IOException {
    Reader reader = getReader("java/Dave.java");
    int result = lineCounter.countLinesWithoutComments(reader);
    assertEquals(3, result);
  }

  @Test
  void shouldCountHelloJavaFile() throws IOException {
    Reader reader = getReader("java/Hello.java");
    int result = lineCounter.countLinesWithoutComments(reader);
    assertEquals(5, result);
  }

  @Test
  void shouldWorkIfCommentIsInsideString() throws IOException {
    int result = lineCounter.countLinesWithoutComments(new StringReader("\"/*Hello\""));
    assertEquals(1, result);
  }

  @Test
  void shouldIgnoreEscapedQuotesInStrings() throws IOException {
    int result = lineCounter.countLinesWithoutComments(new StringReader("\"\\\"\n/*string\""));
    assertEquals(2, result);
  }

  @Test
  void shouldCountTwoLinesWithMultiLineComments() throws IOException {
    int result = lineCounter
        .countLinesWithoutComments(new StringReader("line /*multiline\r\ncomment*/\n line2"));
    assertEquals(2, result);
  }

  @Test
  void shouldCountOneLineWithComment() throws IOException {
    int result = lineCounter.countLinesWithoutComments(new StringReader("line /*comment*/"));
    assertEquals(1, result);
  }


  @Test
  void shouldNotCountLineOnlyWithMultiLineComment() throws IOException {
    int result = lineCounter.countLinesWithoutComments(new StringReader("/*comment*/"));
    assertEquals(0, result);
  }

  @Test
  void shouldNotCountLineOnlyWithLineComment() throws IOException {
    int result = lineCounter.countLinesWithoutComments(new StringReader("//comment line"));
    assertEquals(0, result);
  }

  private File getFile(String name) {
    ClassLoader classLoader = getClass().getClassLoader();
    return new File(classLoader.getResource(name).getFile());
  }

  private Reader getReader(String filePath) throws FileNotFoundException {
    return new BufferedReader(new FileReader(getFile(filePath)));
  }

}
