package com.temasoft.demo.counter;

import java.io.IOException;
import java.io.Reader;

public interface LineCounter {

  int countLinesWithoutComments(Reader reader) throws IOException;


}
