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
import org.tylproject.data.mongo.helpers.MlTextHelper;
import org.tylproject.vaadin.addon.fields.CombinedField;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * A textfield for multilanguage
 */
public abstract class AbstractMultiLangTextField<F extends AbstractTextField> extends CombinedField<MlText, String, F> {

    final Context tylContext;
    final MultiLangEditor multiLangEditor;

//    MlTextHelper mlTextHelper;


    public AbstractMultiLangTextField(final F textField, final Context tylContext) {
        super(textField, new Button(FontAwesome.FLAG), MlText.class);
        this.tylContext = tylContext;
        this.multiLangEditor = new MultiLangEditor(makeMlText());
        this.setValue(makeMlText());
        this.getBackingField().setNullRepresentation("");

        final MultiLangWindow multiLangWindow = new MultiLangWindow(multiLangEditor, this);

        getButton().addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                multiLangWindow.setReadOnly(false);
                MlText mlText = getValue();
                multiLangWindow.getEditor().setMultiLangText(mlText);
                multiLangWindow.setReadOnly(isReadOnly());
                UI.getCurrent().addWindow(multiLangWindow);
                multiLangWindow.getEditor().focus();
            }
        });

        getBackingField().addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
            if (!isReadOnly()) {
                getMlTextHelper().setCurrentText((String) event.getProperty().getValue());
            }
            }
        });

    }

    public MlTextHelper getMlTextHelper() {
        return new MlTextHelper(getValue(), tylContext);
    }

    @Override
    public void setValue(@Nullable MlText newValue) throws ReadOnlyException {
        MlText value = newValue;
        if (newValue == null) {
            value = makeMlText();
        }
        super.setValue(value);
        setDisplayValue(getMlTextHelper());
    }

    private void setDisplayValue(MlTextHelper value) {

        F backingField = getBackingField();
        // !readonly => field is editable
        if (!isReadOnly() && value.isCurrentTextEmpty()) {
            backingField.setValue(null);
        } else {
            boolean isReadOnly = backingField.isReadOnly();
            backingField.setReadOnly(false);
            backingField.setValue(value.getCurrentText());
            backingField.setReadOnly(isReadOnly);
        }

        // display an empty flag on fallback
        if (value.isCurrentTextEmpty()) {
            getButton().setIcon(FontAwesome.FLAG_O);
        } else {
            getButton().setIcon(FontAwesome.FLAG);
        }
    }

    private boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    @Override
    public void setPropertyDataSource(Property newDataSource) {
        super.setPropertyDataSource(newDataSource);

        // if newDataSource is null, clear the field
        if (newDataSource == null) {
            boolean isReadOnly = this.isReadOnly();
            this.setReadOnly(false);
            this.setValue(null);
            this.setReadOnly(isReadOnly);
            return;
        }

        // otherwise, getValue()
        MlText mlText = null;
        mlText = (MlText) newDataSource.getValue();
        if (mlText == null) {
            mlText = makeMlText();
            newDataSource.setValue(mlText);
        }

        super.setValue(mlText);

        // set display value
        boolean isReadOnly = getBackingField().isReadOnly();
        this.getBackingField().setReadOnly(false);
        setDisplayValue(new MlTextHelper(mlText, tylContext));
        this.getBackingField().setReadOnly(isReadOnly);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        multiLangEditor.setReadOnly(readOnly);

        // refresh display value
        setDisplayValue(this.getMlTextHelper());

    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        // the button is always enabled
        getButton().setEnabled(true);
    }

//
//    @Override
//    public MlText getValue() {
//        String text = getBackingField().getValue();
//        MlText mlt = super.getValue();
//
////        if (mlt == null) {
////            mlt = makeMlText();
////        }
//
////        mlt.setText(tylContext.currentLanguage(), text);
//        return mlt;
//    }

    private MlText makeMlText() {
        return new MlText();
    }
}
