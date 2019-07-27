package com.cliquet.gautier.mynews.controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.cliquet.gautier.mynews.Models.ArticlesElements;
import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.NYtimesCalls;
import com.cliquet.gautier.mynews.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class SearchQueriesSelection extends AppCompatActivity implements View.OnClickListener, NYtimesCalls.Callbacks2 {

    //view: terms edditexts
    EditText termsEdittext;

    //view: dates edittexts
    EditText beginDateEdittext;
    EditText endDateEdittext;

    DatePickerDialog dateDatepickerdialog;

    //view: checkboxes
    CheckBox artsCheckbox;
    CheckBox businessCheckbox;
    CheckBox entrepreneursCheckbox;
    CheckBox politicsCheckbox;
    CheckBox sportsCheckbox;
    CheckBox travelCheckbox;

    //view: search button
    Button searchButton;

    //variables for query parameters
    String queryParamTerms;
    String beginDate = "";
    String endDate = "";
    ArrayList<String> queryParamCheckboxes = new ArrayList<>();
    ArrayList<Integer> idCheckboxes = new ArrayList<>();
    String jsonIdCheckbox;

    String checkboxText;
    long minDate;

    Utils utils = new Utils();
    private Gson gson = new Gson();
    private SharedPreferences preferences;
    private String jsonCheckboxes;

    private PojoMaster mPojoMaster = new PojoMaster();

    private ArticlesElements articlesElements = new ArticlesElements();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_queries);

        this.initViews();

        idCheckboxes.add(3);
        preferences = getPreferences(MODE_PRIVATE);

        findViewById();
        getSearchPreferences();
    }

    private void findViewById() {
        //Search edittext
        termsEdittext.setOnClickListener(this);

        //Dates
        beginDateEdittext.setOnClickListener(this);
        endDateEdittext.setOnClickListener(this);
        beginDateEdittext.setInputType(InputType.TYPE_NULL);
        endDateEdittext.setInputType(InputType.TYPE_NULL);

        //checkboxes
        artsCheckbox.setOnClickListener(this);
        businessCheckbox.setOnClickListener(this);
        entrepreneursCheckbox.setOnClickListener(this);
        politicsCheckbox.setOnClickListener(this);
        sportsCheckbox.setOnClickListener(this);
        travelCheckbox.setOnClickListener(this);

        //Button
        searchButton.setOnClickListener(this);
    }

    private void getSearchPreferences() {
        termsEdittext.setText(getPreferences(MODE_PRIVATE).getString("queryterms", ""));
        beginDateEdittext.setText(getPreferences(MODE_PRIVATE).getString("begindate", ""));
        endDateEdittext.setText(getPreferences(MODE_PRIVATE).getString("enddate", ""));
        preferences.edit().putString("checkboxes", jsonCheckboxes = gson.toJson(queryParamCheckboxes)).apply();
//        for(int i = 0; i <= queryParamCheckboxes.size(); i++) {
//            if (queryParamCheckboxes.get(i).equals(strCheck)) {
//                queryParamCheckboxes.remove(i);
//                break;
//            }
//        }
//        idCheckboxes = getPreferences(MODE_PRIVATE).getInt("idcheckbox", 0);
        idCheckboxes = gson.fromJson(jsonIdCheckbox, new TypeToken<int[]>(){}.getType());
}

    //choose dates in Edittexts via DatePickers
    private void setDateInEdittext(final View v) {

        //get the correct Edittext
        int idEdittext = v.getId();
        final EditText dateEditText = findViewById(idEdittext);
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


        dateDatepickerdialog = new DatePickerDialog(SearchQueriesSelection.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String strDateConcatenation;
                strDateConcatenation = utils.dateStringLayoutFormat(year, month, dayOfMonth);
                if(v == findViewById(R.id.activity_search_articles_begindate_edittext)) {
                    beginDate = utils.dateStringParamFormat(year, month, dayOfMonth);
                    preferences.edit().putString("begindate", strDateConcatenation).apply();
                    //minDate = utils.setMinDate(calendar);
                }
                else {
                    endDate = utils.dateStringParamFormat(year, month, dayOfMonth);
                    preferences.edit().putString("enddate", strDateConcatenation).apply();
                }
                dateEditText.setText(strDateConcatenation);
            }
        }, year, month, dayOfMonth);
        dateDatepickerdialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        dateDatepickerdialog.show();
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
        for(int i = 0; i <= queryParamCheckboxes.size(); i++) {
            if (queryParamCheckboxes.get(i).equals(strCheck)) {
                queryParamCheckboxes.remove(i);
                break;
            }
        }
    }

    //add the term associated with the checked checkbox in the arraylist
    private void addCheckedCheckbox (String strCheck) {
        queryParamCheckboxes.add(strCheck);
    }

    private void validateSearchPreferences() {
        articlesElements.setCurrentPage(0);

        HashMap<String, String> queriesHM = new HashMap<>();
        queriesHM.put("begin_date", beginDate);
        queriesHM.put("end_date", endDate);
        queriesHM.put("page", String.valueOf(articlesElements.getCurrentPage()));

        String jsonQueriesHM = gson.toJson(queriesHM);
        //NYtimesCalls.fetchSearchArticles(this, queriesHM);

        preferences.edit().putString("queryterms", queryParamTerms = termsEdittext.getText().toString()).apply();
        preferences.edit().putString("checkboxes", jsonCheckboxes = gson.toJson(queryParamCheckboxes)).apply();

        Intent searchArticleIntent = new Intent(this, ArticlesSearch.class)
                .putExtra("hashmap", jsonQueriesHM);
        startActivity(searchArticleIntent);
    }

    //get the id of the clicked checkbox
    @Override
    public void onClick(View v) {
        int idView = v.getId();

        //differentiate type of view to work accordingly with what is clicked on
        if (v instanceof CheckBox) {

            preferences.edit().putString("idcheckboxes", jsonIdCheckbox = gson.toJson(idCheckboxes)).apply();
            CheckBox mCheckBox = findViewById(idView);
            boolean booleanCheckbox = mCheckBox.isChecked();
            checkboxText = mCheckBox.getText().toString();
            verifyCheckboxState(booleanCheckbox, checkboxText);
        }
        else if(v instanceof EditText && !(v.getId() == termsEdittext.getId())) {
            setDateInEdittext(v);
        }
        else if (v instanceof Button) {
            validateSearchPreferences();
        }
    }

    private void initViews() {
        //bindview: terms edittext
        termsEdittext = findViewById(R.id.activity_search_articles_terms_edittext);

        //bindview: dates edittexts
        beginDateEdittext = findViewById(R.id.activity_search_articles_begindate_edittext);
        endDateEdittext = findViewById(R.id.activity_search_articles_enddate_edittext);

        //bindview: checkboxes
        artsCheckbox = findViewById(R.id.activity_search_articles_arts_checkbox);
        businessCheckbox = findViewById(R.id.activity_search_articles_business_checkbox);
        entrepreneursCheckbox = findViewById(R.id.activity_search_articles_entrepreneurs_checkbox);
        politicsCheckbox = findViewById(R.id.activity_search_articles_politics_checkbox);
        sportsCheckbox = findViewById(R.id.activity_search_articles_sports_checkbox);
        travelCheckbox = findViewById(R.id.activity_search_articles_travel_checkbox);

        //bindview: search button
        searchButton = findViewById(R.id.activity_search_articles_search_button);
    }

    @Override
    public void onResponse(PojoMaster body) {

    }

    @Override
    public void onFailure() {

    }
}
