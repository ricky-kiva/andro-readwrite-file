package com.rickyslash.readwritefileapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}

/* LOCAL DATA PERSISTENT */
// Mechanism of saving data in an app:
// - onSavedInstanceState: (key-value) 'save data' 'only when app is opened', disappearing when close
// - DataStore: (key-value) save data with 'key-value' concept. easy to save primitive data (boolean, int, long, etc)
// --- data 'saved in memory'. still available when app is closed, but 'disappeared' when the 'cache' is cleared / app being uninstalled
// - SQLiteDatabase: (local table data) save data in 'SQL database' in 'relational-table' from
// --- don't need to create connection / authentication in Android for SQLite
// - Room Database: (local ORM table data) ORM Library (Object Relational Mapping) that's included in Jetpack. It includes the abstraction layer of SQLite
// --- No need to write query manually, to perform action towards database
// --- Using DAO (Data Access Object) mechanism, where database object is being converted to Java/Kotlin Object
// --- Integrated with Jetpack's LiveData
// - App-Specific Storage: (private media storage) Using file-making mechanism. File will be saved to device's storage (external/internal)
// - Shared Storage: (public media storage) A storage that could be accessed by another app, like app dedicated for photo, audio, video, etc
// --- the storage won't be disappeared when uninstalled
// - Network: (accessible data table) Save data to server

// File Storage: data saving mechanism that includes 'manipulating file to a storage'
// - 2 type of File Storage:
// --- App-Specific Storage
// --- Shared Storage

// App Specific Storage:
// - data saved here can only be accessed by this app
// - when app is uninstalled, all data here disappeared
// - Save to internal: Use getFilesDir() / getCacheDic() (used for sensitive data that's always available, but has limited size)
// - Save to external: Use getExternalFilesDir() / getExternalCacheDir() (used for data that's not always available, and has a large size)
// - Don't need any permission for Android 4.4 above

// Shared Storage:
// - data saved here can be accessed by other app
// - Commonly, located on /storage/emulated/0/Download
// - when app is uninstalled, all data remains
// - Use MediaStore APIto save media file, and Storage Access Framework (SAF) for another file
// - need permission for MediaStore:
// --- READ_EXTERNAL_STORAGE: for Android 11 above
// --- WRITE_EXTERNAL_STORAGE: for Android 10 below
// - don't need permission for Storage Access Network (SAF)