# <div align="center">![DRoc Identifier](https://github.com/ponderyao/onlineImage/raw/main/readme-title/droc-identifier-title.png)</div>

**<div align="center">A dynamic and configurable distributed identifier generator<br>一款动态可配置化的分布式ID生成器组件</div>**

[<div align="center">![standard-readme compliant](https://img.shields.io/badge/JDK-1.8+-brightgreen.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/SpringBoot-2.3.12.RELEASE-brightgreen.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/Maven-3.8.6-brightgreen.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/Redis-4.0+-pink.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/MySQL-5.6+-orange.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/Caffeine-2.9.3-yellow.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/License-Apache2.0-blue.svg?style=flat-square)</div>](https://github.com/ponderyao/droc-identifier)

## 简介
DRoc-Identifier 是一款动态可配置化的分布式ID生成器组件，支持多种主流的分布式ID生成方式，并给予最大自由度的参数配置支持。

## 特性
- 在允许充分自由的自定义配置规则前提下，提供严格统一的抽象分布式ID类
- 提供**雪花算法分布式ID生成器**，支持**默认、原生、动态**等生成策略
- 动态雪花算法支持区分机房号（数据中心）、时钟回拨处理
- 雪花算法支持机器号（/机房号）的自动注册，默认注册方式为 Redis
- 缓存自增分布式ID生成器支持并推荐模型区分，并允许设置起始偏移量以及递增步长
- 号段模式分布式ID生成器支持并推荐模型区分，并允许设置默认或模型特定的起始偏移量以及取段步长
- 号段模式分布式ID生成器允许模型配置开启动态步长策略，基于给定阈值与吞吐量目标、根据当前取段吞吐量来动态地调整取段步长
- 三种生成器的缓存类型不同，雪花算法基于单例模式，缓存自增基于分布式缓存，号段模式基于Caffeine本地缓存

## 说明
- 目前还未支持多种生成器的混合使用！
- 基于DDD领域驱动设计理念，生成的分布式ID必须是自定义引用类型，不支持生成基础数据类型的ID
- 推荐使用**雪花算法分布式ID生成器**，组件对其特性支持的完整度高于其他生成方式
  - 对雪花算法参数设置不甚了解的入门应用者，推荐选择默认生成策略
  - 对原生64位雪花算法熟悉且习惯使用者，推荐选择原生生成策略
  - 对雪花算法的参数设置有一定理解的高级应用者，推荐选择动态生成策略并自由配置算法的核心参数
- 使用雪花算法分布式ID生成器时，若对机房号、机器号有固定要求，建议关闭自动注册选项，手动设置号码，但注意需人为控制避免重复；若存在未关闭的自动注册选项，则需要添加Redis配置
- 使用缓存自增/号段模式分布式ID生成器时，建议根据数据模型来配置对应的缓存模型
- 使用号段模式分布式ID生成器时，如果对特定数据模型的吞吐量目标不甚明确、对单一取段间隔无较高要求的，不建议开启动态步长策略

## 使用
### Maven引入
```xml
<dependence>
    <groupId>io.github.ponderyao</groupId>
    <artifactId>droc-identifier</artifactId>
    <version>1.3.0</version>
</dependence>
```
### 雪花算法分布式ID生成器
#### 默认生成策略
`默认配置即默认生成策略。只需配置redis即可（详见后述-Redis配置）`
#### 原生生成策略
```yaml
ponder:
  droc:
    snowflake:
      type: native
```
#### 动态生成策略
以下为采用动态生成策略时的配置示例
```yaml
ponder:
  droc:
    snowflake:
      type: dynamic   # 使用动态生成策略
      timestamp-bits: 31   # 时间戳位数
      data-center-id-bits: 6   # 机房号位数
      worker-id-bits: 6   # 机器号位数
      sequence-bits: 10   # 序列号位数
      enable-data-center: true   # 机房号生效
      twepoch: '2022-10-21 14:00:00'   # 起始时间字符串
      enable-timestamp-millisecond: false   # 不启用毫秒级时间戳
      enable-auto-register-data-center: true   # 允许机房号自动注册
      enable-auto-register-worker: true   # 允许机器号自动注册
      auto-register-data-center-way: redis   # 机房号注册方式为 redis
      auto-register-worker-way: redis   # 机器号注册方式为 redis
      auto-register-key-suffix: TEST_DEMO_1_KEY   # 自动注册的键后缀
      data-center-id: 1   # 固定机房号，由于已允许自动注册，该配置无效
      worker-id: 1   # 固定机器号，由于已允许自动注册，该配置无效
      handle-rewind-clock: true   # 启用时钟回拨解决方案
      rewind-clock-reserve: 5   # 时钟回拨预留数
```
#### Redis配置
当且仅当采用动态生成策略的雪花算法分布式ID、且包含以下配置（机房号或机器号的配置如下即为包含）时，可以不依赖Redis配置
```yaml
ponder:
  droc:
    snowflake:
      type: dynamic
      enable-auto-register-data-center: true   # 允许机房号自动注册
      enable-auto-register-worker: true   # 允许机器号自动注册
      auto-register-data-center-way: redis   # 机房号注册方式为 redis
      auto-register-worker-way: redis   # 机器号注册方式为 redis
```
否则，需添加Redis配置
```yaml
spring:
  redis:
    host: ${redis域名或IP}
    port: ${redis端口}
    database: ${redis数据库标识}
    password: ${redis访问密码}
```

### 缓存自增分布式ID生成器
#### 缓存技术选型
`由框架规定缓存可选类型，但不提供默认缓存配置实现，需自行配置。默认采用Redis缓存`

目前已支持的分布式缓存可选类型：
- Redis
#### 自定义缓存自增规则
```yaml
ponder:
  droc:
    type: cache  #必填项，默认是snowflake
    cache:
      type: redis  #默认是redis
      models:
        # 缓存模型列表
        - name: testModel1  #模型名称
          offset: 2  #初始偏移量
          step: 5  #递增步长
        - name: testModel2
          offset: 5
          step: 10
```

### 号段模式分布式ID生成器
#### 数据持久化选型
`由框架规定数据持久化选型，但不提供默认持久化配置实现，需自行配置。默认采用MySQL存储`

目前已支持的持久化存储可选类型：
- MySQL
#### 自定义号段模式配置
```yaml
ponder:
  droc:
    type: segment  #必填项，默认是snowflake
    segment:
      source: mysql  #默认是mysql
      enable-default: true  #是否支持默认匹配
      default-offset: 1  #默认初始偏移量
      default-step: 1000  #默认取段步长
      default-model: default  #默认数据模型
      models:
        # 数据模型集合
        testModel1:
          offset: 1000  #初始偏移量
          step: 1000  #取段步长
          enable-dynamic-step: true  #是否开启动态步长，默认否
          min-step: 100  #最小取段步长阈值
          max-step: 10000  #最大取段步长阈值
          tps: 1000.0  #秒级吞吐量
        testModel2:
          offset: 100
          step: 500
          enable-dynamic-step: true
          min-step: 100
          max-step: 1000
          tps: 0.5
        testModel3:
          offset: 10000
          step: 10000
```

### Java代码
无论采用何种ID生成形式与策略，框架提供统一的抽象DRocId类以供继承实现，使用时请根据数据模型自定义对应的ID类型
```java
@Data
@EqualsAndHashCode(callSuper = true)
public class TestDrocId extends DRocId {
  private static final String TEST_DROC_MODEL = "testDroc";

  //Generate id without model or with default model
  public TestDrocId() {
    super();
    //OR super(TEST_DROC_MODEL);
  }

  //Generate id with custom model
  public TestDrocId(String model) {
    super(model);
  }

  //Build id with validation
  public TestDrocId(Long value) {
    super(value);
    if (!validateValue()) {
      //TODO: throw custom exception for fail parameters validation
      throw new RuntimeException();
    }
  }
}
```
在业务中使用继承了DRocId的分布式ID类有两种可能：
1. ID生成（可选择模型区分）
2. 基于已存在的ID值构造ID对象（会按照配置的生成策略自带校验）
```java
public class TestService {
  public void testFunction() {
    //Generate id without model or with default model
    //ag. snowflake
    TestDrocId testDrocId1 = new TestDrocId();
    
    //Generate id with custom model
    //ag. cache, segment
    TestDrocId testDrocId2 = new TestDrocId("testModel");
    
    //Build id with validation
    TestDrocId testDrocId3 = new TestDrocId(1234567890);
  }
}
```

## 版本
### 最新版本
**1.3.0** 版本：基于雪花算法/缓存自增/号段模式策略的分布式ID生成器
### 发版计划
- *1.3.x* 版本：雪花算法/缓存自增/号段模式分布式ID生成器的后续维护升级
- *2.0.x* 版本：支持多生成器混合使用
