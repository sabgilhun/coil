package coil.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.Coil
import coil.request.ImageRequest
import coil.sample.databinding.ActivityCachePlaceholderTestBinding
import coil.sample.databinding.ActivityNetworkCacheTestBinding

class CachePlaceholderTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCachePlaceholderTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCachePlaceholderTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
