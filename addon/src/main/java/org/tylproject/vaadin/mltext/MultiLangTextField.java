package org.tylproject.vaadin.mltext;

import com.vaadin.ui.TextField;
import org.tylproject.data.mongo.config.Context;

/**
 * Created by evacchi on 03/02/15.
 */
public class MultiLangTextField extends AbstractMultiLangTextField<TextField> {
    public MultiLangTextField(Context tylContext) {
        super(new TextField(), tylContext);
    }
    public MultiLangTextField(Context tylContext, String caption) {
        this(tylContext);
        this.setCaption(caption);
    }
}
