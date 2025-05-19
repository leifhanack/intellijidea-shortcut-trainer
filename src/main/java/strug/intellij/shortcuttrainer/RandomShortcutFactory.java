package strug.intellij.shortcuttrainer;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.keymap.Keymap;
import com.intellij.openapi.keymap.KeymapManager;
import com.intellij.openapi.util.text.StringUtil;

import java.util.Random;

public class RandomShortcutFactory {

  public RandomShortcut createRandomShortcut() {
    Keymap activeKeymap = KeymapManager.getInstance().getActiveKeymap();
    String[] actionIds = activeKeymap.getActionIds();
    Random random = new Random();

    RandomShortcut randomShortcut = new RandomShortcut();
    AnAction shortcutAction;
    do {
      randomShortcut.actionId = actionIds[random.nextInt(actionIds.length)];
      randomShortcut.shortcuts = activeKeymap.getShortcuts(randomShortcut.actionId);
      shortcutAction = ActionManager.getInstance().getAction(randomShortcut.actionId);
    } while (randomShortcut.shortcuts.length == 0 || shortcutAction == null);

    Presentation presentation = shortcutAction.getTemplatePresentation();
    randomShortcut.shortcutDescription = (StringUtil.isNotEmpty(presentation.getText())) ? presentation.getText() : randomShortcut.actionId.replace("$", "");
    randomShortcut.shortcutDetailedDescription = (StringUtil.isNotEmpty(presentation.getDescription())) ? presentation.getDescription() : " ";

    return  randomShortcut;
  }

}
