package org.tylproject.vaadin.mltext;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;
import org.tylproject.data.mongo.common.LangKey;
import org.tylproject.data.mongo.common.MlText;

import java.net.URL;
import java.util.Iterator;
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
            TextArea f = new TextArea(k.name());
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

    @Override
    public void setReadOnly(boolean readOnly) {
        super.setReadOnly(readOnly);
        for (AbstractTextField f : fieldMap.values()) {
            f.setReadOnly(readOnly);
        }
    }

    @Override
    public void focus() {
        super.focus();
        Iterator<AbstractTextField> iter = fieldMap.values().iterator();
        if (iter.hasNext()) iter.next().focus();
    }
}
