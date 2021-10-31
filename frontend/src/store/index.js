import Vue from 'vue';
import Vuex from 'vuex';
import createPersistedState from 'vuex-persistedstate';
import breadcrumbs from './breadcrumbs.module';
import profile from './profile.module';
import board from './board.module';
import tab from './tab.module';

import community from './community.module';
// import config from './config.module';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    breadcrumbs,
    profile,
    board,
    community,
    tab,
    // config,
  },
  plugins: [createPersistedState()],
});
