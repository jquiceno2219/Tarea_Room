package com.example.tarea_room.viewmodel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

// Usa rememberSaveable para guardar el valor
@Composable
fun AddContactDialog(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    var firstName by rememberSaveable { mutableStateOf(state.firstName) }
    var lastName by rememberSaveable { mutableStateOf(state.lastName) }
    var phoneNumber by rememberSaveable { mutableStateOf(state.phoneNumber) }

    AlertDialog(
        onDismissRequest = {
            onEvent(ContactEvent.HideDialog)
        },
        title = {
            Text(text = "Add contact")
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    placeholder = { Text("First name") },
                    modifier = Modifier.testTag("input_name")
                )
                TextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    placeholder = { Text("Last name") }
                )
                TextField(
                    value = phoneNumber,
                    onValueChange = { phoneNumber = it },
                    placeholder = { Text("Phone number") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onEvent(ContactEvent.SetFirstName(firstName))
                onEvent(ContactEvent.SetLastName(lastName))
                onEvent(ContactEvent.SetPhoneNumber(phoneNumber))
                onEvent(ContactEvent.SaveContact)},
                modifier = Modifier.testTag("btn_save")
            ) {
                Text("Save")
            }
        },
        modifier = modifier
    )
}