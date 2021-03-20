package ua.kpi.comsys.iv8230;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class GraphFragment extends Fragment{

    GraphView graphView;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        graphView = getView().findViewById(R.id.graph);
        graphView.setVisibility(View.VISIBLE);

        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(-6.0);
        graphView.getViewport().setMaxX(6.0);

        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(0.0);
        graphView.getViewport().setMaxY(30.0);

        double[] x = new double[100];
        double[] y = new double[100];
        DataPoint[] dataPoint = new DataPoint[100];
        double first = -5.0;
        for (int i = 0; i < 100; i++) {
            x[i] = first;
            y[i] = x[i] * x[i];
            dataPoint[i] = new DataPoint(x[i], y[i]);
            first += 0.1;
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoint);

        graphView.addSeries(series);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_graph, container, false);
    }
}