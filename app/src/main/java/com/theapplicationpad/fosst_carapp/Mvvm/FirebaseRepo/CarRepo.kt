package com.theapplicationpad.fosst_carapp.Mvvm.FirebaseRepo


import com.google.firebase.firestore.FirebaseFirestore
import com.theapplicationpad.fosst_carapp.Mvvm.Model.FirebaseModel.Car
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class CarRepository {
    private val db = FirebaseFirestore.getInstance()

    fun getFeatureCars(): Flow<List<Car>> = callbackFlow {
        val listener = db.collection("FeatureCar")
            .addSnapshotListener { snapshot, _ ->
                val cars = snapshot?.documents?.mapNotNull {
                    it.toObject(Car::class.java)?.copy(id = it.id)
                }.orEmpty()
                trySend(cars)
            }
        awaitClose { listener.remove() }
    }

    fun addToCart(userId: String, car: Car) {
        db.collection("users").document(userId).collection("cart").document(car.id).set(car)
    }

    fun addToLike(userId: String, car: Car) {
        db.collection("users").document(userId).collection("likes").document(car.id).set(car)
    }

    fun getCartCars(userId: String): Flow<List<Car>> = callbackFlow {
        val listener = db.collection("users").document(userId).collection("cart")
            .addSnapshotListener { snapshot, _ ->
                val cars = snapshot?.documents?.mapNotNull {
                    it.toObject(Car::class.java)?.copy(id = it.id)
                }.orEmpty()
                trySend(cars)
            }
        awaitClose { listener.remove() }
    }

    fun getLikedCars(userId: String): Flow<List<Car>> = callbackFlow {
        val listener = db.collection("users").document(userId).collection("likes")
            .addSnapshotListener { snapshot, _ ->
                val cars = snapshot?.documents?.mapNotNull {
                    it.toObject(Car::class.java)?.copy(id = it.id)
                }.orEmpty()
                trySend(cars)
            }
        awaitClose { listener.remove() }
    }

    fun removeFromCart(userId: String, carId: String) {
        db.collection("users").document(userId).collection("cart").document(carId).delete()
    }

    fun removeFromLike(userId: String, carId: String) {
        db.collection("users").document(userId).collection("likes").document(carId).delete()
    }

     fun addToPurchaseHistory(userId: String, car: Car) {
         db.collection("users").document(userId).collection("purchases").document(car.id).set(car)
    }


    fun getPurchasedCars(userId: String): Flow<List<Car>> = callbackFlow {
        val listener = db.collection("users").document(userId).collection("purchases")
            .addSnapshotListener { snapshot, _ ->
                val cars = snapshot?.documents?.mapNotNull {
                    it.toObject(Car::class.java)?.copy(id = it.id)
                }.orEmpty()
                trySend(cars)
            }
        awaitClose { listener.remove() }
    }
}
