package com.example.sequence3

import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.EditTextPreference
import android.preference.Preference
import android.preference.Preference.OnPreferenceChangeListener
import android.preference.PreferenceActivity
import android.widget.Toast
import com.example.sequence3.data.source.remote.api.Provider

class GestionPreferences : PreferenceActivity(), OnPreferenceChangeListener {
    var cbp: CheckBoxPreference? = null
    var edtpl: EditTextPreference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.preferences)
        cbp = findPreference("remember") as CheckBoxPreference
        edtpl = findPreference("login") as EditTextPreference
        cbp!!.setOnPreferenceChangeListener(this)
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        val t = Toast.makeText(this,
            "click cb :" + newValue.toString()
                    + " pref manipulée : " + preference!!.getKey(), Toast.LENGTH_SHORT)
        t.show()
        if (newValue == false) {
            edtpl?.setText("")
        }

        if(preference!!.getKey() == "url"){
            Provider.changeUrl(newValue.toString())
        }
        return true
    }
    // décoche la case depuis cette activité ?
}