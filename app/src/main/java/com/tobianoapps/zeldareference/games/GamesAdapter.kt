package com.tobianoapps.zeldareference.games

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tobianoapps.zeldareference.R
import com.tobianoapps.zeldareference.api.models.Games
import com.tobianoapps.zeldareference.databinding.GameItemBinding


/**
 * A recycler view adapter used to display game info in [GamesFragment].
 *
 */
class GamesAdapter(
    var itemClick: ItemClick
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class GameViewHolder(
        val viewBinding: GameItemBinding
    ): RecyclerView.ViewHolder(viewBinding.root) {
        companion object {
            fun getLayout(): Int {
                return R.layout.game_item
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Games.Data>() {
        override fun areItemsTheSame(oldItem: Games.Data, newItem: Games.Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Games.Data, newItem: Games.Data): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GameViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                GameViewHolder.getLayout(),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    /**
     * Item is populated via data binding directly in the xml layout. We only bind the click
     * listener here.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = differ.currentList[position]
        (holder as GameViewHolder).viewBinding.apply {
            this.data = currentItem
            this.callback = itemClick
        }
    }

    /**
     * Click listener for recycler item.
     */
    class ItemClick(val clickListener: (Games.Data?) -> Unit) {
        fun onClick(item: Games.Data?) = clickListener(item)
    }
}
