package com.example.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// Entities
@Entity(tableName = "rides")
data class RideEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val dateText: String,
    val price: Double,
    val priceText: String,
    val fromAddress: String,
    val toAddress: String,
    val status: String, // مكتملة, ملغاة
    val driverName: String,
    val driverRating: Double,
    val driverCar: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "transactions")
data class WalletTransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val dateText: String,
    val amount: Double,
    val type: String, // income, expense
    val status: String, // مدفوع, مكتمل
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "user_profile")
data class UserProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String,
    val phone: String,
    val balance: Double,
    val rating: Double,
    val level: String,
    val completedRidesCount: Int,
    val memberSince: String
)

// DAOs
@Dao
interface RideDao {
    @Query("SELECT * FROM rides ORDER BY timestamp DESC")
    fun getAllRides(): Flow<List<RideEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRide(ride: RideEntity)

    @Query("DELETE FROM rides")
    suspend fun clearRides()
}

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<WalletTransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: WalletTransactionEntity)

    @Query("DELETE FROM transactions")
    suspend fun clearTransactions()
}

@Dao
interface UserProfileDao {
    @Query("SELECT * FROM user_profile WHERE id = 1")
    fun getUserProfile(): Flow<UserProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(profile: UserProfileEntity)

    @Query("UPDATE user_profile SET balance = balance + :amount WHERE id = 1")
    suspend fun rechargeBalance(amount: Double)

    @Query("UPDATE user_profile SET balance = balance - :amount WHERE id = 1")
    suspend fun deductBalance(amount: Double)
}

// Database
@Database(
    entities = [RideEntity::class, WalletTransactionEntity::class, UserProfileEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rideDao(): RideDao
    abstract fun transactionDao(): TransactionDao
    abstract fun userProfileDao(): UserProfileDao
}

// Repository abstracting access
class MeshwarRepository(
    private val db: AppDatabase
) {
    val allRides: Flow<List<RideEntity>> = db.rideDao().getAllRides()
    val allTransactions: Flow<List<WalletTransactionEntity>> = db.transactionDao().getAllTransactions()
    val userProfile: Flow<UserProfileEntity?> = db.userProfileDao().getUserProfile()

    suspend fun insertRide(ride: RideEntity) = db.rideDao().insertRide(ride)
    suspend fun insertTransaction(transaction: WalletTransactionEntity) = db.transactionDao().insertTransaction(transaction)
    suspend fun insertUserProfile(profile: UserProfileEntity) = db.userProfileDao().insertUserProfile(profile)
    suspend fun rechargeBalance(amount: Double) = db.userProfileDao().rechargeBalance(amount)
    suspend fun deductBalance(amount: Double) = db.userProfileDao().deductBalance(amount)

    suspend fun prepopulateData() {
        // Only prepopulate if profile is empty
        db.userProfileDao().insertUserProfile(
            UserProfileEntity(
                id = 1,
                name = "Ahmed Al-Iraqi",
                phone = "+964 770 000 0000",
                balance = 25450.0,
                rating = 4.9,
                level = "عضو ذهبي",
                completedRidesCount = 142,
                memberSince = "2022"
            )
        )

        // Seed rides
        db.rideDao().clearRides()
        db.rideDao().insertRide(
            RideEntity(
                type = "Taxi - تكسي",
                dateText = "١٥ أكتوبر ٢٠٢٣، ١٠:٣٠ ص",
                price = 8500.0,
                priceText = "٨,٥٠٠ د.ع",
                fromAddress = "شارع المنصور، بغداد",
                toAddress = "الكرادة، ساحة كهرمانة",
                status = "مكتملة",
                driverName = "أحمد العراقي",
                driverRating = 4.9,
                driverCar = "كيا ريو 2022",
                timestamp = System.currentTimeMillis() - 100000
            )
        )
        db.rideDao().insertRide(
            RideEntity(
                type = "Coaster - كوستر",
                dateText = "١٤ أكتوبر ٢٠٢٣، ٠٨:١٥ ص",
                price = 2000.0,
                priceText = "٢,٠٠٠ د.ع",
                fromAddress = "جامعة بغداد، الجادرية",
                toAddress = "حي الجامعة، بغداد",
                status = "ملغاة",
                driverName = "جاسم المحمداوي",
                driverRating = 4.7,
                driverCar = "كوستر أبيض",
                timestamp = System.currentTimeMillis() - 200000
            )
        )
        db.rideDao().insertRide(
            RideEntity(
                type = "Kia Load - كيا حمل",
                dateText = "١٢ أكتوبر ٢٠٢٣، ٠٣:٤٥ م",
                price = 12000.0,
                priceText = "١٢,٠٠٠ د.ع",
                fromAddress = "سوق الشورجة",
                toAddress = "اليرموك، بغداد",
                status = "مكتملة",
                driverName = "علي البصري",
                driverRating = 4.8,
                driverCar = "كيا حمل أزرق",
                timestamp = System.currentTimeMillis() - 300000
            )
        )
        db.rideDao().insertRide(
            RideEntity(
                type = "Taxi - تكسي",
                dateText = "١٠ أكتوبر ٢٠٢٣، ٠٩:٠٠ م",
                price = 25000.0,
                priceText = "٢٥,٠٠٠ د.ع",
                fromAddress = "مطار بغداد الدولي",
                toAddress = "الاعظمية، بغداد",
                status = "مكتملة",
                driverName = "سعد الحلفي",
                driverRating = 4.9,
                driverCar = "تكسي صفراء 2021",
                timestamp = System.currentTimeMillis() - 400000
            )
        )

        // Seed wallet transactions
        db.transactionDao().clearTransactions()
        db.transactionDao().insertTransaction(
            WalletTransactionEntity(
                title = "رحلة إلى المنصور",
                dateText = "أمس، 08:30 م",
                amount = 6500.0,
                type = "expense",
                status = "مدفوع",
                timestamp = System.currentTimeMillis() - 150000
            )
        )
        db.transactionDao().insertTransaction(
            WalletTransactionEntity(
                title = "شحن رصيد (زين كاش)",
                dateText = "12 أكتوبر، 02:15 م",
                amount = 25000.0,
                type = "income",
                status = "مكتمل",
                timestamp = System.currentTimeMillis() - 250000
            )
        )
        db.transactionDao().insertTransaction(
            WalletTransactionEntity(
                title = "رحلة من الكرادة",
                dateText = "10 أكتوبر، 09:00 ص",
                amount = 4000.0,
                type = "expense",
                status = "مدفوع",
                timestamp = System.currentTimeMillis() - 350000
            )
        )
        db.transactionDao().insertTransaction(
            WalletTransactionEntity(
                title = "استرداد نقدي (برومو)",
                dateText = "08 أكتوبر، 11:45 م",
                amount = 1500.0,
                type = "income",
                status = "مكتمل",
                timestamp = System.currentTimeMillis() - 450000
            )
        )
    }
}
