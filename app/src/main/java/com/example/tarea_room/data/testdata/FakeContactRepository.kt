package com.example.tarea_room.data.testdata

import com.example.tarea_room.data.Contact
import com.example.tarea_room.data.ContactRepository
import com.example.tarea_room.model.SortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

// Se utiliza FakeContactRepository para simular el repositorio de contactos sin usar
//la db real.
class FakeContactRepository : ContactRepository {

    private val contacts = MutableStateFlow<List<Contact>>(emptyList())
    private var autoId = 1

    override fun getContacts(sortType: SortType): Flow<List<Contact>> {
        return contacts.map { list ->
            when (sortType) {
                SortType.FIRST_NAME -> list.sortedBy { it.firstName }
                SortType.LAST_NAME -> list.sortedBy { it.lastName }
                SortType.PHONE_NUMBER -> list.sortedBy { it.phoneNumber }
            }
        }
    }

    override suspend fun upsertContact(contact: Contact) {
        val newContact = if (contact.id == 0) {
            // simular Room autogenerando ID
            contact.copy(id = autoId++)
        } else {
            contact
        }

        contacts.value = contacts.value + newContact
    }

    override suspend fun deleteContact(contact: Contact) {
        contacts.value = contacts.value - contact
    }
}
