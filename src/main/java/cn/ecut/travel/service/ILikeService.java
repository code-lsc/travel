package cn.ecut.travel.service;

public interface ILikeService {

    /**
     * 点赞
     * @param type 点赞类型，0是攻略，1是评论
     */
    void clickLike(Integer type,Integer id,Integer uid);

    /**
     * 判断用户是否点赞
     * @param type
     * @param id
     * @param uid
     * @return
     */
    Boolean likeStatus(Integer type,Integer id,Integer uid);

    /**
     * 获取攻略点赞数量
     * @param type
     * @param id
     * @return
     */
    Long strategyLikeCount(Integer type,Integer id);
}
