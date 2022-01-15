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
      import ('../components/test/Test.vue')
  },
  {
    path: '/courses/:courseId/tests/:testId',
    props: true,
    name: 'Test',
    component: () =>
      import ('../components/test/Test.vue')
  },
  {
    path: '/courses/create',
    name: 'CreateCourse',
    component: () =>
      import ('../components/course/CreateCourse')
  },
  {
    path: '/tests/:testId',
    props: true,
    name: 'StudentTest',
    component: () =>
      import ('../views/TestForStudent.vue')
  },
  {
    path: '/courses',
    name: 'Courses',
    component: () =>
      import ('../views/Courses.vue')
  },
  {
    path: '/courses/:courseId',
    props: true,
    name: 'Course',
    component: () =>
      import ('../views/Course.vue'),
    children: [{
        path: 'tests',
        name: 'Course.Tests',
        props: true,

        component: () =>
          import ('../components/course/CourseTests.vue')
      },
      {
        path: 'knowledge-spaces',
        props: true,
        name: 'Course.KnowledgeSpaceTable',
        component: () =>
          import ('../components/knowledgeSpace/KnowledgeSpaceTable.vue')
      },
      {
        path: 'knowledge-spaces/compare',
        props: true,
        name: 'Course.CompareKnowledgeSpaces',
        component: () =>
          import ('../components/knowledgeSpace/CompareKnowledgeSpaces.vue')
      },
      {
        path: 'domain',
        props: true,
        name: 'Course.Domain',
        component: () =>
          import ('../components/domain/DomainProblemTable.vue')
      },

    ]
  },
  {
    path: '/diagram/:knowledgeSpaceId',
    props: true,
    name: 'Diagram',
    component: () =>
      import ('../components/knowledgeSpace/DiagramEditor.vue')
  },
]

const router = new VueRouter({
  routes
})

export default router