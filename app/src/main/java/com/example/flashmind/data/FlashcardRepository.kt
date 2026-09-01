package com.example.flashmind.data

import kotlinx.coroutines.flow.Flow

/**
 * Repository layer abstracting the data source (Room DAO).
 */
class FlashcardRepository(private val dao: FlashcardDao) {

    // Subjects
    fun getAllSubjects(): Flow<List<FlashcardSubject>> = dao.getAllSubjects()
    suspend fun insertSubject(subject: FlashcardSubject) = dao.insertSubject(subject)
    suspend fun deleteSubject(subject: FlashcardSubject) = dao.deleteSubject(subject)

    // Sets
    fun getAllSets(): Flow<List<FlashcardSet>> = dao.getAllSets()
    fun getSetsBySubject(subjectId: String): Flow<List<FlashcardSet>> = dao.getSetsBySubject(subjectId)
    suspend fun insertSet(set: FlashcardSet) = dao.insertSet(set)
    suspend fun updateSet(set: FlashcardSet) = dao.updateSet(set)
    suspend fun deleteSet(set: FlashcardSet) = dao.deleteSet(set)
    suspend fun deleteSetById(setId: String) = dao.deleteSetById(setId)
    suspend fun getSetById(setId: String): FlashcardSet? = dao.getSetById(setId)
    fun getSetWithCards(setId: String): Flow<FlashcardSetWithCards?> = dao.getSetWithCards(setId)

    // Flashcards
    fun getCardsBySet(setId: String): Flow<List<Flashcard>> = dao.getCardsBySet(setId)
    suspend fun insertCard(card: Flashcard) = dao.insertCard(card)
    suspend fun updateCard(card: Flashcard) = dao.updateCard(card)
    suspend fun deleteCard(card: Flashcard) = dao.deleteCard(card)
    suspend fun deleteCardById(cardId: String) = dao.deleteCardById(cardId)
}
