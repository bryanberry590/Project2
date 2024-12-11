// Generated by view binder compiler. Do not edit!
package com.example.project2.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.project2.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class EditCreatureBuddyBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final FrameLayout Frame;

  @NonNull
  public final EditText attackEditText;

  @NonNull
  public final TextView attackLabel;

  @NonNull
  public final ImageView creatureImageBox;

  @NonNull
  public final EditText defenseEditText;

  @NonNull
  public final TextView defenseLabel;

  @NonNull
  public final TextView editTextMessage;

  @NonNull
  public final EditText healthEditText;

  @NonNull
  public final TextView healthLabel;

  @NonNull
  public final Button submitButton;

  private EditCreatureBuddyBinding(@NonNull ConstraintLayout rootView, @NonNull FrameLayout Frame,
      @NonNull EditText attackEditText, @NonNull TextView attackLabel,
      @NonNull ImageView creatureImageBox, @NonNull EditText defenseEditText,
      @NonNull TextView defenseLabel, @NonNull TextView editTextMessage,
      @NonNull EditText healthEditText, @NonNull TextView healthLabel,
      @NonNull Button submitButton) {
    this.rootView = rootView;
    this.Frame = Frame;
    this.attackEditText = attackEditText;
    this.attackLabel = attackLabel;
    this.creatureImageBox = creatureImageBox;
    this.defenseEditText = defenseEditText;
    this.defenseLabel = defenseLabel;
    this.editTextMessage = editTextMessage;
    this.healthEditText = healthEditText;
    this.healthLabel = healthLabel;
    this.submitButton = submitButton;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static EditCreatureBuddyBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static EditCreatureBuddyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.edit_creature_buddy, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static EditCreatureBuddyBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Frame;
      FrameLayout Frame = ViewBindings.findChildViewById(rootView, id);
      if (Frame == null) {
        break missingId;
      }

      id = R.id.attackEditText;
      EditText attackEditText = ViewBindings.findChildViewById(rootView, id);
      if (attackEditText == null) {
        break missingId;
      }

      id = R.id.attackLabel;
      TextView attackLabel = ViewBindings.findChildViewById(rootView, id);
      if (attackLabel == null) {
        break missingId;
      }

      id = R.id.creatureImageBox;
      ImageView creatureImageBox = ViewBindings.findChildViewById(rootView, id);
      if (creatureImageBox == null) {
        break missingId;
      }

      id = R.id.defenseEditText;
      EditText defenseEditText = ViewBindings.findChildViewById(rootView, id);
      if (defenseEditText == null) {
        break missingId;
      }

      id = R.id.defenseLabel;
      TextView defenseLabel = ViewBindings.findChildViewById(rootView, id);
      if (defenseLabel == null) {
        break missingId;
      }

      id = R.id.editTextMessage;
      TextView editTextMessage = ViewBindings.findChildViewById(rootView, id);
      if (editTextMessage == null) {
        break missingId;
      }

      id = R.id.healthEditText;
      EditText healthEditText = ViewBindings.findChildViewById(rootView, id);
      if (healthEditText == null) {
        break missingId;
      }

      id = R.id.healthLabel;
      TextView healthLabel = ViewBindings.findChildViewById(rootView, id);
      if (healthLabel == null) {
        break missingId;
      }

      id = R.id.submitButton;
      Button submitButton = ViewBindings.findChildViewById(rootView, id);
      if (submitButton == null) {
        break missingId;
      }

      return new EditCreatureBuddyBinding((ConstraintLayout) rootView, Frame, attackEditText,
          attackLabel, creatureImageBox, defenseEditText, defenseLabel, editTextMessage,
          healthEditText, healthLabel, submitButton);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
