package com.example.sharedpreferances;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

public class SettingFragments extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_visualiser);
        SharedPreferences sharedPreferences=getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen=getPreferenceScreen();
     //   Preference preference=findPreference(getString("choose name"));
       // preference.setOnPreferenceChangeListener(this);

        int count=preferenceScreen.getPreferenceCount();
        for(int i=0;i<count;i++)
        {
            Preference p=preferenceScreen.getPreference(i);
            if(!(p instanceof CheckBoxPreference))
            {
                String value=sharedPreferences.getString(p.getKey(),"");
                setPreferenceSummary(p,value);
            }
        }

    }
    private void setPreferenceSummary(Preference preference,String value)
    {
        if(preference instanceof ListPreference)
        {
            ListPreference listPreference= (ListPreference) preference;
            int prefIndex=listPreference.findIndexOfValue(value);
            if(prefIndex>=0)
            {
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
        else  if(preference instanceof EditTextPreference)
        {
            preference.setSummary(value);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Preference preference=findPreference(s);
        if(preference!=null)
        {
            if(!(preference instanceof CheckBoxPreference))
            {
                String value=sharedPreferences.getString(preference.getKey(),"");
                setPreferenceSummary(preference,value);
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Toast toast=Toast.makeText(getContext(),"Please enter valid name",Toast.LENGTH_LONG);
        String name=(String) newValue;
        if(name.length()<3)
        {
            toast.show();
            return false;
        }
        return true;
    }
}
