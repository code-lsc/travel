package cn.ecut.travel.dao;

import cn.ecut.travel.entity.SuggestionEntity;
import cn.ecut.travel.vo.SuggestionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SuggestionDao {

    Integer insertSuggestion(SuggestionEntity suggestionEntity);

    List<SuggestionVo> selectSuggestionWithReply();

    Integer updateSuggestionStatus(@Param("id") Integer id);

    List<SuggestionVo> selectSuggestionWithReplyById(@Param("id") Integer id);

}
