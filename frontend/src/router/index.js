import Vue from 'vue';
import Router from 'vue-router';
import store from '@/store';

Vue.use(Router);

const router = new Router({
  routes: [
    {
      path: '/',
      redirect: '/main',
      component: () => import('@/layouts/MainLayout'),
      children: [
        {
          path: '/main',
          name: 'main',
          component: () => import('@/pages/Main.vue'),
        },
        {
          path: '/board',
          name: 'board',
          component: () => import('@/pages/Board.vue'),
        },
      ],
    },
    {
      path: '/:catchAll(.*)*',
      component: () => import('../pages/Error404.vue'),
    },
  ],

  // #을 제거하기 위해 history 를 모드로 추가한다.
  mode: 'history',
});

export default router;
//탭 메뉴들을 바꾸기 위해 라우터 위치를 저장해둠
router.beforeEach((to, from, next) => {
  store.dispatch('getRouterName', to.name);
  next();
});
