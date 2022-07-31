package com.lzp.MiniBlog.common.respond;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.lzp.MiniBlog.DAO.model.Users;
import lombok.Data;

import java.util.Date;

@Data
public class CommentRespond {
    private Integer id;

    private Integer videoId;

    private Users author;

    private String commentText;

    private Date createAt;
}
