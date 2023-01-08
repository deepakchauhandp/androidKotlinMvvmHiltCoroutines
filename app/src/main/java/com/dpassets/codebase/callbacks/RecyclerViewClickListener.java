package com.dpassets.codebase.callbacks;

import android.view.View;

public interface RecyclerViewClickListener {

    void onClick(View v, int position);

    void onLongClick(View v, int position);
}

