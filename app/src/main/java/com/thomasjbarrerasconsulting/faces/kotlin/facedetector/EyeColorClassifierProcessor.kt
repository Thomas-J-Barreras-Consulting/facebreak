/*
 * Copyright 2022 Thomas J. Barreras. All rights reserved.
 * https://www.linkedin.com/in/tombarreras/
*/
package com.thomasjbarrerasconsulting.faces.kotlin.facedetector

import org.tensorflow.lite.support.label.Category
import java.text.NumberFormat

class EyeColorClassifierProcessor {
    companion object{
        fun extractEyeColorClassification(outputs: List<Category?>, genderClassifier: GenderClassifier): MutableList<String>{
            val classifications: MutableList<String> = mutableListOf()
            val percentFormat: NumberFormat = NumberFormat.getPercentInstance()
            if (outputs[0]?.label == "Closed Eyes" && outputs[0]?.score!! > 0.5){
                classifications.add(ClassifierText.get("Closed Eyes", genderClassifier))
            } else {
                val allColorsProbability = getEyeColorProbabilities(outputs)
                val eyesOpenOutputs = outputs.filterNot { it?.label == "Closed Eyes" }
                if (allColorsProbability.count() == 1){
                    val eyeColor =eyesOpenOutputs.first()?.label
                    val colorProbability = percentFormat.format(1.0)
                    val pureEyeColor = ClassifierText.get("Pure $eyeColor", genderClassifier)
                    classifications.add("$pureEyeColor ($colorProbability)")
                } else {
                    val eyeColorLabel1 = ClassifierText.get(eyesOpenOutputs[0]?.label!!, genderClassifier)
                    val eyeColorLabel2 = ClassifierText.get(eyesOpenOutputs[1]?.label!!, genderClassifier)
                    val eyeColorScore1 = allColorsProbability[0]
                    val eyeColorScore2 = allColorsProbability[1]
                    if ((eyesOpenOutputs[0]?.label!! == "Brown Eyes" && eyesOpenOutputs[1]?.label!! == "Blue Eyes") || (eyesOpenOutputs[0]?.label!! == "Blue Eyes" && eyesOpenOutputs[1]?.label!! == "Brown Eyes")){
                        classifications.add("$eyeColorLabel1 (${percentFormat.format(eyeColorScore1)})")
                        val traceColor = ClassifierText.get("Trace ${eyesOpenOutputs[1]?.label!!}", genderClassifier)
                        classifications.add("$traceColor (${percentFormat.format(eyeColorScore2)})")
                    } else {
                        classifications.add("$eyeColorLabel1-$eyeColorLabel2 (${percentFormat.format(eyeColorScore1)}/${percentFormat.format(eyeColorScore2)})")
                    }
                    if (allColorsProbability.count() > 2){
                        val eyeColorLabel3 = ClassifierText.get("Trace ${eyesOpenOutputs[2]?.label!!}", genderClassifier)
                        val eyeColorScore3 = allColorsProbability[2]
                        classifications.add("$eyeColorLabel3 (${percentFormat.format(eyeColorScore3)})")

                        if (allColorsProbability.count() > 3){
                            val eyeColorLabel4 = ClassifierText.get("Trace ${eyesOpenOutputs[3]?.label!!}", genderClassifier)
                            val eyeColorScore4 = allColorsProbability[3]
                            classifications.add("$eyeColorLabel4 (${percentFormat.format(eyeColorScore4)})")
                        }
                    }
                }
            }
            return classifications
        }

        private fun getEyeColorProbabilities(outputs: List<Category?>): List<Float> {
            val probabilities = mutableListOf<Float>()
            var colorCount = 0
            var totalProbabilities = 0.0
            val eyesOpenProbability = outputs.filterNot { o -> o?.label == "Closed Eyes" }.map { it?.score!! }.sum()

            for (category in outputs){
                if (category?.label != "Closed Eyes"){
                    if (category?.score != null){
                        probabilities.add(category.score)
                        totalProbabilities += category.score
                    }

                    colorCount += 1

                    if (totalProbabilities / eyesOpenProbability >= 0.85 || colorCount >= 4){
                        break
                    }
                }
            }
            return probabilities.map { (it / totalProbabilities).toFloat() }
        }
    }
}