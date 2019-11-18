package com.cliquet.gautier.mynews.controllers.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

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
import com.cliquet.gautier.mynews.Utils.AlarmStartStop;
import com.cliquet.gautier.mynews.Utils.NYtimesCalls;
import com.cliquet.gautier.mynews.Utils.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class SearchQueriesSelectionActivity extends AppCompatActivity implements View.OnClickListener, NYtimesCalls.Callbacks {

    //view: terms edditexts
    EditText termsEdittext;

    //view: dates edittexts
    EditText beginDateEdittext;
    EditText endDateEdittext;

    DatePickerDialog mDatePicker;

    //view: checkboxes
    LinearLayoutCompat checkboxesLayout;

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
    String mBeginDate = "";
    String mEndDate = "";

    ArrayList<Integer> mListIdCheckboxes = new ArrayList<>();
    ArrayList<String> mListTextCheckboxes = new ArrayList<>();
    int mCheckboxId;

    Utils utils = new Utils();
    private Gson gson = new Gson();

    private ArticlesElements articlesElements = new ArticlesElements();

    Bundle mBundle = new Bundle();
    int mCalledActivity;
    HashMap<String, String> mQueriesHM;
    String mJsonQueriesHM;
    String mJsonCheckboxState;
    String mJsonBeginDate;
    String mJsonEndDate;
    boolean mBoolSwitch;

    SharedPreferences preferences;

    int mMinYear;
    int mMinMonth;
    int mMinDay;
    int mMaxYear;
    int mMaxMonth;
    int mMaxDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection_queries);

        preferences = getSharedPreferences("MyNewsPreferences", MODE_PRIVATE);

        initViews();

        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            mCalledActivity = mBundle.getInt("actitivy_called");

            //set up views as per selected activity
            switch (mCalledActivity) {
                case 0:
                    emptyViews();
                    setupSearchView();
                    break;
                case 1:
                    setupNotificationsViews();
                    getLastNotificationChoices();

                    mBoolSwitch = preferences.getBoolean("mBoolSwitch", false);
                    if (mBoolSwitch) {
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

    //choose dates in Edittexts via DatePickers
    private void setDateInEdittext(final View v) {

        //get the correct Edittext
        int idEdittext = v.getId();
        final EditText dateEditText = findViewById(idEdittext);
        Calendar minCalendar = null;
        Calendar maxCalendar = Calendar.getInstance();

        //select begin and end dates
        mDatePicker = new DatePickerDialog(SearchQueriesSelectionActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String strDateConcatenation;
                strDateConcatenation = utils.dateStringLayoutFormat(year, month, dayOfMonth);
                if(v == findViewById(R.id.activity_search_articles_begindate_edittext)) {
                    mBeginDate = utils.dateStringFormat(year, month, dayOfMonth);
                    saveMinDate(year, month, dayOfMonth);
                }
                else {
                    mEndDate = utils.dateStringFormat(year, month, dayOfMonth);
                    saveMaxDate(year, month, dayOfMonth);
                }
                dateEditText.setText(strDateConcatenation);
            }
        }, maxCalendar.get(Calendar.YEAR), maxCalendar.get(Calendar.MONTH), maxCalendar.get(Calendar.DAY_OF_MONTH));

        //prevent the user to set the begin date lower than the end date end vice-versa
        if(v == findViewById(R.id.activity_search_articles_enddate_edittext)) {
            if(!mBeginDate.equals("")) {
                minCalendar = utils.setMinMaxDate(mMinYear, mMinMonth, mMinDay);
            }
            maxCalendar = utils.setMinMaxDate(maxCalendar.get(Calendar.YEAR), maxCalendar.get(Calendar.MONTH), maxCalendar.get(Calendar.DAY_OF_MONTH));
        }
        else if(v == findViewById(R.id.activity_search_articles_begindate_edittext) && !mEndDate.equals("")) {
            maxCalendar = utils.setMinMaxDate(mMaxYear, mMaxMonth, mMaxDay);
        }

        if(minCalendar != null) {
            mDatePicker.getDatePicker().setMinDate(minCalendar.getTimeInMillis());
        }
        mDatePicker.getDatePicker().setMaxDate(maxCalendar.getTimeInMillis());
        mDatePicker.show();
    }

    //verify if a checkbox is check or uncheck
    private void verifyCheckboxState(boolean booleanCheckbox, int intCheck) {
        if (booleanCheckbox) {
            addCheckedCheckbox(intCheck);
        } else {
            removeUncheckedCheckbox(intCheck);
        }
    }

    //add the term associated with the checked checkbox in the arraylist
    private void addCheckedCheckbox (int intCheck) {
        mListIdCheckboxes.add(intCheck);
    }

    //remove the term of the unchecked checkbox in the arraylist
    private void removeUncheckedCheckbox(int intCheck) {
        for(int i = 0; i <= mListIdCheckboxes.size(); i++) {
            if (mListIdCheckboxes.get(i).equals(intCheck)) {
                mListIdCheckboxes.remove(i);
                break;
            }
        }
    }

    private void validateSearchPreferences() {
        articlesElements.setCurrentPage(0);

        for(int i = 0; i < mListIdCheckboxes.size(); i++) {
                CheckBox idCheckBox = findViewById(mListIdCheckboxes.get(i));
                mListTextCheckboxes.add(idCheckBox.getText().toString());
            }

        mQueriesHM = utils.creatHashMapQueries(String.valueOf(termsEdittext.getText()), mBeginDate, mEndDate, mListTextCheckboxes, articlesElements.getCurrentPage());

        mJsonQueriesHM = gson.toJson(mQueriesHM);
        mJsonCheckboxState = gson.toJson(mListIdCheckboxes);
        mJsonBeginDate = gson.toJson(mBeginDate);
        mJsonEndDate = gson.toJson(mEndDate);

        switch(mCalledActivity) {
            case 0:
                Intent searchArticleIntent = new Intent(this, ArticlesSearch.class)
                        .putExtra("hashmap", mJsonQueriesHM);
                startActivity(searchArticleIntent);
                break;
            case 1:
                preferences.edit().putString("search_terms", termsEdittext.getText().toString()).apply();
                preferences.edit().putString("checkboxes_state", mJsonCheckboxState).apply();
                break;
            default:
                break;
        }
    }

    private void getLastNotificationChoices() {
        termsEdittext.setText(preferences.getString("search_terms", ""));

        mJsonCheckboxState = preferences.getString("checkboxes_state", "");

        if(!(mJsonCheckboxState == null) && !mJsonCheckboxState.equals("")) {
            mListIdCheckboxes = gson.fromJson(mJsonCheckboxState, new TypeToken<ArrayList<Integer>>(){}.getType());
        }

        for(int i = 0; i < mListIdCheckboxes.size(); i++) {
            CheckBox checkBox = findViewById(mListIdCheckboxes.get(i));
            checkBox.setChecked(true);
        }
    }

    private void saveMinDate(int year, int month, int dayOfMonth) {
        mMinDay = dayOfMonth;
        mMinMonth = month;
        mMinYear = year;
    }

    private void saveMaxDate(int year, int month, int dayOfMonth) {
        mMaxDay = dayOfMonth;
        mMaxMonth = month;
        mMaxYear = year;
    }

    //get the id of the clicked checkbox
    @Override
    public void onClick(View v) {
        int idView = v.getId();

        //differentiate type of view to react accordingly with what is clicked on
        if (v instanceof CheckBox) {
            CheckBox mCheckBox = findViewById(idView);
            boolean booleanCheckbox = mCheckBox.isChecked();
            mCheckboxId = mCheckBox.getId();
            verifyCheckboxState(booleanCheckbox, mCheckboxId);
        }
        else if(v instanceof EditText && !(idView == termsEdittext.getId())) {
            setDateInEdittext(v);
        }
        else if (v instanceof Button) {
            if(mBeginDate.equals("") && mEndDate.equals("")) {
                mBeginDate = utils.notificationBeginDate();
                mEndDate = utils.notificationEndDate();
            }
            if (idView == searchButton.getId()) {
                validateSearchPreferences();
            }
            else if (idView == switchView.getId()) {
                AlarmStartStop alarm = new AlarmStartStop();
                mBoolSwitch = switchView.isChecked();
                preferences.edit().putBoolean("mBoolSwitch", mBoolSwitch).apply();

                if(mBoolSwitch) {
                    trueBoolSwitch();
                    alarm.startAlarm(this);
                    validateSearchPreferences();
                }
                else {
                    falseBoolSwitch();
                    alarm.stopAlarm(this);
                }
            }
        }
    }

    private void initViews() {
        //bindview: terms edittext
        termsEdittext = findViewById(R.id.activity_search_articles_terms_edittext);

        //bindview: dates edittexts
        beginDateEdittext = findViewById(R.id.activity_search_articles_begindate_edittext);
        endDateEdittext = findViewById(R.id.activity_search_articles_enddate_edittext);

        //bindview: checkboxes
        checkboxesLayout = findViewById(R.id.activity_search_articles_checkboxeslayout_LinearLayout);

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
        searchButton.setVisibility(View.VISIBLE);
        beginDateEdittext.setVisibility(View.VISIBLE);
        endDateEdittext.setVisibility(View.VISIBLE);
    }

    private void setupNotificationsViews() {
        switchTextView.setVisibility(View.VISIBLE);
        switchView.setVisibility(View.VISIBLE);
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
