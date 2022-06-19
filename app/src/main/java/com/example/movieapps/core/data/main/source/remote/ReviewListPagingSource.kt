package com.example.movieapps.core.data.main.source.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.movieapps.core.data.main.source.remote.network.MainService
import com.example.movieapps.core.data.main.source.remote.response.ResultsItemReview
import retrofit2.HttpException
import java.io.IOException


private const val STARTING_PAGE_INDEX = 1
@ExperimentalPagingApi
class ReviewListPagingSource(private val service:MainService,private val movieId:String,private val apiKey:String):PagingSource<Int, ResultsItemReview>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItemReview> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getMovieReviewPaging(movieId,apiKey,position)
            val products = response.results
            val nextKey = if (products.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + 1
            }
            LoadResult.Page(
                data = products,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ResultsItemReview>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}