package com.thomasjbarrerasconsulting.faces.kotlin.facedetector

import android.content.Context
import com.thomasjbarrerasconsulting.faces.ml.GenderModel2
import kotlinx.coroutines.sync.Mutex
import org.tensorflow.lite.support.image.TensorImage

class GenderClassifier (val context: Context, private val tensorImage: TensorImage) {

    private var isMaleOrGenderNeutral: Boolean? = null
    fun isMaleOrGenderNeutral(): Boolean {

        if (isMaleOrGenderNeutral == null){
            synchronized(this){
                isMaleOrGenderNeutral = true
            }
        }

//        val genderModel = GenderModel2.newInstance(context)
//        val isMale = genderModel.process(tensorImage).probabilityAsCategoryList.apply { sortByDescending { it.score } }.first().label == "Male"
//        genderModel.close()
        return isMaleOrGenderNeutral!!
    }
}