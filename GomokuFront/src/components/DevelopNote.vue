<!--
 * @Author: Yixuan Chen 2152824@tongji.edu.cn
 * @Date: 2023-12-19 16:51:40
 * @LastEditors: Yixuan Chen 2152824@tongji.edu.cn
 * @LastEditTime: 2023-12-19 17:27:05
 * @FilePath: \GomokuFront\src\components\DevelopNote.vue
 * @Description: 
 * 
 * Copyright (c) 2023 by YixuanChen 2152824@tongji.edu.cn, All Rights Reserved. 
-->
<script></script>

<template>
  <div class="develop-note">
    <h1>开发笔记</h1>
    <ul class="directory">
      <li>
        |-- FrontEnd
        <ul>
          <li>
            |--
            <a href="https://github.com/XMebius/Gomoku/blob/main/document/FrontEnd/PageDesign.md"
              >PageDesign.md</a
            >
          </li>
          <li>
            |--
            <a href="https://github.com/XMebius/Gomoku/blob/main/document/FrontEnd/PostData.md"
              >PostData.md</a
            >
          </li>
          <li>
            |--
            <a href="https://github.com/XMebius/Gomoku/blob/main/document/FrontEnd/StoneDesign.md"
              >StoneDesign.md</a
            >
          </li>
          <li>
            |--
            <a
              href="https://github.com/XMebius/Gomoku/blob/main/document/FrontEnd/Vite%26Vue3Env.md"
              >Vite&Vue3Env.md</a
            >
          </li>
          <li>
            `--
            <a href="#https://github.com/XMebius/Gomoku/blob/main/document/FrontEnd/WindowDesign.md"
              >WindowDesign.md</a
            >
          </li>
        </ul>
      </li>
      <li>
        `-- Server
        <ul>
          <li>|-- Algorithm</li>
          <li>
            |--
            <a href="https://github.com/XMebius/Gomoku/blob/main/document/Server/ProcessData.md"
              >ProcessData.md</a
            >
          </li>
          <li>
            `--
            <a href="https://github.com/XMebius/Gomoku/blob/main/document/Server/SpringEnv.md"
              >SpringEnv.md</a
            >
          </li>
        </ul>
      </li>
    </ul>
  </div>
</template>
<style scoped>
.develop-note {
  font-family: 'Times New Roman', Times, serif;
  background-color: transparent;
  color: white;
  font-size: 18px;
  margin-top: 10%;
  margin-left: 5%;
  height: 100%;
}

.directory {
  list-style-type: none;
  padding-left: 0;
}

/* 为一级标题和二级标题之间添加更多间隔 */
.directory > li {
  list-style-type: none;
  margin-bottom: 20px; /* 调整为您需要的间隔 */
}

/* 为二级标题下的项目添加缩进 */
.directory > li > ul {
  list-style-type: none;
  padding-left: 20px; /* 添加缩进 */
}

/* 二级标题项目的样式 */
.directory > li > ul > li {
  margin-bottom: 5px; /* 在项目之间添加间隔 */
  font-family: monospace;
}

/* 直接链接样式 */
.directory a {
  color: white;
}
</style>
