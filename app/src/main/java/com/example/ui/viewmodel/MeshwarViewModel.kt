package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.data.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MeshwarViewModel(application: Application) : AndroidViewModel(application) {

    private val database: AppDatabase by lazy {
        Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "meshwar_database"
        ).fallbackToDestructiveMigration().build()
    }

    private val repository: MeshwarRepository by lazy {
        MeshwarRepository(database)
    }

    // State flows from Room
    val userProfile: StateFlow<UserProfileEntity?> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val rides: StateFlow<List<RideEntity>> = repository.allRides
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val transactions: StateFlow<List<WalletTransactionEntity>> = repository.allTransactions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // UI-only states
    var enteredPhone = MutableStateFlow("")
    var selectedVehicle = MutableStateFlow("Taxi") // Taxi, Kia, Kia Load, Coaster
    var isDriverMode = MutableStateFlow(false)
    var isOnline = MutableStateFlow(true)
    var isDriverRideRequestVisible = MutableStateFlow(true)
    var activeTimerProgress = MutableStateFlow(1.0f)
    var pickupLocation = MutableStateFlow("المنصور، تقاطع الرواد")
    var dropoffLocation = MutableStateFlow("الكرادة، شارع ٦٢")

    // Current mock active ride tracking states
    var rideState = MutableStateFlow<ActiveRideState>(ActiveRideState.Idle)

    init {
        viewModelScope.launch {
            // Seed database with default data on first launch
            repository.prepopulateData()
            startDriverRideRequestTimer()
        }
    }

    private fun startDriverRideRequestTimer() {
        viewModelScope.launch {
            while (true) {
                if (isDriverMode.value && isDriverRideRequestVisible.value) {
                    activeTimerProgress.value = 1.0f
                    val duration = 120 // increments
                    for (i in 0 until duration) {
                        delay(100)
                        activeTimerProgress.value = 1.0f - (i.toFloat() / duration)
                        if (!isDriverRideRequestVisible.value || !isDriverMode.value) break
                    }
                    if (isDriverRideRequestVisible.value) {
                        isDriverRideRequestVisible.value = false
                        delay(5000)
                        isDriverRideRequestVisible.value = true
                    }
                }
                delay(1000)
            }
        }
    }

    // Actions
    fun updatePhone(phone: String) {
        enteredPhone.value = phone
    }

    fun rechargeWallet(amount: Double, method: String) {
        viewModelScope.launch {
            repository.rechargeBalance(amount)
            repository.insertTransaction(
                WalletTransactionEntity(
                    title = "شحن رصيد ($method)",
                    dateText = "الآن",
                    amount = amount,
                    type = "income",
                    status = "مكتمل"
                )
            )
        }
    }

    fun selectVehicleType(type: String) {
        selectedVehicle.value = type
    }

    fun startTrackingRide(from: String, to: String, price: Double) {
        viewModelScope.launch {
            rideState.value = ActiveRideState.Searching
            delay(1500)
            rideState.value = ActiveRideState.DriverOnTheWay(
                driverName = "أحمد",
                driverRating = 4.9,
                driverCar = "كيا باص • أبيض",
                plateNumber = "1234 A",
                fromAddress = from,
                toAddress = to,
                price = price
            )
        }
    }

    fun completeRideAndGoToRating() {
        viewModelScope.launch {
            val currentState = rideState.value
            if (currentState is ActiveRideState.DriverOnTheWay) {
                // Deduct balance and add historical ride
                repository.deductBalance(currentState.price)
                repository.insertRide(
                    RideEntity(
                        type = "Taxi - تكسي",
                        dateText = "الآن",
                        price = currentState.price,
                        priceText = "${String.format("%,.0f", currentState.price)} د.ع",
                        fromAddress = currentState.fromAddress,
                        toAddress = currentState.toAddress,
                        status = "مكتملة",
                        driverName = currentState.driverName,
                        driverRating = currentState.driverRating,
                        driverCar = currentState.driverCar
                    )
                )
                repository.insertTransaction(
                    WalletTransactionEntity(
                        title = "رحلة إلى ${currentState.toAddress}",
                        dateText = "الآن",
                        amount = currentState.price,
                        type = "expense",
                        status = "مدفوع"
                    )
                )
                rideState.value = ActiveRideState.ArrivedForRating(
                    driverName = currentState.driverName,
                    driverRating = currentState.driverRating,
                    driverCar = currentState.driverCar,
                    price = currentState.price,
                    fromAddress = currentState.fromAddress,
                    toAddress = currentState.toAddress
                )
            }
        }
    }

    fun submitRating(ratingValue: Int, comment: String) {
        viewModelScope.launch {
            rideState.value = ActiveRideState.Idle
        }
    }

    fun cancelActiveRide() {
        viewModelScope.launch {
            val currentState = rideState.value
            if (currentState is ActiveRideState.DriverOnTheWay) {
                repository.insertRide(
                    RideEntity(
                        type = "Taxi - تكسي",
                        dateText = "الآن",
                        price = currentState.price,
                        priceText = "${String.format("%,.0f", currentState.price)} د.ع",
                        fromAddress = currentState.fromAddress,
                        toAddress = currentState.toAddress,
                        status = "ملغاة",
                        driverName = currentState.driverName,
                        driverRating = currentState.driverRating,
                        driverCar = currentState.driverCar
                    )
                )
            }
            rideState.value = ActiveRideState.Idle
        }
    }

    fun toggleDriverMode() {
        isDriverMode.value = !isDriverMode.value
    }

    fun toggleOnlineStatus() {
        isOnline.value = !isOnline.value
    }

    fun dismissDriverRideRequest() {
        isDriverRideRequestVisible.value = false
        viewModelScope.launch {
            delay(8000)
            isDriverRideRequestVisible.value = true
        }
    }
}

sealed class ActiveRideState {
    object Idle : ActiveRideState()
    object Searching : ActiveRideState()
    data class DriverOnTheWay(
        val driverName: String,
        val driverRating: Double,
        val driverCar: String,
        val plateNumber: String,
        val fromAddress: String,
        val toAddress: String,
        val price: Double
    ) : ActiveRideState()
    data class ArrivedForRating(
        val driverName: String,
        val driverRating: Double,
        val driverCar: String,
        val price: Double,
        val fromAddress: String,
        val toAddress: String
    ) : ActiveRideState()
}
