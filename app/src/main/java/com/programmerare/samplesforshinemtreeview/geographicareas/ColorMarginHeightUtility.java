package com.programmerare.samplesforshinemtreeview.geographicareas;
// Tomas

import android.graphics.Color;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import me.texy.treeview.TreeNode;
import me.texy.treeviewdemo.R;

public final class ColorMarginHeightUtility {

    private static ColorMarginHeightUtility colorMarginHeightUtility = new ColorMarginHeightUtility();

    public static ColorMarginHeightUtility getInstance() {
        return colorMarginHeightUtility;
    }

    private ColorMarginHeightUtility() {
    }

    /**
     * Convenience method with the following preconditions:
     * The View must be a RelativeLayout which must contain a linearLayout with the id 'node_container', and it
     * must contain a TextView with the id 'node_name_view'
     *
     * The method will use diffent values for the margin, color and height, depending on the level for the treeNode.
     * The margin will increase for each level, e.g. countries (second level) will look indented under continent at the first level.
     * The color tone will also be a little bit different for each level.
     * The height for each row will also be a little different e.g. smaller height for the level with a county than the level for a world continent.
     */
    public void setColorMarginHeight(
        final TreeNode treeNode,
        final View itemView
    ) {
        final RelativeLayout relativeLayout = (RelativeLayout) itemView;
        final LinearLayout linearLayout = (LinearLayout) relativeLayout.findViewById(R.id.node_container);
        final TextView textViewWithTheName = (TextView) linearLayout.findViewById(R.id.node_name_view);
        setColorMarginHeight(treeNode, relativeLayout, linearLayout, textViewWithTheName);
    }

    private void setColorMarginHeight(
        final TreeNode treeNode,
        final RelativeLayout relativeLayout,
        final LinearLayout linearLayout,
        final TextView textViewWithTheName
    ) {
        final int treeNodeLevel = treeNode.getLevel();

        final int color  = getColorForTreeNodeLevel(treeNodeLevel);
        final int margin = getMarginForTreeNodeLevel(treeNodeLevel);
        final int height = getHeightForTreeNodeLevel(treeNodeLevel);

        final float fontSize = getFontSizeForTreeNodeLevel(treeNodeLevel);
        textViewWithTheName.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);

        relativeLayout.setBackgroundColor(color);

        final ViewGroup.MarginLayoutParams relativeLayoutParams = (ViewGroup.MarginLayoutParams) relativeLayout.getLayoutParams();
        relativeLayoutParams.height = height;
        relativeLayout.setLayoutParams(relativeLayoutParams);


        final ViewGroup.MarginLayoutParams linearLayoutParams = (ViewGroup.MarginLayoutParams) linearLayout.getLayoutParams();
        linearLayoutParams.leftMargin = margin;
        linearLayout.setLayoutParams(linearLayoutParams);
    }

    private int getHeightForTreeNodeLevel(final int treeNodeLevel) {
        int height = 120 - 20 * treeNodeLevel; // height will be 120, 100, 80, ...
        if(height < 40) height = 40;
        return height;
    }


    private float getFontSizeForTreeNodeLevel(final int treeNodeLevel) {
        float fontSize = 20 - 2 * treeNodeLevel; // height will be 120, 100, 80, ...
        if(fontSize < 10) fontSize = 10;
        return fontSize;
    }

    private int getMarginForTreeNodeLevel(final int treeNodeLevel) {
        final int margin = 50 * treeNodeLevel; // margin will be 0, 50, 100, ...
        return margin;
    }

    private Map<Integer, Integer> colorForLevel = new HashMap<Integer, Integer>();

    private int getColorForTreeNodeLevel(final int treeNodeLevel) {
        if(colorForLevel.containsKey(treeNodeLevel)) {
            return colorForLevel.get(treeNodeLevel);
        }
        int rgbValue = 240 - 20 * treeNodeLevel; // below using the same value for red green blue, and it will be 240, 220, 200, ...
        if(rgbValue < 0) rgbValue = 0;

        final int rgbColor = Color.rgb(rgbValue, rgbValue, rgbValue);
        colorForLevel.put(treeNodeLevel, rgbColor);
        return rgbColor;
    }
}