package strug.intellijidea.shortcuttrainer;

import com.intellij.openapi.ui.DialogWrapper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class NextRandomShortcutAction extends AbstractAction {

  private ShortcutTrainerDialog dialog;

  {
    putValue(DialogWrapper.FOCUSED_ACTION, Boolean.FALSE);
    putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
  }

  NextRandomShortcutAction(ShortcutTrainerDialog dialog) {
    super("Next");
    this.dialog = dialog;
  }

  public void actionPerformed(ActionEvent e) {
    dialog.setRandomShortcut(new RandomShortcutFactory().createRandomShortcut());
    dialog.showRandomShortcut();
  }
}
