// Composables
import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    component: () => import('@/layouts/default/Default.vue'),
    children: [
      {
        path: 'home',
        alias: '',
        name: 'Home',
        component: () => import('@/views/Home.vue'),
      },
      {
        path: 'live/:region/:name',
        name: 'Live',
        component: () => import('@/views/Live.vue'),
        props: true
      },
      {
        path: '/summoner/:region/:name',
        name: 'Summoner',
        component: () => import('@/views/Summoner.vue'),
        props: true
      }
    ],
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

router.beforeEach((to, from, next) => {
  if(to.meta.title) {
    document.title = 'LoLMatchup - ' + to.meta.title;
  }

  next();
})

export default router
