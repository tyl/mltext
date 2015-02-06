package org.tylproject.vaadin.mltext;

import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Window;
import org.tylproject.data.mongo.common.MlText;

/**
 * Created by evacchi on 30/01/15.
 */
public class MultiLangWindow extends Window {
    final MultiLangEditor multiLangEditor;
    final AbstractMultiLangTextField owner;

    public MultiLangWindow(MultiLangEditor multiLangEditor, AbstractMultiLangTextField owner) {
        this.multiLangEditor = multiLangEditor;
        this.owner = owner;
        setContent(multiLangEditor);
//        center();
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
            this.owner.setValue(value);
        }
    }

    public MultiLangEditor getEditor() {
        return multiLangEditor;
    }
}
