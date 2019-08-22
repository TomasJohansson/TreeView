package com.programmerare.samplesforshinemtreeview.geographicareas;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

import me.texy.treeview.TreeNode;
import me.texy.treeview.TreeView;
import me.texy.treeviewdemo.R;

public class MainActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    private ViewGroup viewGroup;
    private TreeNode root;
    private TreeView treeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();

        root = TreeNode.root();
        buildTree();
        treeView = new TreeView(root, this, new GeographicAreaNodeViewFactory());
        View view = treeView.getView();
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        viewGroup.addView(view);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select_all:
                treeView.selectAll();
                break;
            case R.id.deselect_all:
                treeView.deselectAll();
                break;
            case R.id.expand_all:
                treeView.expandAll();
                break;
            case R.id.collapse_all:
                treeView.collapseAll();
                break;
            case R.id.expand_level:
                treeView.expandLevel(1);
                break;
            case R.id.collapse_level:
                treeView.collapseLevel(1);
                break;
            case R.id.show_select_node:
                Toast.makeText(getApplication(), getSelectedNodes(), Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private String getSelectedNodes() {
        StringBuilder stringBuilder = new StringBuilder("You have selected: ");
        List<TreeNode> selectedNodes = treeView.getSelectedNodes();
        for (int i = 0; i < selectedNodes.size(); i++) {
            if (i < 5) {
                stringBuilder.append(selectedNodes.get(i).getValue().toString() + ",");
            } else {
                stringBuilder.append("...and " + (selectedNodes.size() - 5) + " more.");
                break;
            }
        }
        return stringBuilder.toString();
    }


    private void buildTree() {
        List<GeographicArea> topLevelGeographicAreas = GeographicAreaRepository.getInstance().getTopLevelGeographicAreas();
        for (GeographicArea geographicArea : topLevelGeographicAreas) {
            TreeNode treeNode = new TreeNode(geographicArea);
            buildTree(geographicArea, treeNode, 0);
            root.addChild(treeNode);
        }
    }

    // recursive method
    private void buildTree(GeographicArea geographicArea, TreeNode treeNode, int treeNodeLevel) {
        treeNode.setLevel(treeNodeLevel);
        List<GeographicArea> geographicSubAreas = geographicArea.getGeographicSubAreas();
        for (GeographicArea geographicSubArea : geographicSubAreas) {
            TreeNode subTreeNode = new TreeNode(geographicSubArea);
            treeNode.addChild(subTreeNode);
            buildTree(geographicSubArea, subTreeNode, treeNodeLevel + 1); // recursive call
        }
    }

    private void setLightStatusBar(@NonNull View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            getWindow().setStatusBarColor(Color.WHITE);
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewGroup = (RelativeLayout) findViewById(R.id.container);
        setSupportActionBar(toolbar);
        setLightStatusBar(viewGroup);
    }
}
