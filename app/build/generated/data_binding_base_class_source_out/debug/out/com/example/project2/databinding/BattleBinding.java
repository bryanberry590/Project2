// Generated by view binder compiler. Do not edit!
package com.example.project2.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public final class BattleBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button Attack;

  @NonNull
  public final Button Defend;

  @NonNull
  public final ImageView character1;

  @NonNull
  public final ImageView character2;

  @NonNull
  public final TextView greeting;

  @NonNull
  public final TextView health;

  @NonNull
  public final TextView health2;

  @NonNull
  public final ConstraintLayout main;

  private BattleBinding(@NonNull ConstraintLayout rootView, @NonNull Button Attack,
      @NonNull Button Defend, @NonNull ImageView character1, @NonNull ImageView character2,
      @NonNull TextView greeting, @NonNull TextView health, @NonNull TextView health2,
      @NonNull ConstraintLayout main) {
    this.rootView = rootView;
    this.Attack = Attack;
    this.Defend = Defend;
    this.character1 = character1;
    this.character2 = character2;
    this.greeting = greeting;
    this.health = health;
    this.health2 = health2;
    this.main = main;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static BattleBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static BattleBinding inflate(@NonNull LayoutInflater inflater, @Nullable ViewGroup parent,
      boolean attachToParent) {
    View root = inflater.inflate(R.layout.battle, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static BattleBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.Attack;
      Button Attack = ViewBindings.findChildViewById(rootView, id);
      if (Attack == null) {
        break missingId;
      }

      id = R.id.Defend;
      Button Defend = ViewBindings.findChildViewById(rootView, id);
      if (Defend == null) {
        break missingId;
      }

      id = R.id.character1;
      ImageView character1 = ViewBindings.findChildViewById(rootView, id);
      if (character1 == null) {
        break missingId;
      }

      id = R.id.character2;
      ImageView character2 = ViewBindings.findChildViewById(rootView, id);
      if (character2 == null) {
        break missingId;
      }

      id = R.id.greeting;
      TextView greeting = ViewBindings.findChildViewById(rootView, id);
      if (greeting == null) {
        break missingId;
      }

      id = R.id.health;
      TextView health = ViewBindings.findChildViewById(rootView, id);
      if (health == null) {
        break missingId;
      }

      id = R.id.health2;
      TextView health2 = ViewBindings.findChildViewById(rootView, id);
      if (health2 == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      return new BattleBinding((ConstraintLayout) rootView, Attack, Defend, character1, character2,
          greeting, health, health2, main);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
