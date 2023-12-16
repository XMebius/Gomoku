<!--
 * @Author: Yixuan Chen 2152824@tongji.edu.cn
 * @Date: 2023-12-12 08:44:56
 * @LastEditors: Yixuan Chen 2152824@tongji.edu.cn
 * @LastEditTime: 2023-12-16 19:48:55
 * @FilePath: \chess\src\components\ChessBoard.vue
 * @Description: 处理渲染棋盘，并处理棋子的放置
 * 
 * Copyright (c) 2023 by YixuanChen 2152824@tongji.edu.cn, All Rights Reserved. 
-->
<template>
  <div class="chessboard">
    <canvas ref="canvas" width="380" height="380" @click="placePiece"></canvas>
  </div>
</template>

<script>
import { ref, onMounted } from 'vue'
import bakImage from '../assets/bak.jpg'
import blackPiece from '../assets/black.png'
import whitePiece from '../assets/white.png'

export default {
  setup(props, { emit }) {
    const board = ref([...Array(19)].map(() => Array(19).fill(null)))
    const currentPiece = ref('white')
    const canvas = ref(null)

    const drawBoard = ctx => {
      // 背景图
      const img = new Image()
      img.src = bakImage
      img.onload = () => {
        ctx.drawImage(img, 0, 0)
        // 绘制网格
        ctx.beginPath()
        for (let i = 0; i < 19; i++) {
          ctx.moveTo(i * 20 + 10, 10)
          ctx.lineTo(i * 20 + 10, 370)
          ctx.moveTo(10, i * 20 + 10)
          ctx.lineTo(370, i * 20 + 10)
        }
        ctx.stroke()
      }
    }

    const getCanvasPos = event => {
      const rect = canvas.value.getBoundingClientRect()
      const x = event.clientX - rect.left
      const y = event.clientY - rect.top
      return { x, y }
    }

    const placePiece = event => {
      const ctx = canvas.value.getContext('2d')
      const { x, y } = getCanvasPos(event)
      // 根据点击位置计算棋盘位置，你需要根据你的网格大小进行调整
      const col = Math.floor(x / 20)
      const row = Math.floor(y / 20)

      if (board.value[row][col] !== null) {
        // 此处已有棋子，直接返回，不做任何处理
        return
      }

      const pieceImg = new Image()
      pieceImg.src = currentPiece.value === 'white' ? whitePiece : blackPiece
      pieceImg.onload = () => {
        ctx.drawImage(pieceImg, col * 20, row * 20, 20, 20)
        board.value[row][col] = currentPiece.value
        // 发出事件
        emit('piece-placed', { row: row, col: col, type: currentPiece.value })
      }
    }

    const placeAIpiece = (row, col, type) => {
      const img = new Image()
      img.src = blackPiece
      img.onload = () => {
        const ctx = canvas.value.getContext('2d')
        ctx.drawImage(img, col * 20, row * 20, 20, 20)
        board.value[row][col] = type
      }
    }

    onMounted(() => {
      const ctx = canvas.value.getContext('2d')
      drawBoard(ctx)
    })

    return {
      canvas,
      placePiece,
      placeAIpiece
    }
  },
  methods: {}
}
</script>

<style scoped>
.chessboard {
  /* 这里添加样式 */
  z-index: 10;
  opacity: 0.72;
}
</style>
