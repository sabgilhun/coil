package coil.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.Coil
import coil.request.ImageRequest
import coil.sample.databinding.ActivityNetworkCacheTestBinding
import coil.sample.databinding.ActivityPaletteTestBinding

class PaletteTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaletteTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaletteTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
