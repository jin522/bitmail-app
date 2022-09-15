import Vue from 'vue'
import Router from 'vue-router'
import MoveCar from '@/components/index/MoveCar'
import MoveCarMsg from '@/components/msg/MoveCarMsg'

Vue.use(Router)

const VueRouterPush = Router.prototype.push
Router.prototype.push = function push(to) {
  return VueRouterPush.call(this, to).catch(err => err)
}

export default new Router({
  routes: [
    {
      path: '/movecar',
      name: 'MoveCar',
      components: {
        default: MoveCar
      },
      meta: {
        requireAuth: false,
        keepAlive: false
      },
    }, {
      path: '/movecarmsg',
      name: 'MoveCarMsg',
      component: MoveCarMsg,
      meta: {
        requireAuth: false,
        keepAlive: false
      },
    },
  ]
})