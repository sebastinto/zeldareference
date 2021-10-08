package com.tobianoapps.zeldareference.bosses.main

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.JsonElement
import com.squareup.moshi.JsonAdapter
import com.tobianoapps.zeldareference.ZeldaBaseViewModel
import com.tobianoapps.zeldareference.api.models.Bosses
import com.tobianoapps.zeldareference.di.CoroutineModule.IoDispatcher
import com.tobianoapps.zeldareference.di.MoshiModule.BossesAdapterMoshi
import com.tobianoapps.zeldareference.di.NetworkModule.BossesApiPath
import com.tobianoapps.zeldareference.games.GamesAdapter
import com.tobianoapps.zeldareference.games.GamesFragment
import com.tobianoapps.zeldareference.repository.ZeldaRepository
import com.tobianoapps.zeldareference.util.SortBy
import com.tobianoapps.zeldareference.util.SortDirection
import com.tobianoapps.zeldareference.util.SortField
import com.tobianoapps.zeldareference.util.networking.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BossesMainViewModel @Inject constructor(
    application: Application,
    private val repository: ZeldaRepository,
    @IoDispatcher var ioDispatcher: CoroutineDispatcher,
    @BossesAdapterMoshi private var moshiAdapter: JsonAdapter<Bosses>,
    @BossesApiPath path: String
) : ZeldaBaseViewModel(application) {

    /*** API DATA ***/
    private var _response = MutableLiveData<Resource<JsonElement>>()
    private val dataList: LiveData<Bosses?> = _response.map {
        when (it) {
            is Resource.Success -> moshiAdapter.fromJson(it.data.toString())
            else -> null
        }
    }

    /**
     * This [LiveData] is observed in [GamesFragment]. Changes are propagated to the [GamesAdapter]
     */
    private val _sortedList = MediatorLiveData<List<Bosses.Data>>()
    val sortedList: LiveData<List<Bosses.Data>>
        get() = _sortedList

    /*** SORTING ***/
    private val sortBy = MutableLiveData(SortBy(SortField.NAME, SortDirection.ASC))

    /**
     * Change the values of `sortByDate` and `sortByName`. This will in turn update the value
     * of [MediatorLiveData] and propagate changes up.
     */
    fun changeSortOrder() {
        if (sortBy.value == SortBy(SortField.NAME, SortDirection.ASC)) sortBy.value =
            SortBy(SortField.NAME, SortDirection.DESC)
        else sortBy.value = SortBy(SortField.NAME, SortDirection.ASC)
    }


    /*** RESPONSE STATES ACTIONS ***/
    val showLoadingView: LiveData<Boolean> = _response.map {
        when (it) {
            is Resource.Loading<*> -> true
            else -> false
        }
    }

    val showEmptyView: LiveData<Boolean> = _response.map {
        when (it) {
            is Resource.Error<*> -> true
            else -> false
        }
    }

    val errorMessage: LiveData<String?> = _response.map {
        when (it) {
            is Resource.Error -> it.message
            else -> null
        }
    }

    init {
        initMediatorLiveData()
        callApi(path)
    }


    /**
     * This function merges the api response data and sort booleans into a single
     * [MediatorLiveData] objects that will emit changes whenever any of the merged value change.
     */
    private fun initMediatorLiveData() {

        _sortedList.addSource(dataList) { games ->
            _sortedList.value = when (sortBy.value) {
                SortBy(SortField.NAME, SortDirection.ASC) -> games?.data?.sortedBy { it.name }
                SortBy(
                    SortField.NAME,
                    SortDirection.DESC
                ) -> games?.data?.sortedByDescending { it.name }
                else -> games?.data
            }
        }

        _sortedList.addSource(sortBy) { isSortedBy ->
            _sortedList.value = when (isSortedBy) {
                SortBy(SortField.NAME, SortDirection.ASC) -> _sortedList.value?.sortedBy { it.name }
                SortBy(
                    SortField.NAME,
                    SortDirection.DESC
                ) -> _sortedList.value?.sortedByDescending { it.name }
                else -> _sortedList.value?.toList()
            }
        }
    }

    override fun callApi(path: String) {
        viewModelScope.launch(ioDispatcher) {
            _response.postValue(Resource.Loading())
            _response.postValue(repository.getZeldaData(getApplication(), path)
            )
        }
    }
}
