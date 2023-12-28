<!--
 * @Author: Yixuan Chen 2152824@tongji.edu.cn
 * @Date: 2023-12-05 08:28:14
 * @LastEditors: Yixuan Chen 2152824@tongji.edu.cn
 * @LastEditTime: 2023-12-28 15:46:48
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
      console.log('Data returned successfully:', response.data)
      const returnMove = response.data
      // 处理AI的移动
      if (returnMove.role === 3) {
        //人类获胜
        alert('You win!')
        chessBoardRef.value.isGameStarted = false
        return
      } else if (returnMove.role === 4 || returnMove.role === 2) {
        //AI获胜
        chessBoardRef.value.placeAIpiece(returnMove.pos[0], returnMove.pos[1], 'black')
        console.log("AI's move:", returnMove.pos)
        chessInfo.value.push({ row: returnMove.pos[0], col: returnMove.pos[1], type: 'black' })
        if (returnMove.role === 4) {
          alert('You lose!')
          chessBoardRef.value.isGameStarted = false
          return
        }
      }
    })
    .catch(error => {
      // 抛出错误以便可以在调用函数时捕获
      console.error('Failed to send data:', error)
    })
}

const restInfo = async () => {
  chessInfo.value = []
}

const handleUndoMove = async () => {
  console.log('Undo move')
  if (chessInfo.value.length < 2) {
    console.log('Not enough moves to undo')
    return
  }
  // chessInfo.value.splice(-2)
  const lastTwoMoves = chessInfo.value.slice(-2).map(move => ({
    x: move.row,
    y: move.col,
    role: move.type === 'black' ? 2 : 1
  }))
  console.log('lastTwoMoves:', lastTwoMoves)
  chessInfo.value.splice(-2)

  try {
    await axios.post('http://localhost:8080/api/chess/undo', lastTwoMoves)
    console.log('Undo move sent to server')
  } catch (error) {
    console.error('Failed to send undo move:', error)
  }
}

window.addEventListener('beforeunload', event => {
  chessBoardRef.value.restartGame()
  event.NONE
})
</script>

<template>
  <div>
    <BackGround />
  </div>
  <div class="whole-page-container">
    <CodeDiagram />
    <div class="middle-page-container">
      <ChessInfo :info="chessInfo" />
      <ChessBoard
        :info="chessInfo"
        ref="chessBoardRef"
        @piece-placed="handlePiecePlaced"
        @reset-chessinfo="restInfo"
        @undo-move="handleUndoMove"
      />
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
