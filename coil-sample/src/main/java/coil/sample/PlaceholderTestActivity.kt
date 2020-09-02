package coil.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.Coil
import coil.request.ImageRequest
import coil.sample.databinding.ActivityPlaceholderTestBinding

class PlaceholderTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlaceholderTestBinding

    val urls = listOf(
        "https://images.unsplash.com/photo-1550979068-47f8ec0c92d0?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjU4MjM5fQ",
        "https://images.unsplash.com/photo-1550947176-68e708cb2dac?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjU4MjM5fQ",
        "https://images.unsplash.com/photo-1550916825-64934687f516?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjU4MjM5fQ"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceholderTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            Coil.enqueue(
                ImageRequest.Builder(this)
                    .data(urls[0])
                    .placeholder(R.drawable.placeholder_background)
                    .target(binding.detail)
                    .build()
            )
        }
    }
}
