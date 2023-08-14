package com.example.cookpal2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cookpal2.R;
import com.example.cookpal2.models.User;
import java.util.List;

public class Useradapter extends RecyclerView.Adapter<Userholder> {

  private List<User> userList;

  public Useradapter(List<User> userList) {
    this.userList = userList;
  }

  @NonNull
  @Override
  public Userholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.login_page, parent, false);
    return new Userholder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull Userholder holder, int position) {
    User user = userList.get(position);
    holder.name.setText(user.getName());
    holder.email.setText(user.getEmail());
    holder.password.setText(user.getPassword());
  }

  @Override
  public int getItemCount() {
    return userList.size();
  }
}