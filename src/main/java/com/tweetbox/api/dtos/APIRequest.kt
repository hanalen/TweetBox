package com.tweetbox.api.dtos

import java.util.SortedMap

open class APIRequest(var url: String, var data: SortedMap<String, String>) {
}