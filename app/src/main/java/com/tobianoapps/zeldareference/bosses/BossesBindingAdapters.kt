package com.tobianoapps.zeldareference.bosses

import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.databinding.BindingAdapter
import com.tobianoapps.zeldareference.R
import com.tobianoapps.zeldareference.api.models.BossAppearances


@BindingAdapter("appearances")
fun inflateAppearancesViews(layout: LinearLayoutCompat, appearances: BossAppearances?) {
    val inflater = LayoutInflater.from(layout.context)
    appearances?.data?.let { app ->
        inflater.inflate(R.layout.appearance_item, layout, true).also {
            it.findViewById<AppCompatTextView>(R.id.tv_name).text =
                layout.resources.getString(R.string.appearance_with_args, app.name)
            it.findViewById<AppCompatTextView>(R.id.tv_id).text = app.id
        }
    }
}
