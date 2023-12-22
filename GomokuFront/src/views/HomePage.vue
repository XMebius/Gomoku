<!--
 * @Author: Yixuan Chen 2152824@tongji.edu.cn
 * @Date: 2023-12-05 08:28:14
 * @LastEditors: Yixuan Chen 2152824@tongji.edu.cn
 * @LastEditTime: 2023-12-22 20:27:53
 * @FilePath: \GomokuFront\src\views\HomePage.vue
 * @Description: 渲染出棋盘，实现落子功能，将落子的坐标传给后端
 * 
 * Copyright (c) 2023 by YixuanChen 2152824@tongji.edu.cn, All Rights Reserved. 
-->

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import ChessBoard from '../components/ChessBoard.vue'
import ChessInfo from '../components/ChessInfo.vue'
import BackGround from '../components/BackGround.vue'
import CodeDiagram from '../components/CodeDiagram.vue'
import DevelopNote from '../components/DevelopNote.vue'

const chessInfo = ref([]) // 用于存储棋盘信息
const chessBoardRef = ref(null) // 用于获取棋盘组件的引用

const handlePiecePlaced = async data => {
  // 前端更新棋子信息
  chessInfo.value.push(data)

  // 打包成统一的数据格式
  let sendData = {
    x: data.row,
    y: data.col,
    role: data.type === 'black' ? 2 : 1
  }

  // 发送数据到后端，并等待返回值
  console.log('Sending data:', sendData)
  axios
    .post('http://localhost:8080/api/chess/move', sendData) // 保证端口和java后端一致
    .then(response => {
      // 请求成功，返回响应数据
      console.log('Data sent successfully:', response.data)
      const aiMove = response.datak
      // 处理AI的移动
      if (chessBoardRef.value && aiMove && aiMove.pos) {
        chessBoardRef.value.placeAIpiece(aiMove.pos[0], aiMove.pos[1], 'black')
        console.log('Data sent back successfully', aiMove.pos)
        chessInfo.value.push({ row: aiMove.pos[0], col: aiMove.pos[0], type: 'black' })
      }
    })
    .catch(error => {
      // 抛出错误以便可以在调用函数时捕获
      console.error('Failed to send data:', error)
    })
}
</script>

<template>
  <div>
    <BackGround />
  </div>
  <div class="whole-page-container">
    <CodeDiagram />
    <div class="middle-page-container">
      <ChessInfo :info="chessInfo" />
      <ChessBoard ref="chessBoardRef" @piece-placed="handlePiecePlaced" />
    </div>
    <DevelopNote />
  </div>
</template>

<style scoped lang="scss">
.middle-page-container {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 10px; /* 两列之间的间隔 */
  height: 110vh;
  width: auto;
  margin: 0;
}
.whole-page-container {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 10px; /* 两列之间的间隔 */
  height: 100vh;
  width: 100%;
  margin: 0;
}
canvas {
  border: 1px solid #000;
}
</style>
