package org.tylproject.vaadin.mltext;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import org.tylproject.data.mongo.common.LangKey;
import org.tylproject.data.mongo.common.MlText;
import org.tylproject.data.mongo.config.TylContext;
import org.tylproject.vaadin.addon.fields.CombinedField;

/**
 * Created by evacchi on 30/01/15.
 */
public class MultiLangTextField extends CombinedField<MlText, String, TextField> {
    MlText value = new MlText();
    final LangKey currentLanguage;
    final MultiLangEditor multiLangEditor;

    public MultiLangTextField(final LangKey currentLanguage) {
        super(new TextField(), new Button(FontAwesome.FLAG), MlText.class);
        this.currentLanguage = currentLanguage;
        this.multiLangEditor = new MultiLangEditor();

        final MultiLangWindow multiLangWindow = new MultiLangWindow(multiLangEditor, this);

        getButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                multiLangWindow.getEditor().setMultiLangText(getValue());
                UI.getCurrent().addWindow(multiLangWindow);
                multiLangWindow.getEditor().focus();
            }
        });
    }

    @Override
    public void setValue(MlText newValue) throws ReadOnlyException {
        this.value = newValue;
        getBackingField().setValue(newValue.getText(currentLanguage));
    }

    @Override
    public MlText getValue() {
        String text = getBackingField().getValue();
        value.setText(this.currentLanguage, text);
        return value;
    }
}
