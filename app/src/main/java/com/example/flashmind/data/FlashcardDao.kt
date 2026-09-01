package com.example.flashmind.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FlashcardDao {
    // Subjects
    @Query("SELECT * FROM subjects")
    fun getAllSubjects(): Flow<List<FlashcardSubject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subject: FlashcardSubject)

    @Delete
    suspend fun deleteSubject(subject: FlashcardSubject)

    // Sets
    @Query("SELECT * FROM flashcard_sets")
    fun getAllSets(): Flow<List<FlashcardSet>>

    @Query("SELECT * FROM flashcard_sets WHERE subjectId = :subjectId")
    fun getSetsBySubject(subjectId: String): Flow<List<FlashcardSet>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSet(set: FlashcardSet)

    @Update
    suspend fun updateSet(set: FlashcardSet)

    @Delete
    suspend fun deleteSet(set: FlashcardSet)
    
    @Query("DELETE FROM flashcard_sets WHERE id = :setId")
    suspend fun deleteSetById(setId: String)

    @Query("SELECT * FROM flashcard_sets WHERE id = :setId")
    suspend fun getSetById(setId: String): FlashcardSet?

    @Transaction
    @Query("SELECT * FROM flashcard_sets WHERE id = :setId")
    fun getSetWithCards(setId: String): Flow<FlashcardSetWithCards?>

    // Flashcards
    @Query("SELECT * FROM flashcards WHERE setId = :setId ORDER BY orderIndex ASC")
    fun getCardsBySet(setId: String): Flow<List<Flashcard>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: Flashcard)

    @Update
    suspend fun updateCard(card: Flashcard)

    @Delete
    suspend fun deleteCard(card: Flashcard)
    
    @Query("DELETE FROM flashcards WHERE id = :cardId")
    suspend fun deleteCardById(cardId: String)
}
