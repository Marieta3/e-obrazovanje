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
    path: '/courses/:courseId/new-test',
    props: true,
    name: 'NewTest',
    component: () =>
      import ('../components/Test.vue')
  },
  {
    path: '/courses/:courseId/tests',
    props: true,
    name: 'CourseTests',
    component: () =>
      import ('../components/course/CourseTests.vue')
  },
  {
    path: '/courses/:courseId/tests/:testId',
    props: true,
    name: 'Test',
    component: () =>
      import ('../components/Test.vue')
  },
  {
    path: '/courses/create',
    name: 'CreateCourse',
    component: () =>
      import ('../components/course/CreateCourse')
  },
  {
    path: '/question',
    name: 'Question',
    component: () =>
      import ('../components/Question.vue')
  },
  {
    path: '/course',
    name: 'Courses',
    component: () =>
      import ('../views/Courses.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router