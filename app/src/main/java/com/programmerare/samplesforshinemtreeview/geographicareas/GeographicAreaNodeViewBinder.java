package com.programmerare.samplesforshinemtreeview.geographicareas;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import me.texy.treeviewdemo.R;
import me.texy.treeview.TreeNode;
import me.texy.treeview.base.CheckableNodeViewBinder;

/**
 * Created by zxy on 17/4/23.
 */

public class GeographicAreaNodeViewBinder extends CheckableNodeViewBinder {
    TextView textView;
    ImageView imageView;
    ImageView mapIconImageView;
    public GeographicAreaNodeViewBinder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.node_name_view);
        imageView = (ImageView) itemView.findViewById(R.id.arrow_img);
        mapIconImageView = (ImageView) itemView.findViewById(R.id.map_icon);
    }

    @Override
    public int getCheckableViewId() {
        return R.id.checkBox;
    }

    @Override
    public int getLayoutId() {
        return R.layout.sample_item_geographic_area;
    }

    @Override
    public void bindView(final TreeNode treeNode) {
        textView.setText(treeNode.getValue().toString());
        imageView.setRotation(treeNode.isExpanded() ? 90 : 0);
        imageView.setVisibility(treeNode.hasChild() ? View.VISIBLE : View.INVISIBLE);

        // note the preconditions for below method regarding the itemView (must be RelativeLayout containing a linearLayout with the id node_container)
        ColorMarginHeightUtility.getInstance().setColorMarginHeight(treeNode, this.itemView);


        final GeographicArea geographicArea = (GeographicArea)treeNode.getValue();
        showMapIconOnlyIfCountryLevel(geographicArea);
    }

    private void showMapIconOnlyIfCountryLevel(GeographicArea geographicArea) {
        mapIconImageView.setVisibility(geographicArea.isCountryLevel() ? View.VISIBLE : View.INVISIBLE);
        // TODO make the map icon clickable 
    }

    @Override
    public void onNodeToggled(TreeNode treeNode, boolean expand) {
        if (expand) {
            imageView.animate().rotation(90).setDuration(200).start();
        } else {
            imageView.animate().rotation(0).setDuration(200).start();
        }
    }
}