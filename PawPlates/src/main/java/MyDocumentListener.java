/**
 * =============================================================================
 * File:           MyDocumentListener.java
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
 *   textField.getDocument().addDocumentListener((MyDocumentListener) e -> { ... });
 *
 * TODO:
 *   - None
 * =============================================================================
 */

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@FunctionalInterface
public interface MyDocumentListener extends DocumentListener {
    void update(DocumentEvent e);
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    @Override
    default void insertUpdate(DocumentEvent e) {
        update(e);
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    @Override
    default void removeUpdate(DocumentEvent e) {
        update(e);
    }
    /**
     * Description
     *
     * @param
     * @return
     * @throws
     */
    @Override
    default void changedUpdate(DocumentEvent e) {
        update(e);
    }
}
