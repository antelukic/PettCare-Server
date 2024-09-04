package com.pettcare.comments.responses

import com.pettcare.comments.models.Comment

data class GetCommentsResponse(
    val items: List<Comment>
)
