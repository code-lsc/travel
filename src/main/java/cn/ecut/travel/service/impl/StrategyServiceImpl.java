package cn.ecut.travel.service.impl;

import cn.ecut.travel.common.constants.LikeConstants;
import cn.ecut.travel.dao.CommentDao;
import cn.ecut.travel.dao.StrategyDao;
import cn.ecut.travel.entity.StrategyEntity;
import cn.ecut.travel.service.ILikeService;
import cn.ecut.travel.service.StrategyService;
import cn.ecut.travel.vo.CommentVo;
import cn.ecut.travel.vo.StrategyDetailVo;
import cn.ecut.travel.vo.StrategySettingVo;
import cn.ecut.travel.vo.StrategyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StrategyServiceImpl implements StrategyService {

    @Autowired
    private StrategyDao strategyDao;

    @Autowired
    private ILikeService iLikeService;

    @Autowired
    private CommentDao commentDao;

    @Override
    public Boolean addStrategy(StrategyEntity strategy) {
        return strategyDao.insertStrategy(strategy)>0?true:false;
    }

    @Override
    public Integer selectKey(Integer uid, String imageUrl) {
        return strategyDao.selectKey(uid, imageUrl);
    }

    @Override
    public List<StrategyVo> selectAllStrategy(Integer uid,String sceneryName,Integer page, Integer size) {
        List<StrategyVo> strategyVos = strategyDao.selectStrategyAllOrBySceneryName(sceneryName,page,size);
        for (StrategyVo strategyVo : strategyVos) {
            strategyVo.setIsLike(iLikeService.likeStatus(LikeConstants.STRATEGY,strategyVo.getId(),uid));
            strategyVo.setLikeNumber(iLikeService.strategyLikeCount(LikeConstants.STRATEGY,strategyVo.getId()));
        }
        return strategyVos;
    }

    @Override
    public Long getStrategyCount(String sceneryName) {
        return strategyDao.getStrategyCount(sceneryName);
    }

    @Override
    public StrategyDetailVo getStrategyDetailInfoById(Integer strategyId,Integer uid) {
        StrategyDetailVo strategyDetailVo = strategyDao.selectStrategyById(strategyId);
        strategyDetailVo.setLikeNumber(iLikeService.strategyLikeCount(LikeConstants.STRATEGY,strategyId));
        strategyDetailVo.setIsLike(iLikeService.likeStatus(LikeConstants.STRATEGY,strategyId,uid));
        //以及评论
        List<CommentVo> comment1 = commentDao.selectCommentByEntityId(0, strategyId);
        for (CommentVo commentVo : comment1) {
            commentVo.setLikeNumber(iLikeService.strategyLikeCount(LikeConstants.COMMENT,commentVo.getId()));
            commentVo.setIsLike(iLikeService.likeStatus(LikeConstants.COMMENT,commentVo.getId(),uid));
            List<CommentVo> comment2 = commentDao.selectCommentByEntityId(1, commentVo.getId());
            for (CommentVo vo : comment2) {
                vo.setLikeNumber(iLikeService.strategyLikeCount(LikeConstants.COMMENT,vo.getId()));
                vo.setIsLike(iLikeService.likeStatus(LikeConstants.COMMENT,vo.getId(),uid));
            }
            commentVo.setReply(comment2);
        }
        strategyDetailVo.setComment(comment1);
        return strategyDetailVo;
    }

    @Override
    public Boolean deleteStrategyById(Integer strategyId) {
        return strategyDao.deleteStrategyById(strategyId)>0?true:false;
    }

    @Override
    public List<StrategyVo> selectStrategyByKey(String key,Integer uid) {
        List<StrategyVo> strategyVos = strategyDao.selectStrategyByKey(key);
        for (StrategyVo strategyVo : strategyVos) {
            strategyVo.setIsLike(iLikeService.likeStatus(LikeConstants.STRATEGY,strategyVo.getId(),uid));
            strategyVo.setLikeNumber(iLikeService.strategyLikeCount(LikeConstants.STRATEGY,strategyVo.getId()));
        }
        return strategyVos;
    }

    @Override
    public List<StrategySettingVo> selectStrategyByUid(Integer uid) {
        return strategyDao.selectStrategyByUid(uid);
    }


}
