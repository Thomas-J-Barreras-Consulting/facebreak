package com.thomasjbarrerasconsulting.faces.preference;

import android.content.Context;
import android.util.AttributeSet;

import androidx.preference.DialogPreference;

public class GdprPrivacyDialogPreference extends DialogPreference {
    public GdprPrivacyDialogPreference(Context context) {
        super(context);
    }
    public GdprPrivacyDialogPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GdprPrivacyDialogPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
