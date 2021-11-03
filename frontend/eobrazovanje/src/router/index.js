import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'

Vue.use(VueRouter)

const routes = [{
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/test',
    name: 'Test',
    component: () =>
      import ('../components/Test.vue')
  },
  {
    path: '/course/create',
    name: 'CreateCourse',
    component: () =>
      import ('../components/course/CreateCourse')
  },
  {
    path: '/question',
    name: 'Question',
    component: () =>
      import ('../components/Question.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router