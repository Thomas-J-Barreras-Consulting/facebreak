package com.thomasjbarrerasconsulting.faces.kotlin.facedetector

import org.tensorflow.lite.support.label.Category
import java.text.NumberFormat

class CharacterFlawsClassifierProcessor {
    companion object {
        fun extractCharacterFlawsClassification(outputs: List<Category?>): MutableList<String> {
            val percentFormat: NumberFormat = NumberFormat.getPercentInstance()
            val classifications: MutableList<String> = mutableListOf()

            val significantOutputs = outputs.filter{ it!!.score >= 0.04 }
            val totalScore = significantOutputs.map { it!!.score }.sum()

            for (output in significantOutputs){
//                val label = output!!.label.replace("Frightened", "Timid")
//                    .replace("Know-It-All", "Pompous")
//                    .replace("Whiny", "Vain")
//                    .replace("Rowdy", "Wild")
//                    .replace("Irritable", "Cranky")
//                    .replace("Flirtatious", "Flirty")
//                    .replace("Abrasive", "Irritating")
//                    .replace("Disorganized", "Confused")
//                    .replace("Cantankerous", "Grumpy")
//                    .replace("Pretentious", "Proud")

                classifications.add("${output!!.label} (${percentFormat.format(output.score / totalScore)})")
            }

            return classifications
        }
    }
}