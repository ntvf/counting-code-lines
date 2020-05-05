package com.temasoft.demo.view;

import static java.lang.String.format;

import com.temasoft.demo.wrappers.FileEntry;
import java.io.PrintStream;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Presentation {

  private static final int INITIAL_INDENTATION = 0;
  private static final String INDENTATION_SYMBOL = " ";
  private static final int DIRECTORY_INDENTATION_OFFSET = 2;
  private static final String FILE_INDENTATION_SYMBOL = INDENTATION_SYMBOL;
  private static final String OUTPUT_FORMAT = "%s%s : %d";

  private final FileEntry data;

  public Presentation(FileEntry data) {
    this.data = data;
  }

  public void print(PrintStream printStream) {
    printEntry(printStream, data, INITIAL_INDENTATION);
  }

  private void printEntry(PrintStream printStream, FileEntry entry, int indentation) {
    String indentationString = IntStream.range(0, indentation)
        .boxed()
        .map(it -> INDENTATION_SYMBOL)
        .collect(Collectors.joining(""));

    printStream
        .println(format(OUTPUT_FORMAT, indentationString, entry.getName(), entry.getLinesCount()));

    entry.getDirs().forEach(
        it -> printEntry(printStream, it, indentation + DIRECTORY_INDENTATION_OFFSET)
    );

    entry.getFiles().forEach(
        it -> printStream.println(
            format(OUTPUT_FORMAT, indentationString + FILE_INDENTATION_SYMBOL, it.getName(),
                it.getLinesCount())));

  }

}
