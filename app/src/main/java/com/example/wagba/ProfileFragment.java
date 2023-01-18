package com.example.wagba;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.List;

public class ProfileFragment extends Fragment {
    EditText email, password, phone, username;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Inflate the layout for this fragment
        email = getView().findViewById(R.id.emailEdit);
        password = getView().findViewById(R.id.passwordEdit);
        phone = getView().findViewById(R.id.phoneEdit);
        username = getView().findViewById(R.id.usernameEdit);
        ProfileDatabase pdb = Room.databaseBuilder(getContext(), ProfileDatabase.class,"profile-database").allowMainThreadQueries().build();
        List<Profile> profileList = pdb.daoProfile().getAllProfiles();
         email.setText(profileList.get(0).email);
        password.setText(profileList.get(0).password);
        phone.setText(profileList.get(0).phoneNum);
        username.setText(profileList.get(0).userName);
    }
}