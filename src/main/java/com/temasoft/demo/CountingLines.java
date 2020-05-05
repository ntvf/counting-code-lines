package com.temasoft.demo;

import com.temasoft.demo.counter.LineCounter;
import com.temasoft.demo.counter.SimplePassExcludeCommentsLineCounter;
import com.temasoft.demo.view.Presentation;
import com.temasoft.demo.wrappers.FileEntry;
import com.temasoft.demo.wrappers.PlainFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CountingLines {

  public void process(String filePath) {
    try {
      processInternal(filePath);
    } catch (IOException e) {
      System.err.println("Error counting lines in path" + filePath);
    }
  }

  private void processInternal(String filePath) throws IOException {
    File file = new File(filePath);

    FileEntry entry;
    if (file.isDirectory()) {
      entry = getFilesEntries(file);
    } else {
      entry = getPlainFileEntry(file);
    }

    new Presentation(entry).print(System.out);
  }

  private static FileEntry getPlainFileEntry(File pathFile) throws IOException {
    String name = pathFile.getName();
    int linesCount = getPlainFile(pathFile).getLinesCount();
    return new FileEntry(name) {
      @Override
      public int getLinesCount() {
        return linesCount;
      }
    };
  }

  private static FileEntry getFilesEntries(File pathFile) throws IOException {
    String name = pathFile.getName();
    List<FileEntry> entries = new ArrayList<>();
    List<PlainFile> plains = new ArrayList<>();

    for (File file : pathFile.listFiles()) {
      if (file.isDirectory()) {
        entries.add(getFilesEntries(file));
      } else {
        plains.add(getPlainFile(file));
      }
    }

    return new FileEntry(name, entries, plains);
  }

  private static PlainFile getPlainFile(File file) throws IOException {
    String name = file.getName();

    LineCounter lineCounter = new SimplePassExcludeCommentsLineCounter();

    int linesCount = lineCounter.countLinesWithoutComments(
        new BufferedReader(new FileReader(file)));

    return new PlainFile(name, linesCount);
  }


}
