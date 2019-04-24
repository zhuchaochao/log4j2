# log4j2
## Log4j2ThreadLookup
### log4j2配置类
1. 实现AbstractLookup，在类上加注解@Plugin(name = "spring" ,category = StrLookup.CATEGORY)，其中"spring"，将在xml配置中使用${spring:key}
2. YamlPropertySourceHelper,封装spring获取yml的类,根据key对应yml属性名称去查找属性值
