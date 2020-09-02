@file:Suppress("unused")

package coil.sample

import androidx.multidex.MultiDexApplication

class Application : MultiDexApplication() {

//    override fun newImageLoader(): ImageLoader {
//        return ImageLoader.Builder(this)
//            .availableMemoryPercentage(0.25) // Use 25% of the application's available memory.
//            .crossfade(true) // Show a short crossfade when loading images from network or disk.
//            .componentRegistry {
//                // Fetchers
//                add(VideoFrameFileFetcher(this@Application))
//                add(VideoFrameUriFetcher(this@Application))
//
//                // Decoders
//                if (SDK_INT >= 28) {
//                    add(ImageDecoderDecoder())
//                } else {
//                    add(GifDecoder())
//                }
//                add(SvgDecoder(this@Application))
//            }
//            .okHttpClient {
//                // Create a disk cache with "unlimited" size. Don't do this in production.
//                // To create the an optimized Coil disk cache, use CoilUtils.createDefaultCache(context).
//                val cacheDirectory = File(filesDir, "image_cache").apply { mkdirs() }
//                val cache = Cache(cacheDirectory, Long.MAX_VALUE)
//
//                // Rewrite the Cache-Control header to cache all responses for a year.
//                val cacheControlInterceptor = ResponseHeaderInterceptor("Cache-Control", "max-age=31536000,public")
//
//                // Don't limit concurrent network requests by host.
//                val dispatcher = Dispatcher().apply { maxRequestsPerHost = maxRequests }
//
//                // Lazily create the OkHttpClient that is used for network operations.
//                OkHttpClient.Builder()
//                    .cache(cache)
//                    .dispatcher(dispatcher)
//                    .forceTls12() // The Unsplash API requires TLS 1.2, which isn't enabled by default before API 21.
//                    .addNetworkInterceptor(cacheControlInterceptor)
//                    .build()
//            }
//            .apply {
//                // Enable logging to the standard Android log if this is a debug build.
//                if (BuildConfig.DEBUG) {
//                    logger(DebugLogger(Log.VERBOSE))
//                }
//            }
//            .build()
//    }
}
