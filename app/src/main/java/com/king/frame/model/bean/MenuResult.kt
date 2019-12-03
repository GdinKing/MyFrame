package com.king.frame.model.bean

import com.squareup.moshi.JsonClass


/**
 * @author king
 * @date 2019-12-02 16:34
 */
@JsonClass(generateAdapter = true)
data class MenuResult(val resultcode: String,
                      val reason: String,
                      val result: ResultBean,
                      val error_code: Int)

@JsonClass(generateAdapter = true)
data class ResultBean(val totalNum: String,
                      val pn: String,
                      val rn: String,
                      val data: List<FoodMenu>)

@JsonClass(generateAdapter = true)
data class FoodMenu(val id: String,
                    val title: String,
                    val tags: String,
                    val imtro: String,
                    val ingredients: String,
                    val burden: String,
                    val albums: List<String>,
                    val steps: List<StepsBean>)

@JsonClass(generateAdapter = true)
data class StepsBean(val img: String,
                     val step: String)
