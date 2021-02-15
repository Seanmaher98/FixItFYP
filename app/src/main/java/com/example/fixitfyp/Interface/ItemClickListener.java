package com.example.fixitfyp.Interface;

import android.view.View;

public interface ItemClickListener {
    //Implemented as done so in below Youtube Video
    //Displaying Products on Home Activity using Firebase RecyclerView Android Studio, by coding cafe, https://youtu.be/745ElNRjJew
    void onClick (View view, int position, boolean isLongClick);
}
