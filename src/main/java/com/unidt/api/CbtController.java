package com.unidt.api;

import com.unidt.mybatis.dto.CbtUserDto;
import com.unidt.services.sader.cbt.CBTService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
@ComponentScan
public class CbtController {


       private static Logger log = LoggerFactory.getLogger(com.unidt.api.CbtController.class);

        @Autowired
        CBTService cbtService;

    /**
     * 根据用户id获取cbt列表
     * @param dto
     * @return
     */
    @RequestMapping(value="/sader/cbt/getCbtList", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String getCbtList(@RequestBody CbtUserDto dto){

        log.info("获取cbt列表："+ dto);

        return cbtService.getCbtList(dto);
    }

    /**
     *  查看单个CBT
     * @param dto
     * @return
     */
    @RequestMapping(value="/sader/cbt/getCbtInfo", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String getCbtInfo(@RequestBody CbtUserDto dto){

        log.info("查看单个CBT信息："+dto);

        return cbtService.getCbtUser(dto);
    }
    /**
     *  新增CBT
     * @param dto
     * @return
     */
    @RequestMapping(value="/sader/cbt/saveCbtInfo", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String saveCbtInfo(@RequestBody CbtUserDto dto){

        log.info("获取cbt列表："+dto);

        return cbtService.saveCbtInfo(dto);
    }

    /**
     *  修改CBT
     * @param dto
     * @return
     */
    @RequestMapping(value="/sader/cbt/editCbtInfo", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String editCbtInfo(@RequestBody CbtUserDto dto){

        log.info("修改cbt");


        return cbtService.editCbtInfo(dto);
    }
    /**
     *  根据responseid获取
     * @param dto
     * @return
     */
    @RequestMapping(value="/sader/cbt/getCbtInfoByResponse", method = RequestMethod.POST)
    @ResponseBody
    @CrossOrigin
    public String getCbtInfoByResponse(@RequestBody CbtUserDto dto){

        log.info("修改cbt");

        return cbtService.getCbtUserByResponse(dto);
    }


    @RequestMapping(value="/sader/cbt/delCbtInfoById", method = RequestMethod.GET)
    @ResponseBody
    @CrossOrigin
    public String delCbtInfoById(String  cbt_id){

        log.info("根据id删除cbt："+cbt_id);
        return cbtService.delCbtInfoById(cbt_id);
    }

}
