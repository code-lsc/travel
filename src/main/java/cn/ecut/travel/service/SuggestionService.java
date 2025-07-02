package cn.ecut.travel.service;

import cn.ecut.travel.entity.SuggestionEntity;
import cn.ecut.travel.vo.SuggestionVo;

import java.util.List;


public interface SuggestionService {

    Boolean insertSuggestion(SuggestionEntity suggestionEntity);

    List<SuggestionVo> selectSuggestionWithReply();

    Boolean updateSuggestionStatus(Integer id);

    List<SuggestionVo> selectSuggestionWithReplyById(Integer id);

}
