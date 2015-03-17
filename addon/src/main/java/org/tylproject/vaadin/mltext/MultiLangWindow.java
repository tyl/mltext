package org.tylproject.vaadin.mltext;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Window;
import org.tylproject.data.mongo.common.MlText;

/**
 * A window to display a multilang editor
 */
public class MultiLangWindow extends Window {
    final MultiLangEditor multiLangEditor;
    final AbstractMultiLangTextField owningField;

    public MultiLangWindow(MultiLangEditor multiLangEditor, AbstractMultiLangTextField owningField) {
        this.multiLangEditor = multiLangEditor;
        this.owningField = owningField;
        setContent(multiLangEditor);
        setModal(true);
        setResizable(false);
        setCloseShortcut(ShortcutAction.KeyCode.ESCAPE);
    }

    @Override
    public void focus() {
        super.focus();
        this.multiLangEditor.focus();
    }

    @Override
    public void close() {
        super.close();
        MlText value = multiLangEditor.getMlText();
        if (!multiLangEditor.isReadOnly()) {
            this.owningField.setValue(value);
        }
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        multiLangEditor.setReadOnly(readOnly);
    }

    public MultiLangEditor getEditor() {
        return multiLangEditor;
    }
}
