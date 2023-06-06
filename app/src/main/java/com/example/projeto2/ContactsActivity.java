package com.example.projeto2;

import java.util.ArrayList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsActivity extends AppCompatActivity implements ContactsAdapter.OnContactDeleteListener {

    private EditText editTextName;
    private EditText editTextEmail;
    private Button buttonAdd;
    private RecyclerView recyclerViewContacts;
    private ContactsAdapter contactsAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonAdd = findViewById(R.id.buttonAdd);
        recyclerViewContacts = findViewById(R.id.recyclerViewContacts);

        databaseHelper = new DatabaseHelper(this);

        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        List<Contact> contactList = databaseHelper.getAllContacts();
        contactsAdapter = new ContactsAdapter(contactList);
        contactsAdapter.setOnContactDeleteListener(this);
        recyclerViewContacts.setAdapter(contactsAdapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
            }
        });
    }

    private void addContact() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        Contact contact = new Contact(0, name, email);
        long rowId = databaseHelper.addContact(contact);
        if (rowId != -1) {
            Toast.makeText(this, "Contato adicionado com sucesso", Toast.LENGTH_SHORT).show();
            List<Contact> updatedContactList = databaseHelper.getAllContacts();
            contactsAdapter.setContactList(updatedContactList);
            contactsAdapter.notifyDataSetChanged();
            editTextName.setText("");
            editTextEmail.setText("");
        } else {
            Toast.makeText(this, "Falha ao adicionar contato", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onContactDelete(Contact contact) {
        int rowsDeleted = databaseHelper.deleteContact(contact.getId());
        if (rowsDeleted > 0) {
            Toast.makeText(this, "Contato deletado com sucesso", Toast.LENGTH_SHORT).show();
            List<Contact> updatedContactList = databaseHelper.getAllContacts();
            contactsAdapter.setContactList((ArrayList<Contact>) updatedContactList);
            contactsAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Falha ao deletar contato", Toast.LENGTH_SHORT).show();
        }
    }
}
