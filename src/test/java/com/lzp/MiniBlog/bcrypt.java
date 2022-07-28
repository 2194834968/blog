package com.lzp.MiniBlog;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.platform.commons.util.StringUtils;
import org.mindrot.jbcrypt.BCrypt;

import java.util.ArrayList;
import java.util.Scanner;

public class bcrypt {

    public static void main(String[] args) {
        String temp = BCrypt.hashpw("123456", BCrypt.gensalt(4));
        System.out.println(temp);
        System.out.println(BCrypt.checkpw("123456", temp));
    }

}

