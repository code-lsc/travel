package cn.ecut.travel.controller;

import cn.ecut.travel.common.constants.LikeConstants;
import cn.ecut.travel.common.exception.TravelException;
import cn.ecut.travel.common.help.UserHelp;
import cn.ecut.travel.common.utils.UUIDUtils;
import cn.ecut.travel.entity.CommentEntity;
import cn.ecut.travel.entity.StrategyEntity;
import cn.ecut.travel.service.CommentService;
import cn.ecut.travel.service.ILikeService;
import cn.ecut.travel.service.StrategySceneryService;
import cn.ecut.travel.service.StrategyService;
import cn.ecut.travel.vo.StrategyDetailVo;
import cn.ecut.travel.vo.StrategySettingVo;
import cn.ecut.travel.vo.StrategyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("/strategy")
public class StrategyController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private UserHelp userHelp;

    @Autowired
    private StrategyService strategyService;

    @Autowired
    private StrategySceneryService strategySceneryService;

    @Autowired
    private ILikeService iLikeService;

    @Autowired
    private CommentService commentService;

    /**
     * 发布攻略
     * @param file
     * @param title
     * @param content
     * @return
     * @throws IOException
     */
    @PostMapping("/add")
    @ResponseBody
    public Boolean uploadFile(@RequestParam("file") MultipartFile file,
                              @RequestParam("title") String title,
                              @RequestParam("content") String content,
                              @RequestParam("scenerys") List<Integer> scenerys) throws IOException {
        if (userHelp.get()==null){
            throw new TravelException("用户未登录");
        }
        if (file.isEmpty()) {
            throw new TravelException("文件不能为空");
        }
        StrategyEntity strategy = new StrategyEntity();
        strategy.setUid(userHelp.get().getId());
        strategy.setTitle(title);
        strategy.setContent(content);
        strategy.setStatus(1);
        Date date = new Date();
        strategy.setCreateTime(date);
        // 获取文件名
        String fileName = file.getOriginalFilename();
        String[] split = fileName.split("\\.");
        fileName=UUIDUtils.getUUid()+"."+split[1];
        strategy.setImageUrl("/uploads/"+fileName);
        try {
            // 确保上传目录存在
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 保存文件
            String savePath = Paths.get(uploadDir, fileName).toString();
            file.transferTo(new File(savePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        strategyService.addStrategy(strategy);
        Integer strategyId = strategyService.selectKey(userHelp.get().getId(),"/uploads/"+fileName);
        strategySceneryService.addStrategyAndSceneryLink(strategyId,scenerys);
       return null;
    }

    @ResponseBody
    @GetMapping("/all")
    public List<StrategyVo> getAllStrategy(@RequestParam(value = "sceneryName",required = false) String sceneryName,
                                           @RequestParam("page") Integer page,
                                           @RequestParam("size") Integer size){
        Integer uid=0;
        if (userHelp.get()!=null){
            uid=userHelp.get().getId();
        }
        return strategyService.selectAllStrategy(uid,sceneryName,page,size);
    }

    @ResponseBody
    @GetMapping("/count")
    public Long getStrategyCount(@RequestParam(value = "sceneryName",required = false) String sceneryName){
        return strategyService.getStrategyCount(sceneryName);
    }

    @GetMapping("/like")
    public void clickLike(@RequestParam("id") Integer id,@RequestParam("type") Integer type){
        if (userHelp.get()==null)
            throw new TravelException("用户未登录");
        if (type==LikeConstants.STRATEGY)
            iLikeService.clickLike(LikeConstants.STRATEGY,id,userHelp.get().getId());
        else
            iLikeService.clickLike(LikeConstants.COMMENT,id,userHelp.get().getId());
    }

    @GetMapping("/detail")
    @ResponseBody
    public StrategyDetailVo getStrategyDetail(@RequestParam("id") Integer id){
        Integer uid=0;
        if (userHelp.get()!=null){
            uid=userHelp.get().getId();
        }
       return strategyService.getStrategyDetailInfoById(id,uid);
    }

    @PostMapping("/comment")
    @ResponseBody
    public Boolean addComment(@RequestBody CommentEntity commentEntity){
        if (userHelp.get()==null){
            throw new TravelException("用户未登录");
        }
        commentEntity.setUid(userHelp.get().getId());
        commentEntity.setStatus(1);
        commentEntity.setCreateTime(new Date());
        return commentService.addComment(commentEntity);
    }

    @GetMapping("/comment/delete")
    @ResponseBody
    public Boolean deleteComment(@RequestParam("commentId") Integer commentId){
        if (userHelp.get()==null){
            throw new TravelException("用户未登录");
        }
        return commentService.deleteComment(commentId);
    }

    @GetMapping("/delete")
    @ResponseBody
    public Boolean deleteStrategy(@RequestParam("strategyId") Integer strategyId){
        if (userHelp.get()==null){
            throw new TravelException("用户未登录");
        }
        return strategyService.deleteStrategyById(strategyId);
    }

    @GetMapping("/search")
    @ResponseBody
    public List<StrategyVo> selectStrategyByKey(@RequestParam("key") String key){
        Integer uid=0;
        if (userHelp.get()!=null){
            uid=userHelp.get().getId();
        }
        return strategyService.selectStrategyByKey(key,uid);
    }

    @GetMapping("/setting")
    @ResponseBody
    public List<StrategySettingVo> getStrategyByUid(@RequestParam("uid") Integer uid){
        if (userHelp.get()==null || userHelp.get().getId()!=uid){
            throw new TravelException("用户未登录");
        }
        return strategyService.selectStrategyByUid(uid);
    }


}
