package org.tylproject.vaadin.mltext;

import com.vaadin.ui.Window;
import org.tylproject.data.mongo.common.MlText;

/**
 * Created by evacchi on 30/01/15.
 */
public class MultiLangWindow extends Window {
    final MultiLangEditor multiLangEditor;
    final MultiLangTextField owner;

    public MultiLangWindow(MultiLangEditor multiLangEditor, MultiLangTextField owner) {
        this.multiLangEditor = multiLangEditor;
        this.owner = owner;
        setContent(multiLangEditor);
        center();
        setModal(true);
        setResizable(false);
    }

    @Override
    public void close() {
        super.close();
        MlText value = multiLangEditor.getMlText();
        this.owner.setValue(value);
    }

    public MultiLangEditor getEditor() {
        return multiLangEditor;
    }
}
