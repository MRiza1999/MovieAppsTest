package com.example.movieapps.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.movieapps.core.data.main.source.remote.response.ResultsItem
import com.example.movieapps.core.data.main.source.remote.response.ResultsItemReview
import com.example.movieapps.core.domain.main.model.*
import com.example.movieapps.core.domain.main.usecase.MainUseCase
import com.example.movieapps.core.vo.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(private val mainUseCase: MainUseCase):ViewModel() {

    private val _isLoadingGenreList = MutableLiveData<Boolean>()
    private val _dataGenreList = MutableLiveData<List<GenreListEntity?>?>()
    private val _isErrorGenreList = MutableLiveData<String>()

    private val _isLoadingMoviePopular = MutableLiveData<Boolean>()
    private val _dataMoviePopular = MutableLiveData<List<MoviePopularEntity?>?>()
    private val _isErrorMoviePopular = MutableLiveData<String>()

    private val _isLoadingMovieComingSoon = MutableLiveData<Boolean>()
    private val _dataMovieComingSoon = MutableLiveData<List<MovieComingSoonEntity?>?>()
    private val _isErrorMovieComingSoon = MutableLiveData<String>()

    private val _isLoadingMovieTopRated = MutableLiveData<Boolean>()
    private val _dataMovieTopRated = MutableLiveData<List<MovieTopRatedEntity?>?>()
    private val _isErrorMovieTopRated = MutableLiveData<String>()
    
    private val _isLoadingMovieDetail = MutableLiveData<Boolean>()
    private val _dataMovieDetail = MutableLiveData<MovieDetailEntity?>()
    private val _isErrorMovieDetail = MutableLiveData<String>()

    private val _isLoadingMovieReview = MutableLiveData<Boolean>()
    private val _dataMovieReview = MutableLiveData<List<MovieReviewEntity?>?>()
    private val _isErrorMovieReview = MutableLiveData<String>()

    val isLoadingMovieReview = _isLoadingMovieReview
    val dataMovieReview = _dataMovieReview
    val isErrorMovieReview = _isErrorMovieReview

    val isLoadingMovieDetail = _isLoadingMovieDetail
    val dataMovieDetail = _dataMovieDetail
    val isErrorMovieDetail = _isErrorMovieDetail

    val isLoadingGenreList = _isLoadingGenreList
    val dataGenreList = _dataGenreList
    val isErrorGenreList = _isErrorGenreList

    val isLoadingMoviePopular = _isLoadingMoviePopular
    val dataMoviePopular = _dataMoviePopular
    val isErrorMoviePopular = _isErrorMoviePopular

    val isLoadingMovieComingSoon = _isLoadingMovieComingSoon
    val dataMovieComingSoon = _dataMovieComingSoon
    val isErrorMovieComingSoon = _isErrorMovieComingSoon

    val isLoadingMovieTopRated = _isLoadingMovieTopRated
    val dataMovieTopRated = _dataMovieTopRated
    val isErrorMovieTopRated = _isErrorMovieTopRated

    fun getGenreList(apiKey:String){
        viewModelScope.launch {
            mainUseCase.getGenreList(apiKey)
                .onStart {
                    _isLoadingGenreList.postValue(true)
                }
                .onCompletion {
                    _isLoadingGenreList.postValue(false)
                }
                .collect { data->
                    when (data) {
                        is Resource.Loading ->
                            _isLoadingGenreList.postValue(true)
                        is Resource.Success -> {
                            _isLoadingGenreList.postValue(false)
                            _dataGenreList.postValue(data.data)
                        }
                        is Resource.Error -> {
                            _isLoadingGenreList.postValue(false)
                            _isErrorGenreList.postValue(data.message!!)
                        }
                    }
                }
        }
    }

    fun getMoviePopular(apiKey: String){
        viewModelScope.launch {
            mainUseCase.getMoviePopular(apiKey)
                .onStart {
                    _isLoadingMoviePopular.postValue(true)
                }
                .onCompletion {
                    _isLoadingMoviePopular.postValue(false)
                }
                .collect { data->
                    when (data) {
                        is Resource.Loading ->
                            _isLoadingMoviePopular.postValue(true)
                        is Resource.Success -> {
                            _isLoadingMoviePopular.postValue(false)
                            _dataMoviePopular.postValue(data.data)
                        }
                        is Resource.Error -> {
                            _isLoadingMoviePopular.postValue(false)
                            _isErrorMoviePopular.postValue(data.message!!)
                        }
                    }
                }
        }
    }

    fun getMovieComingSoon(apiKey: String){
        viewModelScope.launch {
            mainUseCase.getMovieComingSoon(apiKey)
                .onStart {
                    _isLoadingMovieComingSoon.postValue(true)
                }
                .onCompletion {
                    _isLoadingMovieComingSoon.postValue(false)
                }
                .collect { data->
                    when (data) {
                        is Resource.Loading ->
                            _isLoadingMovieComingSoon.postValue(true)
                        is Resource.Success -> {
                            _isLoadingMovieComingSoon.postValue(false)
                            _dataMovieComingSoon.postValue(data.data)
                        }
                        is Resource.Error -> {
                            _isLoadingMovieComingSoon.postValue(false)
                            _isErrorMovieComingSoon.postValue(data.message!!)
                        }
                    }
                }
        }
    }

    fun getMovieTopRated(apiKey: String){
        viewModelScope.launch {
            mainUseCase.getMovieTopRated(apiKey)
                .onStart {
                    _isLoadingMovieTopRated.postValue(true)
                }
                .onCompletion {
                    _isLoadingMovieTopRated.postValue(false)
                }
                .collect { data->
                    when (data) {
                        is Resource.Loading ->
                            _isLoadingMovieTopRated.postValue(true)
                        is Resource.Success -> {
                            _isLoadingMovieTopRated.postValue(false)
                            _dataMovieTopRated.postValue(data.data)
                        }
                        is Resource.Error -> {
                            _isLoadingMovieTopRated.postValue(false)
                            _isErrorMovieTopRated.postValue(data.message!!)
                        }
                    }
                }
        }
    }

    fun getMovieDetail(movieId:String,apiKey: String){
        viewModelScope.launch {
            mainUseCase.getMovieDetail(movieId,apiKey)
                .onStart {
                    _isLoadingMovieDetail.postValue(true)
                }
                .onCompletion {
                    _isLoadingMovieDetail.postValue(false)
                }
                .collect { data->
                    when (data) {
                        is Resource.Loading ->
                            _isLoadingMovieDetail.postValue(true)
                        is Resource.Success -> {
                            _isLoadingMovieDetail.postValue(false)
                            _dataMovieDetail.postValue(data.data)
                        }
                        is Resource.Error -> {
                            _isLoadingMovieDetail.postValue(false)
                            _isErrorMovieDetail.postValue(data.message!!)
                        }
                    }
                }
        }
    }

    fun getMovieReview(movieId:String,apiKey: String){
        viewModelScope.launch {
            mainUseCase.getMovieReview(movieId,apiKey)
                .onStart {
                    _isLoadingMovieReview.postValue(true)
                }
                .onCompletion {
                    _isLoadingMovieReview.postValue(false)
                }
                .collect { data->
                    when (data) {
                        is Resource.Loading ->
                            _isLoadingMovieReview.postValue(true)
                        is Resource.Success -> {
                            _isLoadingMovieReview.postValue(false)
                            _dataMovieReview.postValue(data.data)
                        }
                        is Resource.Error -> {
                            _isLoadingMovieReview.postValue(false)
                            _isErrorMovieReview.postValue(data.message!!)
                        }
                    }
                }
        }
    }

    fun getMovieReviewPaging(movieId:String,apiKey: String): Flow<PagingData<UiReviewModel.DetailListReview>> {
        return mainUseCase.getMovieReviewPaging(movieId, apiKey)
            .map { pagingData->pagingData.map { UiReviewModel.DetailListReview(it) } }
            .cachedIn(viewModelScope)
    }

    fun getMovieListPaging(movieId:String,apiKey: String): Flow<PagingData<UiMovieModel.ListMovie>> {
        return mainUseCase.getMovieListPaging(movieId, apiKey)
            .map { pagingData->pagingData.map { UiMovieModel.ListMovie(it) } }
            .cachedIn(viewModelScope)
    }

    sealed class UiReviewModel {
        data class DetailListReview(val dataListReview: ResultsItemReview) : UiReviewModel()
    }

    sealed class UiMovieModel {
        data class ListMovie(val dataListMovie: ResultsItem) : UiMovieModel()
    }

}