package com.programmerare.samplesforshinemtreeview.geographicareas;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

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

    private void showMapIconOnlyIfCountryLevel(final GeographicArea geographicArea) {
        mapIconImageView.setVisibility(geographicArea.isCountryLevel() ? View.VISIBLE : View.INVISIBLE);
        if(geographicArea.isCountryLevel()) {
            mapIconImageView.setVisibility(View.VISIBLE);
            mapIconImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMapForGeographicArea(geographicArea, mapIconImageView.getContext());
                }
            });
        }
    }

    private void showMapForGeographicArea(
        final GeographicArea geographicArea,
        final Context context
    ) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("TODO: Show a map");
        alertDialog.setMessage("TODO: Show a map for the geographic area: " + geographicArea.getName());
        alertDialog.show();
        // Instead of the above, code like below might be used for showing a map with the clicked geographicArea e.g. country
        // https://developers.google.com/maps/documentation/urls/android-intents
        // Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + geographicArea.getName());
        // Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // mapIntent.setPackage("com.google.android.apps.maps");
        // context.startActivity(mapIntent);
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