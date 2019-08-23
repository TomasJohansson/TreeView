package com.programmerare.samplesforshinemtreeview.geographicareas;

import android.util.Log;
import android.view.View;

import me.texy.treeview.TreeNode;
import me.texy.treeview.base.BaseNodeViewBinder;
import me.texy.treeview.base.BaseNodeViewFactory;

import static com.programmerare.samplesforshinemtreeview.geographicareas.GeographicAreaRepository.ID_CITY_OF_SWEDEN_STOCKHOLM;

/**
 * Created by zxy on 17/4/23.
 */

public class GeographicAreaNodeViewFactory extends BaseNodeViewFactory {
    private final static String TAG = GeographicAreaNodeViewFactory.class.getName();

    GeographicAreaRepository geographicAreaRepository = GeographicAreaRepository.getInstance();

    @Override
    public int getViewType(TreeNode treeNode) {
        GeographicArea geographicArea = (GeographicArea)treeNode.getValue();
        int geographicAreaId = geographicArea.getId();
        Log.d(TAG, " getViewType geographicAreaId: " + geographicAreaId);
        return geographicAreaId; // if an id (e.g. a primary key) is used like this then it could be a solution of issue 25 below
    }

    // https://github.com/shineM/TreeView/issues/25
    //      " I need to inflate different layouts depending on item item, how can I have it?
    //       Your node factory only checks level and Node Data is not accessible there."

    @Override
    public BaseNodeViewBinder getNodeViewBinder(View view, int geographicAreaId) {
        // since an id (geographicAreaId) is the parameter then the node data is accessible below (see comment from issue 25 above)

        Log.d(TAG, " getNodeViewBinder geographicAreaId: " + geographicAreaId);
        GeographicArea geographicAreaBy = geographicAreaRepository.getGeographicAreaById(geographicAreaId);
        // Now the node data is accessible through the above repository (i.e. a potential solution for issue 25 mentioned above)
        Log.d(TAG, " getNodeViewBinder getName: " + geographicAreaBy.getName());


        // Special case with a binder class and a layout file of its own:
        if(geographicAreaId == ID_CITY_OF_SWEDEN_STOCKHOLM) {
            return new StockholmCityCheckableNodeViewBinder(view);
        }

        // All other geographic areas use the same binder class and layout file below.
        // The differences are handled programmatically e.g. hiding content ( 'setVisibility(View.INVISIBLE)' )
        // and changing colors, margins, heights, font sizes.
        return new GeographicAreaNodeViewBinder(view);
    }
}