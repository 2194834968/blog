package com.lzp.MiniBlog.DAO.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author LI
 * @since 2022-07-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Favorite implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer id;

    private Integer videoId;

    private Integer userId;


}
