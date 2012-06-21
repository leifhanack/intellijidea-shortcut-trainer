package de.strug.shortcut_trainer;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.Shortcut;
import com.intellij.openapi.actionSystem.ex.ActionManagerEx;
import com.intellij.openapi.keymap.Keymap;
import com.intellij.openapi.keymap.ex.KeymapManagerEx;
import com.intellij.openapi.util.text.StringUtil;

import java.util.Random;

public class RandomShortcutFactory {

  public RandomShortcut createRandomShortcut() {
    String randomActionId = findRandomActionId();
    return createRandomShortcut(randomActionId);
  }

  private String findRandomActionId() {
    Keymap activeKeymap = KeymapManagerEx.getInstanceEx().getActiveKeymap();
    String[] actionIds = activeKeymap.getActionIds();
    String randomActionId;
    Shortcut[] shortcuts;
    do {
      randomActionId = actionIds[new Random().nextInt(actionIds.length)];
      shortcuts = activeKeymap.getShortcuts(randomActionId);
    } while (shortcuts == null || shortcuts.length <= 0);
    return randomActionId;
  }

  private RandomShortcut createRandomShortcut(String actionId) {
    RandomShortcut randomShortcut = new RandomShortcut();
    randomShortcut.actionId = actionId;

    AnAction shortcutAction = ActionManagerEx.getInstanceEx().getAction(actionId);
    if (shortcutAction == null) {
      randomShortcut.shortcutDescription = actionId;
      return randomShortcut;
    }

    Presentation presentation = shortcutAction.getTemplatePresentation();

    randomShortcut.shortcuts = KeymapManagerEx.getInstanceEx().getActiveKeymap().getShortcuts(actionId);
    randomShortcut.shortcutDescription = (StringUtil.isNotEmpty(presentation.getText())) ? presentation.getText() : actionId.replace("$", "");
    randomShortcut.shortcutDetailedDescription = (StringUtil.isNotEmpty(presentation.getDescription())) ? presentation.getDescription() : " ";
    return  randomShortcut;
  }

}
