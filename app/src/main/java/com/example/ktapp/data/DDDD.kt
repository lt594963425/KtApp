package com.example.ktapp.data

import java.io.Serializable

/**
 *
 * @ProjectName: KtApp
 * @Package: com.example.ktapp
 * @ClassName: DDDD
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/4/27 16:44
 * @UpdateUser: LiuTao
 */
data class TestBean(
    val categorys: ArrayList<Category>,
    val code: String,
    val login_status: Int,
    val mall_uid: String,
    val mall_user: String,
    val msg: String,
    val owner: Int,
    val push_alias: String,
    val push_tags: String,
    val sys_time: Int,
    val topadv: Topadv
) : java.io.Serializable {
    data class Category(
        val cateid: Int,
        val children: ArrayList<Children>,
        val name: String
    ) : java.io.Serializable {
        data class Children(
            val id: String,
            val name: String,
            val photo: String,
            val type: String
        ) : java.io.Serializable
    }

    data class Topadv(
        val `param`: Param,
        val advid: String,
        val src: String,
        val title: String
    ) : Serializable {
        data class Param(
            val `param`: String,
            val mode: String
        )
    }
}
