package coil.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.sample.databinding.ActivityMainBinding

class TestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
