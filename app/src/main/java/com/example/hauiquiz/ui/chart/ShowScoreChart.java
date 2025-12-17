package com.example.hauiquiz.ui.chart;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hauiquiz.R;
import com.example.hauiquiz.dao.Test_resultDAO;
import com.example.hauiquiz.entity.Test_result;
import com.example.hauiquiz.ui.home.LoginActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ShowScoreChart extends AppCompatActivity implements View.OnClickListener {
    private BarChart bar_chart;
    private PieChart pie_chart;
    private ImageButton btn_back;

    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_chart);

        getWidget();
        getData();
        setEvents();
    }

    private void setEvents() {
        btn_back.setOnClickListener(this);
    }

    private void getWidget() {
        bar_chart = findViewById(R.id.bar_chart);
        pie_chart = findViewById(R.id.pie_chart);
        btn_back = findViewById(R.id.thongke_btn_back);
    }

    private void getData() {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();

        Test_resultDAO tsDAO = new Test_resultDAO(this);
        List<Test_result> list = new ArrayList<>(tsDAO.getAllByUserId(LoginActivity.USER_ID));

        int countGioi, countKha, countTB, countYeu, countKem;
        countGioi = countKha = countTB = countYeu = countKem = 0;

        for (Test_result ts : list) {
            Double d = ts.getScore();
            if (d == null) continue; // tránh NullPointer
            if (d < 4.0) {
                countKem++;
            } else if (d < 5.5) {
                countYeu++;
            } else if (d < 7.0) {
                countTB++;
            } else if (d < 8.5) {
                countKha++;
            } else {
                countGioi++;
            }
        }

        barEntries.add(new BarEntry(0, countKem));
        barEntries.add(new BarEntry(1, countYeu));
        barEntries.add(new BarEntry(2, countTB));
        barEntries.add(new BarEntry(3, countKha));
        barEntries.add(new BarEntry(4, countGioi));

        BarDataSet barDataSet = new BarDataSet(barEntries, "Thống kê điểm");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        // BỎ .0 TRÊN CỘT
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        BarData barData = new BarData(barDataSet);
        bar_chart.setData(barData);

        // Nhãn trục X
        String[] labels = {"<4.0", "4.0 – <5.5", "5.5 – <7.0", "7.0 – <8.5", "8.5 – 10"};
        XAxis xAxis = bar_chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // Trục Y chỉ hiển thị số nguyên
        bar_chart.getAxisLeft().setGranularity(1f);
        bar_chart.getAxisLeft().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });
        bar_chart.getAxisRight().setEnabled(false);

        bar_chart.getDescription().setText("Biểu đồ phân bố điểm");
        bar_chart.animateY(1000);
        bar_chart.invalidate();

        // PIE CHART
        pieEntries.add(new PieEntry(countKem, "<4.0"));
        pieEntries.add(new PieEntry(countYeu, "4.0 – <5.5"));
        pieEntries.add(new PieEntry(countTB, "5.5 – <7.0"));
        pieEntries.add(new PieEntry(countKha, "7.0 – <8.5"));
        pieEntries.add(new PieEntry(countGioi, "8.5 – 10"));


        PieDataSet pieDataSet = new PieDataSet(pieEntries, "Tỷ lệ điểm");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        // BỎ .0 TRONG PIE
        pieDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        PieData pieData = new PieData(pieDataSet);
        pie_chart.setData(pieData);
        pie_chart.getDescription().setText("Biểu đồ tỷ lệ điểm");
        pie_chart.animateY(1000);
        pie_chart.invalidate();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.thongke_btn_back) {
            getBack();
        }
    }

    private void getBack() {
        finish();
    }
}