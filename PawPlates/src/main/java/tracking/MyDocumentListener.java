package tracking; /**
 * =============================================================================
 * File:           tracking.MyDocumentListener.java
 * Author:         Dakota Hernandez
 * Created:        04/20/25
 * -----------------------------------------------------------------------------
 * Description:
 *   A functional interface that simplifies DocumentListener by requiring
 *   only one method: update(DocumentEvent e).
 *
 * Dependencies:
 *   - javax.swing.event.DocumentListener
 *   - javax.swing.event.DocumentEvent
 *
 * Usage:
 *   textField.getDocument().addDocumentListener((tracking.MyDocumentListener) e -> { ... });
 *
 * TODO:
 *   - None
 * =============================================================================
 */

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@FunctionalInterface
public interface MyDocumentListener extends DocumentListener {
    /**
     * Called whenever the document is changed and one of the event methods is triggered.
     * This is the only method required to implement when using this functional interface.
     *
     * @param e the document event describing the change
     */
    void update(DocumentEvent e);
    /**
     * Handles insertion into the document and forwards it to the update method.
     *
     * @param e the document event describing the insertion
     */
    @Override
    default void insertUpdate(DocumentEvent e) {
        update(e);
    }
    /**
     * Handles removal from the document and forwards it to the update method.
     *
     * @param e the document event describing the removal
     */
    @Override
    default void removeUpdate(DocumentEvent e) {
        update(e);
    }
    /**
     * Handles attribute or formatting changes and forwards them to the update method.
     *
     * @param e the document event describing the change
     */
    @Override
    default void changedUpdate(DocumentEvent e) {
        update(e);
    }
}
