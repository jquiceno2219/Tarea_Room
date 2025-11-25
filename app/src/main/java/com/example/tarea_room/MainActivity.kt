package com.example.tarea_room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Room
import com.example.tarea_room.ui.theme.Tarea_Room_Theme
import kotlin.getValue

class MainActivity : ComponentActivity() {

    private val db: ContactDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            ContactDatabase::class.java,
            "contacts.db"
        ).build()
    }

    private val viewModel by viewModels<ContactViewModel>(
        factoryProducer = {
            viewModelFactory {
                initializer {
                    ContactViewModel(db.dao)
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Tarea_Room_Theme {
                val state by viewModel.state.collectAsState()
                Screens(state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}