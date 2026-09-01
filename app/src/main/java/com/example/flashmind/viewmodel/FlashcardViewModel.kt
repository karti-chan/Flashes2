package com.example.flashmind.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.flashmind.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * Encapsulates the entire UI state for the FlashMind app.
 */
data class FlashcardUiState(
    val subjects: List<FlashcardSubject> = emptyList(),
    val sets: List<FlashcardSet> = emptyList(),
    val studyState: StudyState = StudyState(),
    val selectedSubjectId: String = "",
    val selectedSetId: String = "",
    val isAddSubjectDialogOpen: Boolean = false,
    val isAddSetDialogOpen: Boolean = false,
    val isAddCardDialogOpen: Boolean = false,
    val isEditingCard: Boolean = false,
    val isLoading: Boolean = false,
    val isDarkMode: Boolean? = null, // null means use system setting
    val error: String? = null
)

/**
 * Professionally refactored ViewModel using Flow for data streaming 
 * and Compose State for seamless UI observation.
 */
class FlashcardViewModel(private val repository: FlashcardRepository) : ViewModel() {
    
    // Internal state using Compose's mutableStateOf so the UI observes it automatically
    private var _uiState by mutableStateOf(FlashcardUiState())
    
    // Provide read-only access for the UI
    val uiState: FlashcardUiState get() = _uiState

    // --- Legacy Accessors (UI code remains unchanged) ---
    val subjects get() = _uiState.subjects
    val sets get() = _uiState.sets
    val studyState get() = _uiState.studyState
    val selectedSubjectId get() = _uiState.selectedSubjectId
    val selectedSetId get() = _uiState.selectedSetId
    val isAddSubjectDialogOpen get() = _uiState.isAddSubjectDialogOpen
    val isAddSetDialogOpen get() = _uiState.isAddSetDialogOpen
    val isAddCardDialogOpen get() = _uiState.isAddCardDialogOpen
    val isEditingCard get() = _uiState.isEditingCard
    val isDarkMode get() = _uiState.isDarkMode

    // --- Form states (kept separate as they are transient/ephemeral) ---
    var newSubjectTitle by mutableStateOf("")
        private set
    var newSubjectDescription by mutableStateOf("")
        private set
    var newSetTitle by mutableStateOf("")
        private set
    var newSetDescription by mutableStateOf("")
        private set
    var newSetGroupType by mutableStateOf(GroupType.FLASHCARDS)
        private set
    var newCardQuestion by mutableStateOf("")
        private set
    var newCardAnswer by mutableStateOf("")
        private set
    var newCardType by mutableStateOf(CardType.TEXT)
        private set
    private var editingCardId by mutableStateOf<String?>(null)
    
    init {
        observeDatabase()
    }

    private fun observeDatabase() {
        // Reactive data streaming using combine to listen for ANY database changes
        repository.getAllSubjects()
            .combine(repository.getAllSets()) { subjects, sets ->
                if (subjects.isEmpty()) {
                    viewModelScope.launch { populateInitialData() }
                }
                _uiState = _uiState.copy(subjects = subjects, sets = sets, isLoading = false)
            }
            .catch { e -> _uiState = _uiState.copy(error = e.message, isLoading = false) }
            .launchIn(viewModelScope)
    }

    private suspend fun populateInitialData() {
        SampleData.getInitialSubjects().forEach { repository.insertSubject(it) }
        SampleData.getInitialSets().forEach { repository.insertSet(it) }
        SampleData.getInitialCards().forEach { repository.insertCard(it) }
    }

    // --- State Update Helpers ---
    fun updateNewSubjectTitle(t: String) { newSubjectTitle = t }
    fun updateNewSubjectDescription(d: String) { newSubjectDescription = d }
    fun updateNewSetTitle(t: String) { newSetTitle = t }
    fun updateNewSetDescription(d: String) { newSetDescription = d }
    fun updateNewCardQuestion(q: String) { newCardQuestion = q }
    fun updateNewCardAnswer(a: String) { newCardAnswer = a }
    fun updateNewCardType(type: CardType) { newCardType = type }
    fun insertMathToQuestion(m: String) { newCardQuestion += m }
    fun insertMathToAnswer(m: String) { newCardAnswer += m }

    // --- Navigation Actions ---
    fun selectSubject(id: String) { _uiState = _uiState.copy(selectedSubjectId = id, selectedSetId = "") }
    fun deselectSubject() { _uiState = _uiState.copy(selectedSubjectId = "", selectedSetId = "") }
    fun selectSet(id: String) { if (!_uiState.studyState.isStudying) _uiState = _uiState.copy(selectedSetId = id) }
    fun deselectSet() { if (!_uiState.studyState.isStudying) _uiState = _uiState.copy(selectedSetId = "") }

    // --- Management Actions ---
    fun showAddSubjectDialog() { 
        newSubjectTitle = ""; newSubjectDescription = ""
        _uiState = _uiState.copy(isAddSubjectDialogOpen = true) 
    }
    fun dismissAddSubjectDialog() { _uiState = _uiState.copy(isAddSubjectDialogOpen = false) }
    fun confirmAddSubject() {
        if (newSubjectTitle.isBlank()) return
        val id = "sub_${System.currentTimeMillis()}"
        val sub = FlashcardSubject(id, newSubjectTitle, newSubjectDescription)
        viewModelScope.launch {
            repository.insertSubject(sub)
            repository.insertSet(FlashcardSet("${id}_sym", id, GroupType.SYMBOLS, "$newSubjectTitle Symbols", ""))
            repository.insertSet(FlashcardSet("${id}_cards", id, GroupType.FLASHCARDS, "$newSubjectTitle Cards", ""))
            newSubjectTitle = ""
            newSubjectDescription = ""
            _uiState = _uiState.copy(isAddSubjectDialogOpen = false, selectedSubjectId = id)
        }
    }

    fun showAddSetDialog(type: GroupType) { 
        newSetTitle = ""; newSetDescription = ""; newSetGroupType = type
        _uiState = _uiState.copy(isAddSetDialogOpen = true) 
    }
    fun dismissAddSetDialog() { _uiState = _uiState.copy(isAddSetDialogOpen = false) }
    fun confirmAddSet(subId: String) {
        if (newSetTitle.isBlank()) return
        val set = FlashcardSet("set_${System.currentTimeMillis()}", subId, newSetGroupType, newSetTitle, newSetDescription)
        viewModelScope.launch { 
            repository.insertSet(set)
            _uiState = _uiState.copy(isAddSetDialogOpen = false) 
        }
    }

    fun showAddCardDialog() { 
        newCardQuestion = ""; newCardAnswer = ""; newCardType = CardType.TEXT
        _uiState = _uiState.copy(isEditingCard = false, isAddCardDialogOpen = true) 
    }
    fun showEditCardDialog(index: Int) {
        val currentSetId = _uiState.selectedSetId
        viewModelScope.launch {
            repository.getCardsBySet(currentSetId).first().getOrNull(index)?.let { card ->
                newCardQuestion = card.question
                newCardAnswer = card.answer
                newCardType = card.type
                editingCardId = card.id
                _uiState = _uiState.copy(isEditingCard = true, isAddCardDialogOpen = true)
            }
        }
    }
    fun dismissAddCardDialog() { _uiState = _uiState.copy(isAddCardDialogOpen = false) }
    fun confirmAddCard() {
        if (newCardQuestion.isBlank() || newCardAnswer.isBlank()) return
        val currentSetId = _uiState.selectedSetId
        viewModelScope.launch {
            val card = Flashcard(
                id = editingCardId ?: "c_${System.currentTimeMillis()}",
                setId = currentSetId,
                type = newCardType,
                question = newCardQuestion,
                answer = newCardAnswer
            )
            repository.insertCard(card)
            _uiState = _uiState.copy(isAddCardDialogOpen = false)
        }
    }

    fun deleteSubject(id: String) {
        viewModelScope.launch {
            _uiState.subjects.find { it.id == id }?.let { repository.deleteSubject(it) }
            _uiState.sets.filter { it.subjectId == id }.forEach { repository.deleteSet(it) }
            if (_uiState.selectedSubjectId == id) deselectSubject()
        }
    }
    fun deleteSet(id: String) { viewModelScope.launch { repository.deleteSetById(id); if (_uiState.selectedSetId == id) deselectSet() } }
    fun deleteCard(setId: String, index: Int) {
        viewModelScope.launch {
            repository.getCardsBySet(setId).first().getOrNull(index)?.let {
                repository.deleteCard(it)
            }
        }
    }

    fun toggleCardType(setId: String, index: Int) {
        viewModelScope.launch {
            repository.getCardsBySet(setId).first().getOrNull(index)?.let { card ->
                val newType = if (card.type == CardType.TEXT) CardType.TRACE else CardType.TEXT
                repository.insertCard(card.copy(type = newType))
            }
        }
    }

    fun getCards(setId: String): Flow<List<Flashcard>> = repository.getCardsBySet(setId)

    // --- Study Actions ---
    fun startStudy() {
        val currentSetId = _uiState.selectedSetId
        viewModelScope.launch {
            repository.getCardsBySet(currentSetId).first().let { cards ->
                _uiState = _uiState.copy(studyState = StudyState(currentSetId, 0, true, cards.shuffled()))
            }
        }
    }
    fun nextCard() { _uiState = _uiState.copy(studyState = _uiState.studyState.copy(currentCardIndex = _uiState.studyState.currentCardIndex + 1)) }
    fun quitStudy() {
        val id = _uiState.studyState.currentSetId
        _uiState = _uiState.copy(studyState = StudyState())
        viewModelScope.launch { repository.getSetById(id)?.let { repository.updateSet(it.copy(studied = true)) } }
    }
    fun previewCard(setId: String, index: Int) {}
    
    fun toggleTheme() {
        val nextMode = when (_uiState.isDarkMode) {
            true -> false
            false -> null
            null -> true
        }
        _uiState = _uiState.copy(isDarkMode = nextMode)
    }

    class Factory(private val repository: FlashcardRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return FlashcardViewModel(repository) as T
        }
    }
}
