package com.nifa.fuel_buddy.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.nifa.fuel_buddy.core.data.datastore.common.PreferenceDataSource
import com.nifa.fuel_buddy.core.data.socket.SocketIoConnectionState
import com.nifa.fuel_buddy.core.data.socket.SocketIoManager
import com.nifa.fuel_buddy.core.presentation.navigation.SetupMainNavGraph
import com.nifa.fuel_buddy.core.presentation.ui.theme.FuelBuddyMobileTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var socketIoManager: SocketIoManager

    @Inject
    lateinit var preferenceDataSource: PreferenceDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val splashScreen = installSplashScreen()
        setContent {
            FuelBuddyMobileTheme {

                val viewModel = hiltViewModel<MainViewModel>()
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val navHostController = rememberNavController()

                splashScreen.setKeepOnScreenCondition {
                    uiState.keepSplashScreen
                }

                SetupMainNavGraph(
                    navHostController = navHostController,
                    startDestination = uiState.startDestination
                )
            }
        }
    }

    private fun initSocket() {
        lifecycleScope.launch {
            preferenceDataSource.jwtTokenFlow.collect { jwtToken ->
                with(socketIoManager) {
                    if (socketIoConnectionState.value == SocketIoConnectionState.DISCONNECTED) {
                        connect(jwtToken)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.d("Application Started")
        initSocket()
    }

    override fun onStop() {
        super.onStop()
        Timber.d("Application Stopped")
        socketIoManager.disconnect()
    }
}
