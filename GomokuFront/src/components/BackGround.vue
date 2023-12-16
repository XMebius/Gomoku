<!--
 * @Author: Yixuan Chen 2152824@tongji.edu.cn
 * @Date: 2023-12-16 13:51:39
 * @LastEditors: Yixuan Chen 2152824@tongji.edu.cn
 * @LastEditTime: 2023-12-16 14:01:23
 * @FilePath: \chess\src\components\BackGround.vue
 * @Description: 
 * 
 * Copyright (c) 2023 by YixuanChen 2152824@tongji.edu.cn, All Rights Reserved. 
-->
<script></script>

<template>
  <div class="background-image"></div>
</template>

<style scoped>
.background-image {
  position: fixed; /* 或者 absolute，取决于您的布局需求 */
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('../assets/background.jpg'); /* 替换为您的图片路径 */
  background-size: cover;
  background-position: center;
  opacity: 0.9; /* 调整透明度 */
  z-index: -1;
}
</style>
