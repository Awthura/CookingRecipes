package com.example.cookpal2.adapter;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cookpal2.R;

public class Userholder extends RecyclerView.ViewHolder {

  TextView name, email, password;

  public Userholder(@NonNull View itemView) {
    super(itemView);
    name = itemView.findViewById(R.id.reg_name);
    email = itemView.findViewById(R.id.reg_email);
    password = itemView.findViewById(R.id.reg_pass);
  }
}
