package de.strug.shortcut_trainer;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;

public class ShortcutTrainer extends AnAction {

  public void actionPerformed(AnActionEvent event) {

    final Project project = event.getData(PlatformDataKeys.PROJECT);
    ShortcutTrainerDialog dialog = new ShortcutTrainerDialog(project);
    dialog.setRandomShortcut(new RandomShortcutFactory().createRandomShortcut());
    dialog.showRandomShortcut();
    dialog.show();
  }
}
