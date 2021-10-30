import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
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
          children: [
            {
              path: '',
              name: 'board-post-list',
              component: () => import('@/components/board/BoardList.vue'),
            },
            {
              path: 'create',
              name: 'board-post-create',
              component: () => import('@/components/board/BoardCreate.vue'),
            },
            {
              path: 'view/:id',
              name: 'board-post-view',
              component: () => import('@/components/board/BoardView.vue'),
            },
            {
              path: 'modify/:id',
              name: 'board-post-modify',
              component: () => import('@/components/board/BoardModify.vue'),
            },
          ],
          redirect: () => {
            return '/board';
          },
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
