package cn.ecut.travel.service.impl;

import cn.ecut.travel.dao.NewsDao;
import cn.ecut.travel.entity.NewsEntity;
import cn.ecut.travel.service.NewsService;
import cn.ecut.travel.vo.NewsDetailVo;
import cn.ecut.travel.vo.NewsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsDao newsDao;

    @Override
    public Integer selectNewCount() {
        return newsDao.selectNewCount();
    }

    @Override
    public List<NewsVo> selectAllNews(Integer page, Integer size) {
        return newsDao.selectAllNews(page,size);
    }

    @Override
    public NewsDetailVo selectNewsById(Integer newsId) {
        return newsDao.selectNewsById(newsId);
    }

    @Override
    public Boolean insertNews(NewsEntity newsEntity) {
        return newsDao.insertNews(newsEntity)>0?true:false;
    }

    @Override
    public List<NewsVo> selectNews() {
        return newsDao.selectNews();
    }

    @Override
    public Boolean deleteNews(Integer status,Integer id) {
        return newsDao.deleteNews(status,id)>0?true:false;
    }

    @Override
    public Integer selectNewsCountByKey(String key) {
        return newsDao.selectNewsCountByKey(key);
    }

    @Override
    public List<NewsVo> selectAllNewsByKey(String key,Integer page, Integer size) {
        return newsDao.selectAllNewsByKey(key,page,size);
    }

    @Override
    public List<NewsVo> selectNewsByKey(String key) {
        return newsDao.selectNewsByKey(key);
    }
}
