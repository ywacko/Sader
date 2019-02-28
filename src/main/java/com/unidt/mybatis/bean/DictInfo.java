package com.unidt.mybatis.bean;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DictInfo {
    public String dict_id;
    public String dict_code;
    public String dict_name;
    public String dict_parent_code;
    public String dict_parent_name;
}
