package cn.ecut.travel.service;

import cn.ecut.travel.entity.NewsEntity;
import cn.ecut.travel.vo.NewsDetailVo;
import cn.ecut.travel.vo.NewsVo;

import java.util.List;

public interface NewsService {

    Integer selectNewCount();

    List<NewsVo> selectAllNews(Integer page, Integer size);

    NewsDetailVo selectNewsById(Integer newsId);

    Boolean insertNews(NewsEntity newsEntity);

    List<NewsVo> selectNews();

    Boolean deleteNews(Integer status,Integer id);

    Integer selectNewsCountByKey(String key);

    List<NewsVo> selectAllNewsByKey(String key,Integer page, Integer size);

    List<NewsVo> selectNewsByKey(String key);
}
