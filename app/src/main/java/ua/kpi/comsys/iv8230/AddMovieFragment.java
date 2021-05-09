package ua.kpi.comsys.iv8230;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddMovieFragment extends Fragment {

    public String add_title;
    public String add_type;
    public String add_year;
    public Movie new_movie;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        EditText title = getView().findViewById(R.id.enter_title);
        EditText type = getView().findViewById(R.id.enter_type);
        EditText year = getView().findViewById(R.id.enter_year);

        Button add_movie = getView().findViewById(R.id.add_movie_button);

        add_movie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_title = title.getText().toString();
                add_type = type.getText().toString();
                add_year = year.getText().toString();

                new_movie = new Movie(add_title, add_year, add_type);

                Bundle result = new Bundle();
                result.putString("add title", add_title);
                result.putString("add type", add_type);
                result.putString("add year", add_year);
                getParentFragmentManager().setFragmentResult("add movie", result);

                ((MainActivity)getActivity()).onBackPressed();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_movie, container, false);
    }
}