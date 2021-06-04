package com.moonlit.mybatisgenerator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

/**
 * 動態生成數據庫數據代碼
 * @author Joshua
 */
@SpringBootApplication
public class MybatisGeneratorApplication {

    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        /* 文件生成路徑 */
        gc.setOutputDir(scanner("文件輸出位置(如：F://JavaProject/mybatis)"));
        /* 作者名稱 */
        gc.setAuthor(scanner("作者名稱"));
        gc.setFileOverride(true);
        gc.setActiveRecord(true);

        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/"+scanner("數據庫")+"?useUnicode=true&characterEncoding=UTF-8&serverTimezone=GMT");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(scanner("數據庫用戶名"));
        dsc.setPassword(scanner("數據庫密碼"));
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent(scanner("項目包配置"));
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 表名生成策略(下划线转驼峰命名)
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 列名生成策略(下划线转驼峰命名)
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 是否启动Lombok配置
        strategy.setEntityLombokModel(true);
        // 是否启动REST风格配置
        strategy.setRestControllerStyle(true);
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        mpg.setStrategy(strategy);

        // 执行生成
        mpg.execute();
    }
}