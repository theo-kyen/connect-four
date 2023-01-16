import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import buttons.Cell;
import buttons.ResetButton;

/**
 * A class representing the game Connect Four.
 */
public class ConnectFour extends JFrame implements ActionListener {
  // a 2D array where each element represents a different cell on the board
  private final Cell[][] cells;

  // set to true when it's X's turn to play
  // set to false when it's O's turn
  private boolean isXTurn;

  // set to true when the game is over
  private boolean isGameOver;

  /**
   * Creates a new instance of {@code ConnectFour}. Creates the board and the reset button.
   */
  public ConnectFour() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500, 500);
    setVisible(true);
    setTitle("Connect Four");
    setLayout(new BorderLayout());

    this.cells = this.setCells();
    this.isXTurn = true;
    this.isGameOver = false;

    JPanel board = new JPanel();
    board.setVisible(true);
    board.setPreferredSize(new Dimension(400, 400));
    board.setLayout(new GridLayout(6, 7));

    for (Cell[] row : this.cells) {
      for (Cell c : row) {
        board.add(c);
      }
    }

    this.add(board, BorderLayout.CENTER);

    JPanel resetPanel = new JPanel();
    resetPanel.setVisible(true);

    ResetButton resetButton = new ResetButton();

    // resets the board
    resetButton.addActionListener(e -> {
      for (Cell[] row : this.cells) {
        for (Cell c : row) {
          c.setText(" ");
          c.setBackground(Color.LIGHT_GRAY);
        }
      }
      this.isXTurn = true;
      this.isGameOver = false;
    });

    resetPanel.add(resetButton);
    this.add(resetPanel, BorderLayout.SOUTH);
  }

  /**
   * The actions to be performed whenever a cell on the board is clicked.
   *
   * @param e the event to be processed
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    // don't do anything if the game is already over
    if (this.isGameOver) {
      return;
    }

    Cell cell = (Cell) e.getSource();
    Cell newCell = this.getBottomCell(cell);

    if (this.isXTurn) {
      newCell.setText("X");
    } else {
      newCell.setText("O");
    }
    this.isXTurn = !(this.isXTurn);


    // all the possible directions that the newly placed cell can possibly win in
    Cell[] lineLeft = this.getLine(newCell, "left");
    Cell[] lineUp = this.getLine(newCell, "up");
    Cell[] lineRight = this.getLine(newCell, "right");
    Cell[] lineDown = this.getLine(newCell, "down");
    Cell[] lineUpLeft = this.getLine(newCell, "up left");
    Cell[] lineDownLeft = this.getLine(newCell, "down left");
    Cell[] lineDownRight = this.getLine(newCell, "down right");
    Cell[] lineUpRight = this.getLine(newCell, "up right");

    Cell[][] lines = new Cell[][]{
            lineLeft, lineUp, lineRight, lineDown,
            lineUpLeft, lineDownLeft, lineDownRight, lineUpRight
    };

    // if there is now a winning line on the board,
    // make that line green and end the game
    for (Cell[] line : lines) {
      if (this.isWinningLine(line)) {
        for (Cell c : line) {
          c.setBackground(Color.GREEN);
        }
        this.isGameOver = true;
        break;
      }
    }
  }

  // instantiates the 2D cell array
  private Cell[][] setCells() {
    Cell[][] arr = new Cell[6][7];

    for (int row = 5; row >= 0; row--) {
      for (int col = 0; col < 7; col++) {
        Cell cell = new Cell(row, col);
        cell.addActionListener(this);
        arr[row][col] = cell;
      }
    }

    return arr;
  }

  // returns the bottom-most empty cell in the same column as the given cell
  private Cell getBottomCell(Cell cell) {
    int[] pos = this.getPos(cell);
    int row = pos[0];
    int col = pos[1];

    // if the given cell is empty, look at the cells below it
    // else, look at the cells above it
    if (cell.getText().equals(" ")) {
      while (row < 5) {
        if (cells[row + 1][col].getText().equals(" ")) {
          row += 1;
          cell = cells[row][col];
        } else {
          break;
        }
      }
    } else {
      while (row > 0) {
        if (cells[row - 1][col].getText().equals(" ")) {
          cell = cells[row - 1][col];
          break;
        } else {
          row -= 1;
        }
      }
    }

    return cell;
  }

  // returns an array representing the position of the given cell
  private int[] getPos(Cell cell) {
    String pos = cell.getName().substring(6);
    int col = 0;
    switch (pos.substring(0, 1)) {
      case "A":
        break;
      case "B":
        col = 1;
        break;
      case "C":
        col = 2;
        break;
      case "D":
        col = 3;
        break;
      case "E":
        col = 4;
        break;
      case "F":
        col = 5;
        break;
      case "G":
        col = 6;
        break;
    }
    int row = 6 - (Integer.parseInt(pos.substring(1)));

    return new int[]{row, col};
  }

  // is the given array of cells a winning line? (is each cell in the array the same?)
  private boolean isWinningLine(Cell[] cells) {
    if (cells[1] == null || cells[2] == null || cells[3] == null) {
      return false;
    }

    return !(cells[0].getText().equals(" "))
            && cells[0].getText().equals(cells[1].getText())
            && cells[1].getText().equals(cells[2].getText())
            && cells[2].getText().equals(cells[3].getText());
  }

  // returns an array representing the line of the given cell, determined by the given direction,
  // where the first element is the given cell
  private Cell[] getLine(Cell cell, String direction) {
    Cell[] cells = new Cell[4];
    int[] pos = this.getPos(cell);
    int row = pos[0];
    int col = pos[1];

    for (int i = 0; i < 4; i++) {
      switch (direction) {
        case "left":
          if (col - i >= 0) {
            cells[i] = this.cells[row][col - i];
          }
          break;
        case "up":
          if (row - i >= 0) {
            cells[i] = this.cells[row - i][col];
          }
          break;
        case "right":
          if (col + i < 7) {
            cells[i] = this.cells[row][col + i];
          }
          break;
        case "down":
          if (row + i < 6) {
            cells[i] = this.cells[row + i][col];
          }
          break;
        case "up left":
          if (col - i >= 0) {
            if (row - i >= 0) {
              cells[i] = this.cells[row - i][col - i];
            }
          }
          break;
        case "down left":
          if (col - i >= 0) {
            if (row + i < 6) {
              cells[i] = this.cells[row + i][col - i];
            }
          }
          break;
        case "down right":
          if (col + i < 7) {
            if (row + i < 6) {
              cells[i] = this.cells[row + i][col + i];
            }
          }
          break;
        case "up right":
          if (col + i < 7) {
            if (row - i >= 0) {
              cells[i] = this.cells[row - i][col + i];
            }
          }
          break;
      }
    }

    return cells;
  }
}