package buttons;

import java.awt.*;

import javax.swing.*;

/**
 * Represents the button to reset the game.
 */
public class ResetButton extends JButton {
  /**
   * Creates a new instance of {@code ResetButton}.
   */
  public ResetButton() {
    setSize(50, 20);
    setText("Reset");
    setName("ButtonReset");
    setBackground(Color.RED);
    setEnabled(true);
    setVisible(true);
  }
}
