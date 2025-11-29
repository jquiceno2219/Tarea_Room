package com.example.tarea_room.data

import com.example.tarea_room.model.SortType
import kotlinx.coroutines.flow.Flow

interface ContactRepository {
    fun getContacts(sortType: SortType): Flow<List<Contact>>

    suspend fun upsertContact(contact: Contact)
    suspend fun deleteContact(contact: Contact)
}