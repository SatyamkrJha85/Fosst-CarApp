package com.theapplicationpad.fosst_carapp.Mvvm.VIewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.theapplicationpad.fosst_carapp.Mvvm.Model.FirebaseModel.Car
import com.theapplicationpad.fosst_carapp.Mvvm.FirebaseRepo.CarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarViewModel(private val carRepository: CarRepository) : ViewModel() {

    private val _featureCars = MutableStateFlow<List<Car>>(emptyList())
    val featureCars: StateFlow<List<Car>> = _featureCars

    private val _likedCars = MutableStateFlow<List<Car>>(emptyList())
    val likedCars: StateFlow<List<Car>> = _likedCars

    private val _cartCars = MutableStateFlow<List<Car>>(emptyList())
    val cartCars: StateFlow<List<Car>> = _cartCars

    private val _purchasedCars = MutableStateFlow<List<Car>>(emptyList())
    val purchasedCars: StateFlow<List<Car>> = _purchasedCars

    init {
        viewModelScope.launch {
            carRepository.getFeatureCars().collect {
                _featureCars.value = it
            }
        }
    }

    fun getLikedCars(userId: String) {
        viewModelScope.launch {
            carRepository.getLikedCars(userId).collect {
                _likedCars.value = it
            }
        }
    }

    fun getPurchasedCars(userId: String) {
        viewModelScope.launch {
            carRepository.getPurchasedCars(userId).collect {
                _purchasedCars.value = it
            }
        }
    }

    fun getCartCars(userId: String) {
        viewModelScope.launch {
            carRepository.getCartCars(userId).collect {
                _cartCars.value = it
            }
        }
    }

    fun addToCart(userId: String, car: Car) {
        viewModelScope.launch {
            carRepository.addToCart(userId, car)
        }
    }

    fun addToLike(userId: String, car: Car) {
        viewModelScope.launch {
            carRepository.addToLike(userId, car)
        }
    }

    fun addToPurchaseHistory(userId: String, car: Car) {
        viewModelScope.launch {
            carRepository.addToPurchaseHistory(userId, car)
        }
    }

    fun removeFromCart(userId: String, carId: String) {
        viewModelScope.launch {
            carRepository.removeFromCart(userId, carId)
        }
    }

    fun removeFromLike(userId: String, carId: String) {
        viewModelScope.launch {
            carRepository.removeFromLike(userId, carId)
        }
    }
}

class CarViewModelFactory(private val carRepository: CarRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarViewModel(carRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
