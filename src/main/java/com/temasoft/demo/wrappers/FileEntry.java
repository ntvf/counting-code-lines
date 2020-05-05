package com.temasoft.demo.wrappers;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class FileEntry {

  private final String name;
  private final List<FileEntry> dirs;
  private final List<PlainFile> files;

  public FileEntry(String name, List<FileEntry> dirs,
      List<PlainFile> files) {
    this.name = name;
    this.dirs = dirs;
    this.files = files;
  }

  public FileEntry(String name) {
    this.name = name;
    this.dirs = Collections.emptyList();
    this.files = Collections.emptyList();
  }

  public String getName() {
    return name;
  }

  public int getLinesCount() {
    return Stream.concat(
        files.stream().map(PlainFile::getLinesCount),
        dirs.stream().map(FileEntry::getLinesCount))
        .reduce(0, (a, b) -> a + b);
  }

  public List<FileEntry> getDirs() {
    return dirs;
  }

  public List<PlainFile> getFiles() {
    return files;
  }
}
