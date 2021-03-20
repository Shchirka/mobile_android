package ua.kpi.comsys.iv8230;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("TrialApp");

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Me");

        tabSpec.setContent(R.id.MePage);
        tabSpec.setIndicator("Me");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Graph");
        tabSpec.setContent(R.id.GraphPage);
        tabSpec.setIndicator("Graph");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Movies");
        tabSpec.setContent(R.id.MoviePage);
        tabSpec.setIndicator("Movies");
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTab(0);
    }

    public void Change(View view){
        Fragment fragment = null;

        switch (view.getId()){
            case R.id.change_graph_btn:
                fragment = new ChartFragment();
                break;
            case R.id.change_chart_btn:
                fragment = new GraphFragment();
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fr_place, fragment);
        fragmentTransaction.commit();
    }
}