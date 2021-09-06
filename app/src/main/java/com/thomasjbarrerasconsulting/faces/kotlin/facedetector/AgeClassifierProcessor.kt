package com.thomasjbarrerasconsulting.faces.kotlin.facedetector

import org.tensorflow.lite.support.label.Category
import kotlin.math.round

class AgeClassifierProcessor {
    companion object{
        fun extractAgeClassification(outputs: List<Category?>): MutableList<String> {
            val classifications: MutableList<String> = mutableListOf()

            var minAge = 100
            var maxAge = 0
            var totalProbability = 0.0f
            var weightedAverage = 0.0f

            for (output in outputs){
                var age = 0
                val probability = output?.score ?: 0.0f
                totalProbability += probability
                age = if (output?.label == "Infant"){
                    0
                } else {
                    output?.label?.toInt() ?: 0
                }

                weightedAverage += probability * age
                if (minAge > age){
                    minAge = age
                }
                if (maxAge < age){
                    maxAge = age
                }
                if (totalProbability > 0.66){
                    break
                }
            }

            val averageAge = round(minAge + 0.5f * (maxAge - minAge))
            val weightedAverageAge = round(weightedAverage / totalProbability)
            classifications.add("$weightedAverageAge ($minAge - $maxAge) $averageAge")

            return classifications
        }
    }


//    private fun getAgeClassifications(
//        ageModel: AgesModel10000,
//        tensorImage: TensorImage
//    ): MutableList<String> {
//        val categories = ageModel.process(tensorImage).probabilityAsCategoryList
//
//        val categoryMap = mutableMapOf("Infant" to Category("Infant", 0.0f))
//
//        for (category in categories){
//            categoryMap[category.label] = category
//        }
//
//        var category = categoryMap["Infant"]
//        var probability = category?.score
//        var n = 0
//
//        while (probability!! < 0.5){
//            n += 1
//            category = categoryMap[n.toString()]
//            probability += category?.score ?: 0.0f
//        }
//
//        return extractClassifications(listOf(category))
//    }
}