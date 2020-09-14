package coil.sample

import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import coil.Coil
import coil.annotation.ExperimentalCoilApi
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.request.SuccessResult
import coil.sample.databinding.ActivityPaletteTestBinding
import coil.transition.CrossfadeTransition
import coil.transition.Transition
import coil.transition.TransitionTarget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PaletteTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaletteTestBinding

    @ExperimentalCoilApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaletteTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            // ImageRequest
            val request = ImageRequest.Builder(this)
                .data("https://images.unsplash.com/photo-1551005916-441029614e3b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjU4MjM5fQ")
                .allowHardware(false) // Disable hardware bitmaps.
                .transition(
                    PaletteTransition(
                        CrossfadeTransition()
                    ) { palette ->
                        palette.vibrantSwatch?.let {
                            binding.colorLabel.setTextColor(it.titleTextColor)
                            binding.colorLabel.setBackgroundColor(it.rgb)
                            binding.colorLabel.text = "Calculated Palette Data"
                        }
                    })
                .target(binding.detail)
                .build()
            Coil.imageLoader(this).enqueue(request)
        }
    }
}

@ExperimentalCoilApi
class PaletteTransition @ExperimentalCoilApi constructor(
    private val delegate: Transition?,
    private val onGenerated: (Palette) -> Unit
) : Transition {

    override suspend fun transition(target: TransitionTarget, result: ImageResult) {
        // Execute the delegate transition.
        val delegateJob = delegate?.let { delegate ->
            coroutineScope {
                launch(Dispatchers.Main.immediate) {
                    delegate.transition(target, result)
                }
            }
        }

        // Compute the palette on a background thread.
        if (result is SuccessResult) {
            val bitmap = (result.drawable as BitmapDrawable).bitmap
            val palette = withContext(Dispatchers.IO) {
                Palette.Builder(bitmap).generate()
            }
            onGenerated(palette)
        }

        delegateJob?.join()
    }
}
