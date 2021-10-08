package com.tobianoapps.zeldareference.bosses.detail

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.JsonElement
import com.squareup.moshi.JsonAdapter
import com.tobianoapps.zeldareference.R
import com.tobianoapps.zeldareference.api.models.BossAppearances
import com.tobianoapps.zeldareference.api.models.Bosses
import com.tobianoapps.zeldareference.di.CoroutineModule.IoDispatcher
import com.tobianoapps.zeldareference.di.MoshiModule.BossAppearancesAdapterMoshi
import com.tobianoapps.zeldareference.di.ZELDA_BASE_URL
import com.tobianoapps.zeldareference.repository.ZeldaRepository
import com.tobianoapps.zeldareference.util.networking.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BossesDetailViewModel @Inject constructor(
    application: Application,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    private val repository: ZeldaRepository,
    @BossAppearancesAdapterMoshi var moshiAppearancesAdapter: JsonAdapter<BossAppearances>,
    state: SavedStateHandle
) : AndroidViewModel(application) {

    /*** APPEARANCES ***/
    private var _appearancesResponse = MutableLiveData<Resource<JsonElement>>()
    val appearancesResponse: LiveData<Resource<BossAppearances?>> = _appearancesResponse.map {
        when (it) {
            is Resource.Success -> {
                Resource.Success(moshiAppearancesAdapter.fromJson(it.data.toString()))
            }
            is Resource.Error -> {
                Resource.Error(it.message ?: application.getString(R.string.unknown_error))
            }
            is Resource.Loading -> {
                Resource.Loading()
            }
            is Resource.Canceled -> {
                Resource.Canceled(it.message ?: application.getString(R.string.unknown_error))
            }
        }
    }

    val showAppearancesLoadingView: LiveData<Boolean> = _appearancesResponse.map {
        when (it) {
            is Resource.Loading<*> -> true
            else -> false
        }
    }

    val appearancesErrorMessage: LiveData<String?> = _appearancesResponse.map {
        when (it) {
            is Resource.Error -> it.message
            else -> null
        }
    }

    init {
        // Get safe args data
        state.get<Bosses.Data>("data")?.let { data ->
            // construct url
            when (data.appearances.isNotEmpty()) {
                true -> data.appearances[0].removePrefix(ZELDA_BASE_URL)
                else -> null
            }?.let { url ->
                // fetch data
                viewModelScope.launch(ioDispatcher) {
                    _appearancesResponse.postValue(Resource.Loading())
                    _appearancesResponse.postValue(
                        repository.getZeldaDetailData(application, url)
                    )
                }
            }
        }
    }
}
