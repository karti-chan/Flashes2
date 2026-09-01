package com.example.flashmind.data

import androidx.room.*

@Entity(tableName = "subjects")
data class FlashcardSubject(
    @PrimaryKey val id: String,
    val title: String,
    val description: String
)

enum class GroupType {
    SYMBOLS,
    FLASHCARDS
}

@Entity(
    tableName = "flashcard_sets",
    foreignKeys = [
        ForeignKey(
            entity = FlashcardSubject::class,
            parentColumns = ["id"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["subjectId"])]
)
data class FlashcardSet(
    @PrimaryKey val id: String,
    val subjectId: String,
    val type: GroupType,
    val title: String,
    val description: String,
    var studied: Boolean = false,
    val lastStudied: String? = null
)

@Entity(
    tableName = "flashcards",
    foreignKeys = [
        ForeignKey(
            entity = FlashcardSet::class,
            parentColumns = ["id"],
            childColumns = ["setId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["setId"])]
)
data class Flashcard(
    @PrimaryKey val id: String,
    val setId: String,
    val type: CardType,
    val question: String,
    val answer: String,
    val orderIndex: Int = 0
)

data class FlashcardSetWithCards(
    @Embedded val set: FlashcardSet,
    @Relation(
        parentColumn = "id",
        entityColumn = "setId"
    )
    val cards: List<Flashcard>
)

enum class CardType {
    TEXT,
    TRACE
}

data class StudyState(
    val currentSetId: String = "",
    val currentCardIndex: Int = 0,
    val isStudying: Boolean = false,
    val sessionCards: List<Flashcard> = emptyList()
)
