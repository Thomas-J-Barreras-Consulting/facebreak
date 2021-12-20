/*
 * Copyright 2021 Thomas J. Barreras. All rights reserved.
 * https://www.linkedin.com/in/tombarreras/
 */

package com.thomasjbarrerasconsulting.faces.preference

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.thomasjbarrerasconsulting.faces.R

class PreferencesFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
    }

    override fun onDisplayPreferenceDialog(preference: Preference?) {
        if (preference is OpenSourceDialogPreference){
            val dialogFragment = OpenSourceDialog()
            dialogFragment.show(parentFragmentManager, "OpenSourcePreference")
        } else {
            super.onDisplayPreferenceDialog(preference)
        }
    }
}