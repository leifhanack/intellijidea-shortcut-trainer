package strug.intellijidea.shortcuttrainer;

import com.intellij.openapi.ui.DialogWrapper;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class ShowRandomShortcutAction extends AbstractAction {

  private ShortcutTrainerDialog dialog;

  {
    putValue(DialogWrapper.FOCUSED_ACTION, Boolean.FALSE);
    putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
  }

  ShowRandomShortcutAction(ShortcutTrainerDialog dialog) {
    super("Show");
    this.dialog = dialog;
  }

  public void actionPerformed(ActionEvent e) {
    dialog.showShortcutGuess();
  }
}
