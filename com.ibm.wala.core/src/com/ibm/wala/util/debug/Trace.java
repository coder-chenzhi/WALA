/*******************************************************************************
 * Copyright (c) 2002 - 2006 IBM Corporation.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.ibm.wala.util.debug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Simple utility for printing trace messages.
 * 
 */
@Deprecated
public class Trace {

  private static final String TRACEFILE_KEY = "com.ibm.wala.tracefile";

  private static String traceFile = null;

  private static PrintStream out = null;

  public synchronized static void setTraceFile(String fileName) {
    System.setProperty(TRACEFILE_KEY, fileName);
  }
  
  public static PrintStream getTraceStream() {
    setTraceFile();
    return out;
  }

  /**
   * @return true iff we can print to the tracefile
   */
  private synchronized static boolean setTraceFile() {
    String fileName = System.getProperty(TRACEFILE_KEY);
    if (fileName == null) {
      if (traceFile != null) {
        traceFile = null;
        if (out != null) {
          out.close();
        }
        out = null;
      }
      return false;
    } else {
      if (traceFile != null) {
        if (traceFile.equals(fileName)) {
          // tracefile already initialized
          return true;
        } else {
          // change in tracefile
          traceFile = null;
          if (out != null) {
            out.close();
          }
          out = null;
        }
      }
      // open the new tracefile
      traceFile = fileName;
      File f = new File(fileName);
      try {
        out = new PrintStream(new FileOutputStream(f));
        return true;
      } catch (FileNotFoundException e) {
        System.err.println("Error: file not found: " + fileName);
        Assertions.UNREACHABLE("Invalid trace file: " + f);
        return false;
      }
    }
  }

  /**
   */
  public static synchronized void println(String string) {
    System.err.println(string);
  }

  /**
   */
  public static synchronized void println(Object o) {
    System.err.println(o);
  }

  /**
   */
  public static synchronized void print(String string) {
    System.err.print(string);
  }

  public static synchronized void format(String format, Object ... args) {
    System.err.format(format, args);
  }

  public static void flush() {
    System.err.flush();
  }

  public static PrintWriter getTraceWriter() {
    if (setTraceFile()) {
      return new PrintWriter(out);
    } else {
      return null;
    }
  }

  /**
   * print S iff s contains substring
   * 
   * @return true if something is printed, false otherwise
   * @throws IllegalArgumentException  if S == null
   */
  public static boolean guardedPrintln(String S, String substring) throws IllegalArgumentException {
    if (S == null) {
      throw new IllegalArgumentException("S == null");
    }
    if (substring == null || S.indexOf(substring) > -1) {
      System.err.println(S);
      return true;
    } else {
      return false;
    }
  }

  /**
   * print S iff s contains substring
   * 
   * @param S
   * @param substring
   * @return true if something is printed, false otherwise
   * @throws IllegalArgumentException  if S == null
   */
  public static boolean guardedPrint(String S, String substring) throws IllegalArgumentException {
    if (S == null) {
      throw new IllegalArgumentException("S == null");
    }
    if (substring == null || S.indexOf(substring) > -1) {
      System.err.print(S);
      return true;
    } else {
      return false;
    }
  }

  /**
   * @return Returns the traceFile.
   */
  public synchronized static String getTraceFile() {
    setTraceFile();
    return traceFile;
  }
}
