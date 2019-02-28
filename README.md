# Sader
The back-stage of Sader's manege-API

> Java编程语言

# 社恐管理API

## 1.话术管理

### 1.1 查询话术列表

> URL http://ip:port/sader/messageBase/getMessageBaseList
> 请求方式 POST

参数
```JOSN
{
    "user_id": "",      //不可为空
    "message_content": "",
    "message_type": "",
    "message_no": ""
}
```

返回值

```JSON
{
    "status": 200,
    "msg": "OK",
    "data": [
        {
            "message_id": "079223c2ed5e11e8bb596c92bf5b8b6f",
            "message_no": "M24",
            "message_content": "哎呀你和我一样，我以前也经常因为一些小事在脑子里翻来覆去想，也造成我很容易焦虑哈哈，但是后来我慢慢学会了接受自己。",
            "message_type": "MESSAGE_TEXT",
            "message_status": "0",
            "message_desc": ""
        },
        {
            "message_id": "07a0ed63ed5e11e8bb596c92bf5b8b6f",
            "message_no": "M26",
            "message_content": "哈哈那我真羡慕你啊，按道理说，不那么纠结的人应该也不那么焦虑吧？我猜？",
            "message_type": "MESSAGE_TEXT",
            "message_status": "0",
            "message_desc": ""
        },
        {
            "message_id": "585e911af22411e8bb596c92bf5b8b6f",
            "message_no": "M98",
            "message_content": "abcdefg哈哈哈",
            "message_type": "MESSAGE_TEXT",
            "message_status": "0",
            "message_desc": ""
        }
    ]
}
```

### 1.2 查询单个话术

> URL http://ip:port/sader/messageBase/getMessageBaseInfo
> 请求方式 POST

参数
```JSON
{
    "user_id": "",      //不可为空
    "message_id": ""      //不可为空
}
```

返回值
```JSON
{
    "status": 200,
    "msg": "OK",
    "data": {
        "message_id": "079223c2ed5e11e8bb596c92bf5b8b6f",
        "message_no": "M24",
        "message_type": "MESSAGE_TEXT",
        "message_url": null,
        "message_content": "哎呀你和我一样，我以前也经常因为一些小事在脑子里翻来覆去想，也造成我很容易焦虑哈哈，但是后来我慢慢学会了接受自己。",
        "message_status": "0",
        "message_local": "0",
        "create_time": "2018-11-21 15:21:14",
        "create_user": null,
        "update_time": "2018-11-21 15:21:14",
        "update_user": null,
        "del_flag": "0",
        "message_desc": "",
        "paramList": []
    }
}
```

### 1.3 新增话术

> URL http://ip:port/sader/messageBase/saveMessageInfo
> 请求方式 POST

参数
```JSON
{
    "user_id": "",      //不可为空
    "message_content": "",
    "message_type": "",
    "message_url":"",
    "message_desc":"",
    "paramList": [      //话术参数，数量不限
        {
            "param_no": "",
            "param_type":"",
            "param_name":""
        },
        {
            "param_no": ""
            "param_type":"",
            "param_name":""
        }
    ]
}
```

返回值
```JSON
{
    "status": 200,
    "msg": "OK",
    "data": {}
}
```

### 1.4 修改话术

> URL http://ip:port/sader/messageBase/editMessageBaseInfo
> 请求方式 POST

参数
```JSON
{
    "user_id": "",      //不可为空
    "message_id": "",      //不可为空
    "message_content": "",
    "message_type": "",
    "message_url":"",
    "message_desc":"",
    "paramList": [      //话术参数，数量不限
        {
            "param_no": "",
            "param_type":""
        },
        {
            "param_no": "",
            "param_type":""
        }
    ]
}
```

返回值
```JSON
{
    "status": 200,
    "msg": "OK",
    "data": {}
}
```

### 1.5 删除话术

> URL http://ip:port/sader/messageBase/delMessageInfo
> 请求方式 POST

参数
```JSON
{
    "user_id": "",      //不可为空
    "message_id": ""      //不可为空
}
```

返回值
```JSON
{
    "status": 200,
    "msg": "OK",
    "data": {}
}
```

## 2.问题管理

### 2.1 查询问题列表

> URL http://ip:port/sader/questionBase/getQuestionBaseList
> 请求方式 POST

参数
```JSON
{
    "user_id":"",      //不可为空
    "question_type":"",
    "question_content":"",
    "question_no":""
}
```

返回值
```JSON
{
    "status": 200,
    "msg": "OK",
    "data": [
        {
            "question_id": "018da838f14f11e8bb596c92bf5b8b6f",
            "question_no": "Q76",
            "question_content": "完美主义的人对自己总是不满意，比别人更要求精益求精，有些人因为这一点而影响自己的发挥，备受折磨，而也有些人却能从中获益，成全自己。其实，如                                  果你认为你的完美主义倾向让自己获益比折磨多，那么大可不必改变。所以你确定想要改变一点自己的完美主义倾向吗？",
            "question_type": "QUESTION_RADIO",
            "question_status": null,
            "question_desc": "完美主义"
        },
        {
            "question_id": "01910d67f14f11e8bb596c92bf5b8b6f",
            "question_no": "Q77",
            "question_content": "看来现在完美主义带给你的好处比坏处更大，祝愿你成为更好的自己，也祝愿你时常享受并不完美的自己，你可以尝试其他方案或者挑战其他面试场景了。",
            "question_type": "QUESTION_RADIO",
            "question_status": null,
            "question_desc": "完美主义"
        },
        {
            "question_id": "019d30f8f14f11e8bb596c92bf5b8b6f",
            "question_no": "Q80",
            "question_content": "现在你能想象到或是感受到自己内心深处那个敏感柔弱的小孩吗？",
            "question_type": "QUESTION_RADIO",
            "question_status": null,
            "question_desc": "完美主义"
        }
    ]
}
```

### 2.2 查询单个问题

> URL http://ip:port/sader/questionBase/getQuestionBaseInfo
> 请求方式 POST

参数
```JSON
{
    "user_id":"",      //不可为空
    "question_id":""      //不可为空
}
```

返回值
```
{
    "status": 200,
    "msg": "OK",
    "data": {
        "question_id": "73ba854bed5811e8bb596c92bf5b8b6f",
        "question_no": "Q30",
        "question_type": "QUESTION_RADIO",
        "question_url": "extra_senior1",
        "question_content": "想要提高面试表现，CBT是如何工作的呢？我们先来看看这个。",
        "question_status": null,
        "question_path": null,
        "create_time": "2018-11-21 14:41:18",
        "create_user": null,
        "update_time": "2018-11-21 14:41:18",
        "update_user": null,
        "question_desc": "CBT方案",
        "optionList": [],
        "frontMessages": [
            {
                "question_message_id": "1598026aeed011e8bb596c92bf5b8b6f",
                "message_content": "好的，那么我们就请出CBT来帮助你了。CBT即认知行为疗法，是国际公认的缓解社交场合的焦虑的干预方法，绝大多数的CBT干预会以和心理咨询师面对面                                     的方式进行，但近年线上CBT干预被证实与线下干预具有同等的效果。",
                "message_local": 1,
                "message_local_seq": 1,
                "message_id": "0757ce28ed5e11e8bb596c92bf5b8b6f"
            }
        ],
        "backMessages": [
            {
                "question_message_id": "159ecde5eed011e8bb596c92bf5b8b6f",
                "message_content": "这位学姐的方法很值得我们学习，在仰视她之前，我们来看看她的做法。她所做的第一步，就是针对一次失败的面试经历填写自己的焦虑环。",
                "message_local": 2,
                "message_local_seq": 1,
                "message_id": "076db093ed5e11e8bb596c92bf5b8b6f"
            },
            {
                "question_message_id": "15a4b447eed011e8bb596c92bf5b8b6f",
                "message_content": "那么我们就马上开始吧，针对刚才你在VR面试中的感受，填写你的第一个焦虑环吧。",
                "message_local": 2,
                "message_local_seq": 2,
                "message_id": "07741751ed5e11e8bb596c92bf5b8b6f"
            }
        ]
    }
}
```

### 2.3 新增问题

> URL http://ip:port/sader/questionBase/saveQuestionBaseInfo
> 请求方式 POST

参数
```JSON
{
    "user_id":"",      //不可为空
    "question_type": "",      //不可为空
    "question_url": "",
    "question_content": "",
    "question_path": "",
    "question_status": "",
    "question_desc": "",
    "optionList": [      //选项列表，数量不限
        {
            "option_content":"",      //不可为空
            "option_type":""
        },
        {
            "option_content":"",      //不可为空
            "option_type":""
        }
    ],
    "frontMessages": [      //前置话术，数量不限
        {
            "message_id": "",      //不可为空
            "message_local": "",
            "message_local_seq": ""
        }
    ],
    "backMessages": [      //后置话术，数量不限
        {
            "message_id": "",      //不可为空
            "message_local": "",
            "message_local_seq": ""
        },
        {
            "message_id": "",      //不可为空
            "message_local": "",
            "message_local_seq": ""
        }
    ]
}
```

返回值
```JSON
{
    "status": 200,
    "msg": "OK",
    "data": {}
}
```

### 2.4 修改问题

> URL http://ip:port/sader/questionBase/editQuestionBaseInfo
> 请求方式 POST

参数
```JSON
{
    "user_id": "",      //不可为空
    "question_id": "",      //不可为空
    "question_type": "",
    "question_content": "",
    "question_status": "",
    "question_path": "",
    "question_desc": "",
    "optionList": [      //选项列表，数量不限
        {
            "option_id": "",      //为空代表新增，否则代表修改
            "option_content": "",
            "option_type": ""
        },
        {
            "option_content": "",
            "option_type": ""
        }
    ],
    "frontMessages": [      //前置话术，数量不限
        {
            "message_id":"",      //不可为空
            "message_local": "",
            "message_local_seq": ""
        }
    ],
    "backMessages": [      //后置话术，数量不限
        {
            "message_id":"",      //不可为空
            "message_local": "",
            "message_local_seq": ""
        }
    ]
}
```

返回值
```JSON
{
    "status": 200,
    "msg": "OK",
    "data": {}
}
```

### 2.5 删除话术

> URL http://ip:port/sader/message/editMessage
> 请求方式 POST

参数
```JSON
{
    "user_id":"",      //不可为空
    "question_id":""      //不可为空
}
```

返回值
```JSON
{
    "status": 200,
    "msg": "OK",
    "data": {}
}
```

## 3.问卷管理

### 3.1 查询问卷列表

> URL http://ip:port/sader/surveys/addSurveys
> 请求方式 POST

参数
```JSON
{
    "user_id":"",      //不可为空
    "surveys_no":"",
    "surveys_name":"",
    "surveys_desc":""
}
```

返回值
```JSON
{
    "status": 200,
    "msg": "OK",
    "data": [
        {
            "surveys_id": "5bf10655f1f011e8bb596c92bf5b8b6f",
            "surveys_no": "S8",
            "surveys_name": "完美主义问卷",
            "surveys_desc": "完美主义问卷分类",
            "create_time": "2018-11-27 10:58:47",
            "surveys_status": "1"
        }
    ]
}
```

### 3.2 查询单个问卷

> URL http://ip:port/sader/surveysBase/getSurveyBaseInfo
> 请求方式 POST

参数
```JSON
{
    "user_id":"",      //不可为空
    "surveys_id":""      //不可为空
}
```

返回值
```JSON
{
    "status": 200,
    "msg": "OK",
    "data": {
        "surveys_id": "f8cd4a1df12211e8bb596c92bf5b8b6f",
        "surveys_no": "S1",
        "surveys_name": "欢迎问卷",
        "surveys_status": "1",
        "surveys_type": "01",
        "surveys_desc": "新用户首次进入的第一个问卷",
        "del_flag": "0",
        "create_user": null,
        "create_time": "2018-11-26 10:28:34",
        "update_user": null,
        "update_time": "2018-11-26 10:28:34",
        "chapterList": [
            {
                "surveys_chapter_id": "11f84b76f12311e8bb596c92bf5b8b6f",
                "question_id": "7172737ded5811e8bb596c92bf5b8b6f",
                "question_no": "Q1",
                "question_type": "QUESTION_RADIO",
                "question_content": "Hello，很高兴认识你，我叫Sader。我想你可能需要帮助？",
                "surveys_chapter_seq": "1",
                "next_surveys": null,
                "surveys_next_chapter": "11fde71ef12311e8bb596c92bf5b8b6f",
                "surveysOptionList": [
                    {
                        "surveys_option_id": "117b9780f12411e8bb596c92bf5b8b6f",
                        "option_id": "0fac1e15ed5b11e8bb596c92bf5b8b6f",
                        "option_no": "Q1_OPT1",
                        "option_type": "",
                        "option_content": "你能够帮助我吗？",
                        "option_seq": 1,
                        "option_next_surveys": null,
                        "next_chapter_id": ""
                    }
                ]
            },
            {
                "surveys_chapter_id": "11fde71ef12311e8bb596c92bf5b8b6f",
                "question_id": "7177f954ed5811e8bb596c92bf5b8b6f",
                "question_no": "Q2",
                "question_type": "QUESTION_RADIO",
                "question_content": "没错，我最擅长处理人类的社交焦虑，比如和人相处、或者公开表现时产生的各种不舒服的感觉。",
                "surveys_chapter_seq": "2",
                "next_surveys": null,
                "surveys_next_chapter": "12031282f12311e8bb596c92bf5b8b6f",
                "surveysOptionList": [
                    {
                        "surveys_option_id": "1181cd22f12411e8bb596c92bf5b8b6f",
                        "option_id": "0fb19f76ed5b11e8bb596c92bf5b8b6f",
                        "option_no": "Q2_OPT2",
                        "option_type": "",
                        "option_content": "你有什么方法可以帮到我呢？",
                        "option_seq": 1,
                        "option_next_surveys": null,
                        "next_chapter_id": ""
                    }
                ]
            }
        ]
    }
}
```

### 3.3 新增问卷

> URL http://ip:port/sader/surveysBase/saveSurveyBaseInfo
> 请求方式 POST

参数
```JSON
{
    "user_id":"",      //不可为空
    "surveys_name": "",      //不可为空
    "surveys_status": "",
    "surveys_type": "",
    "surveys_desc": "",
    "chapterList": [      //章节列表
        {
            "question_id": "",      //不可为空
            "surveys_chapter_seq": "",      //不可为空，且为有序数字序列
            "next_surveys": "",
            "next_chapter_seq": "",      //不可为空，且数字与上面的"surveys_chapter_seq"一一对应，为0则视为终章
            "surveysOptionList": [
                {
                    "option_id": "",      //不可为空
                    "option_next_surveys": "",
                    "next_chapter_seq": ""
                }
            ]
        },
        {
                "question_id": "",      //不可为空
                "surveys_chapter_seq": "",      //不可为空，且为有序数字序列
                "next_surveys": "",
                "next_chapter_seq": "",      //不可为空，且数字与上面的"surveys_chapter_seq"一一对应，为0则视为终章
                "surveysOptionList": [
                    {
                        "option_id": "",      //不可为空
                        "option_next_surveys": "",
                        "next_chapter_seq": ""
                    }
                ]
            }
    ]
}
```

返回值
```JSON
{
    "status": 200,
    "msg": "OK",
    "data": {}
}
```

### 3.4 修改问卷

> URL http://ip:port/sader/surveysBase/editSurveyBaseInfo
> 请求方式 POST

参数
```JSON
{
    "user_id": "",      //不可为空
    "surveys_id": "",      //不可为空
    "surveys_name": "",
    "surveys_status": "",
    "surveys_type": "",
    "surveys_desc": "",
    "chapterList": [
        {
            "surveys_chapter_id": "",      //若不为空则视为修改，为空视为新增
            "question_id": "",      //不可为空
            "surveys_chapter_seq": "",      //不可为空，且为有序数字序列
            "next_surveys": null,
            "next_chapter_seq": "",      //不可为空，且数字与上面的"surveys_chapter_seq"一一对应，为0则视为终章
            "surveysOptionList": [
                {
                    "option_id": "",      //不可为空
                    "option_next_surveys": "",
                    "next_chapter_id": ""
                }
            ]
        },
        {
            "question_id": "",      //不可为空
            "surveys_chapter_seq": "",      //不可为空，且为有序数字序列
            "next_surveys": "",
            "next_chapter_seq": ""      //不可为空，且数字与上面的"surveys_chapter_seq"一一对应，为0则视为终章
        }
    ]
}
```

返回值
```JSON
{
    "status": 200,
    "msg": "OK",
    "data": {}
}
```

### 3.5 删除问卷

> URL http://ip:port/sader/surveys/editSurveys
> 请求方式 POST

参数
```JSON
{
    "user_id":"",      //不可为空
    "surveys_id":""      //不可为空
}
```

返回值
```JSON
{
    "status": 200,
    "msg": "OK",
    "data": {}
}
```