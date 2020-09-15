package coil.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import coil.Coil
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.sample.databinding.ActivityCachePlaceholderTestBinding
import coil.size.PixelSize
import coil.size.Size
import coil.size.SizeResolver
import kotlinx.coroutines.launch

class CachePlaceholderTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCachePlaceholderTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCachePlaceholderTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            lifecycle.coroutineScope.launch {
                val request = ImageRequest.Builder(this@CachePlaceholderTestActivity)
                    .data("https://images.unsplash.com/photo-1551005916-441029614e3b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjU4MjM5fQ")
                    .diskCachePolicy(CachePolicy.DISABLED)
                    .size(SampledSizeResolver(binding.detail, 32))
                    .build()

                val result = (Coil.execute(request) as SuccessResult)
                val memoryKey = result.metadata.memoryCacheKey

                val realDataRequest = ImageRequest.Builder(this@CachePlaceholderTestActivity)
                    .data("https://images.unsplash.com/photo-1551005916-441029614e3b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjU4MjM5fQ")
                    .placeholderMemoryCacheKey(memoryKey)
                    .diskCachePolicy(CachePolicy.DISABLED)
                    .memoryCachePolicy(CachePolicy.DISABLED)
                    .crossfade(250)
                    .target(binding.detail)
                    .build()

                Coil.execute(realDataRequest)
            }
        }
    }

    class SampledSizeResolver(private val view: View, private val sampleFactor: Int) : SizeResolver {
        override suspend fun size(): Size {
            return PixelSize(view.width / sampleFactor, view.height / sampleFactor)
        }
    }
}
