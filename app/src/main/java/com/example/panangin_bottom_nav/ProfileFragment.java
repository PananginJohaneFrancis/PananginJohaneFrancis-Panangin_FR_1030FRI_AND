package com.example.panangin_bottom_nav;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ProfileFragment extends Fragment {

    private EditText nameEditText, emailEditText, phoneEditText;
    private RadioGroup genderRadioGroup;
    private RadioButton maleRadioButton, femaleRadioButton, otherRadioButton;
    private CheckBox codingCheckBox, sportsCheckBox, musicCheckBox, readingCheckBox;
    private Button saveButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        // Initialize views
        nameEditText = view.findViewById(R.id.nameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        genderRadioGroup = view.findViewById(R.id.genderRadioGroup);
        maleRadioButton = view.findViewById(R.id.maleRadioButton);
        femaleRadioButton = view.findViewById(R.id.femaleRadioButton);
        codingCheckBox = view.findViewById(R.id.interestCodingCheckBox);
        sportsCheckBox = view.findViewById(R.id.interestSportsCheckBox);
        musicCheckBox = view.findViewById(R.id.interestMusicCheckBox);
        readingCheckBox = view.findViewById(R.id.interestReadingCheckBox);
        saveButton = view.findViewById(R.id.saveButton);

        // Handle Save button click
        saveButton.setOnClickListener(v -> saveProfileData());

        return view;
    }

    private void saveProfileData() {
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String phone = phoneEditText.getText().toString();

        String gender = "";
        int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
        if (selectedGenderId == maleRadioButton.getId()) {
            gender = "Male";
        } else if (selectedGenderId == femaleRadioButton.getId()) {
            gender = "Female";
        }

        StringBuilder interests = new StringBuilder();
        if (codingCheckBox.isChecked()) interests.append("Coding ");
        if (sportsCheckBox.isChecked()) interests.append("Sports ");
        if (musicCheckBox.isChecked()) interests.append("Music ");
        if (readingCheckBox.isChecked()) interests.append("Reading ");

        Toast.makeText(getContext(), "Profile saved: " + name + ", " + email + ", " + phone + ", " + gender + ", " + interests, Toast.LENGTH_LONG).show();
    }
}
