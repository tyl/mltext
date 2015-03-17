package org.tylproject.vaadin.mltext;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import org.tylproject.data.mongo.common.LangKey;
import org.tylproject.data.mongo.common.MlText;
import org.tylproject.data.mongo.common.Signature;
import org.tylproject.data.mongo.config.Context;
import org.tylproject.data.mongo.config.ThreadSafeContext;
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

    Context ctx = new Context() {

        LangKey defaultLanguage = LangKey.en;

        @Override
        public LangKey defaultLanguage() {
            return defaultLanguage;
        }

        @Override
        public void setDefaultLanguage(LangKey language) {
            this.defaultLanguage = language;
        }

        @Override
        public Signature currentUser() {
            return null;
        }

        @Override
        public void setCurrentUser(Signature signature) {

        }

        @Override
        public LangKey currentLanguage() {
            final UI currentUI = UI.getCurrent();
            if (currentUI == null)
                return defaultLanguage;
            else {
                final LangKey selectedLanguage = currentUI.getSession().getAttribute(LangKey.class);
                return selectedLanguage == null? defaultLanguage : selectedLanguage;
            }
        }

        @Override
        public void setCurrentLanguage(LangKey language) {
            final UI currentUI = UI.getCurrent();
            if (currentUI == null) throw new NullPointerException();
            currentUI.getSession().setAttribute(LangKey.class, language);
        }
    };

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


        final MultiLangTextField multiLangTextField = new MultiLangTextField(ctx);
        multiLangTextField.setValue(mlText1);

        final MultiLangTextArea multiLangTextArea = new MultiLangTextArea(ctx);
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
