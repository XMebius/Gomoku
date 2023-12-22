<!--
 * @Author: Yixuan Chen 2152824@tongji.edu.cn
 * @Date: 2023-12-12 08:44:56
 * @LastEditors: Yixuan Chen 2152824@tongji.edu.cn
 * @LastEditTime: 2023-12-22 20:31:33
 * @FilePath: \GomokuFront\src\components\ChessBoard.vue
 * @Description: 处理渲染棋盘，并处理棋子的放置
 * 
 * Copyright (c) 2023 by YixuanChen 2152824@tongji.edu.cn, All Rights Reserved. 
-->
<script>
import { ref, onMounted } from 'vue'
import axios from 'axios'
import bakImage from '../assets/bak.jpg'
import blackPiece from '../assets/black.png'
import whitePiece from '../assets/white.png'

export default {
  setup(props, { emit }) {
    const board = ref([...Array(19)].map(() => Array(19).fill(null)))
    const currentPiece = ref('white')
    const canvas = ref(null)
    const isGameStarted = ref(false)

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
      // if (!isGameStarted.value) {
      //   return // 如果游戏未开始，不执行任何操作
      // }
      const ctx = canvas.value.getContext('2d')
      const { x, y } = getCanvasPos(event)
      // 根据点击位置计算棋盘位置
      const col = Math.floor(x / 20)
      const row = Math.floor(y / 20)

      if (board.value[row][col] !== null) {
        // 此处已有棋子，直接返回
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

    const startGame = () => {
      isGameStarted.value = true
      alert('游戏已开始，你执白棋')
    }

    const setDifficulty = difficulty => {
      let difficultyValue
      switch (difficulty) {
        case 'easy':
          difficultyValue = 1
          break
        case 'medium':
          difficultyValue = 2
          break
        case 'hard':
          difficultyValue = 5
          break
        default:
          difficultyValue = 1 // 默认难度
      }

      console.log('难度设置为:', difficultyValue)

      // 发送难度设置到后端
      // axios
      //   .post('http://localhost:8080/api/chess/difficulty', { difficulty: difficultyValue })
      //   .then(response => {
      //     console.log('难度设置成功:', response.data)
      //   })
      //   .catch(error => {
      //     console.error('难度设置失败:', error)
      //   })
    }

    return {
      canvas,
      placePiece,
      placeAIpiece,
      startGame,
      setDifficulty
    }
  },
  methods: {}
}
</script>

<template>
  <div class="chessboard">
    <TitleBlock />
    <canvas ref="canvas" width="380" height="380" @click="placePiece"></canvas>
    <div class="game-controls">
      <button class="large-button" @click="startGame">开始游戏</button>
      <select class="large-button" @change="setDifficulty($event.target.value)">
        <option disabled selected>难度设置</option>
        <option value="easy">容易</option>
        <option value="medium">一般</option>
        <option value="hard">困难</option>
      </select>
    </div>
  </div>
</template>

<style scoped>
.chessboard {
  z-index: 10;
  opacity: 0.72;
  order: 2; /* 显示顺序 */
}
.large-button {
  background-color: rgb(38, 63, 68);
  color: white;
  border: none;
  padding: 15px 30px; /* 增大了 padding 来放大按钮 */
  margin: 10px;
  cursor: pointer;
  font-size: 16px; /* 增大了字体大小 */
}

.game-controls {
  display: flex;
  justify-content: center;
}
.game-controls select.large-button {
  -webkit-appearance: none; /* 移除默认样式 */
  appearance: none;
  background-image: url('data:image/svg+xml;utf8,<svg fill="%23ffffff" height="24" viewBox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg"><path d="M7 10l5 5 5-5z"/></svg>'); /* 添加下拉箭头图标 */
  background-repeat: no-repeat;
  background-position: right 10px top 50%;
}
</style>
