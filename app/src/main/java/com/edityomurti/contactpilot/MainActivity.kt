package com.edityomurti.contactpilot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edityomurti.contactpilot.data.Contact
import com.edityomurti.contactpilot.ui.contactList.ContactListAdapter
import com.edityomurti.contactpilot.ui.contactList.ContactsClickListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    lateinit var rvContacts: RecyclerView
    lateinit var contactAdapter: ContactListAdapter

    var datas: MutableList<Contact> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindView()
        getData()
    }

    private fun bindView() {
        rvContacts = findViewById(R.id.rv_contacts)

        contactAdapter = ContactListAdapter(ContactsClickListener {  })
        val layoutManager = LinearLayoutManager(this)
        rvContacts.adapter = contactAdapter
        rvContacts.layoutManager = layoutManager
    }

    private fun getData() {
        val fileInString: String =
            applicationContext.assets.open("data.json").bufferedReader().use { it.readText() }

        val listContactType = object : TypeToken<List<Contact>>() {}.type

        val contacts: List<Contact> = Gson().fromJson(fileInString, listContactType)

        datas.addAll(contacts)

        contactAdapter.submitList(datas)
    }
}