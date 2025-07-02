package cn.ecut.travel.service.impl;

import cn.ecut.travel.dao.SuggestionDao;
import cn.ecut.travel.entity.SuggestionEntity;
import cn.ecut.travel.service.SuggestionService;
import cn.ecut.travel.vo.SuggestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuggestionServiceImpl implements SuggestionService {

    @Autowired
    private SuggestionDao suggestionDao;

    @Override
    public Boolean insertSuggestion(SuggestionEntity suggestionEntity) {
        return suggestionDao.insertSuggestion(suggestionEntity)>0?true:false;
    }

    @Override
    public List<SuggestionVo> selectSuggestionWithReply() {
        return suggestionDao.selectSuggestionWithReply();
    }

    @Override
    public Boolean updateSuggestionStatus(Integer id) {
        return suggestionDao.updateSuggestionStatus(id)>0?true:false;
    }

    @Override
    public List<SuggestionVo> selectSuggestionWithReplyById(Integer id) {
        return suggestionDao.selectSuggestionWithReplyById(id);
    }

}
