package net.dusterthefirst.windowsxp;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import net.dusterthefirst.simplespigot.ConsoleColors;

public class ConsoleOutputStream extends OutputStream {

   private final MasterWindow window;
   private final StringBuilder sb = new StringBuilder();
   private final Color color;

   public ConsoleOutputStream(MasterWindow window, Color color) {
      this.window = window;
      this.color = color;
   }

   @Override
   public void flush() {
   }

   @Override
   public void close() {
   }

   @Override
   public void write(int b) throws IOException {

      if (b == '\r')
         return;

      if (b == '\n') {
         final String text = sb.toString() + "\n";
         ConsoleColors.parse(text, window, color);
         sb.setLength(0);

         return;
      }

      sb.append((char) b);
   }
}