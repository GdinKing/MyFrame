package com.king.frame.model.bean

/**
 * 菜谱实体类
 *
 * @author king
 * @date 2019-11-29 10:04
 */
class FoodMenu {
    /**
     * id : 45
     * title : 秘制红烧肉
     * tags : 家常菜;热菜;烧;煎;炖;红烧;炒锅
     * imtro : 做红烧肉的豆亲们很多，大家对红烧肉的热爱更不用我说，从名字上就能反映出来。一些高手们对红烧肉的认识更是令我佩服，单单就红烧肉的做法、菜谱都看得我是眼花缭乱，口水横流。单纯的红烧肉我平时还真没做过，再不抓紧时间做一回解解馋，不是对不起别人，而是太对不起我自己了！ 这道菜的菜名用了秘制二字来形容，当然是自己根据自己多年吃货的经验想象出来的，我不介意把自己的做法与大家共享，只为大家能同我一样，吃到不同口味的红烧肉。不同的人们根据自己的习惯都有不同的做法，味道也不尽相同。我的秘制的关键就是必须用玫瑰腐乳、冰糖和米醋这三种食材，腐乳和冰糖可以使烧出来的肉色泽红亮，米醋能解腻，令肥肉肥而不腻，此法烧制的红烧肉软糯中略带咸甜，的确回味无穷！
     * ingredients : 五花肉,500g
     * burden : 玫瑰腐乳,适量;盐,适量;八角,适量;草果,适量;香叶,适量;料酒,适量;米醋,适量;生姜,适量
     * albums : ["http://img.juhe.cn/cookbook/t/0/45_854851.jpg"]
     * steps : [{"img":"http://img.juhe.cn/cookbook/s/1/45_0824e37faf00b71e.jpg","step":"1.将五花肉煮至断生状态"},{"img":"http://img.juhe.cn/cookbook/s/1/45_b6d7329b703f6e85.jpg","step":"2.切成大小一致的块"},{"img":"http://img.juhe.cn/cookbook/s/1/45_6ee9e8dab0516333.jpg","step":"3.放在锅内煎"},{"img":"http://img.juhe.cn/cookbook/s/1/45_b9afd6d4dd81f55c.jpg","step":"4.入生姜"},{"img":"http://img.juhe.cn/cookbook/s/1/45_d0170fbe236421f9.jpg","step":"5.放八角草果各一个，香叶一片"},{"img":"http://img.juhe.cn/cookbook/s/1/45_639b12210745fa41.jpg","step":"6.放冰糖"},{"img":"http://img.juhe.cn/cookbook/s/1/45_c25e0cedd2012f45.jpg","step":"7.加料酒"},{"img":"http://img.juhe.cn/cookbook/s/1/45_eb68327980f022dd.jpg","step":"8.加玫瑰腐乳和腐乳汁及适量盐"},{"img":"http://img.juhe.cn/cookbook/s/1/45_ac17263a11507a41.jpg","step":"9.加米醋"},{"img":"http://img.juhe.cn/cookbook/s/1/45_f5489af5d12b4930.jpg","step":"10.加水继续炖"},{"img":"http://img.juhe.cn/cookbook/s/1/45_8e0cf83cb7306281.jpg","step":"11.直至肉变软糯汤汁收干即可"}]
     */

        var id: String? = null
        var title: String? = null
        var tags: String? = null
        var imtro: String? = null
        var ingredients: String? = null
        var burden: String? = null
        var albums: List<String>? = null
        var steps: List<StepsBean>? = null

        class StepsBean {
            /**
             * img : http://img.juhe.cn/cookbook/s/1/45_0824e37faf00b71e.jpg
             * step : 1.将五花肉煮至断生状态
             */

            var img: String? = null
            var step: String? = null
        }

}