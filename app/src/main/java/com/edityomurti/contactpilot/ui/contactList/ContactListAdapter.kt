package com.edityomurti.contactpilot.ui.contactList

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.edityomurti.contactpilot.R
import com.edityomurti.contactpilot.data.Contact

class ContactListAdapter(val contactsClickListener: ContactsClickListener) :
    ListAdapter<Contact, ContactViewHolder>(ContactListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_contacts, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)

        holder.tvName.text = "${contact.firstName ?: ""} ${contact.lastName ?: ""}"

        holder.itemView.isClickable = true
        holder.itemView.setOnClickListener {
            Log.i("", "CLICKED!!")
            contactsClickListener.onClick(contact)
        }
    }
}

class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvName = itemView.findViewById<TextView>(R.id.tv_name);
}

private class ContactListDiffCallback : DiffUtil.ItemCallback<Contact>() {
    override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
        return oldItem == newItem
    }
}

class ContactsClickListener(val clickListener: (contact: Contact) -> Unit) {
    fun onClick(contact: Contact) = clickListener(contact)
}