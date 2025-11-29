package com.example.tarea_room

import androidx.activity.ComponentActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.tarea_room.data.testdata.FakeContactRepository
import com.example.tarea_room.viewmodel.ContactViewModel
import com.example.tarea_room.viewmodel.Screens
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScreensTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun insertarContacto_apareceEnLista() {
        // 1. Abrir el diálogo
        composeRule.onNodeWithTag("fab_add").performClick()

        // 2. Ingresar nombre
        composeRule.onNodeWithTag("input_name")
            .performTextInput("Juan")

        // 3. Guardar contacto
        composeRule.onNodeWithTag("btn_save").performClick()

        // 4. Verificar que la lista existe
        composeRule.onNodeWithTag("list_contacts").assertExists()

        // 5. Verificar que el contacto aparece
        composeRule.onNodeWithText("Juan").assertExists()
    }
}