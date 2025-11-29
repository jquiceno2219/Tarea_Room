package com.example.tarea_room.viewmodel

import com.example.tarea_room.data.Contact
import com.example.tarea_room.model.SortType

// Esta clase representa todo el estado que necesita la pantalla de contactos.
// Compose se recompone automáticamente cuando alguna propiedad de este estado cambia.
data class ContactState(
    val contacts: List<Contact> = emptyList(),

    // Valores de entrada que el usuario ha escrito en el formulario.
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",

    // Indica si la UI debe mostrar el formulario para agregar un contacto.
    val isAddingContact: Boolean = false,

    // Tipo de ordenamiento seleccionado para la lista.
    // Puede ser por nombre, apellido, etc., según la enum SortType.
    val sortType: SortType = SortType.FIRST_NAME
)