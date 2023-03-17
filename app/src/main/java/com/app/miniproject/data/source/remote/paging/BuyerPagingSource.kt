package com.app.miniproject.data.source.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.app.miniproject.data.source.remote.response.DataBuyerResponse
import com.app.miniproject.data.source.remote.services.ApiService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BuyerPagingSource @Inject constructor(private val apiService: ApiService) :
    PagingSource<Int, DataBuyerResponse>() {

    private var token = ""

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataBuyerResponse> {
        val position = params.key ?: INITIAL_POSITION

        return try {
            val response = apiService.getBuyerList(position, params.loadSize, token)
            val data = response.data

            LoadResult.Page(
                data = data ?: emptyList(),
                prevKey = if (position == INITIAL_POSITION) null else position,
                nextKey = if ((response.page ?: 0) >= (response.totalPage
                        ?: 0)
                ) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataBuyerResponse>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    fun setToken(token: String) {
        this.token = token
    }

    companion object {
        private const val INITIAL_POSITION = 1
    }
}