package com.thomasjbarrerasconsulting.faces.kotlin

import android.app.Activity
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import com.google.android.gms.ads.AdView
import com.thomasjbarrerasconsulting.faces.R
import com.thomasjbarrerasconsulting.faces.kotlin.billing.Premium
import com.thomasjbarrerasconsulting.faces.kotlin.facedetector.FaceClassifierProcessor

class PremiumStatus {
    companion object {
        private const val TAG = "PremiumStatus"

        fun populateClassifierSelector(activity: Activity, featureSelector: Spinner){
            try {
                activity.runOnUiThread{
                    // Creating adapter for spinner
                    val dataAdapter = ArrayAdapter(
                        activity,
                        R.layout.spinner_style,
                        if (Premium.premiumIsActive()) FaceClassifierProcessor.allClassificationDescriptions(activity) else FaceClassifierProcessor.allClassificationDescriptionsFree(activity)
                    )
                    // Drop down layout style - list view with radio button
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // attaching data adapter to spinner
                    featureSelector.adapter = dataAdapter

                    featureSelector.setSelection(FaceClassifierProcessor.Classifier.values().indexOf(Settings.selectedClassifier))
                }

            } catch (e: Exception){
                ExceptionHandler.alert(activity, e.message.toString(), TAG, e)
            }
        }

        fun updatePremiumStatusImage(activity: Activity, premiumStatusImageView: ImageView) {
            activity.runOnUiThread {
                when {
                    Premium.premiumIsActive() -> premiumStatusImageView.setBackgroundResource(R.drawable.ic_premium)
                    Premium.premiumIsPending() -> premiumStatusImageView.setBackgroundResource(R.drawable.ic_premium_pending)
                    else -> premiumStatusImageView.setBackgroundResource(R.drawable.ic_free)
                }
            }
        }

        fun updateAds(activity: Activity, adView: AdView) {
            activity.runOnUiThread {
                if (Premium.premiumIsActive()) Ads.removeAds(adView)
            }
        }
    }
}