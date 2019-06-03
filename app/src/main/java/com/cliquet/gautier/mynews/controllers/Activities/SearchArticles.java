package com.cliquet.gautier.mynews.controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.cliquet.gautier.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchArticles extends AppCompatActivity {

    @BindView(R.id.activity_search_articles_politics_checkbox)
    CheckBox usCheckbox;
    @BindView(R.id.activity_search_articles_business_checkbox)
    CheckBox businessCheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_articles);
        ButterKnife.bind(this);

        usCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usCheckbox.isChecked()) {
                    businessCheckbox.setChecked(true);
                    usCheckbox.setText("OK!!!!");
                }
                else {
                    usCheckbox.setText("U.S.");
                }
            }
        });
    }
}
