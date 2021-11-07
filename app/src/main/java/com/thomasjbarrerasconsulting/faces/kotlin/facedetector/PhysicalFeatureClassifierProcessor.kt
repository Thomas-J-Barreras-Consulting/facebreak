package com.thomasjbarrerasconsulting.faces.kotlin.facedetector

import android.content.Context
import com.thomasjbarrerasconsulting.faces.ml.FeaturesFaceModel3
import com.thomasjbarrerasconsulting.faces.ml.HairStyleModel
import com.thomasjbarrerasconsulting.faces.ml.JawModel2
import org.tensorflow.lite.support.image.TensorImage
import java.text.NumberFormat

class PhysicalFeatureClassifierProcessor {
    companion object{
        private fun isHeadCovering(label: String):Boolean{
            return label == "Hat" || label == "Cowboy Hat" || label == "Hair Ribbon" || label == "Baseball Cap" || label == "Headscarf"
        }

        private fun isGlasses(label: String): Boolean{
            return label == "Glasses" || label == "Sunglasses"
        }

        fun extractPhysicalFeatureClassifications(
            tensorImage: TensorImage,
            context: Context,
            classificationTracker: ClassificationTracker
        ): List<String> {
            val classifications = mutableListOf<String>()
            val percentFormat: NumberFormat = NumberFormat.getPercentInstance()

            val featuresFaceModel = FeaturesFaceModel3.newInstance(context)
            val faceOutputs = classificationTracker.merge(featuresFaceModel.process(tensorImage).probabilityAsCategoryList)
            val significantFaceOutputs = faceOutputs.apply{sortByDescending { it.score }}.filter { it.label != "Clear" }.filter{ it.score >= 0.15 }
            featuresFaceModel.close()

            val featuresHairModel = HairStyleModel.newInstance(context)
            val hairStyleOutputs = classificationTracker.merge(featuresHairModel.process(tensorImage).probabilityAsCategoryList)
            val significantHairStyleOutputs = hairStyleOutputs.apply{sortByDescending { it.score }}.filter { it.label != "Head Covered" }.filter{ it.score >= 0.2}.take(1)
            featuresHairModel.close()

            val featuresJawModel = JawModel2.newInstance(context)
            val jawModelOutputs = classificationTracker.merge(featuresJawModel.process(tensorImage).probabilityAsCategoryList)
            val significantJawModelOutputs = jawModelOutputs.apply{sortByDescending { it.score }}.filter { it.label != "Clear" }.filter{ it.score >= 0.15}
            featuresJawModel.close()

            var headCoveringDetected = false
            var glassesDetected = false
            for (output in significantFaceOutputs){
                if (isHeadCovering(output.label) && headCoveringDetected){
                    continue
                }
                if (isGlasses(output.label) && glassesDetected){
                    continue
                }
                classifications.add(output.label)
                if (isHeadCovering(output.label)){
                    headCoveringDetected = true
                }
                if (isGlasses(output.label)){
                    glassesDetected = true
                }
            }

            for (output in significantJawModelOutputs){
                val label = output.label
                classifications.add("$label (${percentFormat.format(output.score)})")
            }

            for (output in significantHairStyleOutputs){
                val label = output.label.replace("Medium", "Medium-Length Hair")
                    .replace("Afro", "Afro Hairstyle")
                    .replace("Bob", "Bob Hairstyle")
                    .replace("Curly", "Curly Hair")
                    .replace("Long", "Long Hair")
                    .replace("Mullet", "Mullet Hairstyle")
                    .replace("Pixie", "Pixie Hairstyle")
                classifications.add(label)
            }


            if (classifications.count() == 0){
                classifications.add("No physical features detected")
            }
            return classifications.toList()
        }
    }
}