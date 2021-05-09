package ua.kpi.comsys.iv8230;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

public class MainActivity extends AppCompatActivity{
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("TrialApp");

        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);

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

    /*View.OnClickListener ShowMovieInfo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment fragment = new MovieFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_movies, fragment);
            fragmentTransaction.addToBackStack("back");
            fragmentTransaction.commit();
        }
    };*/
    /*View.OnClickListener AddMovie = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment fragment = new AddMovieFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fr_movies, fragment);
            fragmentTransaction.addToBackStack("back");
            fragmentTransaction.commit();
        }
    };*/
    @Override
    public void onBackPressed() {
        actionBar.setDisplayHomeAsUpEnabled(false);
        if (getFragmentManager().getBackStackEntryCount() != 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}