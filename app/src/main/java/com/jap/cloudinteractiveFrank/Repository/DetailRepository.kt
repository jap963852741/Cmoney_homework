package com.jap.cloudinteractiveFrank.Repository

import com.jap.cloudinteractiveFrank.Repository.network.DetailDataSource


class DetailRepository(val dataSource: DetailDataSource) {

    fun detail(): DetailDataSource {
        return dataSource
    }

}