package com.thomasjbarrerasconsulting.faces.kotlin.facedetector
import org.tensorflow.lite.support.label.Category
import java.util.*

class ClassificationTracker(val timeOutSeconds: Int, val classifier: String) {
    private val categories: MutableMap<String, MutableList<ClassificationProbability>> = mutableMapOf()

    fun merge(newCategories: List<Category>): MutableList<Category>{
        val categoryList = mutableListOf<Category>()
        val now = Date()

        for (category in newCategories){
            if (categories[category.label] == null){
                categories[category.label] = mutableListOf()
            }

            val expiredClassificationProbabilities = mutableListOf<ClassificationProbability>()
            for (classificationProbability in categories[category.label]!!){
                val timeoutDate = Date()
                timeoutDate.time = classificationProbability.timeStamp.time + timeOutSeconds * 1000

                if (now > timeoutDate){
                    expiredClassificationProbabilities.add(classificationProbability)
                }
            }

            for (expiredClassificationProbability in expiredClassificationProbabilities){
                categories[category.label]?.remove(expiredClassificationProbability)
            }
            categories[category.label]?.add(ClassificationProbability(now, category.score))

            var score = 0.0f
            var scoresCount = 0
            for (classificationProbability in categories[category.label]!!){
                score += classificationProbability.probability
                scoresCount++
            }
            score /= scoresCount

            categoryList.add(Category(category.label, score))
        }
        return categoryList
    }
}