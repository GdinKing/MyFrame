package com.king.frame.bean

/**
 * 书籍实体类
 *
 * @author king
 * @date 2019-11-27 11:11
 */
class Book {

    var count: Int = 0
    var start: Int = 0
    var total: Int = 0
    var books: List<BooksBean>? = null

    class BooksBean {

        var rating: RatingBean? = null
        var subtitle: String? = null
        var pubdate: String? = null
        var origin_title: String? = null
        var image: String? = null
        var binding: String? = null
        var catalog: String? = null
        var pages: String? = null
        var images: ImagesBean? = null
        var alt: String? = null
        var id: String? = null
        var publisher: String? = null
        var isbn10: String? = null
        var isbn13: String? = null
        var title: String? = null
        var url: String? = null
        var alt_title: String? = null
        var author_intro: String? = null
        var summary: String? = null
        var series: SeriesBean? = null
        var price: String? = null
        var author: List<String>? = null
        var tags: List<TagsBean>? = null
        var translator: List<*>? = null

        class RatingBean {
            /**
             * max : 10
             * numRaters : 3676
             * average : 8.5
             * min : 0
             */

            var max: Int = 0
            var numRaters: Int = 0
            var average: String? = null
            var min: Int = 0
        }

        class ImagesBean {
            /**
             * small : https://img1.doubanio.com/spic/s10069398.jpg
             * large : https://img1.doubanio.com/lpic/s10069398.jpg
             * medium : https://img1.doubanio.com/mpic/s10069398.jpg
             */

            var small: String? = null
            var large: String? = null
            var medium: String? = null
        }

        class SeriesBean {
            /**
             * id : 4279
             * title : 明代四大奇书
             */

            var id: String? = null
            var title: String? = null
        }

        class TagsBean {
            /**
             * count : 1523
             * name : 金瓶梅
             * title : 金瓶梅
             */

            var count: Int = 0
            var name: String? = null
            var title: String? = null
        }
    }
}
