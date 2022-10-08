package com.edityomurti.contactpilot.ui.contactDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.edityomurti.contactpilot.R
import com.edityomurti.contactpilot.data.Contact
import com.edityomurti.contactpilot.utils.AppConstants

class ContactDetailActivity : AppCompatActivity() {
    lateinit var contact: Contact

    private lateinit var etFirstName: TextView
    private lateinit var etLastName: TextView
    private lateinit var etEmail: TextView
    private lateinit var etPhone: TextView
    private lateinit var btnCancel: View
    private lateinit var btnSave: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        val contact = intent.extras?.get(Contact.key) as Contact? ?: return

        this.contact = contact
        bindView()
    }

    private fun bindView() {
        etFirstName = findViewById(R.id.et_first_name)
        etLastName = findViewById(R.id.et_last_name)
        etEmail = findViewById(R.id.et_email)
        etPhone = findViewById(R.id.et_phone)
        btnCancel = findViewById(R.id.btn_cancel)
        btnSave = findViewById(R.id.btn_save)

        etFirstName.text = contact.firstName ?: ""
        etLastName.text = contact.lastName ?: ""
        etEmail.text = contact.email ?: ""
        etPhone.text = contact.phone ?: ""

        btnCancel.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {
            val updatedContact = Contact(
                contact.id,
                etFirstName.text.toString(),
                etLastName.text.toString(),
                etEmail.text.toString(),
                etPhone.text.toString(),
            )
            if (contact != updatedContact) {
                setResult(
                    AppConstants.ResultCode.UPDATED_CONTACT,
                    Intent().also {
                        it.putExtras(Bundle().also {
                            it.putSerializable(
                                Contact.key,
                                updatedContact
                            )
                        })
                    })
            }
            finish()
        }
    }
}