package coil.sample

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair.create
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.annotation.ExperimentalCoilApi
import coil.memory.MemoryCache
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.request.SuccessResult
import coil.sample.databinding.ActivityCachePlaceholderTestBinding
import coil.sample.databinding.ActivityDetailBinding
import coil.size.PixelSize
import coil.transition.CrossfadeTransition
import coil.transition.Transition
import coil.transition.TransitionTarget
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.buffer
import okio.source
import org.json.JSONArray


class CachePlaceholderTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCachePlaceholderTestBinding
    private lateinit var adapter: SampleAdapter

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCachePlaceholderTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = SampleAdapter(this.lifecycle) { holder, item, key ->

            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("item", item)
                putExtra("key", key)
            }

            val thumbView: View = holder.view

            val pairThumb: androidx.core.util.Pair<View, String> = create(
                thumbView, thumbView.transitionName
            )

            val optionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairThumb)
            startActivity(intent, optionsCompat.toBundle())
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)

        lifecycle.coroutineScope.launch {
            val items = mutableListOf<String>()
            val json = JSONArray(
                this@CachePlaceholderTestActivity.assets.open(AssetType.JPG.fileName).source().buffer().readUtf8()
            )
            for (index in 0 until json.length()) {
                val image = json.getJSONObject(index)

                items += image.getJSONObject("urls").getString("regular")
            }
            adapter.replaceAll(items)
        }
    }

    private class SampleAdapter(
        val lifecycle: Lifecycle,
        var onClick: (SampleViewHolder, String, MemoryCache.Key?) -> Unit
    ) :
        RecyclerView.Adapter<SampleViewHolder>() {
        val items = mutableListOf<String>()

        fun replaceAll(items: List<String>) {
            this.items.clear()
            this.items.addAll(items)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SampleViewHolder {

            val holder = SampleViewHolder(parent.inflate(R.layout.item))
            holder.view.setOnClickListener {
                onClick.invoke(holder, holder.item!!, holder.key!!)
            }

            return holder
        }

        override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
            lifecycle.coroutineScope.launch {
                holder.bind(items[position])
            }
        }

        override fun getItemCount() = items.size
    }

    private class SampleViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var key: MemoryCache.Key? = null
        var item: String? = null

        suspend fun bind(string: String) {
            val result = Coil.execute(
                ImageRequest.Builder(view.context)
                    .data(string)
                    .target((view as ImageView))
                    .size(PixelSize(60, 60))
                    .build()
            )
            item = string
            key = (result as SuccessResult).metadata.memoryCacheKey
        }
    }
}

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @ExperimentalCoilApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.extras!!["item"] as String
        val key = intent.extras!!["key"] as MemoryCache.Key

        val request = ImageRequest.Builder(this@DetailActivity)
            .data(url)
            .allowHardware(false)
            .transition(DelayedTransition(CrossfadeTransition(500)))
            .placeholderMemoryCacheKey(key)
            .target(binding.detail)
            .build()

        lifecycle.coroutineScope.launch {
            val result = Coil.execute(request)
            binding.source.text = (result as SuccessResult).metadata.dataSource.toString()
        }
    }
}

@ExperimentalCoilApi
class DelayedTransition @ExperimentalCoilApi constructor(
    private val delegate: Transition?
) : Transition {

    override suspend fun transition(target: TransitionTarget, result: ImageResult) {
        // Execute the delegate transition.
        delegate?.let { delegate ->
            coroutineScope {
                launch(Dispatchers.Main) {
                    delay(500)
                    delegate.transition(target, result)
                }
            }
        }
    }
}
