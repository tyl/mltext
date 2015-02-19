package org.tylproject.vaadin.mltext;

import com.vaadin.data.Property;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Button;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import javafx.scene.text.Font;
import org.tylproject.data.mongo.common.LangKey;
import org.tylproject.data.mongo.common.MlText;
import org.tylproject.data.mongo.config.Context;
import org.tylproject.data.mongo.helpers.MlTextHelper;
import org.tylproject.vaadin.addon.fields.CombinedField;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by evacchi on 30/01/15.
 */
public abstract class AbstractMultiLangTextField<F extends AbstractTextField> extends CombinedField<MlText, String, F> {


//    final LangKey currentLanguage;
    final Context tylContext;
    final MultiLangEditor multiLangEditor;

    MlTextHelper mlTextHelper;


    public AbstractMultiLangTextField(final F textField, final Context tylContext) {
        super(textField, new Button(FontAwesome.FLAG), MlText.class);
        this.tylContext = tylContext;
//        this.currentLanguage = tylContext.currentLanguage();
        this.multiLangEditor = new MultiLangEditor(makeMlText());

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
                getMlTextHelper().setCurrentText((String) event.getProperty().getValue());
            }
        });
    }

    public MlTextHelper getMlTextHelper() {
        return mlTextHelper;
    }

    @Override
    public void setValue(@Nullable MlText newValue) throws ReadOnlyException {
        MlText value = newValue;
        if (newValue == null) {
            value = makeMlText();
        }
        setSuperValue(value);
        setDisplayValue(value);
    }

    public void setSuperValue(MlText value) {
        super.setValue(value);
        mlTextHelper = MlTextHelper.of(value);
        mlTextHelper.setTylContext(tylContext);
    }

    private void setDisplayValue(MlText value) {

        getBackingField().setValue(mlTextHelper.getCurrentText());

        final LangKey currentLanguage = tylContext.currentLanguage();
        if (isEmpty(value.getText(currentLanguage))) {
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

        setSuperValue(mlText);

        boolean isReadOnly = getBackingField().isReadOnly();
        this.getBackingField().setReadOnly(false);
        setDisplayValue(mlText);
        this.getBackingField().setReadOnly(isReadOnly);
    }

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        multiLangEditor.setReadOnly(readOnly);
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        // the button is always enabled
        getButton().setEnabled(true);
    }


    @Override
    public MlText getValue() {
        String text = getBackingField().getValue();
        MlText mlt = super.getValue();

        if (mlt == null) {
            mlt = makeMlText();
        }


        mlt.setText(tylContext.currentLanguage(), text);
        return mlt;
    }

    private MlText makeMlText() {
        return new MlText();
    }
}
