package ua.kpi.comsys.iv8230;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoviesFragment extends Fragment{
    ArrayAdapter<String> arrayAdapter;
    public int index;
    public String imdb;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);

        ArrayList<TableRow> tableRows = new ArrayList<>();

        index = 0;

        //arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

        String list = "";
        InputStream is = null;
        try {
            is = getView().getContext().getAssets().open("MoviesList.txt");
            DataInputStream data = new DataInputStream(is);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(data));

            String string;
            while ((string = bufferedReader.readLine()) != null){
                list += list + string;
            }
            bufferedReader.close();
            data.close();
            is.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        list = list.substring(list.indexOf("[") + 1, list.indexOf("]"));

        String leftbr = "{";
        String rightbr = "}";
        int lastIndex_l = 0;
        int lastIndex_r = 0;
        ArrayList<String> movies = new ArrayList<>();
        while ((lastIndex_l != -1)&&(lastIndex_r != -1)){
            lastIndex_l = list.indexOf(leftbr, lastIndex_l);
            lastIndex_r = list.indexOf(rightbr, lastIndex_r);
            if((lastIndex_l != -1)&&(lastIndex_r != -1)){
                movies.add(list.substring(lastIndex_l+1, lastIndex_r));
                lastIndex_l += 1;
                lastIndex_r += 1;
            }
        }
        Movie movie = new Movie();
        ArrayList<Map<String, String>> MoviesArray = movie.splitMovies(movies);
        int rows = MoviesArray.size();

        TableLayout tableLayout = getView().findViewById(R.id.tableLayout);

        for(int i = 0; i < rows; i++){
            TableRow tableRow = new TableRow(getView().getContext());
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ImageView imageView = new ImageView(getView().getContext());
            String postername = MoviesArray.get(i).get("Poster");
            InputStream inputStream = null;
            try {
                inputStream = getView().getContext().getApplicationContext().getAssets().open("Posters/" + postername);
                Drawable drawable = Drawable.createFromStream(inputStream, null);
                imageView.setImageDrawable(drawable);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            catch (IOException e){
                e.printStackTrace();
            }
            tableRow.addView(imageView, 0);

            LinearLayout linearLayout_title = new LinearLayout(getView().getContext());
            linearLayout_title.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout linearLayout_year = new LinearLayout(getView().getContext());
            linearLayout_year.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout linearLayout_type = new LinearLayout(getView().getContext());
            linearLayout_type.setOrientation(LinearLayout.HORIZONTAL);
            TextView title = new TextView(getView().getContext());
            TextView year = new TextView(getView().getContext());
            TextView type = new TextView(getView().getContext());

            title.setText(MoviesArray.get(i).get("Title"));
            year.setText(MoviesArray.get(i).get("Year"));
            type.setText(MoviesArray.get(i).get("Type"));

            title.setTextSize(24);
            year.setTextSize(18);
            type.setTextSize(18);

            linearLayout_title.addView(title);
            linearLayout_year.addView(year);
            linearLayout_type.addView(type);

            TableLayout tl = new TableLayout(getView().getContext());
            tl.addView(linearLayout_title, 0);
            tl.addView(linearLayout_year, 1);
            tl.addView(linearLayout_type, 2);
            tableRow.addView(tl, 1);

            tableRows.add(tableRow);

            tableLayout.addView(tableRow, i);

            index++;

            tableRow.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getView().getContext());
                    deleteDialog.setTitle("Deleting movie. \nAre you sure?");

                    deleteDialog.setPositiveButton("Yeap!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tableLayout.removeView(tableRow);
                            index--;
                        }
                    });

                    deleteDialog.setNegativeButton("Definitely not", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    deleteDialog.show();
                    return false;
                }
            });
        }
        tableLayout.setColumnShrinkable(1, true);

        for(int i = 1; i < tableRows.size(); i++){
            String imdbID = MoviesArray.get(i).get("imdbID");
            if (imdbID != null){
                tableRows.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Fragment fragment = new MovieFragment();

                        Bundle id = new Bundle();
                        id.putString("id", imdbID);
                        getParentFragmentManager().setFragmentResult("send id", id);

                        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                        fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentById(R.id.fr_movies)).add(R.id.fr_movies, fragment);
                        fragmentTransaction.addToBackStack("back");
                        fragmentTransaction.commit();
                    }
                });
            }
        }

        getParentFragmentManager().setFragmentResultListener("add movie", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String get_title = result.getString("add title");
                String get_type = result.getString("add type");
                String get_year = result.getString("add year");

                TableRow tableRow = new TableRow(getView().getContext());
                tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                LinearLayout linearLayout_title = new LinearLayout(getView().getContext());
                linearLayout_title.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout linearLayout_year = new LinearLayout(getView().getContext());
                linearLayout_year.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout linearLayout_type = new LinearLayout(getView().getContext());
                linearLayout_type.setOrientation(LinearLayout.HORIZONTAL);
                TextView title = new TextView(getView().getContext());
                TextView year = new TextView(getView().getContext());
                TextView type = new TextView(getView().getContext());

                title.setText(get_title);
                year.setText(get_year);
                type.setText(get_type);

                title.setTextSize(24);
                year.setTextSize(18);
                type.setTextSize(18);

                linearLayout_title.addView(title);
                linearLayout_year.addView(year);
                linearLayout_type.addView(type);

                TableLayout tl = new TableLayout(getView().getContext());
                tl.addView(linearLayout_title, 0);
                tl.addView(linearLayout_year, 1);
                tl.addView(linearLayout_type, 2);
                tableRow.addView(tl);

                tableLayout.addView(tableRow, index);

                tableRow.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getView().getContext());
                        deleteDialog.setTitle("Deleting movie. \nAre you sure?");
                        deleteDialog.setPositiveButton("Yeap!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tableLayout.removeView(tableRow);
                                index--;
                            }
                        });
                        deleteDialog.setNegativeButton("Definitely not", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        deleteDialog.show();
                        return false;
                    }
                });
                index++;
            }
        });

        ImageButton addMovie = getView().findViewById(R.id.add_button);
        addMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddMovieFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentById(R.id.fr_movies)).add(R.id.fr_movies, fragment);
                fragmentTransaction.addToBackStack("back");
                fragmentTransaction.commit();
            }
        });
        /*SearchView searchView = getView().findViewById(R.id.search_movie);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return true;
            }
        });*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}