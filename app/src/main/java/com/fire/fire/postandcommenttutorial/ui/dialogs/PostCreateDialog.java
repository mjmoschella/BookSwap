package com.fire.fire.postandcommenttutorial.ui.dialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fire.fire.postandcommenttutorial.R;
import com.fire.fire.postandcommenttutorial.models.Post;
import com.fire.fire.postandcommenttutorial.models.User;
import com.fire.fire.postandcommenttutorial.utils.Constants;
import com.fire.fire.postandcommenttutorial.utils.FirebaseUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;

/**
 * This is the class that is used to create the posts from the info the user enters in.
 */

public class PostCreateDialog extends DialogFragment implements View.OnClickListener {
    private static final int RC_PHOTO_PICKER = 1;
    private Post mPost;
    private ProgressDialog mProgressDialog;
    private Uri mSelectedUri;
    private ImageView mPostDisplay;
    private View mRootView;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mPost = new Post();
        mProgressDialog = new ProgressDialog(getContext());

        mRootView = getActivity().getLayoutInflater().inflate(R.layout.create_post_dialog, null);
        mPostDisplay = (ImageView) mRootView.findViewById(R.id.post_dialog_display);
        mRootView.findViewById(R.id.post_dialog_send_imageview).setOnClickListener(this);
        mRootView.findViewById(R.id.post_dialog_select_imageview).setOnClickListener(this);
        builder.setView(mRootView);
        return builder.create();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.post_dialog_send_imageview:
                sendPost();
                break;
            case R.id.post_dialog_select_imageview:
                selectImage();
                break;
        }
    }


    private void sendPost() {
        mProgressDialog.setMessage("Sending post...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();

        FirebaseUtils.getUserRef(FirebaseUtils.getCurrentUser().getEmail().replace(".", ","))
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);

                        if (user.getActive() == true) {

                        final String postId = FirebaseUtils.getUid();
                        TextView postDialogTextView = (TextView) mRootView.findViewById(R.id.post_dialog_edittext);
                            TextView titleDialogTextView = (TextView) mRootView.findViewById(R.id.post_dialog_edittitle);
                            TextView authorDialogTextView = (TextView) mRootView.findViewById(R.id.post_dialog_editauthor);
                            TextView courseDialogTextView = (TextView) mRootView.findViewById(R.id.post_dialog_editcourse);
                            TextView priceDialogTextView = (TextView) mRootView.findViewById(R.id.post_dialog_editprice);

                        String text = postDialogTextView.getText().toString();
                            String text1 = titleDialogTextView.getText().toString();
                            String text2 = authorDialogTextView.getText().toString();
                            String text3 = courseDialogTextView.getText().toString();
                            String text4 = priceDialogTextView.getText().toString();



                            mPost.setUser(user);
                            mPost.setNumComments(0);
                            mPost.setNumLikes(0);
                            mPost.setTimeCreated(System.currentTimeMillis());
                            mPost.setPostId(postId);
                            mPost.setPostText(text);
                            mPost.setBookTitle(text1);
                            mPost.setBookAuthor(text2);
                            mPost.setBookCourse(text3);
                            mPost.setBookPrice(text4);



                            if (mSelectedUri != null) {
                                FirebaseUtils.getImageSRef()
                                        .child(mSelectedUri.getLastPathSegment())
                                        .putFile(mSelectedUri)
                                        .addOnSuccessListener(getActivity(),
                                                new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                    @Override
                                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                        String url = Constants.POST_IMAGES + "/" + mSelectedUri.getLastPathSegment();
                                                        mPost.setPostImageUrl(url);
                                                        addToMyPostList(postId);
                                                    }
                                                });
                            } else {
                                addToMyPostList(postId);
                            }
                        } else {

                            mProgressDialog.dismiss();

                            Toast.makeText(getActivity(), "Your account has been suspended." +
                                    "\nYou cannot use this feature.", Toast.LENGTH_LONG).show();

                        }

                    }


                        @Override
                        public void onCancelled (DatabaseError databaseError){
                            mProgressDialog.dismiss();
                        }

                });
    }

    private void addToMyPostList(String postId) {
        FirebaseUtils.getPostRef().child(postId)
                .setValue(mPost);
        FirebaseUtils.getMyPostRef().child(postId).setValue(true)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        mProgressDialog.dismiss();
                        dismiss();
                    }
                });

        FirebaseUtils.addToMyRecord(Constants.POST_KEY, postId);
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER) {
            if (resultCode == RESULT_OK) {
                mSelectedUri = data.getData();
                mPostDisplay.setImageURI(mSelectedUri);
            }
        }
    }
}
