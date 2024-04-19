package strug.intellijidea.shortcuttrainer;

import com.intellij.openapi.actionSystem.Shortcut;
import com.intellij.openapi.keymap.KeymapUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RandomShortcut {

  public String actionId;
  public String shortcutDescription;
  public String shortcutDetailedDescription = " ";
  public Shortcut[] shortcuts;

  public List<String> convertShortcutsToStrings() {
    List<String> convertedShortcuts = new ArrayList<String>();
    for (Shortcut shortcut : Arrays.asList(shortcuts)) {
      convertedShortcuts.add(KeymapUtil.getShortcutText(shortcut));
    }
    return convertedShortcuts;
  }
}
