package com.raiden;

import android.graphics.Bitmap;
import android.graphics.Rect;

class Image {

    final int screenWidth;    // the width of the screen
    final int screenHeight;   // the height of the screen
    final Bitmap bitmap;      // the actual bitmap
    final Rect sourceRect;    // the rectangle to be drawn from the animation bitmap

    Image(Bitmap bitmap, int screenHeight, int screenWidth) {
        this.bitmap = bitmap;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.sourceRect = new Rect(0, 0, this.bitmap.getWidth(), this.bitmap.getHeight());
    }

}
