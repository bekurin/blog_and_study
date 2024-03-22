package com.example.countingdailyvisitors

object Constant {
    object V1 {
        const val PAGE_VISIT_KEY = "page:v1:visitor"
        const val UNIQUE_VISIT_KEY = "unique:v1:visitor"
    }

    object V2 {
        val PAGE_VISIT_KEY = "page:v2:visitor".toByteArray()
        val UNIQUE_VISIT_KEY = "unique:v2:visitor".toByteArray()
    }

    object V3 {
        val PAGE_VISIT_KEY = "page:v3:visitor".toByteArray()
        val UNIQUE_VISIT_KEY = "unique:v3:visitor".toByteArray()
        val UNIQUE_VISIT_EVENT = "unique:v3:visitor:event".toByteArray()
    }
}
