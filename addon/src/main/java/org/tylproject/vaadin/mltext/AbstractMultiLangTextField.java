package org.tylproject.vaadin.mltext;

import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import org.tylproject.data.mongo.common.LangKey;
import org.tylproject.data.mongo.common.MlText;
import org.tylproject.data.mongo.config.Context;
import org.tylproject.data.mongo.config.TylContext;
import org.tylproject.vaadin.addon.fields.CombinedField;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by evacchi on 30/01/15.
 */
public abstract class AbstractMultiLangTextField<F extends AbstractTextField> extends CombinedField<MlText, String, F> {


    final LangKey currentLanguage;
    final Context tylContext;
    final MultiLangEditor multiLangEditor;

    public AbstractMultiLangTextField(final F textField, final Context tylContext) {
        super(textField, new Button(FontAwesome.FLAG), MlText.class);
        this.tylContext = tylContext;
        this.currentLanguage = tylContext.currentLanguage();
        this.multiLangEditor = new MultiLangEditor(makeMlText());

        final MultiLangWindow multiLangWindow = new MultiLangWindow(multiLangEditor, this);

        getButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                multiLangEditor.setReadOnly(false);
                multiLangWindow.getEditor().setMultiLangText(getValue());
                multiLangWindow.setReadOnly(isReadOnly());
                UI.getCurrent().addWindow(multiLangWindow);
                multiLangWindow.getEditor().focus();
            }
        });

        getBackingField().addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                AbstractMultiLangTextField.super.getValue().setText(tylContext.currentLanguage(), (String) event.getProperty().getValue());
            }
        });
    }

    @Override
    public void setValue(@Nullable MlText newValue) throws ReadOnlyException {
        MlText value = newValue;
        if (newValue == null) {
            value = makeMlText();
        }
        super.setValue(value);


        getBackingField().setValue(value.getText(currentLanguage));
    }

    @Override
    public void setPropertyDataSource(Property newDataSource) {
        super.setPropertyDataSource(newDataSource);

        // if newDataSource is null, clear the field
        if (newDataSource == null) {
            this.setReadOnly(false);
            this.setValue(null);
            return;
        }

        // otherwise, getValue()
        MlText mlText = null;
        if (newDataSource != null) {
            mlText = (MlText) newDataSource.getValue();
            if (mlText == null) {
                mlText = makeMlText();
                newDataSource.setValue(mlText);
            }
        }

        super.setValue(mlText);

        boolean isReadOnly = getBackingField().isReadOnly();
        this.getBackingField().setReadOnly(false);
        this.getBackingField().setValue(mlText.getText(currentLanguage));
        this.getBackingField().setReadOnly(isReadOnly);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        multiLangEditor.setReadOnly(readOnly);
    }

    @Override
    public MlText getValue() {
        String text = getBackingField().getValue();
        MlText mlt = super.getValue();

        if (mlt == null) {
            mlt = makeMlText();
        }


        mlt.setText(this.currentLanguage, text);
        return mlt;
    }

    private MlText makeMlText() {
        return new MlText(tylContext);
    }
}
