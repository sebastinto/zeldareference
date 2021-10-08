package com.tobianoapps.zeldareference.bosses.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tobianoapps.zeldareference.R
import com.tobianoapps.zeldareference.api.models.Bosses
import com.tobianoapps.zeldareference.databinding.BossItemBinding
import com.tobianoapps.zeldareference.games.GamesFragment

/**
 * A recycler view adapter used to display game info in [GamesFragment].
 *
 */
class BossesAdapter(
    var itemClick: ItemClick
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class BossViewHolder(
        val viewBinding: BossItemBinding
    ) : RecyclerView.ViewHolder(viewBinding.root) {
        companion object {
            fun getLayout(): Int {
                return R.layout.boss_item
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<Bosses.Data>() {
        override fun areItemsTheSame(oldItem: Bosses.Data, newItem: Bosses.Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Bosses.Data, newItem: Bosses.Data): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BossViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                BossViewHolder.getLayout(),
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
        (holder as BossViewHolder).viewBinding.apply {
            this.data = currentItem
            this.callback = itemClick
        }
    }

    /**
     * Click listener for recycler item.
     */
    class ItemClick(val clickListener: (Bosses.Data?) -> Unit) {
        fun onClick(item: Bosses.Data?) = clickListener(item)
    }
}
