package com.iceford.heartguard.report;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.iceford.heartguard.R;
import com.iceford.heartguard.report.knowledge.Children_Heart_Rate_Zones_Activity;
import com.iceford.heartguard.report.knowledge.Factors_Affecting_Heart_Rate_Activity;
import com.iceford.heartguard.report.knowledge.High_Heart_Rate_Complications_Activity;
import com.iceford.heartguard.report.knowledge.Resting_Heart_Rate_Activity;
import com.iceford.heartguard.report.knowledge.Target_Heart_Rate_Activity;
import com.iceford.heartguard.report.knowledge.What_Is_Heart_Rate_Activity;

public class HealthKnowledgeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_knowledge);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        ImageButton returnPrevious = findViewById(R.id.return_previous);
        returnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageButton whatIsHeartRateButton = findViewById(R.id.what_is_heart_rate);
        whatIsHeartRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthKnowledgeActivity.this, What_Is_Heart_Rate_Activity.class);
                startActivity(intent);
            }
        });


        ImageButton factorsAffectingHeartRateButton = findViewById(R.id.factors_affecting_heart_rate);
        factorsAffectingHeartRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthKnowledgeActivity.this, Factors_Affecting_Heart_Rate_Activity.class);
                startActivity(intent);
            }
        });


        ImageButton restingHeartRateButton = findViewById(R.id.resting_heart_rate);
        restingHeartRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthKnowledgeActivity.this, Resting_Heart_Rate_Activity.class);
                startActivity(intent);
            }
        });


        ImageButton targetHeartRateButton = findViewById(R.id.target_heart_rate);
        targetHeartRateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthKnowledgeActivity.this, Target_Heart_Rate_Activity.class);
                startActivity(intent);
            }
        });


        ImageButton highHeartRateComplicationsButton = findViewById(R.id.high_heart_rate_complications);
        highHeartRateComplicationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthKnowledgeActivity.this, High_Heart_Rate_Complications_Activity.class);
                startActivity(intent);
            }
        });


        ImageButton childrenHeartRateZonesButton = findViewById(R.id.children_heart_rate_zones);
        childrenHeartRateZonesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HealthKnowledgeActivity.this, Children_Heart_Rate_Zones_Activity.class);
                startActivity(intent);
            }
        });
    }

}

