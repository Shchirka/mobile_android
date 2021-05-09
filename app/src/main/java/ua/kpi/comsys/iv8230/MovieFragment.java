package ua.kpi.comsys.iv8230;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

public class MovieFragment extends Fragment {
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        TableLayout tableLayout = getView().findViewById(R.id.movie_information);

        getParentFragmentManager().setFragmentResultListener("send id", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String list = "";
                InputStream is = null;
                try {
                    is = getView().getContext().getAssets().open(result.getString("id")+".txt");
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
                list.replace("{", "");
                list.replace("}", "");

                Movie movie = new Movie();
                Map<String, String> movie_information = movie.splitInformation(list);
                ImageView poster = getView().findViewById(R.id.movie_poster);
                String postername = movie_information.get("Poster");
                InputStream inputStream = null;
                try {
                    inputStream = getView().getContext().getApplicationContext().getAssets().open("Posters/" + postername);
                    Drawable drawable = Drawable.createFromStream(inputStream, null);
                    poster.setImageDrawable(drawable);
                    poster.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    poster.setLayoutParams(new TableLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 700));
                }
                catch (IOException e){
                    e.printStackTrace();
                }
                for(int i = 0; i < movie_information.size(); i++){
                    Object someKey = movie_information.keySet().toArray()[i];
                    if(someKey.toString() != "Poster"){
                        TextView information = new TextView(getView().getContext());


                        information.setText(movie_information.get(someKey.toString()));
                        information.setTextSize(18);

                        TextView typeOfInformation = new TextView(getView().getContext());
                        typeOfInformation.setText(someKey.toString());
                        typeOfInformation.setTextSize(24);

                        tableLayout.addView(typeOfInformation, 0);
                        tableLayout.addView(information, 1);
                    }
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }
}