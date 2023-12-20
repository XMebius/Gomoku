<!--
 * @Author: Yixuan Chen 2152824@tongji.edu.cn
 * @Date: 2023-12-19 15:13:49
 * @LastEditors: Yixuan Chen 2152824@tongji.edu.cn
 * @LastEditTime: 2023-12-19 16:50:12
 * @FilePath: \GomokuFront\src\components\CodeDiagram.vue
 * @Description: 
 * 
 * Copyright (c) 2023 by YixuanChen 2152824@tongji.edu.cn, All Rights Reserved. 
-->
<script></script>

<template>
  <div class="code-diagram">
    <div class="title-block">
      <h2>项目代码架构</h2>
    </div>
    <div class="diagrams">
      <img src="../assets/CodeDiagramSingle.svg" alt="CodeDiagram" />
    </div>
  </div>
</template>

<style>
.title-block {
  text-align: center;
  margin-bottom: -15px;
  color: #189, 164, 128;
  h2 {
    font-size: 24px;
  }
}
.diagrams img {
  transform: scale(0.88); /* 缩小到50% */
}
.code-diagram {
  display: flex;
  background-color: transparent;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100vh;
  width: 35%;
  z-index: 9;
}
</style>
