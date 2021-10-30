<template>
  <v-container class="pa-5">
    <!-- <h1 class="mt-3">{{ seriesname }}</h1> -->
    <router-view />
  </v-container>
</template>
<script>
import { mapGetters } from 'vuex';

export default {
  data() {
    return {};
  },
  created() {
    let seriesId = this.$route.query.seriesId;
    // this.$store.dispatch('getBoards', boardId);
    if (seriesId) this.$store.dispatch('getSeriesName', seriesId);
  },
  computed: {
    // ...mapGetters(['boards']),
    ...mapGetters(['seriesname']),
  },
};
</script>
<style scoped>
h1 {
  font-family: BMHANNAPro;
}
</style>
