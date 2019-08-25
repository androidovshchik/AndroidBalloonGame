Skia Graphics Release Notes

This file includes a list of high level updates for each milestone release.

-----

Milestone 78

 * Added RELEASE_NOTES.txt file

 * SkDrawLooper is no longer supported in SkPaint or SkCanvas.

 * SkPath::Iter::next() now ignores its consumDegenerates bools. Those will so go away entirely

 * SkImage: new factories: DecodeToRaster, DecodeToTexture

 * SkImageFilter API refactor started:
   - Provide new factory API in include/effects/SkImageFilters
   - Consolidated enum types to use SkTileMode and SkColorChannel
   - Hide filter implementation classes
   - Hide previously public functions on SkImageFilter that were intended for internal use only

 * SkColorFilters::HSLAMatrix - new matrix color filter operating in HSLA space.

 * Modify GrBackendFormat getters to not return internal pointers. Use an enum class for GL formats.

 * Expose GrContext::dump() when SK_ENABLE_DUMP_GPU is defined.

 * Vulkan backend now supports YCbCr sampler for I420 Vulkan images that are not
   backed by external images.

 * Add SkCodec::SelectionPolicy for distinguishing between decoding a still image
   or an image sequence for a container format that has both (e.g. HEIF).

* SkImage::makeTextureImage and SkImage::MakeCrossContextFromPixmap no longer take an
  SkColorSpace parameter. It was unused.

* SkImage::reinterpretColorSpace - to reinterpret image contents in a new color space.

* Removed SkImage::MakeCrossContextFromEncoded.
