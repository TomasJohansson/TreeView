package com.programmerare.samplesforshinemtreeview.geographicareas;

import android.view.View;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.CheckableNodeViewBinder;
import me.texy.treeviewdemo.R;

public class StockholmCityCheckableNodeViewBinder extends CheckableNodeViewBinder {

    public StockholmCityCheckableNodeViewBinder(View itemView) {
        super(itemView);
    }

    @Override
    public int getCheckableViewId() {
        return R.id.checkBox;
    }

    @Override
    public int getLayoutId() {
        return R.layout.sample_item_stockholmcity_geographic_area;
    }

    @Override
    public void bindView(TreeNode treeNode) {
    }
}
