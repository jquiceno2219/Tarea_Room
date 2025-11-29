package com.example.tarea_room

import com.example.tarea_room.data.testdata.FakeContactRepository
import com.example.tarea_room.model.SortType
import com.example.tarea_room.viewmodel.ContactEvent
import com.example.tarea_room.viewmodel.ContactViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ContactViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Simula el hilo principal de Android
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `Given valid input, when saving a contact, then repository stores it`() = runTest {
        // GIVEN
        val repository = FakeContactRepository()
        val viewModel = ContactViewModel(repository)

        viewModel.onEvent(ContactEvent.SetFirstName("Ana"))
        viewModel.onEvent(ContactEvent.SetLastName("Gomez"))
        viewModel.onEvent(ContactEvent.SetPhoneNumber("12345"))

        // WHEN
        viewModel.onEvent(ContactEvent.SaveContact)

        // Esperar a que las corrutinas del ViewModel terminen
        advanceUntilIdle()

        // THEN
        val list = repository.getContacts(SortType.FIRST_NAME).first()

        assertEquals(1, list.size)
        assertEquals("Ana", list[0].firstName)
        assertEquals("Gomez", list[0].lastName)
        assertEquals("12345", list[0].phoneNumber)
    }

    @Test
    fun `Given multiple contacts, when sorting by phone number, then they are ordered correctly`() = runTest {
        // GIVEN
        val repository = FakeContactRepository()
        val viewModel = ContactViewModel(repository)

        // Agregar tres contactos con números desordenados
        viewModel.onEvent(ContactEvent.SetFirstName("Juan"))
        viewModel.onEvent(ContactEvent.SetLastName("Perez"))
        viewModel.onEvent(ContactEvent.SetPhoneNumber("300"))
        viewModel.onEvent(ContactEvent.SaveContact)

        viewModel.onEvent(ContactEvent.SetFirstName("Ana"))
        viewModel.onEvent(ContactEvent.SetLastName("Gomez"))
        viewModel.onEvent(ContactEvent.SetPhoneNumber("100"))
        viewModel.onEvent(ContactEvent.SaveContact)

        viewModel.onEvent(ContactEvent.SetFirstName("Luis"))
        viewModel.onEvent(ContactEvent.SetLastName("Diaz"))
        viewModel.onEvent(ContactEvent.SetPhoneNumber("200"))
        viewModel.onEvent(ContactEvent.SaveContact)

        // Esperar ejecución de corrutinas
        advanceUntilIdle()

        // WHEN
        val result = repository.getContacts(SortType.PHONE_NUMBER).first()

        advanceUntilIdle()

        // THEN
        assertEquals(3, result.size)
        assertEquals("100", result[0].phoneNumber)
        assertEquals("200", result[1].phoneNumber)
        assertEquals("300", result[2].phoneNumber)
    }
}
