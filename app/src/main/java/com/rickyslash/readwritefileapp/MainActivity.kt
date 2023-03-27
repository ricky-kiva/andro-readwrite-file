package com.rickyslash.readwritefileapp

import android.icu.text.CaseMap.Title
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.rickyslash.readwritefileapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonNew.setOnClickListener(this)
        binding.buttonOpen.setOnClickListener(this)
        binding.buttonSave.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button_new -> newFile()
            R.id.button_open -> showList()
            R.id.button_save -> saveFile()
        }
    }

    private fun newFile() {
        binding.editTitle.setText("")
        binding.editFile.setText("")
        Toast.makeText(this, "Clearing file", Toast.LENGTH_SHORT).show()
    }

    private fun showList() {
        val items = fileList()
        // create instance of 'AlertDialog'
        val builder = AlertDialog.Builder(this)
        // set items to be displayed in 'AlertDialog' modal
        builder.setItems(items) { dialog, item -> loadData(items[item].toString()) }
        // creates the 'AlertDialog'
        val alert = builder.create()
        alert.show()
    }

    private fun saveFile() {
        when {
            binding.editTitle.text.toString().isEmpty() -> Toast.makeText(this, "Title can't be empty", Toast.LENGTH_SHORT).show()
            binding.editFile.text.toString().isEmpty() -> Toast.makeText(this, "Content can't be empty", Toast.LENGTH_SHORT).show()
            else -> {
                val title = binding.editTitle.text.toString()
                val text = binding.editFile.text.toString()
                val fileModel = FileModel()
                fileModel.filename = title
                fileModel.data = text
                FileHelper.writeToFile(fileModel, this)
                Toast.makeText(this, "Saving '${fileModel.filename}' file", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadData(title: String) {
        val fileModel = FileHelper.readFromFile(this, title)
        binding.editTitle.setText(fileModel.filename)
        binding.editFile.setText(fileModel.data)
        Toast.makeText(this, "Loading ${fileModel.filename} data", Toast.LENGTH_SHORT).show()
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