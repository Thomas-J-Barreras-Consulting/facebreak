package com.thomasjbarrerasconsulting.faces.preference

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.thomasjbarrerasconsulting.faces.R

class DisplayPreferences {
    var faceBoxWidth: Float = FACE_BOX_STROKE_DEFAULT_WIDTH

    companion object {
        private const val FACE_CLASSIFICATION_TEXT_SIZE_LARGE = 60.0f
        private const val FACE_CLASSIFICATION_TEXT_SIZE_SMALL = 50.0f
        private const val FACE_BOX_STROKE_DEFAULT_WIDTH = 5.0f

        private fun readFloat(prefKey: String, default:Float, context:Context):Float{
            // TODO: Take care of this
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            val prefValueString = sharedPreferences.getString(prefKey, default.toString())
            if (prefValueString != null) {
                return prefValueString.toFloat()
            }
            return default
        }

        fun getDisplayPreferences(context: Context):DisplayPreferences{
            val preferences = DisplayPreferences()

//            val prefKey: String = context.getString(R.string.pref_key_info_hide)

            preferences.faceBoxWidth = readFloat("pref_key_live_preview_face_box_line_width", FACE_BOX_STROKE_DEFAULT_WIDTH, context)

            return preferences
        }
    }
}