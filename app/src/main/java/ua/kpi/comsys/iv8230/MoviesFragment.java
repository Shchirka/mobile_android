package ua.kpi.comsys.iv8230;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

public class MoviesFragment extends Fragment {

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ScrollView scrollView = (ScrollView) getView().findViewById(R.id.scrollView);

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

            tableLayout.addView(tableRow, i);
        }
        tableLayout.setColumnShrinkable(1, true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }
}