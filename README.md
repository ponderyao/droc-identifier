# <div align="center">![DRoc Identifier](https://github.com/ponderyao/onlineImage/raw/main/readme-title/droc-identifier-title.png)</div>

**<div align="center">A dynamic and configurable distributed identifier generator<br>一款动态可配置化的分布式ID生成器组件</div>**

[<div align="center">![standard-readme compliant](https://img.shields.io/badge/JDK-1.8+-brightgreen.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/SpringBoot-2.3.12.RELEASE-brightgreen.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/Maven-3.8.6-brightgreen.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/Redis-5.0-pink.svg?style=flat-square)](https://github.com/ponderyao/droc-identifier)
[![standard-readme compliant](https://img.shields.io/badge/License-Apache2.0-blue.svg?style=flat-square)</div>](https://github.com/ponderyao/droc-identifier)

## 简介
DRoc-Identifier 是一款动态可配置化的分布式ID生成器组件，支持多种主流的分布式ID生成方式，并给予最大自由度的参数配置支持。

## 特性
- 提供**雪花算法分布式ID生成器**，支持**默认、原生、动态**等生成策略
- 动态雪花算法支持区分机房号（数据中心）、时钟回拨处理
- 雪花算法支持机器号（/机房号）的自动注册，默认注册方式为 Redis

## 说明
- 目前还未支持多种生成器的混合使用！
- 推荐使用**雪花算法分布式ID生成器**，组件对其特性支持的完整度高于其他生成方式
  - 对雪花算法参数设置不甚了解的入门应用者，推荐选择默认生成策略
  - 对原生64位雪花算法熟悉且习惯使用者，推荐选择原生生成策略
  - 对雪花算法的参数设置有一定理解的高级应用者，推荐选择动态生成策略并自由配置算法的核心参数
- 使用雪花算法分布式ID生成器时，若对机房号、机器号有固定要求，建议关闭自动注册选项，手动设置号码，但注意需人为控制避免重复
- 使用雪花算法分布式ID生成器时，除非关闭机房号、机器号自动注册选项，否则需要添加Redis配置

## 使用
### Maven引入
```xml
<dependence>
    <groupId>io.github.ponderyao</groupId>
    <artifactId>droc-identifier</artifactId>
    <version>1.1.0</version>
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
### Redis配置
当且仅当采用动态生成策略的雪花算法分布式ID、且包含以下配置（机房号或机器号的配置如下即为包含）时，可以不依赖Redis配置
```yaml
ponder:
  prod:
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

## 版本
### 最新版本
**1.1.0** 版本：实现基于雪花算法策略的分布式ID生成器
### 发版计划
- *1.x* 版本：雪花算法分布式ID生成器的后续维护升级
- *2.x* 版本：提供基于缓存自增策略的分布式ID生成器
- *3.x* 版本：提供基于号段模式策略的分布式ID生成器
