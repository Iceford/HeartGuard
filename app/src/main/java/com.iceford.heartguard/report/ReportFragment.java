package com.iceford.heartguard.report;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import com.iceford.heartguard.R;
import com.iceford.heartguard.auth.UserSessionManager;

public class ReportFragment extends Fragment {
    Boolean loginStatus = UserSessionManager.getInstance().isLoggedIn();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);

        // 心率记录
        CardView cardRecord = view.findViewById(R.id.card_record);
        cardRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginStatus) {
                    Intent intent = new Intent(getActivity(), HeartRateRecordActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "请先登录后查看", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // ECG图像
        CardView cardECG = view.findViewById(R.id.card_ecg);
        cardECG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginStatus) {
                    Intent intent = new Intent(getActivity(), ECGImageActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "请先登录后查看", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // 健康知识
        CardView cardKnowledge = view.findViewById(R.id.card_knowledge);
        cardKnowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HealthKnowledgeActivity.class);
                startActivity(intent);
            }
        });


        // 疾病预防
        CardView cardPrevention = view.findViewById(R.id.card_prevention);
        cardPrevention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DiseasePreventionActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}

