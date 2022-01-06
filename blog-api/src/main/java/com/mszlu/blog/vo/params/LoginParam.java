package com.mszlu.blog.vo.params;

import lombok.Data;

/**
 * @author by away
 * @date 2022/1/6 13:24
 */
@Data
public class LoginParam {
    private String account;
    private String password;
    private String nickname;
}
