package com.unidt.api.manage;

import com.unidt.mybatis.dto.MessageBaseDto;
import com.unidt.services.sader.message.MessageBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

/**
 * 基础话术管理
 */
@RestController
@EnableAutoConfiguration
@ComponentScan
public class MessageBaseController {

    private static Logger log = LoggerFactory.getLogger(com.unidt.api.manage.MessageBaseController.class);

    @Autowired
    MessageBaseService messageBaseService;

    /**
     * 根据输入获取话术列表
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sader/messageBase/getMessageBaseList", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String getMessageBaseList(@RequestBody MessageBaseDto dto) {
        log.info("根据输入获取话术列表");
        return messageBaseService.getMesBaseList(dto);
    }

    /**
     * 根据id获取单条话术
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sader/messageBase/getMessageBaseInfo", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String getMessageBaseInfo(@RequestBody MessageBaseDto dto) {
        log.info("根据id获取单条话术");
        return messageBaseService.getMesBaseInfo(dto);
    }

    /**
     * 根据id修改单条话术
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sader/messageBase/editMessageBaseInfo", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String editMessageBaseInfo(@RequestBody MessageBaseDto dto) {
        log.info("根据id修改单条话术");
        return messageBaseService.editMesBaseInfo(dto);
    }

    /**
     * 根据输入增加单条话术
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sader/messageBase/saveMessageInfo", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String saveMessageBaseInfo(@RequestBody MessageBaseDto dto) {
        log.info("根据输入增加单条话术");
        return messageBaseService.saveMesBaseInfo(dto);
    }

    /**
     * 根据id删除单条话术
     * @param dto
     * @return
     */
    @RequestMapping(value = "/sader/messageBase/delMessageInfo", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String delMessageBaseInfo(@RequestBody MessageBaseDto dto) {
        log.info("根据id删除单条话术");
        return messageBaseService.delMesBaseInfo(dto);
    }

}
