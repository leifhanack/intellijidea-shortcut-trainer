package strug.intellij.shortcuttrainer;

import com.intellij.openapi.actionSystem.Shortcut;
import com.intellij.openapi.keymap.KeymapUtil;

import java.util.ArrayList;
import java.util.List;

public class RandomShortcut {

  public String actionId;
  public String shortcutDescription;
  public String shortcutDetailedDescription = " ";
  public Shortcut[] shortcuts;

  public List<String> convertShortcutsToStrings() {
    List<String> convertedShortcuts = new ArrayList<>();
    for (Shortcut shortcut : shortcuts) {
      convertedShortcuts.add(KeymapUtil.getShortcutText(shortcut));
    }
    return convertedShortcuts;
  }
}
