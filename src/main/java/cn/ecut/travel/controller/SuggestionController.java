package cn.ecut.travel.controller;

import cn.ecut.travel.common.exception.TravelException;
import cn.ecut.travel.common.help.UserHelp;
import cn.ecut.travel.entity.ReplyEntity;
import cn.ecut.travel.entity.SuggestionEntity;
import cn.ecut.travel.service.ReplyService;
import cn.ecut.travel.service.SuggestionService;
import cn.ecut.travel.vo.SuggestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/suggestion")
public class SuggestionController {

    @Autowired
    private SuggestionService suggestionService;

    @Autowired
    private ReplyService replyService;

    @Autowired
    private UserHelp userHelp;

    @PostMapping("/add")
    public Boolean addSuggestion(@RequestBody SuggestionEntity suggestionEntity){
        if (userHelp.get()==null){
            throw new TravelException("用户未登录");
        }
        suggestionEntity.setUid(userHelp.get().getId());
        suggestionEntity.setCreateTime(new Date());
        suggestionEntity.setStatus(0);
        return suggestionService.insertSuggestion(suggestionEntity);
    }

    @GetMapping("/all")
    public List<SuggestionVo> getAllSuggestion(){
        return suggestionService.selectSuggestionWithReply();
    }

    @PostMapping("/reply")
    public Boolean replySuggestion(@RequestBody ReplyEntity replyEntity){
        Boolean b1 = suggestionService.updateSuggestionStatus(replyEntity.getSid());
        replyEntity.setStatus(0);
        replyEntity.setCreateTime(new Date());
        Boolean b2 = replyService.insertReply(replyEntity);
        return b1&&b2;
    }

    @GetMapping("/uid")
    public List<SuggestionVo> getSuggestionById(){
        if (userHelp.get()==null)
            throw new TravelException("用户未登录");
        return suggestionService.selectSuggestionWithReplyById(userHelp.get().getId());
    }

    @GetMapping("/reply/read")
    public void readReply(@RequestParam("id") Integer id){
        replyService.updateReplyStatus(id);
    }

}
