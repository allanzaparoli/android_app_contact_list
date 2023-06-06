package com.example.projeto2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private List<Contact> contactList;
    private OnContactDeleteListener deleteListener;

    public ContactsAdapter(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
        notifyDataSetChanged();
    }

    public void setOnContactDeleteListener(OnContactDeleteListener listener) {
        this.deleteListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact contact = contactList.get(position);
        holder.textViewName.setText(contact.getName());
        holder.textViewEmail.setText(contact.getEmail());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewEmail;
        public Button buttonDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            Contact contact = contactList.get(position);
                            deleteListener.onContactDelete(contact);
                        }
                    }
                }
            });
        }
    }

    public interface OnContactDeleteListener {
        void onContactDelete(Contact contact);
    }
}
