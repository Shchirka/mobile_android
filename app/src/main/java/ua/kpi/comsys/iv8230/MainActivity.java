package ua.kpi.comsys.iv8230;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    GraphView graphView;
    PieChart pieChart;
    Button change_btn;
    public int count = 1;
    TextView graphText;
    TextView chartText;

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

        tabHost.setCurrentTab(0);

        graphText = (TextView) findViewById(R.id.graph_text);
        chartText = (TextView) findViewById(R.id.chart_text);
        graphText.setVisibility(View.VISIBLE);
        chartText.setVisibility(View.GONE);

        graphView = (GraphView) findViewById(R.id.graph);
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

        String label = "colors";
        pieChart = findViewById(R.id.pieChart_view);
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        Map<String, Integer> entriesMap = new HashMap<>();

        entriesMap.put("green", 35);
        entriesMap.put("yellow", 40);
        entriesMap.put("red", 25);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.GREEN);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);

        for (String type : entriesMap.keySet()) {
            pieEntries.add(new PieEntry(entriesMap.get(type).floatValue(), type));
        }

        PieDataSet pieDataSet = new PieDataSet(pieEntries, label);
        pieDataSet.setValueTextSize(12f);
        pieDataSet.setColors(colors);
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(true);
        pieChart.setData(pieData);
        pieChart.invalidate();
        pieData.setValueFormatter(new PercentFormatter());

        pieChart.setVisibility(View.GONE);

        change_btn = (Button) findViewById(R.id.change_btn);
        change_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (count) {
            case 1:
                count += 1;
                graphView.setVisibility(View.GONE);
                pieChart.setVisibility(View.VISIBLE);
                chartText.setVisibility(View.VISIBLE);
                graphText.setVisibility(View.GONE);
                break;
            case 2:
                count -= 1;
                graphView.setVisibility(View.VISIBLE);
                pieChart.setVisibility(View.GONE);
                graphText.setVisibility(View.VISIBLE);
                chartText.setVisibility(View.GONE);
                break;
        }
    }
}