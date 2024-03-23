package com.example.tbilisi_parking_final_exm.data.data_source.transactions

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.transactions.toDomain
import com.example.tbilisi_parking_final_exm.data.service.transactions.TransactionsService
import com.example.tbilisi_parking_final_exm.domain.model.transactions.GetTransaction

class TransactionsPagingSource(
    private val service: TransactionsService,
    private val userId: Int,
    private val fromDate: String,
    private val toDate: String,
    private val pageSize: Int
) : PagingSource<Int, GetTransaction.GetTransactionItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetTransaction.GetTransactionItem> {
        val page = params.key ?: 0
        val resource = HandleResponse().safeApiCallWithoutFlow {
            service.getTransactions(
                userId = userId,
                fromDate = fromDate,
                toDate = toDate,
                page = page,
                pageSize = pageSize
            )
        }

        return try {
            resource as Resource.Success

            LoadResult.Page(
                data = resource.data.toDomain().getContent,//response.body()?.toDomain()?.getContent ?: emptyList(),
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (resource.data.contentDto.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GetTransaction.GetTransactionItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage =
                state.closestPageToPosition(anchorPosition)

            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}