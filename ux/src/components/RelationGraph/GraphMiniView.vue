<template>
  <div ref="miniView" class="c-mini-graph">
    <div :style="{width:(100 * zoom) + 'px',height:(graphSetting.canvasNVInfo.height * 100/graphSetting.canvasNVInfo.width * zoom)+'px'}" class="c-mini-canvas">
      <template v-for="thisNode in $parent.nodeViewList">
        <div v-if="isAllowShowNode(thisNode)" :key="thisNode.id" :style="{'margin-left':(thisNode.x * 100/graphSetting.canvasSize.width * zoom)+'px','margin-top':(thisNode.y * 100/graphSetting.canvasSize.width * zoom)+'px'}" class="c-mini-node" />
      </template>
    </div>
    <div :style="getPositionData()" class="c-mini-view">
      <i class="el-icon-view" />
    </div>
  </div>
</template>

<script>
import SeeksGraphMath from './core4vue/SeeksGraphMath'
export default {
  name: 'GraphMiniView',
  props: {
    graphSetting: {
      mustUseProp: true,
      default: () => { return {} },
      type: Object
    }
  },
  data() {
    return {
      zoom: 1
    }
  },
  mounted() {
  },
  methods: {
    getPositionData() {
      var _c_width = 100
      var _r = _c_width / this.graphSetting.canvasNVInfo.width
      var _width = this.graphSetting.viewNVInfo.width * _r
      var _height = this.graphSetting.viewNVInfo.height * _r
      var _view_x = (this.graphSetting.viewNVInfo.x - this.graphSetting.canvasNVInfo.x) * _r
      var _view_y = (this.graphSetting.viewNVInfo.y - this.graphSetting.canvasNVInfo.y) * _r
      if (_width > 100) {
        _height = _height * 100 / _width
        _view_x = _view_x * 100 / _width
        _view_y = _view_y * 100 / _width
        this.zoom = 100 / _width
        _width = 100
      } else {
        this.zoom = 1
      }
      // console.log('Mini View style:', _view_center_x, _canvas_center_x)
      var style = {
        width: _width + 'px',
        height: _height + 'px',
        'margin-left': _view_x + 'px',
        'margin-top': _view_y + 'px'
      }
      return style
    },
    isAllowShowNode(nodeData) {
      return SeeksGraphMath.isAllowShowNode(nodeData)
    }
  }
}
</script>

<style scoped>
  .c-mini-graph {
    position: absolute;
    z-index: 999;
    width: 100px;
    height: 100px;
    margin-top: 100px;
    margin-left: 60px;
  }

  .c-fixedLayout {
    position: fixed;
    top: 100px;
  }

  .c-mini-canvas {
    position: absolute;
    background-color: #aacbff;
    border: #7ba8ff solid 1px;
    opacity: 0.8;
  }

  .c-mini-view {
    position: absolute;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 14px;
    color: #fff;
    text-align: center;
    background-color: #f5a565;
    border: #c03639 solid 1px;
    opacity: 0.5;
  }

  .c-mini-node {
    position: absolute;
    width: 2px;
    height: 2px;
    background-color: #000;
    border-radius: 1px;
  }
</style>
