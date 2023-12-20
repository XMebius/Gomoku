<!--
 * @Author: Yixuan Chen 2152824@tongji.edu.cn
 * @Date: 2023-12-19 14:42:58
 * @LastEditors: Yixuan Chen 2152824@tongji.edu.cn
 * @LastEditTime: 2023-12-19 15:02:55
 * @FilePath: \GomokuFront\src\components\TitleBlock.vue
 * @Description: 
 * 
 * Copyright (c) 2023 by YixuanChen 2152824@tongji.edu.cn, All Rights Reserved. 
-->
<template>
  <div class="title-block">
    <h1>2023年秋季学期人工智能基础</h1>
    <h2>2152824 陈逸轩</h2>
    <a href="https://github.com/XMebius/Gomoku" target="_blank" class="github-link">
      <span class="github-icon"></span>
      github
    </a>
  </div>
</template>

<script></script>

<style scoped>
.title-block {
  text-align: center;
  margin-bottom: 0;
  color: #fff;

  h1 {
    font-size: 24px;
  }

  h2 {
    font-size: 16px;
  }

  a {
    display: block;
    font-size: 14px;
    color: #fff;
    margin: 10px 0;
    text-decoration: none;
  }

  a:hover {
    text-decoration: underline;
  }
}
.github-link {
  display: inline-block;
  font-size: 14px;
  color: #fff;
  margin: 10px 0;
  text-decoration: none;
  padding-left: 30px;
  position: relative;
}

.github-link:hover {
  text-decoration: underline;
}

.github-icon {
  content: '';
  position: absolute;
  left: 43%;
  top: 50%;
  transform: translateY(-50%);
  height: 16px;
  width: 16px;
  background-image: url('../assets/github-mark-white.svg');
  background-size: contain;
  background-repeat: no-repeat;
}
</style>
