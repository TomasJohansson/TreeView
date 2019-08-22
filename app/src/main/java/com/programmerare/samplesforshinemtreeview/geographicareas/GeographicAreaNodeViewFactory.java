package com.programmerare.samplesforshinemtreeview.geographicareas;

import android.view.View;

import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.BaseNodeViewFactory;


/**
 * Created by zxy on 17/4/23.
 */

public class GeographicAreaNodeViewFactory extends BaseNodeViewFactory {
    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int level) {
        return new GeographicAreaNodeViewBinder(view);
    }
}
