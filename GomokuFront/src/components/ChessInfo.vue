<!--
 * @Author: Yixuan Chen 2152824@tongji.edu.cn
 * @Date: 2023-12-16 20:47:28
 * @LastEditors: Yixuan Chen 2152824@tongji.edu.cn
 * @LastEditTime: 2023-12-19 16:49:48
 * @FilePath: \GomokuFront\src\components\ChessInfo.vue
 * @Description: 
 * 
 * Copyright (c) 2023 by YixuanChen 2152824@tongji.edu.cn, All Rights Reserved. 
-->
<script>
// import { ref } from 'vue'

export default {
  props: {
    info: Array
  },
}
</script>

<template>
  <div class="chess-info">
    <a>棋子记录</a>
    <ul>
      <li v-for="(item, index) in info" :key="index">
        {{ item.row }}, {{ item.col }}, {{ item.type }}
      </li>
    </ul>
  </div>
</template>

<style scoped>
.chess-info {
  background-color: rgb(11, 46, 56);
  width: 150px; /* 或者您期望的宽度 */
  height: 80%;
  margin: 0;
  padding: 0 10px;
  box-sizing: border-box;
  overflow-y: auto;
  font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva,
    Verdana, sans-serif;
  font-size: 14px;
  color: rgb(216, 192, 146);
  z-index: 10;
  opacity: 0.7;
  order: 1;
}

ul {
  padding: 0;
  list-style: none;
  margin: 0;
}

li {
  margin: 10px 0;
  line-height: 0.7; /*行高调整 */
}
</style>
