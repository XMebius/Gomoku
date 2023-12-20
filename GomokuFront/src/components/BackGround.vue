<!--
 * @Author: Yixuan Chen 2152824@tongji.edu.cn
 * @Date: 2023-12-16 13:51:39
 * @LastEditors: Yixuan Chen 2152824@tongji.edu.cn
 * @LastEditTime: 2023-12-19 17:54:23
 * @FilePath: \GomokuFront\src\components\BackGround.vue
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
  display: flex;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('../assets/background.jpg');
  background-size: cover;
  background-position: center;
  opacity: 0.94; /* 调整透明度 */
  z-index: -1;
}
</style>
