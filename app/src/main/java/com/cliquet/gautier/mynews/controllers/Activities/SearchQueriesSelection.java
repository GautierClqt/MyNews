package com.cliquet.gautier.mynews.controllers.Activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
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
import android.widget.Switch;
import android.widget.TextView;

import com.cliquet.gautier.mynews.Models.ArticlesElements;
import com.cliquet.gautier.mynews.Models.PojoCommon.PojoMaster;
import com.cliquet.gautier.mynews.R;
import com.cliquet.gautier.mynews.Utils.AlarmReceiver;
import com.cliquet.gautier.mynews.Utils.NYtimesCalls;
import com.cliquet.gautier.mynews.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class SearchQueriesSelection extends AppCompatActivity implements View.OnClickListener, NYtimesCalls.Callbacks {

    //view: terms edditexts
    EditText termsEdittext;

    //view: dates edittexts
    EditText beginDateEdittext;
    EditText endDateEdittext;

    DatePickerDialog datePicker;

    //view: checkboxes
    CheckBox artsCheckbox;
    CheckBox businessCheckbox;
    CheckBox entrepreneursCheckbox;
    CheckBox politicsCheckbox;
    CheckBox sportsCheckbox;
    CheckBox travelCheckbox;

    //view: search button
    Button searchButton;

    //views: notification textview and switch
    TextView switchTextView;
    Switch switchView;

    //variables for query parameters
    String queryParamTerms;
    String beginDate = "";
    String endDate = "";
    ArrayList<String> queryParamCheckboxes = new ArrayList<>();

    String checkboxText;

    Utils utils = new Utils();
    private Gson gson = new Gson();

    private ArticlesElements articlesElements = new ArticlesElements();

    Bundle bundle = new Bundle();
    int calledActivity;
    HashMap<String, String> queriesHM;
    HashMap<String, String> viewsStateHM = new HashMap<>();
    String jsonQueriesHM;
    boolean boolSwitch;

    SharedPreferences preferences;

    int minYear;
    int minMonth;
    int minDay;
    int maxYear;
    int maxMonth;
    int maxDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_queries);

        preferences = getSharedPreferences("MyNewsPreferences", MODE_PRIVATE);

        initViews();

        bundle = getIntent().getExtras();
        if (bundle != null) {
            calledActivity = bundle.getInt("actitivy_called");

            //set up views as per selected activity
            switch (calledActivity) {
                case 0:
                    emptyViews();
                    setupSearchView();
                    break;
                case 1:
                    setupNotificationsViews();
                    //getNotificationPreferences();

                    boolSwitch = preferences.getBoolean("boolSwitch", false);
                    if (boolSwitch) {
                        switchView.setChecked(true);
                        trueBoolSwitch();
                    } else {
                        switchView.setChecked(false);
                        falseBoolSwitch();
                    }
                    break;
                default:
                    break;
            }
        }

        findViewById();
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

        //switch views
        switchView.setOnClickListener(this);
    }


    //choose dates in Edittexts via DatePickers
    private void setDateInEdittext(final View v) {

        //get the correct Edittext
        int idEdittext = v.getId();
        final EditText dateEditText = findViewById(idEdittext);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        //select begin and end dates
        datePicker = new DatePickerDialog(SearchQueriesSelection.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String strDateConcatenation;
                strDateConcatenation = utils.dateStringLayoutFormat(year, month, dayOfMonth);
                if(v == findViewById(R.id.activity_search_articles_begindate_edittext)) {
                    beginDate = utils.dateStringFormat(year, month, dayOfMonth);
                    minYear = year;
                    minMonth = month;
                    minDay = dayOfMonth;
                }
                else {
                    endDate = utils.dateStringFormat(year, month, dayOfMonth);
                    maxYear = year;
                    maxMonth = month;
                    maxDay = dayOfMonth;
                }
                dateEditText.setText(strDateConcatenation);
            }
        }, year, month, dayOfMonth);

        //Conditions that prevent users to select a begin date higher than end date and vice versa
        if(!beginDate.equals("") && !(v == findViewById(R.id.activity_search_articles_begindate_edittext))) {
            calendar = utils.setMinMaxDate(minYear, minMonth, minDay);
            datePicker.getDatePicker().setMinDate(calendar.getTimeInMillis());
            calendar = Calendar.getInstance();
            calendar = utils.setMinMaxDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        }
        else if (beginDate.equals("") && !(v == findViewById(R.id.activity_search_articles_begindate_edittext))) {
            Calendar.getInstance();
            calendar = utils.setMinMaxDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        }

        if(!endDate.equals("") && !(v == findViewById(R.id.activity_search_articles_enddate_edittext))) {

            calendar = utils.setMinMaxDate(maxYear, maxMonth, maxDay);
            datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        }
        datePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        datePicker.show();
    }

    //verify if a checkbox is check or uncheck
    private void verifyCheckboxState(boolean booleanCheckbox, String strCheck) {
        if (booleanCheckbox) {
            addCheckedCheckbox(strCheck);
            viewsStateHM.put(strCheck, "1");
        } else {
            removeUncheckedCheckbox(strCheck);
            viewsStateHM.remove(strCheck);
        }
    }

    //add the term associated with the checked checkbox in the arraylist
    private void addCheckedCheckbox (String strCheck) {
        queryParamCheckboxes.add(strCheck);
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

    private void validateSearchPreferences() {
        articlesElements.setCurrentPage(0);


        queriesHM = utils.creatHashMapQueries(String.valueOf(termsEdittext.getText()), beginDate, endDate, queryParamCheckboxes, articlesElements.getCurrentPage());

        jsonQueriesHM = gson.toJson(queriesHM);

        switch(calledActivity) {
            case 0:
                Intent searchArticleIntent = new Intent(this, ArticlesSearch.class)
                        .putExtra("hashmap", jsonQueriesHM);
                startActivity(searchArticleIntent);
                break;
            case 1:
                preferences.edit().putString("notifications", jsonQueriesHM).apply();
                break;
            default:
                break;
        }
    }

    //get the id of the clicked checkbox
    @Override
    public void onClick(View v) {
        int idView = v.getId();

        //differentiate type of view to work accordingly with what is clicked on
        if (v instanceof CheckBox) {

            CheckBox mCheckBox = findViewById(idView);
            boolean booleanCheckbox = mCheckBox.isChecked();
            checkboxText = mCheckBox.getText().toString();
            verifyCheckboxState(booleanCheckbox, checkboxText);
        }
        else if(v instanceof EditText && !(idView == termsEdittext.getId())) {
            setDateInEdittext(v);
        }
        else if (v instanceof Button) {
            if (idView == searchButton.getId()) {
                validateSearchPreferences();
            }
            else if (idView == switchView.getId()) {
                boolSwitch = switchView.isChecked();
                preferences.edit().putBoolean("boolSwitch", boolSwitch).apply();

                AlarmManager alarmManager;
                alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(this, AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT); //TEST

                if(boolSwitch) {
                    trueBoolSwitch();

                    Calendar calendar = Calendar.getInstance();
//
                    endDate = utils.createNotificationDate(calendar);
                    calendar.add(Calendar.DATE, -1);
                    beginDate = utils.createNotificationDate(calendar);

                    calendar.setTimeInMillis(System.currentTimeMillis());

                    //calendar.set(Calendar.HOUR_OF_DAY, 18);
                    //calendar.set(Calendar.HOUR_OF_DAY, 19);

                    calendar.add(Calendar.SECOND, 20);

                    //alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 1000 * 60, pendingIntent); //TEST

                    validateSearchPreferences();

                    //alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

                    //alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
                }
                else {
                    falseBoolSwitch();
                    alarmManager.cancel(pendingIntent);
                }
            }
        }
    }

//    private void getNotificationPreferences() {
//        jsonQueriesHM = preferences.getString("notifications", "");
//        queriesHM = gson.fromJson(jsonQueriesHM, new TypeToken<HashMap<String, String>>(){}.getType());
//    }

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

        //bindview: switch
        switchView = findViewById(R.id.activity_search_articles_switch);
        switchTextView = findViewById(R.id.activity_search_articles_switch_textview);
    }

    //empty all views to wipe previews search
    private void emptyViews() {
        termsEdittext.setText("");
        beginDateEdittext.setText("");
        endDateEdittext.setText("");
        artsCheckbox.setChecked(false);
        businessCheckbox.setChecked(false);
        entrepreneursCheckbox.setChecked(false);
        politicsCheckbox.setChecked(false);
        sportsCheckbox.setChecked(false);
        travelCheckbox.setChecked(false);
    }

    private void setupSearchView() {
        switchTextView.setVisibility(View.GONE);
        switchView.setVisibility(View.GONE);
        termsEdittext.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.VISIBLE);
        beginDateEdittext.setVisibility(View.VISIBLE);
        endDateEdittext.setVisibility(View.VISIBLE);
    }

    private void setupNotificationsViews() {
        switchTextView.setVisibility(View.VISIBLE);
        switchView.setVisibility(View.VISIBLE);
        termsEdittext.setVisibility(View.VISIBLE);
        searchButton.setVisibility(View.GONE);
        beginDateEdittext.setVisibility(View.GONE);
        endDateEdittext.setVisibility(View.GONE);
    }

    private void trueBoolSwitch() {
        //if switch is true then disable every other views
        termsEdittext.setEnabled(false);
        artsCheckbox.setEnabled(false);
        businessCheckbox.setEnabled(false);
        entrepreneursCheckbox.setEnabled(false);
        politicsCheckbox.setEnabled(false);
        sportsCheckbox.setEnabled(false);
        travelCheckbox.setEnabled(false);
    }

    private void falseBoolSwitch() {
        //if switch is true then disable every other views
        termsEdittext.setEnabled(true);
        artsCheckbox.setEnabled(true);
        businessCheckbox.setEnabled(true);
        entrepreneursCheckbox.setEnabled(true);
        politicsCheckbox.setEnabled(true);
        sportsCheckbox.setEnabled(true);
        travelCheckbox.setEnabled(true);
    }

    @Override
    public void onResponse(PojoMaster body) {

    }

    @Override
    public void onFailure() {

    }
}
