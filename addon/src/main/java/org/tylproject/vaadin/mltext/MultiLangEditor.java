package org.tylproject.vaadin.mltext;

import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import org.tylproject.data.mongo.common.LangKey;
import org.tylproject.data.mongo.common.MlText;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by evacchi on 30/01/15.
 */
public class MultiLangEditor extends FormLayout {
    Map<LangKey, AbstractTextField> fieldMap = new LinkedHashMap<LangKey, AbstractTextField>();
    MlText source = new MlText();

    public MultiLangEditor() {

        for (LangKey k: LangKey.values()) {
            TextField f = new TextField(k.name());
            fieldMap.put(k, f);
            addComponent(f);
        }

    }

    public void setMultiLangText(MlText source) {
        this.source = source;

        for (LangKey k: LangKey.values()) {
            fieldMap.get(k).setValue(source.getText(k));
        }

        setMargin(true);
        setSizeUndefined();
    }

    public MlText getMlText() {
        for (Map.Entry<LangKey, AbstractTextField> e: fieldMap.entrySet()) {
            source.setText(e.getKey(), e.getValue().getValue());
        }
        return source;
    }
}
