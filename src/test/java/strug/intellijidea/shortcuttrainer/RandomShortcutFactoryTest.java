package strug.intellijidea.shortcuttrainer;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.actionSystem.Shortcut;
import com.intellij.openapi.keymap.Keymap;
import com.intellij.openapi.keymap.KeymapManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Random;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class RandomShortcutFactoryTest {
    @InjectMocks
    private RandomShortcutFactory factory;

    private MockedStatic<KeymapManager> mockedKeymapManager;
    private MockedStatic<ActionManager> mockedActionManager;
    @Mock
    private KeymapManager keymapManager;
    @Mock
    private Keymap keymap;

    @Mock
    private ActionManager actionManager;

    private Presentation presentation;

    @Before
    public void setUp() {
        mockedKeymapManager = Mockito.mockStatic(KeymapManager.class);
        mockedKeymapManager.when(KeymapManager::getInstance).thenReturn(keymapManager);
        when(keymapManager.getActiveKeymap()).thenReturn(keymap);

        mockedActionManager = Mockito.mockStatic(ActionManager.class);
        mockedActionManager.when(ActionManager::getInstance).thenReturn(actionManager);

        presentation = new Presentation();
        presentation.setText("Valid Action");
        presentation.setDescription("Valid description");
    }

    @After
    public void tearDown() {
        mockedKeymapManager.close();
        mockedActionManager.close();
    }

    @Test
    public void testCreateRandomShortcut_EmptyShortcuts() {
        try (MockedConstruction<Random> ignored = mockConstruction(Random.class, (mockRandom, context) -> {
            when(mockRandom.nextInt(anyInt())).thenReturn(0, 1);  // Simulate no shortcuts, then valid shortcuts and action
        })) {
            String actionIdWithNoShortcut = "actionWithNoShortcut";
            String validActionId = "validAction";
            when(keymap.getActionIds()).thenReturn(new String[]{actionIdWithNoShortcut, validActionId});

            Shortcut[] emptyShortcutArray = new Shortcut[0];
            when(keymap.getShortcuts(actionIdWithNoShortcut)).thenReturn(emptyShortcutArray);
            when(actionManager.getAction(actionIdWithNoShortcut)).thenReturn(null);

            Shortcut[] validShortcutArray = {mock(Shortcut.class)};
            when(keymap.getShortcuts(validActionId)).thenReturn(validShortcutArray);
            AnAction validAction = mock(AnAction.class);
            when(actionManager.getAction(validActionId)).thenReturn(validAction);
            when(validAction.getTemplatePresentation()).thenReturn(presentation);

            // execute
            RandomShortcut randomShortcut = factory.createRandomShortcut();

            assertNotNull("RandomShortcut should not be null after finding valid shortcuts", randomShortcut);
            assertNotEquals("Should not have actionId of empty shortcuts", actionIdWithNoShortcut, randomShortcut.actionId);
        }
    }

    @Test
    public void testCreateRandomShortcut_NullAction() {
        try (MockedConstruction<Random> ignored = mockConstruction(Random.class, (mockRandom, context) -> {
            when(mockRandom.nextInt(anyInt())).thenReturn(0, 1);  // Simulate shortcuts but action is null, then valid action
        })) {
            String actionIdWithNullAction = "actionWithNullAction";
            String validActionId = "validAction";
            when(keymap.getActionIds()).thenReturn(new String[]{actionIdWithNullAction, validActionId});

            Shortcut[] nonEmptyShortcutArray = {mock(Shortcut.class)};
            when(keymap.getShortcuts(actionIdWithNullAction)).thenReturn(nonEmptyShortcutArray);
            when(actionManager.getAction(actionIdWithNullAction)).thenReturn(null);

            when(keymap.getShortcuts(validActionId)).thenReturn(nonEmptyShortcutArray);
            AnAction validAction = mock(AnAction.class);
            when(actionManager.getAction(validActionId)).thenReturn(validAction);
            when(validAction.getTemplatePresentation()).thenReturn(presentation);

            // execute
            RandomShortcut randomShortcut = factory.createRandomShortcut();

            assertNotNull("RandomShortcut should not be null after finding valid action", randomShortcut);
            assertNotEquals("Should not have actionId of null action", actionIdWithNullAction, randomShortcut.actionId);
        }
    }

    @Test
    public void testCreateRandomShortcut_ValidShortcutsAndAction() {
        try (MockedConstruction<Random> ignored = mockConstruction(Random.class, (mockRandom, context) -> {
            when(mockRandom.nextInt(anyInt())).thenReturn(0);  // Immediately simulate valid shortcuts and action
        })) {
            String validActionId = "validAction";
            when(keymap.getActionIds()).thenReturn(new String[]{validActionId});

            Shortcut[] validShortcutArray = {mock(Shortcut.class)};
            when(keymap.getShortcuts(validActionId)).thenReturn(validShortcutArray);

            AnAction validAction = mock(AnAction.class);
            when(actionManager.getAction(validActionId)).thenReturn(validAction);

            Presentation presentation = new Presentation();
            presentation.setText("Valid Action");
            presentation.setDescription("Valid description");
            when(validAction.getTemplatePresentation()).thenReturn(presentation);

            // execute
            RandomShortcut randomShortcut = factory.createRandomShortcut();

            assertNotNull("RandomShortcut should not be null", randomShortcut);
            assertEquals("Expected the validActionId to be set", validActionId, randomShortcut.actionId);
            assertEquals("Valid Action", randomShortcut.shortcutDescription);
        }
    }

}
