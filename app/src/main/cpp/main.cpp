#include <jni.h>
#include <cmath>
#include <android/bitmap.h>
#include <include/core/SkSurface.h>
#include <include/core/SkCanvas.h>
#include <include/core/SkBitmap.h>

extern "C"

JNIEXPORT void JNICALL
Java_defpackage_DrawActivity_draw_on_bitmap(JNIEnv *env, jobject, jobject dstBitmap) {
    // Grab the dst bitmap info and pixels
    AndroidBitmapInfo dstInfo;
    void *dstPixels;
    AndroidBitmap_getInfo(env, dstBitmap, &dstInfo);
    AndroidBitmap_lockPixels(env, dstBitmap, &dstPixels);

    SkImageInfo info = SkImageInfo::MakeN32Premul(dstInfo.width, dstInfo.height);

    // Create a surface from the given bitmap
    sk_sp<SkSurface> surface(SkSurface::MakeRasterDirect(info, dstPixels, dstInfo.stride));
    SkCanvas *canvas = surface->getCanvas();

    // Draw something "interesting"

    // Clear the canvas with a white color
    canvas->drawColor(SK_ColorWHITE);

    // Setup a SkPaint for drawing our text
    SkPaint paint;
    paint.setColor(SK_ColorBLACK); // This is a solid black color for our text
    paint.setAntiAlias(true); // We turn on anti-aliasing so that the text to looks good.

    // Adapt the SkPaint for drawing blue lines
    paint.setAntiAlias(false); // Turning off anti-aliasing speeds up the line drawing
    paint.setColor(0xFF0000FF); // This is a solid blue color for our lines
    paint.setStrokeWidth(SkIntToScalar(2)); // This makes the lines have a thickness of 2 pixels

    canvas->drawLine(0, 0,   // first endpoint
                     800, 800, // second endpoint
                     paint);

    // Unlock the dst's pixels
    AndroidBitmap_unlockPixels(env, dstBitmap);
}

extern "C"

JNIEXPORT void JNICALL
Java_defpackage_DrawActivity_draw_on_bitmap(JNIEnv *env, jobject, jobject dstBitmap) {
    // Grab the dst bitmap info and pixels
    AndroidBitmapInfo dstInfo;
    void *dstPixels;
    AndroidBitmap_getInfo(env, dstBitmap, &dstInfo);
    AndroidBitmap_lockPixels(env, dstBitmap, &dstPixels);

    SkImageInfo info = SkImageInfo::MakeN32Premul(dstInfo.width, dstInfo.height);

    // Create a surface from the given bitmap
    sk_sp<SkSurface> surface(SkSurface::MakeRasterDirect(info, dstPixels, dstInfo.stride));
    SkCanvas *canvas = surface->getCanvas();

    // Draw something "interesting"

    // Clear the canvas with a white color
    canvas->drawColor(SK_ColorWHITE);

    // Setup a SkPaint for drawing our text
    SkPaint paint;
    paint.setColor(SK_ColorBLACK); // This is a solid black color for our text
    paint.setAntiAlias(true); // We turn on anti-aliasing so that the text to looks good.

    // Adapt the SkPaint for drawing blue lines
    paint.setAntiAlias(false); // Turning off anti-aliasing speeds up the line drawing
    paint.setColor(0xFF0000FF); // This is a solid blue color for our lines
    paint.setStrokeWidth(SkIntToScalar(2)); // This makes the lines have a thickness of 2 pixels

    canvas->drawLine(0, 0,   // first endpoint
                     800, 800, // second endpoint
                     paint);

    // Unlock the dst's pixels
    AndroidBitmap_unlockPixels(env, dstBitmap);
}