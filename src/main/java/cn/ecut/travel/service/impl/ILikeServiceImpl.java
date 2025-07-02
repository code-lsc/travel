package cn.ecut.travel.service.impl;

import cn.ecut.travel.common.constants.LikeConstants;
import cn.ecut.travel.service.ILikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

@Service
public class ILikeServiceImpl implements ILikeService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void clickLike(Integer type,Integer id,Integer uid) {
        //redis 操作比较频繁，创建一个事务管理
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                //对帖子点赞和评论的key
                String key = String.format(LikeConstants.LIKE_KEY,type,id);
                //判断当前用户是否点过赞
                Boolean member = redisTemplate.opsForSet().isMember(key,uid);
                //开启事务
                operations.multi();
                //如果用户点过赞，取消点赞
                if (member) {
                    redisTemplate.opsForSet().remove(key, uid);
                } else {
                    //点赞
                    redisTemplate.opsForSet().add(key,uid);
                }
                operations.exec();
                return null;
            }
        });
    }

    @Override
    public Boolean likeStatus(Integer type, Integer id, Integer uid) {
        String key = String.format(LikeConstants.LIKE_KEY, type, id);
        return redisTemplate.opsForSet().isMember(key,uid);
    }

    @Override
    public Long strategyLikeCount(Integer type, Integer id) {
        String key = String.format(LikeConstants.LIKE_KEY, type, id);
        return redisTemplate.opsForSet().size(key);
    }
}
