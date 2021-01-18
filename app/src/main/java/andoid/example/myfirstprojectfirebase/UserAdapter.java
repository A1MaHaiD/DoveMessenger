package andoid.example.myfirstprojectfirebase;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<User> user;
    private OnUserClickListener listener;

    private FirebaseStorage avatarsStorage;
    private StorageReference avatarsStorageReference;

    private static final int RC_AVATAR_PICKER = 125;

    public interface OnUserClickListener {
        void onUserClick(int position);
    }

    public void setOnUserClickListener(OnUserClickListener listener) {
        this.listener = listener;
    }

    public UserAdapter(ArrayList<User> user) {
        this.user = user;

    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        UserViewHolder userViewHolder = new UserViewHolder(view, listener);

        return userViewHolder;
        //   avatarsStorageReference = avatarsStorage.getReference().child("avatar_images");
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User currentUser = user.get(position);
        holder.avatarImageView.setImageResource(currentUser.getAvatarMockUpResource());
        holder.avatarImageView.setImageResource(currentUser.getAvatarResource());
        holder.userNameTextView.setText(currentUser.getName());

    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatarImageView;
        public TextView userNameTextView;

        public UserViewHolder(@NonNull View itemView, OnUserClickListener listener) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
            

           // Glide.with(avatarImageView.getContext())
            //        .load(RC_AVATAR_PICKER)
              //      .circleCrop()
                //    .into(avatarImageView);
            userNameTextView = itemView.findViewById(R.id.userNameTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onUserClick(position);
                            avatarImageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View viewImage) {
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent.setType("image/*");
                                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                                    startActivityForResult(Intent.createChooser(intent, "Choose an avatar"),
                                            RC_AVATAR_PICKER);
                                }

                                private void startActivityForResult(Intent choose_an_avatar, int rcAvatarPicker) {
                                }
                            });
                        }
                    }
                }
            });
        }

    }

}
