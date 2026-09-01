package com.example.flashmind.viewmodel

import com.example.flashmind.data.*
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FlashcardViewModelTest {

    private val repository = mockk<FlashcardRepository>(relaxed = true)
    private lateinit var viewModel: FlashcardViewModel
    private val testDispatcher = UnconfinedTestDispatcher()
    
    // Use MutableStateFlow to simulate database updates
    private val subjectsFlow = MutableStateFlow<List<FlashcardSubject>>(emptyList())
    private val setsFlow = MutableStateFlow<List<FlashcardSet>>(emptyList())

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        // Mock the database observation to use our flows
        every { repository.getAllSubjects() } returns subjectsFlow
        every { repository.getAllSets() } returns setsFlow
        
        // Initialize with non-empty to avoid populateInitialData in tests
        subjectsFlow.value = listOf(FlashcardSubject("1", "Title", "Desc"))
        
        viewModel = FlashcardViewModel(repository)
        
        clearMocks(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `selectSubject should update state`() {
        val subjectId = "test_sub"
        viewModel.selectSubject(subjectId)
        assertEquals(subjectId, viewModel.selectedSubjectId)
    }

    @Test
    fun `confirmAddSubject should call repository`() = runTest {
        viewModel.updateNewSubjectTitle("Math")
        viewModel.confirmAddSubject()
        coVerify { repository.insertSubject(any()) }
        assertEquals("", viewModel.newSubjectTitle)
    }

    @Test
    fun `deleteCard should use current sets from state`() = runTest {
        // Arrange
        val setId = "set1"
        val testCard = Flashcard("c1", CardType.TEXT, "Q", "A")
        val testSet = FlashcardSet(setId, "sub1", GroupType.FLASHCARDS, "Title", "Desc", listOf(testCard))
        setsFlow.value = listOf(testSet) 
        
        // Act
        viewModel.deleteCard(setId, 0)
        
        // Assert
        coVerify { repository.updateSet(any()) }
    }
}
