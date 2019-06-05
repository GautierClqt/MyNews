package com.cliquet.gautier.mynews.controllers.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.cliquet.gautier.mynews.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchArticles extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.activity_search_articles_arts_checkbox)
    CheckBox artsCheckbox;
    @BindView(R.id.activity_search_articles_business_checkbox)
    CheckBox businessCheckbox;
    @BindView(R.id.activity_search_articles_entrepreneurs_checkbox)
    CheckBox entrepreneursCheckbox;
    @BindView(R.id.activity_search_articles_politics_checkbox)
    CheckBox politicsCheckbox;
    @BindView(R.id.activity_search_articles_sports_checkbox)
    CheckBox sportsCheckbox;
    @BindView(R.id.activity_search_articles_travel_checkbox)
    CheckBox travelCheckbox;


    ArrayList<String> strSearchCheck = new ArrayList<>();
    String strCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_articles);
        ButterKnife.bind(this);

        artsCheckbox.setOnClickListener(this);
        businessCheckbox.setOnClickListener(this);
        entrepreneursCheckbox.setOnClickListener(this);
        politicsCheckbox.setOnClickListener(this);
        sportsCheckbox.setOnClickListener(this);
        travelCheckbox.setOnClickListener(this);
    }

    //verify if a checkbox is check or uncheck
    private void verifyCheckboxState(boolean booleanCheckbox, String strCheck) {
        if (booleanCheckbox) {
            addCheckedCheckbox(strCheck);
        } else {
            removeUncheckedCheckbox(strCheck);
        }
    }

    //remove the term of the unchecked checkbox of the arraylist
    private void removeUncheckedCheckbox(String strCheck) {
        for(int i = 0; i <= strSearchCheck.size(); i++) {
                        if (strSearchCheck.get(i).equals(strCheck)) {
                            strSearchCheck.remove(i);
                            break;
                        }
                    }
    }

    //add the term associated with the checked checkbox in the arraylist
    private void addCheckedCheckbox (String strCheck) {
        strSearchCheck.add(strCheck);
    }

    //get the id of the clicked checkbox
    @Override
    public void onClick(View v) {
        int idCheckbox = v.getId();
        CheckBox mCheckBox = findViewById(idCheckbox);

        boolean booleanCheckbox = mCheckBox.isChecked();
        strCheck = mCheckBox.getText().toString();
        verifyCheckboxState(booleanCheckbox, strCheck);
    }
}
