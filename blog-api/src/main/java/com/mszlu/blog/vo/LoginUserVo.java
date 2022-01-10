package com.mszlu.blog.vo;

import lombok.Data;

/**
 * @author away
 */
@Data
public class LoginUserVo {
	//与页面交互

    private String id;

    private String account;

    private String nickname;

    private String avatar;
}

