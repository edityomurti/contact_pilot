package com.edityomurti.contactpilot

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Constants
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.edityomurti.contactpilot.data.Contact
import com.edityomurti.contactpilot.ui.contactDetail.ContactDetailActivity
import com.edityomurti.contactpilot.ui.contactList.ContactListAdapter
import com.edityomurti.contactpilot.ui.contactList.ContactsClickListener
import com.edityomurti.contactpilot.utils.AppConstants
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    lateinit var contactAdapter: ContactListAdapter
    lateinit var rvContacts: RecyclerView
    lateinit var swipeRefresh: SwipeRefreshLayout

    var datas: MutableList<Contact> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bindView()
        getData()
    }

    private fun bindView() {
        rvContacts = findViewById(R.id.rv_contacts)
        swipeRefresh = findViewById(R.id.swipe_refresh)

        contactAdapter = ContactListAdapter(ContactsClickListener { contact ->
            val intent = Intent(this, ContactDetailActivity::class.java)
            intent.putExtras(Bundle().also { it.putSerializable(Contact.key, contact) })
            startActivityForResult(intent, AppConstants.RequestCode.OPEN_DETAIL)
        })
        val layoutManager = LinearLayoutManager(this)
        rvContacts.adapter = contactAdapter
        rvContacts.layoutManager = layoutManager

        swipeRefresh.setOnRefreshListener {
            getData()
        }
    }

    private fun getData() {
        val fileInString: String =
            applicationContext.assets.open("data.json").bufferedReader().use { it.readText() }

        val listContactType = object : TypeToken<List<Contact>>() {}.type

        val contacts: List<Contact> = Gson().fromJson(fileInString, listContactType)

        datas.clear()
        datas.addAll(contacts)

        contactAdapter.submitList(datas)
        contactAdapter.notifyDataSetChanged()
        swipeRefresh.isRefreshing = false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            AppConstants.RequestCode.OPEN_DETAIL -> {
                if (data != null && resultCode == AppConstants.ResultCode.UPDATED_CONTACT) {
                    val updatedContact = data.extras?.getSerializable(Contact.key) as Contact? ?: return

                    val index = datas.indexOfFirst { it.id == updatedContact.id }
                    if (index == -1) return

                    datas.removeAt(index)
                    datas.add(index, updatedContact)

                    contactAdapter.notifyItemChanged(index)
                    for (data in datas) {
                        Log.i("", "name: ${data.firstName} ${data.lastName}")
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

}