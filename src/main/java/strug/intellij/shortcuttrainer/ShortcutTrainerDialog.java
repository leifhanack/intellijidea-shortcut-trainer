package strug.intellij.shortcuttrainer;

import com.intellij.openapi.keymap.KeymapUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ShortcutTrainerDialog extends DialogWrapper {

    private RandomShortcut randomShortcut;
    private JPanel contentPane;
    private JLabel shortcutDetailedDescription;
    private JLabel shortcutDescription;
    private JTextField shortcutGuess;
    private JLabel guessFeedback;

    public ShortcutTrainerDialog(Project project) {
        super(project);
        setModal(true);
        setTitle("Shortcut Trainer");
        setOKActionEnabled(false);
        init();
    }

    private final KeyAdapter keyListener = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent event) {
            handleKeyEventAsShortcut(event);
        }
    };

    private void handleKeyEventAsShortcut(KeyEvent event) {
        KeyStroke stroke = KeyStroke.getKeyStrokeForEvent(event);
        String shortcutText = KeymapUtil.getKeystrokeText(stroke);
        shortcutGuess.setText(shortcutText);
        if (randomShortcut.convertShortcutsToStrings().contains(shortcutText)) {
            guessFeedback.setText("Correct!");
        } else {
            guessFeedback.setText(null);
        }
    }

    @Override
    protected JButton createJButtonForAction(Action action) {
        JButton button = super.createJButtonForAction(action);
        button.addKeyListener(keyListener);
        return button;
    }

    public void setRandomShortcut(RandomShortcut randomShortcut) {
        this.randomShortcut = randomShortcut;
    }

    public void showRandomShortcut() {
        shortcutDescription.setText(randomShortcut.shortcutDescription);
        shortcutDetailedDescription.setText(randomShortcut.shortcutDetailedDescription);
        shortcutGuess.setText("");
        shortcutGuess.addKeyListener(keyListener);
        guessFeedback.setText(null);
        pack();
        repaint();
    }

    public void showShortcutGuess() {
        shortcutGuess.setText(StringUtils.join(randomShortcut.convertShortcutsToStrings(), "  or  "));
        pack();
        repaint();
    }

    @Override
    protected JComponent createCenterPanel() {
        return contentPane;
    }

    @Override
    protected Action @NotNull [] createActions() {
        return new Action[]{
                new ShowRandomShortcutAction(this),
                new NextRandomShortcutAction(this)
        };
    }
}
