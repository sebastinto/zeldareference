package com.tobianoapps.zeldareference.games

import android.app.Application
import androidx.lifecycle.*
import com.google.gson.JsonElement
import com.squareup.moshi.JsonAdapter
import com.tobianoapps.zeldareference.ZeldaBaseViewModel
import com.tobianoapps.zeldareference.api.models.Games
import com.tobianoapps.zeldareference.di.CoroutineModule.IoDispatcher
import com.tobianoapps.zeldareference.di.MoshiModule.GamesAdapterMoshi
import com.tobianoapps.zeldareference.di.NetworkModule.GamesApiPath
import com.tobianoapps.zeldareference.repository.ZeldaRepository
import com.tobianoapps.zeldareference.util.Extensions.unixTime
import com.tobianoapps.zeldareference.util.SortBy
import com.tobianoapps.zeldareference.util.SortDirection.ASC
import com.tobianoapps.zeldareference.util.SortDirection.DESC
import com.tobianoapps.zeldareference.util.SortField
import com.tobianoapps.zeldareference.util.SortField.DATE
import com.tobianoapps.zeldareference.util.SortField.NAME
import com.tobianoapps.zeldareference.util.networking.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(
    application: Application,
    private val repository: ZeldaRepository,
    @IoDispatcher var ioDispatcher: CoroutineDispatcher,
    @GamesAdapterMoshi private var moshiAdapter: JsonAdapter<Games>,
    @GamesApiPath path: String?
) : ZeldaBaseViewModel(application) {

    /*** API DATA ***/
    private var _response = MutableLiveData<Resource<JsonElement>>()
    private val dataList: LiveData<Games?> = _response.map {
        when (it) {
            is Resource.Success -> moshiAdapter.fromJson(it.data.toString())
            else -> null
        }
    }

    /**
     * This [LiveData] is observed in [GamesFragment]. Changes are propagated to the [GamesAdapter]
     */
    private val _sortedList = MediatorLiveData<List<Games.Data>>()
    val sortedList: LiveData<List<Games.Data>>
        get() = _sortedList

    /*** SORTING ***/
    private val sortBy = MutableLiveData(SortBy(NAME, ASC))

    /**
     * Change the values of `sortByDate` and `sortByName`. This will in turn update the value
     * of [MediatorLiveData] and propagate changes up.
     */
    fun changeSortOrder(sortField: SortField) {
        when (sortField) {
            NAME -> {
                if (sortBy.value == SortBy(NAME, ASC))
                    sortBy.value = SortBy(NAME, DESC)
                else
                    sortBy.value = SortBy(NAME, ASC)
            }
            DATE -> {
                if (sortBy.value == SortBy(DATE, ASC))
                    sortBy.value = SortBy(DATE, DESC)
                else
                    sortBy.value = SortBy(DATE, ASC)
            }
        }
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
        path?.let {
            callApi(it)
        }
    }

    /**
     * This function merges the api response data and sort booleans into a single
     * [MediatorLiveData] objects that will emit changes whenever any of the merged value change.
     */
    private fun initMediatorLiveData() {

        _sortedList.addSource(dataList) { games ->
            _sortedList.value = when (sortBy.value) {
                SortBy(NAME, ASC) -> games?.data?.sortedBy { it.name }
                SortBy(NAME, DESC) -> games?.data?.sortedByDescending { it.name }
                SortBy(DATE, ASC) -> games?.data?.sortedBy { it.releasedDate.trim().unixTime() }
                SortBy(DATE, DESC) -> games?.data?.sortedByDescending {
                    it.releasedDate.trim().unixTime()
                }
                else -> games?.data
            }
        }

        _sortedList.addSource(sortBy) { isSortedBy ->
            _sortedList.value = when (isSortedBy) {
                SortBy(NAME, ASC) -> _sortedList.value?.sortedBy { it.name }
                SortBy(NAME, DESC) -> _sortedList.value?.sortedByDescending { it.name }
                SortBy(DATE, ASC) -> _sortedList.value?.sortedBy {
                    it.releasedDate.trim().unixTime()
                }
                SortBy(DATE, DESC) -> _sortedList.value?.sortedByDescending {
                    it.releasedDate.trim().unixTime()
                }
                else -> _sortedList.value?.toList()
            }
        }
    }

    override fun callApi(path: String) {
        viewModelScope.launch(ioDispatcher) {
            _response.postValue(Resource.Loading())
            _response.postValue(repository.getZeldaData(getApplication(), path))
        }
    }
}
