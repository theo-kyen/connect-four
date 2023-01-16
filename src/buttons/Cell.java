package buttons;

import java.awt.*;

import javax.swing.*;

/**
 * Represents a single cell on the board.
 */
public class Cell extends JButton {
  private final int row;
  private final int col;

  /**
   * Creates an instance of {@code Cell}. Sets the position of the cell
   * based on the given row and column.
   * @param row the row position of the cell
   * @param col the column position of the cell
   */
  public Cell(int row, int col) {
    this.row = row;
    this.col = col;

    setText(" ");
    setName(this.assignName());
    setBackground(Color.LIGHT_GRAY);
    setFocusPainted(false);
    setVisible(true);
  }

  // assigns the name of the cell based on its position
  private String assignName() {
    String stringCol = "";

    switch (this.col) {
      case 0:
        stringCol = "A";
        break;
      case 1:
        stringCol = "B";
        break;
      case 2:
        stringCol = "C";
        break;
      case 3:
        stringCol = "D";
        break;
      case 4:
        stringCol = "E";
        break;
      case 5:
        stringCol = "F";
        break;
      case 6:
        stringCol = "G";
        break;
    }

    return "Button" + stringCol + (6 - this.row);
  }
}
