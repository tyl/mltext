package org.tylproject.vaadin.mltext;

import com.vaadin.data.Property;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.UI;
import org.tylproject.data.mongo.common.LangKey;

import java.util.*;

/**
 * Created by evacchi on 05/02/15.
 */
public class LanguagePicker extends ComboBox {

    private final LanguageContextResolver languageContextResolver = new VaadinLanguageContextResolver();

    public LanguagePicker(Collection<?> languageTags) {
        addItems(makeLocaleCollection(languageTags));
        setNullSelectionAllowed(false);

        Locale selectedLangLocale = languageContextResolver.getCurrentLanguageLocale();

        setValue(selectedLangLocale);
        addValueChangeListener(new ValueChange());
    }

    private static Collection<?> makeLocaleCollection(Collection<?> langTags) {
        Set<Locale> locales = new HashSet<Locale>();
        for (Object tag: langTags) {
            Locale locale = Locale.forLanguageTag(tag.toString());
            locales.add(locale);
        }
        return locales;
    }


    class ValueChange implements ValueChangeListener {
        @Override
        public void valueChange(Property.ValueChangeEvent event) {
            Locale selected = (Locale) event.getProperty().getValue();
            UI.getCurrent().setLocale(selected);
            UI.getCurrent().getSession().setLocale(selected);
            UI.getCurrent().getPage().setLocation("");
        }
    }
}
