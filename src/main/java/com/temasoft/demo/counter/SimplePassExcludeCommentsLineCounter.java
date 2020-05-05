package com.temasoft.demo.counter;

import static java.lang.Character.isWhitespace;

import java.io.IOException;
import java.io.Reader;

public class SimplePassExcludeCommentsLineCounter implements LineCounter {

  private static final char NEW_LINE = '\n';
  private static final char ESCAPE_CHARACTER = '\\';
  private static final char STRING_CHARACTER = '"';
  private static final char STAR_CHARACTER = '*';
  private static final char SLASH_CHARACTER = '/';

  public int countLinesWithoutComments(Reader reader) throws IOException {

    int lineCount = 0;

    boolean lineAccepted = false;
    boolean insideComment = false;
    boolean insideString = false;
    boolean previousEscapeSymbol = false;
    boolean potentialCommentStart = false;
    boolean potentialCommentEnd = false;
    boolean commentTillEndLine = false;

    int r;
    while ((r = reader.read()) != -1) {
      char ch = (char) r;

      potentialCommentStart = dropStateIfNeeded(potentialCommentStart, ch);

      potentialCommentEnd = dropStateIfNeeded(potentialCommentEnd, ch);

      if (ch == STRING_CHARACTER) {
        if (previousEscapeSymbol) {
          continue;
        }
        insideString = !insideString;
      }

      previousEscapeSymbol = isEscapeCharacter(ch);

      if (!insideString) {
        if (ch == SLASH_CHARACTER) {
          if (potentialCommentEnd) {
            insideComment = false;
            potentialCommentEnd = false;
          } else {
            if (potentialCommentStart) {
              commentTillEndLine = true;
            } else {
              potentialCommentStart = true;
            }
          }
        }

        if (ch == STAR_CHARACTER) {
          if (potentialCommentStart) {
            insideComment = true;
            potentialCommentStart = false;
          } else {
            potentialCommentEnd = true;
          }
        }
      }

      if (!lineAccepted && !commentTillEndLine && isNotMultiLineCommentCharacter(ch)
          && !isWhitespace(ch)
          && !insideComment) {
        lineCount++;
        lineAccepted = true;
      }

      if (ch == NEW_LINE) {
        lineAccepted = false;
        commentTillEndLine = false;
      }
    }
    return lineCount;
  }

  private boolean dropStateIfNeeded(boolean trackingState, char ch) {
    if (trackingState && isNotMultiLineCommentCharacter(ch)) {
      trackingState = false;
    }
    return trackingState;
  }

  private boolean isNotMultiLineCommentCharacter(char ch) {
    return ch != STAR_CHARACTER && ch != SLASH_CHARACTER;
  }

  private boolean isEscapeCharacter(char ch) {
    return ch == ESCAPE_CHARACTER;
  }
}
