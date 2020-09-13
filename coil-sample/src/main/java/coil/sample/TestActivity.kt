package coil.sample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.sample.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            startActivity(Intent(this, PlaceholderTestActivity::class.java))
        }

        binding.button2.setOnClickListener {
            startActivity(Intent(this, NetworkCacheTestActivity::class.java))
        }

        binding.button3.setOnClickListener {
            startActivity(Intent(this, PaletteTestActivity::class.java))
        }

        binding.button4.setOnClickListener {
            startActivity(Intent(this, CachePlaceholderTestActivity::class.java))
        }
    }
}
