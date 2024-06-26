package com.swmarastro.mykkumi.data.repository

import com.swmarastro.mykkumi.data.datasource.HelloWorldDataSource
import com.swmarastro.mykkumi.domain.entity.HelloWorldVO
import com.swmarastro.mykkumi.domain.repository.HelloWorldRepository
import javax.inject.Inject

class HelloWorldRepositoryImpl @Inject constructor(
    private val helloWorldDataSource: HelloWorldDataSource
) : HelloWorldRepository {

    /*override suspend fun getHelloWorld(): GetHelloWorldResponse {
        val response = helloWorldDataSource.getHelloWorld()
        return GetHelloWorldResponse().apply {
            statusCode = response.code().toString()
            responseMessage = response.message()
            data = response.body()?.toEntity()
        }
    }*/

    override suspend fun getHelloWorld(): HelloWorldVO {
        return helloWorldDataSource.getHelloWorld().toEntity()
    }
}