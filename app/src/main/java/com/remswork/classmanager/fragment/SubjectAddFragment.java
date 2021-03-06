package com.remswork.classmanager.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.remswork.classmanager.R;
import com.remswork.classmanager.model.clazz.Subject;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.remswork.classmanager.R.id.activity_subject_hint;

/**
 * Created by Rafael on 7/25/2017.
 */

public class SubjectAddFragment extends Fragment implements View.OnClickListener {

    private View customView;
    private FloatingActionButton fabSave;
    private EditText textTitle;
    private EditText textDescription;
    private EditText textCode;
    private EditText textUnit;
    private EditText textCategory;
    private TextInputLayout textTitleLayout;
    private TextInputLayout textDescriptionLayout;
    private TextInputLayout textCodeLayout;
    private TextInputLayout textUnitLayout;
    private TextInputLayout textCategoryLayout;
    private CardView hintView;
    private TextView textHint;
    private ImageView imageView;

    private SubjectAddFragmentListener subjectAddFragmentListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        subjectAddFragmentListener = (SubjectAddFragmentListener) getActivity();
        customView = inflater.inflate(R.layout.activity_subject_fragment_add, container, false);
        fabSave = (FloatingActionButton) customView.findViewById(
                R.id.fragment_subject_add_subject_save);
        textTitle = (EditText) customView.findViewById(R.id.fragment_subject_add_text_title);
        textDescription =
                (EditText) customView.findViewById(R.id.fragment_subject_add_text_description);
        textCode = (EditText) customView.findViewById(R.id.fragment_subject_add_text_code);
        textUnit = (EditText) customView.findViewById(R.id.fragment_subject_add_text_unit);
        textCategory = (EditText) customView.findViewById(R.id.fragment_subject_add_text_category);

        textTitleLayout = (TextInputLayout) customView.findViewById(
                R.id.fragment_subject_add_text_title_layout);
        textDescriptionLayout =
                (TextInputLayout) customView.findViewById(
                        R.id.fragment_subject_add_text_description_layout);
        textCodeLayout = (TextInputLayout) customView.findViewById(
                R.id.fragment_subject_add_text_code_layout);
        textUnitLayout = (TextInputLayout) customView.findViewById(
                R.id.fragment_subject_add_text_unit_layout);
        textCategoryLayout = (TextInputLayout) customView.findViewById(
                R.id.fragment_subject_add_text_category_layout);

        hintView = (CardView) customView.findViewById(R.id.activity_subject_hint);
        textHint = (TextView) customView.findViewById(R.id.activity_subject_hint_text);
        imageView = (ImageView) customView.findViewById(R.id.activity_subject_hint_button);

        fabSave.setOnClickListener(this);
        imageView.setOnClickListener(this);
        textHint.setText(getRandomHint());
        return customView;
    }


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.fragment_subject_add_subject_save) {
            if (isInputValid()) {
                Subject subject = new Subject();
                subject.setName(textTitle.getText().toString().trim());
                subject.setDesc(textDescription.getText().toString().trim());
                subject.setCode(textCode.getText().toString().trim());
                subject.setUnit(textUnit.getText().toString().trim()
                        .equals("") ? 0 : Integer.parseInt(textUnit.getText().toString()));
                subject.setCategory(textCategory.getText().toString().trim());
                subjectAddFragmentListener.saveSubject(subject);
            }
        }else if(v.getId() == R.id.activity_subject_hint_button){
            hintView.setVisibility(View.GONE);
        }
    }

    public boolean isInputValid() {
        boolean isValid = true;
        if (textTitle.getText().toString().trim().equals("")) {
            isValid = false;
            textTitleLayout.setError("Please enter a subject title");
        } else {
            textTitleLayout.setErrorEnabled(false);
        }
        if (textDescription.getText().toString().trim().equals("")) {
            isValid = false;
            textDescriptionLayout.setError("Please enter a subject description");
        } else {
            textDescriptionLayout.setErrorEnabled(false);
        }
        if (!isNumeric(textUnit.getText().toString()) && !textUnit.getText().toString()
                .equals("")) {
            isValid = false;
            textUnitLayout.setError("Please enter only a numeric character");
        }else {
            textUnitLayout.setErrorEnabled(false);
        }
        if(textCode.getText().toString().trim().toCharArray().length > 10){
            isValid = false;
            textCodeLayout.setError("Maximum of 10 character");
        }else{
            textCodeLayout.setErrorEnabled(false);
        }
        return isValid;
    }

    public interface SubjectAddFragmentListener {
        void saveSubject(Subject subject);
    }

    public boolean isNumeric(String testString) {
        try {
            Integer.parseInt(testString);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public String getRandomHint(){
        switch((int) (Math.random() * 3)){
            case 1 :
                return "Hint : Subject Title and Description are required.";
            case 2 :
                return "Tips : Use a unique subject code.";
            default :
                return "Hint : Click the check button to add.";
        }
    }


}
