package cn.ecut.travel.dao;

import cn.ecut.travel.entity.NewsEntity;
import cn.ecut.travel.vo.NewsDetailVo;
import cn.ecut.travel.vo.NewsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsDao {

    Integer selectNewCount();

    List<NewsVo> selectAllNews(@Param("page") Integer page,@Param("size") Integer size);

    NewsDetailVo selectNewsById(@Param("newsId") Integer newsId);

    Integer insertNews(NewsEntity newsEntity);

    List<NewsVo> selectNews();

    Integer deleteNews(@Param("status") Integer status,@Param("id") Integer id);

    Integer selectNewsCountByKey(@Param("key") String key);

    List<NewsVo> selectAllNewsByKey(@Param("key") String key,@Param("page") Integer page,@Param("size") Integer size);

    List<NewsVo> selectNewsByKey(@Param("key") String key);


}
