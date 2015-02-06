package org.tylproject.vaadin.mltext;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.tylproject.data.mongo.common.LangKey;
import org.tylproject.data.mongo.common.MlText;
import org.tylproject.data.mongo.config.TylContext;
import org.tylproject.vaadin.addon.fields.CombinedField;
import org.vaadin.spring.VaadinUI;

import java.util.Arrays;
import java.util.Locale;

import static org.tylproject.data.mongo.common.LangKey.*;

/**
 * Created by evacchi on 30/01/15.
 */
@VaadinUI
@Theme("valo")
public class DemoUI extends UI {

//    final LangKey currentLanguage = en;

    @Override
    protected void init(VaadinRequest request) {

        final LanguagePicker languagePicker = new LanguagePicker(Arrays.asList(LangKey.values()));

        final MlText mlText1 = makeText();
        final MlText mlText2 = makeText();

        Locale current = UI.getCurrent().getLocale();


        LangKey currentLanguage;
        try {
            currentLanguage = LangKey.valueOf(current.toLanguageTag());
        } catch (IllegalArgumentException ex) {
            currentLanguage = it;
        }


        final MultiLangTextField multiLangTextField = new MultiLangTextField(currentLanguage);
        multiLangTextField.setValue(mlText1);

        final MultiLangTextArea multiLangTextArea = new MultiLangTextArea(currentLanguage);
        multiLangTextArea.setValue(mlText2);



        VerticalLayout vl = new VerticalLayout(languagePicker, multiLangTextField, multiLangTextArea);
        vl.setMargin(true);
        setContent(vl);

    }

    private MlText makeText() {
        final MlText mlText = new MlText();

        mlText.setText(it, "Ciao Mondo");
        mlText.setText(en, "Hello World");
        mlText.setText(fr, "Salut à tous le Monde");
        mlText.setText(es, "Hola Mundo");
        mlText.setText(de, "Hallo Welt");
        return mlText;
    }
}
