package com.example.tarea_room.data

import com.example.tarea_room.model.SortType
import kotlinx.coroutines.flow.Flow

class ContactRepositoryImpl(
    private val dao: ContactDao
) : ContactRepository {

    override fun getContacts(sortType: SortType) = when (sortType) {
        SortType.FIRST_NAME -> dao.getContactsOrderedByFirstName()
        SortType.LAST_NAME -> dao.getContactsOrderedByLastName()
        SortType.PHONE_NUMBER -> dao.getContactsOrderedByPhoneNumber()
    }

    override suspend fun upsertContact(contact: Contact) {
        dao.upsertContact(contact)
    }

    override suspend fun deleteContact(contact: Contact) {
        dao.deleteContact(contact)
    }
}
