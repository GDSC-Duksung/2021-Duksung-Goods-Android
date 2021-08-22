package com.example.duksunggoodsplatform_2021_android.community

import android.content.res.Resources

fun communityPostList(resources: Resources): List<CommunityPost> {
    return listOf(
        CommunityPost(
            id = 1,
            username = "김덕우",
            contents = "너무 예뻐요."
        ),
        CommunityPost(
            id = 2,
            username = "김덕우",
            contents = "너무 예뻐요."
        ),
        CommunityPost(
            id = 3,
            username = "김덕우",
            contents = "너무 예뻐요."
        )
    )
}